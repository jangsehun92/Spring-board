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
	public void insertAuth(final Auth dto) {
		sqlSession.insert("authMapper.insertAuth",dto);
	}
	
	@Override
	public Auth selectAuth(final String email) {
		return sqlSession.selectOne("authMapper.selectAuth", email);
	}
	
	@Override
	public int selectAuthCount(final Auth dto) {
		return sqlSession.selectOne("authMapper.selectAuthCount", dto);
	}
	
	@Override
	public boolean selectAuthCheck(final Auth dto) {
		return sqlSession.selectOne("authMapper.selectAuthCheck", dto);
	}
	
	@Override
	public void updateAuth(final Auth dto) {
		sqlSession.update("authMapper.updateAuth",dto);
	}
	
	@Override
	public void deleteAuth(final Auth dto) {
		sqlSession.update("authMapper.deleteAuth",dto);
	}

}
