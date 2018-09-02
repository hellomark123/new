package com.seo.utils.security;


import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

import javax.crypto.Cipher;


import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密解密工具类
 **/

public class RSAUtil {

    /**
     * 私钥字符串
     */
    private static String PRIVATE_KEY ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIL+VTwlCZQD xgErWxps+Jol9HNNgMOK54fujxiw5ZC9MqK0SqMvGZJ5mJwrRO+uF9dTLaXJ CZrEHuDO2G/kfQUC7f0BFMT6tqLZhrq+3j9tzX3DSr16P6yN0BD+xTzaJ+3U 3xt9Nryn7YWKZll1dwvKjshvvLUCo2kEtu/4I42lAgMBAAECgYB9tGnBFN3T L6fUuXFss4QCEUg7WfWmMOcs1+50SFWUhytPXep+HJXSrWOZAiDeTNfOJ6EX hgv0fB4RqA2lmsOpKpLBNQs8YlQU9bEKLhfyoX0abs/6rYZ+3EmzQiXPnWoS srsEk+tybiYnZhFdQB9K9ukhnOxRHlGBlCEi6CUboQJBAMCj1bSuuv9zs3YA CHgmXFPGXY0za6+U3P6HmzB2bOMcBmoSaFcgEV/CLVL+gXNRT7hfz1ufHhIl W1T65X4fGn0CQQCuE+b+MZSMX8cahnLYNPOLe5zVyvNAXr9IUr1464M8Cg0p /3MxWw6D2mqDOGaA7RKMeFimgz0kDi3m89b0aQBJAkEAhi/ZhhGMmrKqmJCv VZpLB+QOeHQQF+9W/UnSlTB46r4zzaFi4wWodI6a6cv0ujIymNjMZSLyXGt/ MjPOtkrONQJAXGMXr0TlD/MtGnjMrs67iwWZ4SWLIFiosFupT2m/BrhW8Dkx C7rF41iiOu5OCJLJy197vTPd2SrlVbOIG8M2cQJBAJEY5oYDZREl46wE9LgX d/Czqnlhx9lqGyT6Odj319NUPvCvEvjgqUe0OdWgtIOelUlPAQlFLNVuwTqk 1HjCEG0=";
    /**
     * 公钥字符串
     */
    private static String PUBLIC_KEY ="";


    public static final String KEY_ALGORITHM = "RSA";


    /**
     * 读取密钥字符串
     * @throws Exception
     */

    public static void convert() throws Exception {
        byte[] data = null;

        try {
            InputStream is = RSAUtil.class.getResourceAsStream("/enc_pri");
            int length = is.available();
            data = new byte[length];
            is.read(data);
        } catch (Exception e) {
        }

        String dataStr = new String(data);
        try {
            PRIVATE_KEY = dataStr;
        } catch (Exception e) {
        }

        if (PRIVATE_KEY == null) {
            throw new Exception("Fail to retrieve key");
        }
    }



    /**
     * 私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
//        convert();
        byte[] keyBytes = Base64Util.decode(PRIVATE_KEY);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//        Key privateKey = makePrivateKey(PRIVATE_KEY);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = Base64Util.decode(key);
        X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(pkcs8KeySpec);

//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     *  引入第三方密码工具包 处理编码
     * @param stored
     * @return
     * @throws GeneralSecurityException
     * @throws Exception
     */
    public static PrivateKey makePrivateKey(String stored) throws GeneralSecurityException, Exception {
        /*byte[] data = Base64.getDecoder().decode(stored);
        PKCS8EncodedKeySpec spec = new  PKCS8EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePrivate(spec);*/
        byte[] data = Base64Util.decode(stored);
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(0));
        ASN1EncodableVector v2 = new ASN1EncodableVector();
        v2.add(new ASN1ObjectIdentifier(PKCSObjectIdentifiers.rsaEncryption.getId()));
        v2.add(DERNull.INSTANCE);
        v.add(new DERSequence(v2));
        v.add(new DEROctetString(data));
        ASN1Sequence seq = new DERSequence(v);
        byte[] privKey = seq.getEncoded("DER");
        PKCS8EncodedKeySpec spec = new  PKCS8EncodedKeySpec(privKey);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey key = fact.generatePrivate(spec);

        return key;

    }


    public static void main(String[] args) throws Exception {
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
//        keyPairGen.initialize(1024);
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//        PrivateKey privateKey = keyPair.getPrivate();
//        PublicKey publicKey = keyPair.getPublic();
//        System.out.println(Base64Util.encode(privateKey.getEncoded()));
//        System.out.println(Base64Util.encode(publicKey.getEncoded()));
//        convert();
        byte[] enR = encryptByPublicKey("hahaha".getBytes("utf-8"),PUBLIC_KEY);
        System.out.println("enr" + enR.toString());
        byte[] result = decryptByPrivateKey(enR);
        System.out.println(new String(result,"utf-8"));


    }

}
