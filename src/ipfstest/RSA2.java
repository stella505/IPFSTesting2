/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipfstest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.util.DerValue;

public class RSA2
{

    private static String keyPath1 = "/Users/miaoshiwu/ipfsFiles/src/privatekey/";
    private static String keyPath2 = "/Users/miaoshiwu/ipfsFiles/src/publickey/"; 

    private KeyPairGenerator keyPairGen;

    private KeyPair keyPair;

    private RSAPrivateKey privateKey;

    private RSAPublicKey publicKey;

    public void createKey(String id)
    {
        try
        {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(512);
            keyPair = keyPairGen.generateKeyPair();
            // Generate keys
            privateKey = (RSAPrivateKey)keyPair.getPrivate();
            publicKey = (RSAPublicKey)keyPair.getPublic();

            byte[] privateEncode = privateKey.getEncoded();
            try
            {
                createFile(privateEncode, keyPath1+id+"private.txt", true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            byte[] publicEncode = publicKey.getEncoded();
            try
            {
                createFile(publicEncode, keyPath2+id+"public.txt", true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public static String createFile(byte[] desEncode, String keyPath, boolean overWrite) throws IOException
    {

        boolean fileExisted = false;

//        if ((keyPath.indexOf(":\\") > 0) || (keyPath.indexOf(":/") > 0))
//        {
//            //
//        }
//        else
//        {
//            System.out.println("" + keyPath);
//            return "";
//        }

        java.io.File keyFile = new File(keyPath);
        fileExisted = keyFile.exists();
        if (fileExisted && ((overWrite == false) || (keyFile.canWrite() == false)))
        {
            System.out.println("the key file" + keyFile + "has been exist");
            return "";
        }

        if (fileExisted == false)
        {
            if (keyFile.createNewFile() == false)
            {
                System.out.println("create" + keyFile + "fail");
                return "";
            }
        }

        java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(keyPath));
        try
        {
            out.writeObject(desEncode);
        }
        catch (IOException ex)
        {
            return "";
        }
        finally
        {
            out.close();
        }
        return "";
    }


    public RSAPrivateKey getPrivateKey(String keyPath)
    {
        try
        {
            java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(keyPath));
            try
            {
                byte[] desEncodeRead = (byte[])in.readObject();
                in.close();
                DerValue d = new DerValue(desEncodeRead);

                PrivateKey p = RSAPrivateKeyImpl.parseKey(d);
                return (RSAPrivateKey)p;
            }
            catch (ClassNotFoundException ex1)
            {
                ex1.printStackTrace();
            }
            catch (IOException ex2)
            {
                ex2.printStackTrace();
            }

            return null;

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public RSAPublicKey getPublicKey(String keyPath)
    {
        try
        {
            java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(keyPath));
            try
            {
                byte[] desEncodeRead = (byte[])in.readObject();
                in.close();
                DerValue d = new DerValue(desEncodeRead);

                PublicKey p = RSAPublicKeyImpl.parse(d);
                return (RSAPublicKey)p;
            }
            catch (ClassNotFoundException ex1)
            {
                ex1.printStackTrace();
            }
            catch (IOException ex2)
            {
                ex2.printStackTrace();
            }

            return null;

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public void encryptFile(RSAPublicKey publicKey, File file, File newFile)
    {
        try
        {
            InputStream is = new FileInputStream(file);
            OutputStream os = new FileOutputStream(newFile);

            byte[] bytes = new byte[53];
            while (is.read(bytes) > 0)
            {
                byte[] e = this.encrypt(publicKey, bytes);
                bytes = new byte[53];
                os.write(e, 0, e.length);
            }
            os.close();
            is.close();
            System.out.println("write success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void decryptFile(RSAPrivateKey privateKey, File file, File newFile)
    {
        try
        {
            InputStream is = new FileInputStream(file);
            OutputStream os = new FileOutputStream(newFile);
            byte[] bytes1 = new byte[64];
            while (is.read(bytes1) > 0)
            {
                byte[] de = this.decrypt(privateKey, bytes1);
                bytes1 = new byte[64];
                os.write(de, 0, de.length);
            }
            os.close();
            is.close();
            System.out.println("write success");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** */
    /**
     * * Encrypt String. *
     * 
     * @return byte[]
     */
    protected byte[] encrypt(RSAPublicKey publicKey, byte[] obj)
    {
        if (publicKey != null)
        {
            try
            {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return cipher.doFinal(obj);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /** */
    /**
     * * Basic decrypt method *
     * 
     * @return byte[]
     */
    protected byte[] decrypt(RSAPrivateKey privateKey, byte[] obj)
    {
        if (privateKey != null)
        {
            try
            {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return cipher.doFinal(obj);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args)
    {
        RSA2 encrypt = new RSA2();
    
        File file = new File("D:\\a.rar");
        File newFile = new File("D:\\b.rar");
    
        String id = "abc";
        encrypt.createKey(id);
    
        RSAPublicKey publicKey = encrypt.getPublicKey(keyPath2+id+"public.txt");
    
        encrypt.encryptFile(publicKey, file, newFile);
    
        RSAPrivateKey privateKey = encrypt.getPrivateKey(keyPath1+id+"private.txt");
    
        File file1 = new File("/Users/miaoshiwu/ipfsFiles/scr/");
        File newFile1 = new File("D:\\c.rar");
        encrypt.decryptFile(privateKey, file1, newFile1);
    }
}
