package jsh.project.board.account.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

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
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestAccountEditDto;
import jsh.project.board.account.dto.request.RequestAccountResetDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.dto.response.ResponseAccountInfoDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.service.AccountService;

@Controller
public class AccountController {
	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	// 로그인 요청
	@RequestMapping("/login")
    public String loginPage() {
        return "userPages/login";
    }
  
	// 회원가입 페이지 요청 
    @GetMapping("/account/join")
    public String joinPage() {
    	return "userPages/join";
    }
    
    // 회원가입
    @PostMapping("/account/join")
    public @ResponseBody ResponseEntity<HttpStatus> join(@RequestBody @Valid RequestAccountCreateDto dto) throws Exception{
    	accountService.register(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 해당 계정 정보 보기 페이지 요청
    @GetMapping("/account/info/{id}")
    public String accountInfoPage(@PathVariable("id")int id, Model model) {
    	model.addAttribute("id",id);
    	return "userPages/info";
    }
    
    // 해당 계정 정보 가져오기
    @GetMapping("/account/{id}")
    public @ResponseBody ResponseEntity<ResponseAccountInfoDto> accountInfo(@PathVariable("id")int id) {
    	return new ResponseEntity<>(accountService.getAccountInfo(id),HttpStatus.OK);
    }
    
    // 계정 정보(닉네임)변경 페이지 요청
    @GetMapping("/account/edit")
    public String accountEditPage() {
    	return "userPages/edit";
    }
    
    // 계정 정보(닉네임)변경 
    @PreAuthorize("(#id == principal.id)")
    @PatchMapping("/account/{id}")
    public @ResponseBody ResponseEntity<HttpStatus> accountEdit(Principal principal, Authentication auth, Model model, @PathVariable("id")int id, @RequestBody @Valid RequestAccountEditDto dto) {
    	Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	account.changeAccountNickname(dto.getNickname());
    	accountService.editAccount(account);
    	
    	Authentication newAuth = new UsernamePasswordAuthenticationToken(account, auth.getCredentials(), account.getAuthorities());
    	SecurityContextHolder.getContext().setAuthentication(newAuth);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // email 중복 체크
    @GetMapping("/account/email")
    public @ResponseBody ResponseEntity<HttpStatus> checkEmail(@Valid RequestEmailDto dto){
    	accountService.emailCheck(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 이메일 발송 완료 페이지 이동
    @RequestMapping("/account/sendEmail")
    public String emailPage(RequestEmailDto dto, Model model) {
    	model.addAttribute("email", dto.getEmail());
    	return "userPages/sendEmail";
    }
    
    // 회원가입(계정 활성화) 이메일 인증
    @GetMapping("/account/emailConfirm")
    public String emailConfirm(RequestEmailConfirmDto dto) {
    	accountService.authConfirm(dto);
    	accountService.activation(dto.getEmail());
    	return "redirect:/login";
    }
    
    // 인증이메일 재발송
    @GetMapping("/account/resend")
    public @ResponseBody ResponseEntity<HttpStatus> resendEmail(RequestEmailDto dto) throws Exception{
    	accountService.resendEmail(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 계정 찾기 페이지 요청
    @GetMapping("/account/find-email")
    public String findEmailPage(){
    	return "userPages/findAccountPage";
    }
    
    // 계정 찾기
    @PostMapping("/account/find-email")
    public @ResponseBody ResponseEntity<List<ResponseFindAccountDto>> findEmail(@RequestBody @Valid RequestFindAccountDto dto) throws Exception{
    	return new ResponseEntity<>(accountService.getAccounts(dto), HttpStatus.OK);
    }
    
    // 비밀번호 찾기 페이지 요청 
    @GetMapping("/account/find-password")
    public String findPasswordPage(){
    	return "userPages/findPasswordPage";
    }
    
    // 계정 정보를 입력 후 비밀번호 재설정 요청(인증이메일 발송)
    @PostMapping("/account/reset")
    public @ResponseBody ResponseEntity<HttpStatus> findPassword(@RequestBody @Valid RequestAccountResetDto dto) throws Exception{
    	accountService.sendResetEmail(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 비밀번호 재설정을 위한 이메일 인증
    @GetMapping("/account/resetConfirm")
    public String resetRequest(RequestEmailConfirmDto dto, Model model) {
    	accountService.authConfirm(dto);
    	model.addAttribute("dto",dto);
    	return "userPages/passwordResetPage";
    }
    
    // 비밀번호 재설정
    @PostMapping("/account/resetPassword")
    public @ResponseBody ResponseEntity<HttpStatus> resetPassword(@RequestBody @Valid RequestPasswordResetDto dto) throws Exception{
    	accountService.resetPassword(dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // 로그인상태에서 비밀번호 재설정 페이지 요청
    @GetMapping("/account/passwordChange")
    public String passwordChangePage() {
    	return "userPages/passwordChangePage";
    }
    
    // 로그인상태에서 비밀번호 변경 
    @PostMapping("/account/passwordChange")
    public @ResponseBody ResponseEntity<HttpStatus> passwordChange(Principal principal, @RequestBody @Valid RequestPasswordDto dto) {
    	Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	accountService.changePassword(account, dto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping("/account/status")
    public String authPage(String email, Model model) {
    	return "commonPages/accountStatusPage";
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
