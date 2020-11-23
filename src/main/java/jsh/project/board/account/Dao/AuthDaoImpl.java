package jsh.project.board.account.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.domain.Auth;

@Repository
public class AuthDaoImpl implements AuthDao{
	
	private final SqlSession sqlSession;
	
	public AuthDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insertAuth(final Auth auth) {
		sqlSession.insert("authMapper.insertAuth", auth);
	}
	
	@Override
	public Auth selectAuth(final String email) {
		return sqlSession.selectOne("authMapper.selectAuth", email);
	}
	
	@Override
	public int selectAuthCount(final Auth auth) {
		return sqlSession.selectOne("authMapper.selectAuthCount", auth);
	}
	
	@Override
	public boolean selectAuthCheck(final Auth auth) {
		return sqlSession.selectOne("authMapper.selectAuthCheck", auth);
	}
	
	@Override
	public void updateAuth(final Auth auth) {
		sqlSession.update("authMapper.updateAuth", auth);
	}
	
	@Override
	public void deleteAuth(final Auth auth) {
		sqlSession.update("authMapper.deleteAuth", auth);
	}

}
