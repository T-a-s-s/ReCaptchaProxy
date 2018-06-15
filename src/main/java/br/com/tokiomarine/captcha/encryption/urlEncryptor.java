package br.com.tokiomarine.captcha.encryption;

public class urlEncryptor {
	
	public static void main (String...args) {
		
		String s = "http://url.destino/navegar/2ac35ae1-966d-4ae6-ad54-b52e756339a2";
		String secretKey = "Key@TomasAndersS";
		String initVector = Encryptor.generateInitVector();
		String sCrypt = "";
		try {
			sCrypt = Encryptor.encrypt(secretKey, initVector, s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sCrypt = " + sCrypt);
		System.out.println("initVector = " + initVector);
		
	}

}
