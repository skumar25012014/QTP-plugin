package be.isabel.uftplugin.tweaker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.ParametersAction;
import hudson.model.Result;
import hudson.model.StringParameterValue;
import hudson.util.ArgumentListBuilder;

/**
 * Created by faerts on 8/01/14.
 */
public class FailedTests {

    public static int reRun(
            int countFailedTests,
            String newParamFilename,
            Launcher launcher,
            BuildListener listener,
            FilePath CmdLineExe) throws IOException, InterruptedException{
                PrintStream out = listener.getLogger();
                ArgumentListBuilder args = new ArgumentListBuilder();
                args.add(CmdLineExe);
                args.add("-paramfile");
                args.add(newParamFilename);
                listener.getLogger().println("[QTP-plugin] Rerunning " + countFailedTests + " failed tests");
                int returnCode = launcher.launch().cmds(args).stdout(out).pwd(CmdLineExe.getParent()).join();

                return returnCode;
            }

    public static boolean isSamePackageAsPrevious(AbstractBuild<?, ?> build, BuildListener listener) {
        String packageNumberThis = "This";
        String packageNumberPrevious = "That";
        try{
            packageNumberThis = ((StringParameterValue)((ParametersAction)((CopyOnWriteArrayList) (build.getActions())).get(0)).getParameter("PACKAGE_NUMBER")).value;
            packageNumberPrevious = ((StringParameterValue)((ParametersAction)((CopyOnWriteArrayList) (build.getPreviousBuild().getActions())).get(0)).getParameter("PACKAGE_NUMBER")).value;
        }catch(Exception e){
            listener.getLogger().println("[QTP-plugin] No PACKAGE_NUMBER parameters provided");
        }
        return packageNumberThis.equals(packageNumberPrevious);
    }

    public static boolean isPreviousBuildSuccess(AbstractBuild<?, ?> build) {
        if (build.getPreviousBuild().getResult().equals(Result.SUCCESS)){
            return true;
        }
        else return false;
    }

    public static boolean isPreviousBuildFailed(AbstractBuild<?, ?> build) {
        if (build.getPreviousBuild().getResult().equals(Result.FAILURE)){
            return true;
        }
        else return false;
    }

    public static boolean isPreviousBuildAborted(AbstractBuild<?,?> build) {
        if (build.getPreviousBuild().getResult().equals(Result.ABORTED)){
            return true;
        }
        if (build.getPreviousBuild().getResult().equals(Result.NOT_BUILT)){
            return true;
        }
        else return false;
    }

    public static ArrayList<String> getLastFailedTestsPathsFromConsoleOutput(AbstractBuild<?, ?> build) {
        ArrayList<String> failedTestPaths = new ArrayList<String>();
        File logFile =  build.getLogFile();
        FileReader logFileReader = null;
        BufferedReader logReader = null;
        List<String> logLines = new ArrayList<String>();
        try {
            logFileReader = new FileReader(logFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logReader = new BufferedReader(logFileReader);

        String line;
        try {
            // write log to ArrayList (so we can read it backwards)
            while((line = logReader.readLine()) != null){
                logLines.add(line);
            }
            // read logFile backwards
            for(int i=logLines.size()-1;i>=0;i--) {
                // if last run status is encountered in logFile

                if(logLines.get(i).length() > 9 && logLines.get(i).substring(0, 10).equals("Run status")){
                    int lengthOfRemainingLogFile = i-1;
                    i++;
                    // read Failed tests paths from log
                    for(int j = i;j<logLines.size()-1;j++){
                        if(logLines.get(j).length() > 5 && logLines.get(j).substring(0,6).equals("Failed")){
                        failedTestPaths.add(logLines.get(j).substring(9));
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return failedTestPaths;
    }

    public static String writeNewPropsFileToWorkspace(AbstractBuild<?, ?> build, ArrayList<String> failedTestPaths, FilePath file, String paramFileName, int counter) {
        String workspacePath = file.getParent().toString();
        FileReader propsFileReader = null;
        BufferedReader propsReader = null;
        FileWriter newPropsFileWriter= null;
        BufferedWriter newPropsWriter = null;
        StringBuilder newParamFileName = null;
        try {
            //create props file reader
            String propsFilePath = workspacePath + "\\" + paramFileName;
            java.io.File propsFile = new java.io.File(propsFilePath);
            propsFileReader = new FileReader(propsFile);
            propsReader = new BufferedReader(propsFileReader);

            //create props file writer
            newParamFileName = new StringBuilder(paramFileName);
            if(counter == -1) newParamFileName.insert((paramFileName.length()-4), "_previousBuildFailedTests");
            else if(counter == 0) newParamFileName.insert((paramFileName.length()-4), "_failedTests");
            else newParamFileName.insert((paramFileName.length()-4), "_" + counter);
            String newPropsFilePath = workspacePath + "\\" + newParamFileName;
            newPropsFileWriter = new FileWriter(newPropsFilePath);
            newPropsWriter = new BufferedWriter(newPropsFileWriter);

            //copy props file to new props file (replace tests with '#')
            String line;
            while((line = propsReader.readLine()) != null){
                if(line.length() > 3 && line.substring(0,4).equals("Test")){
                    newPropsWriter.write("#" + "\r\n");
                }
                else{
                    newPropsWriter.write(line + "\r\n");
                }
            }
            //write failed test paths in new props file
            StringBuilder failedTestPathBuilder;
            String failedTestPath;
            for(int i = 0; i < failedTestPaths.size(); i++){
                failedTestPathBuilder = new StringBuilder(failedTestPaths.get(i));
                int indexBackSlash = failedTestPathBuilder.indexOf("\\", 2);
                while(indexBackSlash > -1){
                    failedTestPathBuilder.insert(indexBackSlash, '\\');
                    indexBackSlash = failedTestPathBuilder.indexOf("\\", (indexBackSlash+2));

                }
                failedTestPathBuilder.insert(0,"Test" + (i+1) + "=");
                failedTestPath = failedTestPathBuilder +"";
                newPropsWriter.write(failedTestPath + "\r\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (propsReader != null) propsReader.close();
                if (propsFileReader != null) propsFileReader.close();
                if (newPropsWriter != null) newPropsWriter.close();
                if (newPropsFileWriter != null) newPropsFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newParamFileName.toString();
    }
}
