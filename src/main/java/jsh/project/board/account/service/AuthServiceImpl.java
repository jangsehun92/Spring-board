package jsh.project.board.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.enums.AuthOption;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.global.infra.util.AuthKey;

@Service
public class AuthServiceImpl implements AuthService{
	
	private AuthDao authDao;
	
	public AuthServiceImpl(AuthDao authDao) {
		this.authDao = authDao;
	}

	@Override
	public AuthDto createAuth(String email, AuthOption authOption) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("authOption", authOption.getValue());
		
		AuthDto authDto = null;
		
		if(authDao.checkAuth(paramMap) == 0) {
			String authKey = new AuthKey().getKey();
			authDto = new AuthDto(email, authKey, authOption.getValue());
			authDao.insertAuth(authDto);
		}else {
			authDto = updateAuthKey(email);
		}
		return authDto;
	}

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

	@Override
	public void verification(RequestEmailConfirmDto dto) {
		authDao.deleteAuth(dto);
	}

}
