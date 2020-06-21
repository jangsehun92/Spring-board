package jsh.project.board.account.dao;

import java.util.Map;

import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;

public interface AuthDao {
	
	public void authSave(AuthDto dto);
	
	public AuthDto findByEmail(String email);
	
	public int authCheck(Map<String, String> paramMap);
	
	public void updateAuthKey(AuthDto dto);
	
	public void authKeyExpired(RequestEmailConfirmDto dto);

}
