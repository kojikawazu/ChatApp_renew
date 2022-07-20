package com.example.demo.common.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 暗号化/復号化共通クラス
 * @author nanai
 *
 */
public class CommonEncript {

	/** 暗号化/復号化の秘密鍵 */
	private static final String ENCRYPT_KEY = "encrypt";
	
	/** 暗号化アルゴリズム */
	private static final String ALGORITHM = "BLOWFISH";
	
	/**
	 * 暗号化
	 * @param inData 暗号化したい文字列
	 * @return 暗号化済文字列
	 */
	public static String encrypt(String inData) {
		final String decryptString = inData;
		String outputData = "";
		
		try {
			// 引数チェック
			if(decryptString == null || decryptString.isBlank()) {
				// null, 又はブランク文字は暗号化しない
				return outputData;
			}
			
			// 暗号化
			SecretKeySpec sksSpec = new SecretKeySpec(
					ENCRYPT_KEY.getBytes(), ALGORITHM);
			Cipher cipther = Cipher.getInstance(ALGORITHM);
			cipther.init(Cipher.ENCRYPT_MODE, sksSpec);
			byte[] encriptedBytes = cipther.doFinal(decryptString.getBytes());
			
			// Base64エンコード
			String encryptedString = Base64.encodeBase64String(encriptedBytes);
			
			// 暗号化できました
			outputData = encryptedString;
		} catch (NoSuchAlgorithmException 
				| NoSuchPaddingException
				| InvalidKeyException 
				| IllegalBlockSizeException
				| BadPaddingException ex) {
			// 例外処理
			ex.printStackTrace();
			outputData = "";
		}  
		return outputData;
	}
	
	/**
	 * 復号化
	 * @param inData 復号化したい文字列
	 * @return 復号化済文字列
	 */
	public static String decrypt(String inData) {
		String encryptString = inData;
		String outputData = "";
		
		try {
			// 引数チェック
			if(encryptString == null || encryptString.isBlank()) {
				// null, 又はブランク文字は復号化しない
				return outputData;
			}
			
			// Base64エンコードされた文字列をデコード
			byte[] encryptedBytes = Base64.decodeBase64(encryptString);
			
			// 復号化
			SecretKeySpec sksSpec = new SecretKeySpec(
					ENCRYPT_KEY.getBytes(), ALGORITHM);
			Cipher cipther = Cipher.getInstance(ALGORITHM);
			cipther.init(Cipher.DECRYPT_MODE, sksSpec);
			byte[] decryptedBytes = cipther.doFinal(encryptedBytes);
			
			// 復号化できました
			String decryptedString = new String(decryptedBytes);
			outputData = decryptedString;
		} catch (NoSuchAlgorithmException 
				| NoSuchPaddingException
				| InvalidKeyException 
				| IllegalBlockSizeException
				| BadPaddingException ex) {
			// 例外処理
			ex.printStackTrace();
			outputData = "";
		}
		return outputData;
	}
}
