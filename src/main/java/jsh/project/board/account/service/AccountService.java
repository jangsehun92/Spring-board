package jsh.project.board.account.service;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.dto.request.RequestAccountResetDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.dto.response.ResponseAccountInfoDto;

public interface AccountService {
	
	//회원가입
	public void register(RequestAccountCreateDto dto) throws Exception;
	//해당 유저 정보 가져오기
	public ResponseAccountInfoDto getAccountInfo(int id);
	//사용자 정보 수정
	public void editAccount(Account account);
	//비밀번호 변경
	public void changePassword(Account account, RequestPasswordDto dto);
	//로그인 실패(비밀번호 틀림) 횟수 가져오기
	public int getAccountFailureCount(String email);
	//로그인 실패, 성공에 따른 실패 카운트 증가 및 초기화
	public void updateFailureCount(String email, int failureCount);
	//로그인 성공시 마지막 로그인 날짜 업데이트
	public void updateLoginDate(String email);
	//계정 잠금 및 해제
	public void updateLocked(String email, int locked);
	//회원가입 시 이메일 중복 체크
	public void emailCheck(RequestEmailDto dto);
	//계정 찾기
	public List<ResponseFindAccountDto> getAccounts(RequestFindAccountDto dto) throws AccountNotFoundException;
	//회원가입 인증 이메일 재발송
	public void resendEmail(RequestEmailDto dto) throws Exception;
	//비밀번호 초기화 인증 이메일 발송
	public void sendResetEmail(RequestAccountResetDto dto) throws Exception;
	//비밀번호 재설정(초기화)
	public void resetPassword(RequestPasswordResetDto dto);
	//이메일 인증
	public void authConfirm(RequestEmailConfirmDto dto);

}
