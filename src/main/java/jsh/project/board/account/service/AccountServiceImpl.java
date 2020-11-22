package jsh.project.board.account.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestAccountResetDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.dto.response.ResponseAccountInfoDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.AccountNotEmailCheckedException;
import jsh.project.board.account.exception.AccountNotFoundException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.account.exception.FindAccountBadRequestException;

@Service
public class AccountServiceImpl implements AccountService{
	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	private final AccountDao accountDao;
	private final PasswordEncoder passwordEncoder;
	
	public AccountServiceImpl(final AccountDao accountDao, final PasswordEncoder passwordEncoder) {
		this.accountDao = accountDao;
		this.passwordEncoder = passwordEncoder;
	}
	
	// 회원가입
	@Transactional
	@Override
	public Account register(final RequestAccountCreateDto dto) {
		log.info(dto.toString());
		
		final Account account = Account.of(passwordEncoder, dto, Role.USER);
		log.info(account.toString());
		
		accountDao.insertAccount(account);
		return account;
	}
	
	// 계정 정보 찾기
	@Override
	public ResponseAccountInfoDto getAccountInfo(final int id) {
		final Account account = accountDao.selectAccountInfo(id);
		log.info(account.toString());
		return ResponseAccountInfoDto.from(account);
	}
	
	// 회원정보 수정(닉네임)
	@Transactional
	@Override
	public void editAccount(final Account account) {
		log.info(account.toString());
		accountDao.updateAccount(account);
	}
	
	// 비밀번호 변경
	@Transactional
	@Override
	public void changePassword(Account account, final RequestPasswordDto dto) {
		log.info(account.toString());
		log.info(dto.toString());
		account.changePassword(passwordEncoder, dto);
		accountDao.updatePassword(account);
	}
	
	// 로그인 실패(비밀번호 틀림) 횟수 가져오기
	@Override
	public int getAccountFailureCount(final String email) {
		log.info(email);
		return accountDao.selectFailureCount(email);
	}
	
	// 로그인 실패, 성공에 따른 로그인 실패 횟수 증가 및 초기화
	@Override
	public void updateFailureCount(final String email, final int failureCount) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("failureCount", failureCount);
		accountDao.updateFailureCount(paramMap);
	}
	
	// 로그인 성공시 마지막 로그인 날짜 업데이트
	@Transactional
	@Override
	public void updateLoginDate(final String email) {
		log.info(email);
		accountDao.updateLoginDate(email);
	}
	
	// 계정 잠금 및 해제
	@Transactional
	@Override
	public void updateLocked(final String email, final int locked) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("locked", locked);
		accountDao.updateLocked(paramMap);
	}
	
	// 회원가입 시 이메일 중복 체크
	@Override
	public void emailCheck(final RequestEmailDto dto) {
		log.info(dto.toString());
		if(accountDao.selectEmailCount(dto) != 0) {
			throw new EmailAlreadyUsedException();
		}
	}
	
	// 가입한 계정 찾기
	@Override
	public List<ResponseFindAccountDto> getAccounts(final RequestFindAccountDto dto) throws AccountNotFoundException {
		final List<Account> accountList = accountDao.selectAccounts(dto);
		List<ResponseFindAccountDto> responseAccountList = new ArrayList<>();
		
		if(accountList.isEmpty()) throw new AccountNotFoundException();
		
		for(Account account : accountList) {
			ResponseFindAccountDto responseDto = ResponseFindAccountDto.from(account);
			responseAccountList.add(responseDto);
		}
		
		return responseAccountList;
	}
	
	// 비밀번호 재설정 인증이메일 발송을 위한 계정 잠금
	@Transactional
	@Override
	public Account lockAccount(final RequestAccountResetDto dto){
		log.info(dto.toString()); 
		final Account account = accountDao.selectAccount(dto.getEmail());
		
		if(account==null) throw new AccountNotFoundException(); 
		if(!account.checkAccount(dto)) throw new FindAccountBadRequestException(); 
		if(!account.isEnabled()) throw new AccountNotEmailCheckedException(); 
		
		updateLocked(dto.getEmail(), 0); //계정 잠금
		return account;
	}
	
	// 비밀번호 재설정(초기화)
	@Transactional
	@Override
	public void resetPassword(final RequestPasswordResetDto dto) {
		log.info(dto.toString());
		
		Account account = accountDao.selectAccount(dto.getEmail());
		if(account == null) throw new AccountNotFoundException(); 
		
		account.resetPassword(passwordEncoder, dto);
		accountDao.updatePassword(account);
		updateLocked(dto.getEmail(), 1); //계정 잠금 풀기
	}
	
	// 계정 활성화
	@Override
	public void activation(final String email) {
		log.info(email);
		accountDao.updateEnabled(email);
	}
	
}
