/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipfstest;
import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
/**
 *
 * @author miaoshiwu
 */
public class IPFSFunc {
    public static void IPFSDaemontest(){
            String path = "/usr/local/bin/ipfs";
            //Runtime run = Runtime.getRuntime();
            try {
                //run.exec("cmd /k shutdown -s -t 3600");
                //Process process = run.exec("cmd.exe /k start " + path);

//                String myFile = "/Users/miaoshiwu/ipfsFiles/doc.txt";
//                String myCommand = "cp -R '" + myFile + "' $HOME 2> errorlog";
//                String myCommand = "ipfs daemon & 2> errorlog";
//                Runtime.getRuntime().exec(new String[] { "bash", "-c", myCommand });
                Process process = Runtime.getRuntime().exec(path + "ipfs daemon");
                InputStream in = process.getInputStream();
                while (in.read() != -1) {
                    System.out.println(in.read());
                }
                in.close();
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }}
     public void IPFSDaemon() throws IOException{
            String path = "/usr/local/bin/ipfs";
            Runtime run = Runtime.getRuntime();
            try {
                ProcessBuilder pb = new ProcessBuilder(path, "daemon");
//                pb.directory();
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                Process p = pb.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
     public void IPFSDisconnect() throws IOException{
            String path = "/usr/local/bin/ipfs";
            Runtime run = Runtime.getRuntime();
            try {
                ProcessBuilder pb = new ProcessBuilder(path, "shutdown");
//                pb.directory();
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                Process p = pb.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     public void ConnectIntoPN() throws IOException{
     
     }
     public void IPFSwarmpeers() throws IOException, InterruptedException{
         String path = "/usr/local/bin/ipfs";
         String[] cmd = {path,"swarm","peers"};
//         Process pb = Runtime.getRuntime().exec(cmd);
//         pb.waitFor();
         try{
             ProcessBuilder pb = new ProcessBuilder(cmd);
             pb.redirectOutput(Redirect.INHERIT);
             pb.redirectError(Redirect.INHERIT);
             Process p = pb.start();
         }catch (Exception e) {
                e.printStackTrace();
         }
     }
     public void ShowIPFSInfo() throws IOException{
         String path = "/usr/local/bin/ipfs";
         Runtime run = Runtime.getRuntime();
         try{
             ProcessBuilder pb = new ProcessBuilder(path, "id");
             pb.redirectOutput(Redirect.INHERIT);
             pb.redirectError(Redirect.INHERIT);
             Process p = pb.start();
         }catch (Exception e) {
                e.printStackTrace();
         }
     }
     public void IPFSAddFile(String id,String filePath) throws IOException, Exception{
         TestDES td = new TestDES(id);
         td.encrypt(filePath,filePath+"cryp");
            String path = "/usr/local/bin/ipfs";
            Runtime run = Runtime.getRuntime();
            try {
                ProcessBuilder pb = new ProcessBuilder(path, "add", filePath+"cryp");
//                pb.directory();
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                Process p = pb.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
     }
     public void IPFSCatFile(String id,String fileHash, String name) throws IOException, Exception{
         TestDES td = new TestDES(id);
            String path = "/usr/local/bin/ipfs";
            String oldFilePath = "/Users/miaoshiwu/ipfsFiles/src/";
            String newFilePath = "/Users/miaoshiwu/ipfsFiles/src/"+name;
            String[] cmd = {path,"cat",fileHash, ">",oldFilePath};
            Runtime run = Runtime.getRuntime();
            try {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                //
//                pb.directory();
//                pb.redirectOutput(oldFilePath);
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                Process p = pb.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            td.decrypt(oldFilePath+fileHash, newFilePath);
            System.out.println("the decrypt file is:" + newFilePath);
     }

}
