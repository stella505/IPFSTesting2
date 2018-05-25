/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipfstest;

/**
 *
 * @author miaoshiwu
 */
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.security.Key;  
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.SecureRandom;  
import java.security.Security;
  
import javax.crypto.Cipher;  
import javax.crypto.CipherInputStream;  
import javax.crypto.CipherOutputStream;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
  
public class TestDES {   
  Key key;   
  KeyPair keypair; 
  public TestDES(String str) {   
    getKey(str); 
  }   
  public TestDES(){}
//  public void getKey(String strKey) {   
//    try {   
//        KeyGenerator _generator = KeyGenerator.getInstance("DES");   
//        _generator.init(new SecureRandom(strKey.getBytes()));   
//        this.key = _generator.generateKey();   
//        _generator = null;   
//    } catch (Exception e) {   
//        throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);   
//    }   
//  }   
     public void getKey(String strKey) {   
        try {   
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                byte  b[] = strKey.getBytes();
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
                secureRandom.setSeed(b);
                
        KeyGenerator _generator = KeyGenerator.getInstance("DES");   
        _generator.init(512,secureRandom); 
        this.key = _generator.generateKey();  
        _generator = null;   
    } catch (Exception e) {   
        throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);   
    }   
  } 

     private static KeyPair initKey(String id) {
                    try {
                            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                            byte  b[] = id.getBytes();
                             SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
                             secureRandom.setSeed(b);

                            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA","BC");
                            generator.initialize(512,secureRandom);
                            return generator.generateKeyPair();
                    } catch (Exception e) {
                            throw new RuntimeException(e);
                    }
            }
     public void encrypt(String id, String file, String destFile) throws Exception {   
    Cipher cipher = Cipher.getInstance("DES");   
    // cipher.init(Cipher.ENCRYPT_MODE, getKey());   
    cipher.init(Cipher.ENCRYPT_MODE, this.key);   
    InputStream is = new FileInputStream(file);   
    OutputStream out = new FileOutputStream(destFile);   
    CipherInputStream cis = new CipherInputStream(is, cipher);   
    byte[] buffer = new byte[1024];   
    int r;   
    while ((r = cis.read(buffer)) > 0) {   
        out.write(buffer, 0, r);   
    }   
    cis.close();   
    is.close();   
    out.close();   
  } 
//  public void encrypt(String file, String destFile) throws Exception {   
//    Cipher cipher = Cipher.getInstance("DES");   
//    // cipher.init(Cipher.ENCRYPT_MODE, getKey());   
//    cipher.init(Cipher.ENCRYPT_MODE, this.key);   
//    InputStream is = new FileInputStream(file);   
//    OutputStream out = new FileOutputStream(destFile);   
//    CipherInputStream cis = new CipherInputStream(is, cipher);   
//    byte[] buffer = new byte[1024];   
//    int r;   
//    while ((r = cis.read(buffer)) > 0) {   
//        out.write(buffer, 0, r);   
//    }   
//    cis.close();   
//    is.close();   
//    out.close();   
//  }   
  
  public void decrypt(String file, String dest) throws Exception {   
    Cipher cipher = Cipher.getInstance("DES");   
    cipher.init(Cipher.DECRYPT_MODE, this.key);   
    InputStream is = new FileInputStream(file);   
    OutputStream out = new FileOutputStream(dest);   
    CipherOutputStream cos = new CipherOutputStream(out, cipher);   
    byte[] buffer = new byte[1024];   
    int r;   
    while ((r = is.read(buffer)) >= 0) {   
        System.out.println();  
        cos.write(buffer, 0, r);   
    }   
    cos.close();   
    out.close();   
    is.close();   
  }   
  public static byte[] decryptBASE64(String key) throws Exception {   
        return (new BASE64Decoder()).decodeBuffer(key);   
    }   
public static String encryptBASE64(byte[] key) throws Exception {   
        return (new BASE64Encoder()).encodeBuffer(key);   
    } 
//  public static void main(String[] args) throws Exception {   
//    TestDES td = new TestDES("aaa");   
//    td.encrypt("e:/r.txt", "e:/ren.txt");   
//    td.decrypt("e:/rde.txt", "e:/r1.txt");   
//      
//  }   
    static {
    System.out.println(System.getProperty("java.version"));
    for (Provider provider : Security.getProviders())
      System.out.println(provider);
  }

  public static void AESTest() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(128);
    SecretKey secretKey = keyGenerator.generateKey();
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
  }

}  
