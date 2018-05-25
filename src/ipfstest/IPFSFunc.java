/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipfstest;
import java.io.BufferedReader;  
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import static java.lang.Thread.sleep;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import static javax.xml.crypto.dsig.Transform.BASE64;
import javax.crypto.Cipher;
import javax.swing.JOptionPane;
/**
 *
 * @author miaoshiwu
 */
public class IPFSFunc {
    private static String keyPath1 = "/Users/miaoshiwu/ipfsFiles/src/privatekey/"; // 私钥文件地址
    private static String keyPath2 = "/Users/miaoshiwu/ipfsFiles/src/publickey/"; // 公钥文件地址

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
     public void IPFSbootstrap() throws IOException, InterruptedException{
         String path = "/usr/local/bin/ipfs";
         String[] cmd = {path,"bootstrap"};
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
     
     public boolean IPFSAddFile(String id,String filePath) throws IOException, Exception{
         //TestDES td = new TestDES(id);
         RSA2 r2 = new RSA2();
         Exception e = null;
         Error er = null;
         File file = new File(filePath);
         File newFile = new File(filePath+"cryp");
         r2.createKey(id);
         RSAPublicKey publicKey = r2.getPublicKey(keyPath2+id+"public.txt");
         
         r2.encryptFile(publicKey, file, newFile);
            String path = "/usr/local/bin/ipfs";
//            Runtime run = Runtime.getRuntime();
            try {
                ProcessBuilder pb = new ProcessBuilder(path, "add", newFile.getPath());
//                pb.directory();
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                
                Process p = pb.start();
            } catch (Exception ex) {
                e = ex;
                e.printStackTrace();
            }catch (Error ex) {
            er = ex;
        }
        if (e==null && er==null) {
            return true;
        }else{
        return false;
        }
     }
//     public void IPFSAddFile(String id,String filePath) throws IOException, Exception{
//         //TestDES td = new TestDES(id);
//         TestDES td = new TestDES();
//         String  result1= td.encryptBASE64(id.getBytes());
//         
//         td.encrypt(id,filePath,filePath+"cryp");
//            String path = "/usr/local/bin/ipfs";
////            Runtime run = Runtime.getRuntime();
//            try {
//                ProcessBuilder pb = new ProcessBuilder(path, "add", filePath+"cryp");
////                pb.directory();
//                pb.redirectOutput(Redirect.INHERIT);
//                pb.redirectError(Redirect.INHERIT);
//                Process p = pb.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//     }
     public void IPFSCatFile(String id,String fileHash, String filename) throws IOException, Exception{
         RSA2 r2 = new RSA2();
         // ipfs cat hashcode > /filepath/name
         String path = "/usr/local/bin/ipfs";
            String oldFilePath = filename + "cryp";
            String newFilePath = filename;
            String[] cmd = {path,"cat",fileHash,">",oldFilePath};
            Runtime run = Runtime.getRuntime();
            try {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                Process p = pb.start();
            } catch (Exception e) {
                e.printStackTrace();
            }        
            RSAPrivateKey privateKey = r2.getPrivateKey(keyPath1+id+"private.txt");
            System.out.println(privateKey);
            File file1 = new File(oldFilePath);
            File newFile1 = new File(newFilePath);
            r2.decryptFile(privateKey, file1, newFile1);
            System.out.println("the decrypt file is:" + newFilePath);
     }
     public String IPFSgetFile(String id,String fileHash, String filename) throws IOException, Exception{
         RSA2 r2 = new RSA2();
         
         // ipfs cat hashcode > /filepath/name
            String path = "/usr/local/bin/ipfs";
            String oldFilePath = "/Users/miaoshiwu/NetBeansProjects/IPFSTest2/"+fileHash;
            String newFilePath = "/Users/miaoshiwu/ipfsFiles/src/"+filename;
            String[] cmd = {path,"get",fileHash};
            Runtime run = Runtime.getRuntime();
            try {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                Process p = pb.start();
                sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            RSAPrivateKey privateKey = r2.getPrivateKey(keyPath1+id+"private.txt");
            System.out.println(privateKey);
            File file1 = new File(oldFilePath);
            File newFile1 = new File(newFilePath);
            r2.decryptFile(privateKey, file1, newFile1);
            System.out.println("the decrypt file is:" + newFilePath);
            return newFilePath;
     }
//     public void IPFSCatFile(String id,String fileHash, String name) throws IOException, Exception{
//         TestDES td = new TestDES(id);
//            String path = "/usr/local/bin/ipfs";
//            String oldFilePath = "/Users/miaoshiwu/ipfsFiles/src/";
//            String newFilePath = "/Users/miaoshiwu/ipfsFiles/src/"+name;
//            String[] cmd = {path,"cat",fileHash, ">",oldFilePath};
//            Runtime run = Runtime.getRuntime();
//            try {
//                ProcessBuilder pb = new ProcessBuilder(cmd);
//                //
////                pb.directory();
////                pb.redirectOutput(oldFilePath);
//                pb.redirectOutput(Redirect.INHERIT);
//                pb.redirectError(Redirect.INHERIT);
//                Process p = pb.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            td.decrypt(oldFilePath+fileHash, newFilePath);
//            System.out.println("the decrypt file is:" + newFilePath);
//     }
     public void IPFSBitswap(String nodeId) throws IOException, InterruptedException{
         nodeId = "QmePGExj3Yn367EtF4Z1v12Sn42tmh1XhPpqidRAq37oHr";
         String path = "/usr/local/bin/ipfs";
         String[] cmd = {path,"bitswap","ledger",nodeId};
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
}
