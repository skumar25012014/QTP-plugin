package be.isabel.uftplugin.tweaker;

import javax.xml.parsers.ParserConfigurationException;
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
import org.xml.sax.SAXException;

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
                /**
                FileWriter newPropsFileWriter = null;
                FileReader propsFileReader = null;
                BufferedWriter newPropsWriter = null;
                BufferedReader propsReader = null;
                int countFailedTests = 0;
                try {
                    ArrayList <String> failedTestPaths = extractFailedTestPathsFromResultsFile(build, ResultFilename);
                    countFailedTests = failedTestPaths.size();

                    //create props file reader
                    String propsFilePath = build.getWorkspace().absolutize().toURI().getPath() + ParamFileName;
                    java.io.File propsFile = new java.io.File(propsFilePath);
                    propsFileReader = new FileReader(propsFile);
                    propsReader = new BufferedReader(propsFileReader);

                    //create rerun_props file writer
                    ParamFileName = "rerun_"+ retestCount +"_props" + time + ".txt";
                    String newPropsFilePath = build.getWorkspace().absolutize().toURI().getPath() + ParamFileName;
                    newPropsFileWriter = new FileWriter(newPropsFilePath);
                    newPropsWriter = new BufferedWriter(newPropsFileWriter);

                    //copy props file to rerun_props file
                    String line;
                    while((line = propsReader.readLine()) != null){
                        StringBuilder lineBuilder;
                        if(line.length() > 15 && line.substring(0,15).equals("resultsFilename")){
                            lineBuilder = new StringBuilder(line);
                            if(retestCount == 1)lineBuilder.insert(16,"rerun_1_");
                            else lineBuilder.replace(22,23,retestCount+"");
                            ResultFilename = lineBuilder.substring(16, lineBuilder.length());
                            line = lineBuilder.toString() + "\r\n";
                        }
                        //don't copy test paths (only failed test paths are added later)
                        else if ((line.length() > 4) && (line.substring(0,4).equals("Test"))){
                            line = "";
                        }
                        else{
                            line = line + "\r\n";
                        }
                        newPropsWriter.write(line);
                    }

                    //write failed test paths in rerun_props file
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
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    try {
                        if (propsReader != null) propsReader.close();
                        if (propsFileReader != null) propsFileReader.close();
                        if (newPropsWriter != null) newPropsWriter.close();
                        if (newPropsFileWriter != null) newPropsFileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                */
                PrintStream out = listener.getLogger();
                ArgumentListBuilder args = new ArgumentListBuilder();
                args.add(CmdLineExe);
                args.add("-paramfile");
                args.add(newParamFilename);
                listener.getLogger().println("[QTP-plugin] Rerunning " + countFailedTests + " failed tests");
                int returnCode = launcher.launch().cmds(args).stdout(out).pwd(CmdLineExe.getParent()).join();

                return returnCode;
            }

    public static String getTimeStampFromFileName(String paramFileName) {
        String timeStamp = paramFileName.substring(5, (paramFileName.length() - 4));
        return timeStamp;
    }

    public static String getResultFilename(AbstractBuild<?, ?> build, String paramFileName) {
        //create props file reader
        BufferedReader propsReader = null;
        FileReader propsFileReader = null;
        String propsFilePath = null;
        try {
            propsFilePath = build.getWorkspace().absolutize().toURI().getPath() + paramFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            java.io.File propsFile = new java.io.File(propsFilePath);
            propsFileReader = new FileReader(propsFile);
            propsReader = new BufferedReader(propsFileReader);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        //get resultFileName from props file
        String line = "";
        try {
            while((line = propsReader.readLine()) != null){
                if(line.length() > 15 && line.substring(0,15).equals("resultsFilename")){
                    line = line.substring(16);
                    break;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return line;
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

    public static String getResultFileNameFromPreviousBuild(AbstractBuild<?, ?> build) {
        File dir = null;
        try {
            dir = new File(build.getPreviousBuild().getWorkspace().toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File[] files = dir.listFiles();
        if (files.length == 0) {
            return null;
        }

        //initialize lastModifiedFile with a result file
        File lastModifiedFile;
        int counter = 0;
        do{
            lastModifiedFile = files[counter];
            counter++;
        }
        while (!lastModifiedFile.getName().substring(0, 7).contains("Results") && counter < files.length);


        //get last modified result file
        for (int i = 1; i < files.length; i++) {
            if(files[i].getName().contains("Results")){
                if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                    lastModifiedFile = files[i];
                }
            }
        }
        return lastModifiedFile.getName();
    }

    public static String createNewParamFile(AbstractBuild<?, ?> build, String lastResultFileName, String paramFileName) {
        FileReader propsFileReader = null;
        BufferedReader propsReader = null;
        FileWriter newPropsFileWriter= null;
        BufferedWriter newPropsWriter = null;
        StringBuilder newParamFileName = null;
        try {
            ArrayList<String> failedTestPaths = extractFailedTestPathsFromResultsFile(build, lastResultFileName);

            //create props file reader
            String propsFilePath = build.getWorkspace().absolutize().toURI().getPath() + paramFileName;
            java.io.File propsFile = new java.io.File(propsFilePath);
            propsFileReader = new FileReader(propsFile);
            propsReader = new BufferedReader(propsFileReader);

            //create props file writer
            newParamFileName = new StringBuilder(paramFileName);
            newParamFileName.insert((paramFileName.length()-4), "_onlyFailedTests");
            String newPropsFilePath = build.getWorkspace().absolutize().toURI().getPath() + newParamFileName;
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
        } catch (Exception e){
            e.printStackTrace();
        } finally {
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

    private static ArrayList<String> extractFailedTestPathsFromResultsFile(AbstractBuild<?, ?> build, String ResultFilename) throws ParserConfigurationException, IOException, SAXException {
        //extract paths of failed tests from previous results file
        ArrayList <String> failedTestPaths = new ArrayList<String>();
        String resultsFilePath = null;
        try {
            resultsFilePath = build.getWorkspace().absolutize().toURI().getPath() + ResultFilename;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File resultsXmlFile = new File(resultsFilePath);
        javax.xml.parsers.DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = dBuilder.parse(resultsXmlFile);
        doc.getDocumentElement().normalize();
        org.w3c.dom.NodeList nList = doc.getElementsByTagName("testcase");
        int j = 0;
        for (int i = 0; i < nList.getLength(); i++) {
            org.w3c.dom.Node nNode = nList.item(i);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
                if (eElement.getAttribute("status").equals("fail")){
                    failedTestPaths.add(eElement.getAttribute("name"));
                    j++;
                }
            }
        }
        return failedTestPaths;
    }

    public static String getResultsFromPreviousBuild(AbstractBuild<?, ?> build, String lastResultFileName, String paramFileName) {
        FileReader propsFileReader = null;
        BufferedReader propsReader = null;
        FileWriter newPropsFileWriter= null;
        BufferedWriter newPropsWriter = null;
        StringBuilder newParamFileName = null;
        try {
            ArrayList<String> failedTestPaths = extractFailedTestPathsFromResultsFile(build, lastResultFileName);

            //create props file reader
            String propsFilePath = build.getWorkspace().absolutize().toURI().getPath() + paramFileName;
            java.io.File propsFile = new java.io.File(propsFilePath);
            propsFileReader = new FileReader(propsFile);
            propsReader = new BufferedReader(propsFileReader);

            //create props file writer
            newParamFileName = new StringBuilder(paramFileName);
            newParamFileName.insert((paramFileName.length()-4), "_previous_report");
            String newPropsFilePath = build.getWorkspace().absolutize().toURI().getPath() + newParamFileName;
            newPropsFileWriter = new FileWriter(newPropsFilePath);
            newPropsWriter = new BufferedWriter(newPropsFileWriter);

            //copy props file to new props file (replace resultsFileName)
            String line;
            while((line = propsReader.readLine()) != null){
                if(line.length() > 15 && line.substring(0,16).equals("resultsFilename=")){
                    newPropsWriter.write("resultsFilename=" + lastResultFileName + "\r\n");
                }
                else{
                    newPropsWriter.write(line + "\r\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
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
