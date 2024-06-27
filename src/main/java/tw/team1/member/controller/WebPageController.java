package tw.team1.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tw.team1.member.model.Member;
import tw.team1.member.model.VerificationToken;
import tw.team1.member.model.VerificationTokenRepository;
import tw.team1.member.service.MembersService;

@Controller
public class WebPageController {

	@GetMapping(value = {"/", "/index"})
    public String index() {
		return "index";
	}
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@GetMapping("/member")
	public String member() { return "member";}


	@GetMapping("/forgetpassword")
	public String forgetPassword() {
		return "resetpassword";
	}

	@Autowired
	private VerificationTokenRepository tokenRepository;
	@Autowired
	private MembersService membersService;

	@GetMapping("/verify/{token}")
	public String verifyMember(@PathVariable String token) {
		// 獲取會員實體,例如從請求體或會話中獲取
		VerificationToken verificationToken = tokenRepository.findByToken(token);
		Member member = verificationToken.getMember();
		Boolean result = membersService.verifyMember(token, member);
		return result ? "register" : "errortoken";
	}

	@GetMapping("/verifyResetPassword/{token}")
	public String verifyResetPassword(@PathVariable String token) {
		// 獲取會員實體,例如從請求體或會話中獲取
		VerificationToken verificationToken = tokenRepository.findByToken(token);
		Member member = verificationToken.getMember();
		Boolean result = membersService.verifyMember(token, member);

		// 重定向到重設密碼頁面(html)
		return result ? "resetok" : "errortoken";
	}
}
