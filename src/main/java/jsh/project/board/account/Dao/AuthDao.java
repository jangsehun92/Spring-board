package jsh.project.board.account.dao;

import jsh.project.board.account.domain.Auth;

public interface AuthDao {
	
	public void insertAuth(final Auth dto);
	
	public Auth selectAuth(final String email);
	
	public int selectAuthCount(final Auth dto);
	
	public boolean selectAuthCheck(final Auth dto);
	
	public void updateAuth(final Auth dto);
	
	public void deleteAuth(final Auth dto);

}
