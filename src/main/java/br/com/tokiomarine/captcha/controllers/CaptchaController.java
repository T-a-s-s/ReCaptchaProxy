package br.com.tokiomarine.captcha.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tokiomarine.captcha.services.CaptchaService;
import br.com.tokiomarine.captcha.util.ReCaptchaUtil;

@Controller
@RequestMapping("/service")
public class CaptchaController {
	
//	private final String CHAVE_SITE_TESTE = "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI";
	private final String CHAVE_SITE_TOKIO = "6LeIoQQTAAAAAGCYSThUxIwJiXgvMx2wGsfwpsKk";
	private String cryptoInitVector;
	private String cryptoMsg;
 

	
	@Autowired
	CaptchaService captchaService;
	
	@GetMapping("")
	public @ResponseBody String captchaPage(Model model) {
		return "Serviço de Captcha Tokio Marines";
	}
	
	@GetMapping("/{cryptoInitVector}/{cryptoMsg}")
	public String captchaPageUrlDocstore(Model model, 
										 @PathVariable(value="cryptoInitVector") String cryptoInitVector,
										 @PathVariable(value="cryptoMsg") String cryptoMsg) {
		this.cryptoInitVector = cryptoInitVector;
		this.cryptoMsg = cryptoMsg;
		
		model.addAttribute("recaptcha_site_key", CHAVE_SITE_TOKIO);
//		model.addAttribute("recaptcha_site_key", CHAVE_SITE_TESTE);		
		return "captcha";
	}
	
	@PostMapping("/verifyCaptcha")
	public String verifyCaptcha(Model model, final HttpServletRequest request) {
		final ReCaptchaUtil captchaUtil = new ReCaptchaUtil(request);
		if (!captchaUtil.validate()) {
//			model.addAttribute("recaptcha_site_key", CHAVE_SITE_TESTE);
			model.addAttribute("recaptcha_site_key", CHAVE_SITE_TOKIO);
			return "captcha";
		}
		return captchaService.getDoc(cryptoInitVector, cryptoMsg);
	}
	
}
