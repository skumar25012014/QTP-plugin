// (c) Copyright 2012 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package com.hp.application.automation.tools;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;

import be.isabel.uftplugin.tweaker.FailedTests;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Hudson;
import hudson.model.Result;
import hudson.util.ArgumentListBuilder;

//TWEAK:info
//reRunCount in constructor is a tweak

public class AlmToolsUtils {
    
    public static void runOnBuildEnv(
            AbstractBuild<?, ?> build,
            Launcher launcher,
            BuildListener listener,
            FilePath file,
            String paramFileName,
            int reRunCount,
            boolean onlyRunFailedTestsIfSameBuild
            ) throws IOException, InterruptedException {
        
        ArgumentListBuilder args = new ArgumentListBuilder();
        PrintStream out = listener.getLogger();

        //TWEAK: begin
        //check if user is using 'onlyRunFailedTestsIfSameBuild' functionality
        boolean samePackageAlreadyPassed = false;
        if(onlyRunFailedTestsIfSameBuild && FailedTests.isSamePackageAsPrevious(build, listener)){
            //if previous build success, skip executing testcases
            if(FailedTests.isPreviousBuildSuccess(build)){
                samePackageAlreadyPassed = true;
                listener.getLogger().println("[QTP-plugin] Previous build on same package passed => not executing any testcases...");
            }
            //update paramFileName when previous build is the same and it failed => re-execute only failed tests
            else if(FailedTests.isPreviousBuildFailed(build)){ //create new param file
                listener.getLogger().println("[QTP-plugin] Previous build on same package failed => only re-executing failed testcases...");
                ArrayList<String> failedTestPaths = FailedTests.getLastFailedTestsPathsFromConsoleOutput(build.getPreviousBuild());
                paramFileName = FailedTests.writeNewPropsFileToWorkspace(build, failedTestPaths, file, paramFileName, -1);
            //if previous build aborted, act as 'onlyRunFailedTestsIfSameBuild == false' => act as normal
            }else if(FailedTests.isPreviousBuildAborted(build)){//execute as normal
                listener.getLogger().println("[QTP-plugin] Previous build on same package aborted => re-executing all testcases...");
            }
        }
        //TWEAK: end

        // Use script to run the cmdLine and get the console output
        args.add(file);
        args.add("-paramfile");
        args.add(paramFileName);

        // Run the script on node
        // Execution result should be 0
        int returnCode = 0;
        if(!samePackageAlreadyPassed){//TWEAK:if condition added
            returnCode = launcher.launch().cmds(args).stdout(out).pwd(file.getParent()).join();
            listener.getLogger().println("[Plugin-error] Logging immediately after first run");
        }

        //TWEAK begin
        // get parameters of first run for re-use in reruns
        /**
        String ResultFilename = null;
        String timeFirstRun = null;
        FilePath CmdLineExe = null;

        if(reRunCount > 0){

            // begin logging for error
            BufferedReader propsReader = null;
            FileReader propsFileReader = null;
            String propsFilePath = "test";
            listener.getLogger().println("[Plugin-error] reRunCount > 0 - paramFileName = " + paramFileName);
            try{
                propsFilePath = build.getWorkspace().absolutize().toURI().getPath() + paramFileName;
            } catch (NullPointerException e){
                listener.getLogger().println("[Plugin-error] reRunCount > 0 - NullPointerException on executing String propsFilePath = build.getWorkspace().absolutize().toURI().getPath() + paramFileName;");
            } catch (Exception e){
                listener.getLogger().println("[Plugin-error] reRunCount > 0 - Exception on executing String propsFilePath = build.getWorkspace().absolutize().toURI().getPath() + paramFileName;");
            }
            listener.getLogger().println("[Plugin-error] reRunCount > 0 - Before going into FailedTests.getResultFilename, propsFilePath = " + propsFilePath);
            try{
                java.io.File propsFile = new java.io.File(propsFilePath);
                propsFileReader = new FileReader(propsFile);
                propsReader = new BufferedReader(propsFileReader);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

            //get resultFileName from props file
            String line = "";
            String fileContent = "";
            try {
                while((line = propsReader.readLine()) != null){
                    fileContent += " \n" + line;
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            listener.getLogger().println("[Plugin-error] reRunCount > 0 - Content of propsFile = " + fileContent);
            // end logging for error

            ResultFilename = FailedTests.getResultFilename(build, paramFileName);
            timeFirstRun = FailedTests.getTimeStampFromFileName(paramFileName);
            CmdLineExe = file;
        }
        */
        int counter = 0;
        //rerun failed testcases
        while (counter < reRunCount && returnCode == 1){
            ArrayList<String> failedTestPaths = FailedTests.getLastFailedTestsPathsFromConsoleOutput(build);
            String newParamFileName = FailedTests.writeNewPropsFileToWorkspace(build, failedTestPaths, file, paramFileName, counter);
            paramFileName = newParamFileName;
            returnCode = FailedTests.reRun(failedTestPaths.size(), newParamFileName, launcher, listener, file);
            counter++;
        }
        //testcases with 'result == warning' are set to SUCCESS
        if (returnCode == 2) returnCode = 0;
        //String added to Console output for onTrack to make a green ball with orange center
        if(returnCode == 0 && FailedTests.isPreviousBuildFailed(build)){
            listener.getLogger().println("[QTP-plugin] At least one test has rerun !");
        }
        //TWEAK end

        if (returnCode != 0) {
            if (returnCode == 1) {
                build.setResult(Result.FAILURE);
            } else if (returnCode == 2) {
                build.setResult(Result.UNSTABLE);
            } else if (returnCode == 3) {
                build.setResult(Result.ABORTED);
            }
        }
    }
    
    
    public static void runHpToolsAborterOnBuildEnv(
            AbstractBuild<?, ?> build,
            Launcher launcher,
            BuildListener listener,
            String paramFileName) throws IOException, InterruptedException {
        
        ArgumentListBuilder args = new ArgumentListBuilder();
        PrintStream out = listener.getLogger();

        String hpToolsAborter_exe = "HpToolsAborter.exe";
        URL hpToolsAborterUrl = Hudson.getInstance().pluginManager.uberClassLoader.getResource("HpToolsAborter.exe");
        FilePath hpToolsAborterFile =build.getWorkspace().child(hpToolsAborter_exe);
        
        args.add(hpToolsAborterFile);
        args.add(paramFileName);
        
        hpToolsAborterFile.copyFrom(hpToolsAborterUrl);
        
        int returnCode = launcher.launch().cmds(args).stdout(out).pwd(hpToolsAborterFile.getParent()).join();

        try {
        	hpToolsAborterFile.delete();
		} catch (Exception e) {
			 listener.error(e.getMessage());
		}
        
        
        if (returnCode != 0) {
            if (returnCode == 1) {
                build.setResult(Result.FAILURE);
            } else if (returnCode == 2) {
                build.setResult(Result.UNSTABLE);
            } else if (returnCode == 3) {
                build.setResult(Result.ABORTED);
            }
        }
    }
}
