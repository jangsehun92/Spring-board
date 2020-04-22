package jsh.project.board.account.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountFindDto;
import jsh.project.board.account.dto.AccountPasswordChangeDto;
import jsh.project.board.account.service.AccountService;

@Controller
public class AccountController {
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	private AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
  
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
    
    @GetMapping("/account/join")
    public String joinPage() {
    	return "join";
    }
    
    @PostMapping("/account/join")
    public @ResponseBody ResponseEntity<HttpStatus> join(@RequestBody AccountCreateDto dto) throws Exception {
    	accountService.register(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /*  */
    @GetMapping("/account/info")
    public String info(Principal principal, Authentication auth) {
    	//비동기 요청을 통해 정보를 전달해줄 것인지?
    	log.info(principal.getName());//null을 유발할수 있다.
    	//이렇게도 인증한 정보를 얻어올 수 있다.
    	//Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return "login";
    }
    
    //email 중복 체크
    @GetMapping("/account/email")
    public @ResponseBody ResponseEntity<HttpStatus> checkEmail(String email){
    	accountService.emailCheck(email);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //회원가입 완료 후 이메일 발송 완료 페이지 이동
    @RequestMapping("/account/sendEmail")
    public String emailPage(String email, Model model) {
    	model.addAttribute("email", email);
    	return "sendEmail";
    }
    
    @RequestMapping("/account/error")
    public String authPage(String email, Model model) {
    	return "errorPage";
    }
    
    //인증이메일 재발송
    @GetMapping("/account/resend")
    public @ResponseBody ResponseEntity<HttpStatus> resendEmail(String email) throws Exception{
    	accountService.resendEmail(email);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/account/find-email")
    public String findEmailPage() throws Exception{
    	return "";
    }
    
    @PostMapping("/account/find-email")
    public String findEmail(AccountFindDto dto) throws Exception{
    	return "";
    }
    
    @GetMapping("/account/find-password")
    public String findPasswordPage() throws Exception{
    	//비밀번호 찾기 페이지로 이동한다.
    	return "";
    }
    
    @PostMapping("/account/password")
    public @ResponseBody ResponseEntity<HttpStatus> resetPassword(@RequestBody AccountPasswordChangeDto dto) throws Exception{
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    //email 인증처리
    @GetMapping("/account/emailConfirm")
    public String emailConfirm(AccountAuthRequestDto dto) {
    	accountService.emailConfirm(dto);
    	return "redirect:/login";
    }
    

	

}
