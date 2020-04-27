package jsh.project.board.account.service;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountFindRequestDto;
import jsh.project.board.account.dto.AccountFindResponseDto;
import jsh.project.board.account.dto.AccountPasswordDto;
import jsh.project.board.account.dto.AccountPasswordResetDto;
import jsh.project.board.account.dto.AccountPasswordResetRequestDto;

public interface AccountService {
	
	//회원가입
	public void register(AccountCreateDto dto) throws Exception;
	
	public void passwordChange(Account account, AccountPasswordDto dto);
	
	//로그인 실패(비밀번호 틀림) 횟수 가져오기
	public int accountFailureCount(String email);
	
	//로그인 실패, 성공에 따른 failure_count 증가 및 초기화
	public void updateFailureCount(String email, int failureCount);
	
	//계정 잠금 및 해제
	public void updateLocked(String email, int locked);
	
	//회원가입 시 이메일 중복 체크
	public void emailCheck(String email);
	
	//계정 찾기
	public List<AccountFindResponseDto> findAccount(AccountFindRequestDto dto) throws AccountNotFoundException;
	
	//회원가입 인증 이메일 재발송
	public void resendEmail(String email) throws Exception;
	
	//비밀번호 초기화 인증 이메일 발송
	public void sendResetEmail(AccountPasswordResetRequestDto dto) throws Exception;
	
	//비밀번호 재설정(초기화)
	public void resetPassword(AccountPasswordResetDto dto);
	
	//이메일 인증 링크를 통해 인증키 비교 후 계정 상태값(enabled) 변경 
	public void signUpConfirm(AccountAuthRequestDto dto);
	
	//비밀번호 재설정을 위한 이메일을 통한 인증
	public void resetPasswordConfirm(AccountAuthRequestDto dto);

}
