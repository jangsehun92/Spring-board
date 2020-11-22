package jsh.project.board.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.domain.Auth;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.enums.AuthOption;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.global.infra.util.AuthKeyMaker;

@Service
public class AuthServiceImpl implements AuthService{
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	private final AuthDao authDao;
	private final AuthKeyMaker authKeyMaker;
	
	public AuthServiceImpl(final AuthDao authDao, final AuthKeyMaker authKeyMaker) {
		this.authDao = authDao;
		this.authKeyMaker = authKeyMaker;
	}

	@Transactional
	@Override
	public Auth createAuth(final Account account, final AuthOption authOption) {
		final Auth auth = Auth.of(account, authKeyMaker, authOption);
		authDao.insertAuth(auth);
		return auth;
	}
	
	@Transactional
	@Override
	public Auth updateAuth(final String email) {
		Auth auth = authDao.selectAuth(email);
		if(auth == null) throw new BadAuthRequestException();
		auth.updateAuthKey();
		authDao.updateAuth(auth);
		return auth;
	}
	
	@Transactional
	@Override
	public void authConfirm(final RequestEmailConfirmDto dto) {
		log.info(dto.toString());
		final Auth auth = authDao.selectAuth(dto.getEmail());
		
		if (auth == null || !dto.getAuthKey().equals(auth.getAuthKey()) || !dto.getAuthOption().equals(auth.getAuthOption())) {
			throw new BadAuthRequestException();
		}
		expired(auth);
	}
	
	@Transactional
	private void expired(final Auth auth) {
		authDao.deleteAuth(auth);
	}

	@Override
	public void checkAuth(final RequestPasswordResetDto dto) {
		final Auth auth = Auth.from(dto);
		if(!authDao.selectAuthCheck(auth)) throw new BadAuthRequestException(); 
	}

}
