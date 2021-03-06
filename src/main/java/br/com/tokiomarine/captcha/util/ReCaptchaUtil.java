package br.com.tokiomarine.captcha.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;



public final class ReCaptchaUtil {
	
	private final static Logger log = LoggerFactory.getLogger(ReCaptchaUtil.class);

	private String recaptchaResponse;
	
//	private String secretKeyTeste = " 6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe"; 
	private String secretKey = "6LeIoQQTAAAAAOfRitrbshZeGpYM4Ly6Pfpxu6JU";
	
	private String remoteAddr;
	
	public ReCaptchaUtil(HttpServletRequest request) {
		recaptchaResponse = request.getParameter("g-recaptcha-response");
		remoteAddr = "http://acx.tokiomarine.com.br";
//		remoteAddr = request.getRemoteAddr();
//		secretKey = (String) request.getAttribute("recaptcha_secret_key");
	}
	
	public boolean validate() {
		try {
			URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			configureHttpConnection(urlConnection);
			
			String urlParameters = "&secret="+ secretKey +"&response="+ recaptchaResponse +"&remoteip="+ remoteAddr;
//			String urlParameters = "&secret="+ secretKeyTeste +"&response="+ recaptchaResponse;			
			
			write(urlConnection, urlParameters);
			
			String response = read(urlConnection);
			
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			ReCaptchaResponse reCaptchaResponse = gson.fromJson(response, ReCaptchaResponse.class);
			if(reCaptchaResponse.isSuccess()) {
				return true;
			} else {
				log.error("Erro ao validar reCaptcha: "+ StringUtils.arrayToCommaDelimitedString(reCaptchaResponse.getErrorCodes()));
			}
		} catch (MalformedURLException e) {
			log.error("Erro ao parsear a URL de validação do reCaptcha");
		} catch (ConnectException e) {
			log.error("Erro ao conectar na URL de validação do reCaptcha");
		} catch (IOException e) {
			log.error("Erro ao validar reCaptcha: ", e.getMessage());
		}
		
		return false;
	}
	
	private void configureHttpConnection(final HttpURLConnection urlConnection) throws ProtocolException {
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
	}
	
	private void write(final URLConnection urlConnection, final String params) throws IOException {
		urlConnection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();
	}
	
	private String read(final HttpURLConnection urlConnection) throws ConnectException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		
		if(urlConnection.getResponseCode() != 200) {
			throw new ConnectException("Erro response: Code ["+ urlConnection.getResponseCode() +"]: "+ response.toString());
		}
		
		return response.toString();
	}
	
	
	public final class ReCaptchaResponse {
		
		private boolean success;
		
		@SerializedName("error-codes")
		private String[] errorCodes;

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String[] getErrorCodes() {
			return errorCodes;
		}

		public void setErrorCodes(String[] errorCodes) {
			this.errorCodes = errorCodes;
		}
		
	}

}

