package com.messenger.message_service.encryption;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class MessageEncryptor {
    private static final int KEY_SIZE_BITS = 256;

    public static byte[] generateKeyFromMessageAndSenderId(String message, String senderId) {
        String concatenatedData = message + senderId;

        SHA256Digest digest = new SHA256Digest();
        HMac hMac = new HMac(digest);
        byte[] key = new byte[hMac.getMacSize()];

        byte[] inputBytes = concatenatedData.getBytes(StandardCharsets.UTF_8);

        KeyParameter keyParameter = new KeyParameter(inputBytes);
        hMac.init(keyParameter);
        hMac.update(inputBytes, 0, inputBytes.length);
        hMac.doFinal(key, 0);

        return key;
    }

    public static byte[] encrypt(byte[] key, String message, String senderId) throws InvalidCipherTextException {
        AESEngine aesEngine = new AESEngine();
        CFBBlockCipher cfbCipher = new CFBBlockCipher(aesEngine, aesEngine.getBlockSize() * 8);
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cfbCipher, new PKCS7Padding());
        CipherParameters params = new KeyParameter(generateKeyFromMessageAndSenderId(message, senderId));
        cipher.init(true, params);

        byte[] plaintextBytes = message.getBytes(StandardCharsets.UTF_8);

        byte[] output = new byte[cipher.getOutputSize(plaintextBytes.length)];
        int bytesProcessed = cipher.processBytes(plaintextBytes, 0, plaintextBytes.length, output, 0);
        int finalBytes = cipher.doFinal(output, bytesProcessed);

        byte[] result = new byte[bytesProcessed + finalBytes];
        System.arraycopy(output, 0, result, 0, result.length);
        return result;
    }

    public static String decrypt(byte[] key, String encryptedMessage) throws InvalidCipherTextException {
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);

        AESEngine aesEngine = new AESEngine();
        CFBBlockCipher cfbCipher = new CFBBlockCipher(aesEngine, aesEngine.getBlockSize() * 8);
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cfbCipher, new PKCS7Padding());
        CipherParameters params = new KeyParameter(key);
        cipher.init(false, params);

        byte[] output = new byte[cipher.getOutputSize(encryptedMessageBytes.length)];
        int bytesProcessed = cipher.processBytes(encryptedMessageBytes, 0, encryptedMessageBytes.length, output, 0);
        int finalBytes = cipher.doFinal(output, bytesProcessed);

        byte[] resultBytes = new byte[bytesProcessed + finalBytes];
        System.arraycopy(output, 0, resultBytes, 0, resultBytes.length);

        return new String(resultBytes, StandardCharsets.UTF_8);
    }

    public static byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[KEY_SIZE_BITS / 8];
        random.nextBytes(key);
        return key;
    }
}


