package com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ConfigTools {

    /*public static void main(String[] args) throws Exception {
        String password = args[0];
        String[] arr = genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + encrypt(arr[0], password));
    }*/

    public static String decrypt(String cipherText) throws Exception {
        return decrypt((String) null, cipherText);
    }

    public static String decrypt(String publicKeyText, String cipherText) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyText);

        return decrypt(publicKey, cipherText);
    }

    public static PublicKey getPublicKey(String publicKeyText) {
        try {
            byte[] publicKeyBytes = Base64.base64ToByteArray(publicKeyText);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
                    publicKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get public key", e);
        }
    }

    public static String decrypt(PublicKey publicKey, String cipherText)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            // 因为 IBM JDK 不支持私钥加密, 公钥解密, 所以要反转公私钥
            // 也就是说对于解密, 可以通过公钥的参数伪造一个私钥对象欺骗 IBM JDK
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
            cipher = Cipher.getInstance("RSA"); //It is a stateful object. so we need to get new one.
            cipher.init(Cipher.DECRYPT_MODE, fakePrivateKey);
        }

        if (cipherText == null || cipherText.length() == 0) {
            return cipherText;
        }

        byte[] cipherBytes = Base64.base64ToByteArray(cipherText);
        byte[] plainBytes = cipher.doFinal(cipherBytes);

        return new String(plainBytes);
    }

    public static String encrypt(String plainText) throws Exception {
        return encrypt((String) null, plainText);
    }

    public static String encrypt(String key, String plainText) throws Exception {
        byte[] keyBytes = Base64.base64ToByteArray(key);
        return encrypt(keyBytes, plainText);
    }

    public static String encrypt(byte[] keyBytes, String plainText)
            throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
        PrivateKey privateKey = factory.generatePrivate(spec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            //For IBM JDK, 原因请看解密方法中的说明
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            Key fakePublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, fakePublicKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.byteArrayToBase64(encryptedBytes);
    }

    public static byte[][] genKeyPairBytes(int keySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[][] keyPairBytes = new byte[2][];

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
        gen.initialize(keySize, new SecureRandom());
        KeyPair pair = gen.generateKeyPair();

        keyPairBytes[0] = pair.getPrivate().getEncoded();
        keyPairBytes[1] = pair.getPublic().getEncoded();

        return keyPairBytes;
    }

    public static String[] genKeyPair(int keySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[][] keyPairBytes = genKeyPairBytes(keySize);
        String[] keyPairs = new String[2];

        keyPairs[0] = Base64.byteArrayToBase64(keyPairBytes[0]);
        keyPairs[1] = Base64.byteArrayToBase64(keyPairBytes[1]);

        return keyPairs;
    }

}
