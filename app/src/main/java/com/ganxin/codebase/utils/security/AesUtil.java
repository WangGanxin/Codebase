package com.ganxin.codebase.utils.security;

import com.ganxin.codebase.utils.bitmap.ByteUtil;

import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description : AES  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class AesUtil {

    public static String encrypt(final String seed, final String cleartext)
            throws Exception {

        byte[] rawKey = getRawKey(seed.getBytes());

        byte[] result = encrypt(rawKey, URLEncoder.encode(cleartext, "UTF-8")
                .getBytes("UTF-8"));

        return ByteUtil.bytesToHex(result);

    }

    public static String decrypt(final String seed, final String encrypted)
            throws Exception {

        byte[] rawKey = getRawKey(seed.getBytes());

        byte[] enc = ByteUtil.hexStringToBytes(encrypted);

        byte[] result = decrypt(rawKey, enc);

        String urlEncoded = new String(result, "UTF-8");

        return urlEncoded;

    }

    private static byte[] getRawKey(final byte[] seed) throws Exception {

        byte[] keyPtr = new byte[KeySizeAES128];

        IvParameterSpec ivParam = new IvParameterSpec(keyPtr);
        for (int i = 0; i < KeySizeAES128; i++) {

            if (i < seed.length) {
                keyPtr[i] = seed[i];
            } else {
                keyPtr[i] = 0;
            }
        }

        return keyPtr;

    }

    private static String TYPE = "AES";
    private static int KeySizeAES128 = 16;

    private static Cipher getCipher(final int mode, final String key) {

        Cipher mCipher;

        byte[] keyPtr = new byte[KeySizeAES128];

        IvParameterSpec ivParam = new IvParameterSpec(keyPtr);

        byte[] passPtr = key.getBytes();

        try {

            mCipher = Cipher.getInstance(TYPE + "/CBC/PKCS5Padding");

            for (int i = 0; i < KeySizeAES128; i++) {

                if (i < passPtr.length) {
                    keyPtr[i] = passPtr[i];
                } else {
                    keyPtr[i] = 0;
                }

            }

            SecretKeySpec keySpec = new SecretKeySpec(keyPtr, TYPE);

            mCipher.init(mode, keySpec, ivParam);

            return mCipher;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static byte[] encrypt(final byte[] raw, final byte[] clear)
            throws Exception {

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));

        byte[] encrypted = cipher.doFinal(clear);

        return encrypted;

    }

    private static byte[] decrypt(final byte[] raw, final byte[] encrypted)
            throws Exception {

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));

        byte[] decrypted = cipher.doFinal(encrypted);

        return decrypted;

    }


}
