package com.blockchain.demo.util;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import io.micrometer.common.util.StringUtils;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

@Slf4j
@UtilityClass
public class CryptoUtil {

    public static final String ENC_KEY = "wRqw12KbgDE5y4SGYRHPZbXJPPN5XpeV";
    private static final String algorithm = "AES/CBC/PKCS5Padding";
    private static final String AES = "AES";
    public static byte[] ivBytes = {0x21, 0x31, 0x20, 0x33, 0x10, 0x21, 0x19, 0x30, 0x03, 0x07, 0x20, 0x01, 0x12, 0x50,
        0x27, 0x12};
    public static String CHARSET = "UTF-8";

    public static String encrypt(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        
        try {
            byte[] textBytes = str.getBytes(CHARSET);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(ENC_KEY.getBytes(CHARSET), AES);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            return Base64.encodeBase64String(cipher.doFinal(textBytes));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorCodeException(ErrorCode.FAILED_ENCRYPT_OR_DECRYPT);
        }
    }

    //AES256 복호화
    public static String decrypt(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        try {
            byte[] textBytes = Base64.decodeBase64(str);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(ENC_KEY.getBytes(CHARSET), AES);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

            return new String(cipher.doFinal(textBytes), CHARSET);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorCodeException(ErrorCode.FAILED_ENCRYPT_OR_DECRYPT);
        }
    }
}