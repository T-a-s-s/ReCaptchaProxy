package br.com.tokiomarine.captcha;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.tokiomarine.captcha.encryption.Encryptor;

public class EcryptionTest {
	
	@Test
	public void encryptionTest() {
		try 
		{
			String key = "Key@TokioMarineC";
			for(int i = 0; i < 1000; i++) 
			{
				String initVector = Encryptor.generateInitVector();
				String encrypted = Encryptor.encrypt(key, initVector, "StringTeste");
				assertTrue("StringTeste".equals(Encryptor.decrypt(key, initVector, encrypted)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
