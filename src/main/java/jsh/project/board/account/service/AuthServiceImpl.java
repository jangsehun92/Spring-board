package jsh.project.board.account.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.auth.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.enums.AuthOption;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.global.infra.util.AuthKey;

@Service
public class AuthServiceImpl implements AuthService{
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	private final AuthDao authDao;
	
	public AuthServiceImpl(AuthDao authDao) {
		this.authDao = authDao;
	}

	@Transactional
	@Override
	public AuthDto createAuth(Account account, AuthOption authOption) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", account.getUsername());
		paramMap.put("authOption", authOption.getValue());
		
		if(authDao.selectAuthCount(paramMap) != 0) {
			return updateAuthKey(account.getUsername());
		}
		
		AuthDto authDto = new AuthDto(account.getUsername(), new AuthKey().getKey(), authOption.getValue());
		authDao.insertAuth(authDto);
		return authDto;
	}
	
	@Transactional
	@Override
	public void authConfirm(final RequestEmailConfirmDto dto) {
		log.info(dto.toString());
		final AuthDto authDto = getAuth(dto.getEmail());
		
		if (authDto == null || !dto.getAuthKey().equals(authDto.getAuthKey()) || !dto.getAuthOption().equals(authDto.getAuthOption())) {
			throw new BadAuthRequestException();
		}
		expired(dto);
	}
	
	@Transactional
	@Override
	public AuthDto updateAuthKey(String email) {
		AuthDto authDto = authDao.selectAuth(email);
		if(authDto == null || authDto.isAuthExpired()) {
			throw new BadAuthRequestException();
		}
		String authKey = new AuthKey().getKey();
		authDto.setAuthKey(authKey);
		authDao.updateAuth(authDto);
		return authDto;
	}

	@Override
	public AuthDto getAuth(String email) {
		return authDao.selectAuth(email);
	}

	@Transactional
	private void expired(RequestEmailConfirmDto dto) {
		authDao.deleteAuth(dto);
	}

	@Override
	public void checkAuth(Map<String, String> paramMap) {
		if(!authDao.selectAuthCheck(paramMap)) throw new BadAuthRequestException(); 
	}

}
