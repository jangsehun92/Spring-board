package jsh.project.board.account.dao;

import java.util.Map;

import jsh.project.board.account.dto.auth.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;

public interface AuthDao {
	
	public void insertAuth(AuthDto dto);
	
	public AuthDto selectAuth(String email);
	
	public int selectAuthCount(Map<String, String> paramMap);
	
	public boolean selectAuthCheck(Map<String, String> paramMap);
	
	public void updateAuth(AuthDto dto);
	
	public void deleteAuth(RequestEmailConfirmDto dto);

}
