package br.com.tokiomarine.captcha.services;

public interface CaptchaService 
{

	String getDoc(String initVector, String msg);

}
