package jsh.project.board.account.dao;

import jsh.project.board.account.domain.Auth;

public interface AuthDao {
	
	public void insertAuth(final Auth auth);
	
	public Auth selectAuth(final String email);
	
	public int selectAuthCount(final Auth auth);
	
	public boolean selectAuthCheck(final Auth auth);
	
	public void updateAuth(final Auth auth);
	
	public void deleteAuth(final Auth auth);

}
