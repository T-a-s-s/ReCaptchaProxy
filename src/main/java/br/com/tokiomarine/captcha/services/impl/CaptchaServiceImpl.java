package br.com.tokiomarine.captcha.services.impl;

import org.springframework.stereotype.Service;

import br.com.tokiomarine.captcha.encryption.Encryptor;
import br.com.tokiomarine.captcha.services.CaptchaService;

@Service
public class CaptchaServiceImpl implements CaptchaService 
{

	private final String  secretKey = "Key@TokioMarineC";
	
	@Override
	public String getDoc(String initVector, String msg) {
		String url = null;
		try {
			url = Encryptor.decrypt(secretKey, initVector, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + url;
	}

}
