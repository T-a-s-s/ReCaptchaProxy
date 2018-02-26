package br.com.tokiomarine.captcha;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.tokiomarine.captcha.controllers.CaptchaController;
import br.com.tokiomarine.captcha.encryption.Encryptor;
import br.com.tokiomarine.captcha.services.CaptchaService;

@ContextConfiguration
@WebAppConfiguration
public class CaptchaControllerTest {
	
	MockMvc mvc;
	
	@Mock
	private CaptchaService captchaService;
	
	@InjectMocks
    private CaptchaController captchaController;
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders
				.standaloneSetup(captchaController)
				.build();
	}
	
	@Test
	public void captchaServiceTest() 
	{
		try 
		{
			mvc.perform(get("/service"))
				.andExpect(model().attributeExists("recaptcha_site_key"))
				.andExpect(status().is(HttpStatus.OK.value()));
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void urlDocstoreTest() 
	{
		try 
		{
			String urlDocstore = "http://docstore-aceitev2.tokiomarine.com.br/docstore-services/rest/download-anexo/2ac35ae1-966d-4ae6-ad54-b52e756339a2";
			String key = "Key@TokioMarineC";
			String initVector = Encryptor.generateInitVector();
			String cryptoInitVector = Encryptor.encrypt(key, initVector, initVector);
			String cryptoUrlDocstore = Encryptor.encrypt(key, initVector, urlDocstore); 
			
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("/service")
					  .append("/")
					  .append(cryptoInitVector)
					  .append("/")
					  .append(cryptoUrlDocstore);
			mvc.perform(get(urlBuilder.toString()))
				.andExpect(model().attributeExists("recaptcha_site_key"))
				.andExpect(status().is(HttpStatus.OK.value()));
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
