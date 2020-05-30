package jsh.project.board.account.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountEditRequestDto;
import jsh.project.board.account.dto.AccountEmailDto;
import jsh.project.board.account.dto.AccountFindRequestDto;
import jsh.project.board.account.dto.AccountFindResponseDto;
import jsh.project.board.account.dto.AccountPasswordDto;
import jsh.project.board.account.dto.AccountPasswordResetDto;
import jsh.project.board.account.dto.AccountPasswordResetRequestDto;
import jsh.project.board.account.dto.AccountResponseDto;
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
        return "userPages/login";
    }
  
    @GetMapping("/account/join")
    public String joinPage() {
    	return "userPages/join";
    }
    
    @PostMapping("/account")
    public @ResponseBody ResponseEntity<HttpStatus> join(@RequestBody @Valid AccountCreateDto dto) throws Exception {
    	log.info("AccountCreateDto : " + dto.toString());
    	accountService.register(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/account/info/{id}")
    public String accountInfoPage(@PathVariable("id")int id, Model model) {
    	model.addAttribute("id",id);
    	return "userPages/info";
    }
    
    @GetMapping("/account/{id}")
    public @ResponseBody ResponseEntity<AccountResponseDto> accountInfo(@PathVariable("id")int id) {
    	return new ResponseEntity<>(accountService.accountInfo(id),HttpStatus.OK);
    }
    
    @GetMapping("/account/edit")
    public String accountEditPage() {
    	return "userPages/edit";
    }
    
    @PreAuthorize("(#id == principal.id)")
    @PatchMapping("/account/{id}")
    public @ResponseBody ResponseEntity<HttpStatus> accountEdit(Principal principal, Authentication auth, Model model, @PathVariable("id")int id, @RequestBody @Valid AccountEditRequestDto dto) {
    	Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	account.setNickname(dto.getNickname());
    	accountService.accountEdit(account);
    	
    	Authentication newAuth = new UsernamePasswordAuthenticationToken(account, auth.getCredentials(), account.getAuthorities());
    	SecurityContextHolder.getContext().setAuthentication(newAuth);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //email 중복 체크
    @GetMapping("/account/email")
    public @ResponseBody ResponseEntity<HttpStatus> checkEmail(@Valid AccountEmailDto dto){
    	accountService.emailCheck(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //이메일 발송 완료 페이지 이동
    @RequestMapping("/account/sendEmail")
    public String emailPage(AccountEmailDto dto, Model model) {
    	model.addAttribute("email", dto.getEmail());
    	return "userPages/sendEmail";
    }
    
    //회원가입(계정 활성화) 이메일 인증
    @GetMapping("/account/emailConfirm")
    public String emailConfirm(AccountAuthRequestDto dto) {
    	accountService.signUpConfirm(dto);
    	return "redirect:/login";
    }
    
    //인증이메일 재발송
    @GetMapping("/account/resend")
    public @ResponseBody ResponseEntity<HttpStatus> resendEmail(AccountEmailDto dto) throws Exception{
    	accountService.resendEmail(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //계정 찾기 페이지 요청
    @GetMapping("/account/find-email")
    public String findEmailPage(){
    	return "userPages/findAccountPage";
    }
    
    //계정 찾기
    @PostMapping("/account/find-email")
    public @ResponseBody ResponseEntity<List<AccountFindResponseDto>> findEmail(@RequestBody @Valid AccountFindRequestDto dto) throws Exception{
    	return new ResponseEntity<>(accountService.findAccount(dto), HttpStatus.OK);
    }
    
    //비밀번호 찾기 페이지 요청 
    @GetMapping("/account/find-password")
    public String findPasswordPage(){
    	return "userPages/findPasswordPage";
    }
    
    //계정 정보를 입력 후 비밀번호 리셋요청(인증이메일 발송)
    @PostMapping("/account/reset")
    public @ResponseBody ResponseEntity<HttpStatus> findPassword(@RequestBody @Valid AccountPasswordResetRequestDto dto) throws Exception{
    	accountService.sendResetEmail(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //비밀번호 리셋 이메일 인증
    @GetMapping("/account/resetConfirm")
    public String resetRequest(AccountAuthRequestDto dto, Model model) {
    	accountService.resetPasswordConfirm(dto);
    	model.addAttribute("dto",dto);
    	return "userPages/passwordResetPage";
    }
    
    //비밀번호 변경
    @PostMapping("/account/resetPassword")
    public @ResponseBody ResponseEntity<HttpStatus> resetPassword(@RequestBody @Valid AccountPasswordResetDto dto) throws Exception{
    	accountService.resetPassword(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //로그인상태에서 비밀번호 재설정 페이지 요청
    @GetMapping("/account/passwordChange")
    public String passwordChangePage() {
    	return "userPages/passwordChangePage";
    }
    
    //로그인상태에서 비밀번호 재설정 
    @PostMapping("/account/passwordChange")
    public @ResponseBody ResponseEntity<HttpStatus> passwordChange(Principal principal, @RequestBody @Valid AccountPasswordDto dto) {
    	Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	accountService.passwordChange(account, dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping("/account/status")
    public String authPage(String email, Model model) {
    	return "commonPages/accountStatusPage";
    }
    
//	@PreAuthorize("hasAuthority('ROLE_USER')")
// 	@PreAuthorize("@accountService.accessCheck(#id, principal.id)") // service 클래스 (컴포넌트 선언해줘야함)
    @PreAuthorize("(#id == principal.id)")
    @GetMapping("/account/test/{id}")
    public String test(@PathVariable("id")int id) {
    	System.out.println(id);
    	return "login";
    }
    
    @GetMapping("/account/denied")
    public String accountDenied() {
    	return "commonPages/accessDenied";
    }
    
    @GetMapping("/auth/denied")
    public String authDenied() {
    	return "commonPages/authDenied";
    }
    
}
