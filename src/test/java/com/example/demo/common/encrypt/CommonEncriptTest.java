package com.example.demo.common.encrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doThrow;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CommonEncriptTest {
	
	/**
	 * 準備
	 */
	@BeforeEach
	void before() {
		
	}

	/**
	 * 暗号化テスト
	 */
	@Test
	void testEncrypt() {
		String testData = "1";
		
		String outputData = CommonEncript.encrypt(testData);
		Assertions.assertEquals(outputData, "2NuZi0LYL18=");
	}
	
	/**
	 * 暗号化テスト
	 */
	@Test
	void testEncrypt_sample() {
		String testData = "0";
		
		String outputData = CommonEncript.encrypt(testData);
		System.out.println(outputData);
		Assertions.assertNotNull(outputData);
	}
	
	/**
	 * 暗号化テスト
	 * (ブランクチェック)
	 */
	@Test
	void testEncrypt_blank() {
		String testData = "";
		
		String outputData = CommonEncript.encrypt(testData);
		Assertions.assertNotNull(outputData);
		Assertions.assertEquals(outputData, "");
	}
	
	/**
	 * 暗号化テスト
	 * (nullチェック)
	 */
	@Test
	void testEncrypt_null() {
		String outputData = CommonEncript.encrypt(null);
		System.out.println(outputData);
		Assertions.assertNotNull(outputData);
		Assertions.assertEquals(outputData, "");
	}
	
	/**
	 * 復号化テスト
	 */
	@Test
	void testDecrypt(){
		String testData = "2NuZi0LYL18=";
		
		String outputData = CommonEncript.decrypt(testData);
		Assertions.assertEquals(outputData, "1");
	}
	
	/**
	 * 復号化テスト
	 * (ブランクチェック)
	 */
	@Test
	void testDecrypt_blank() {
		String testData = "";
		
		String outputData = CommonEncript.decrypt(testData);
		Assertions.assertNotNull(outputData);
		Assertions.assertEquals(outputData, "");
	}
	
	/**
	 * 復号化テスト
	 * (nullチェック)
	 */
	@Test
	void testDecrypt_null() {
		String outputData = CommonEncript.decrypt(null);
		
		Assertions.assertNotNull(outputData);
		Assertions.assertEquals(outputData, "");
	}

}
