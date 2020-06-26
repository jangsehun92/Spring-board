package jsh.project.board.account.dao;

import java.util.Map;

import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;

public interface AuthDao {
	
	public void insertAuth(AuthDto dto);
	
	public AuthDto selectAuth(String email);
	
	public int checkAuth(Map<String, String> paramMap);
	
	public void updateAuth(AuthDto dto);
	
	public void deleteAuth(RequestEmailConfirmDto dto);

}
