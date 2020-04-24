package jsh.project.board.account.dao;

import java.util.Map;

import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AuthDto;

public interface AuthDao {
	
	public void authSave(AuthDto dto);
	
	public AuthDto findByEmail(String email);
	
	public int authCheck(Map<String, String> paramMap);
	
	public void updateAuthKey(AuthDto dto);
	
	public void authKeyExpired(AccountAuthRequestDto dto);

}
