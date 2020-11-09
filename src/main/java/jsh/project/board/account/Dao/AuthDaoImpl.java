package jsh.project.board.account.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.auth.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;

@Repository
public class AuthDaoImpl implements AuthDao{
	
	private final SqlSession sqlSession;
	
	public AuthDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insertAuth(AuthDto dto) {
		sqlSession.insert("authMapper.insertAuth",dto);
	}
	
	@Override
	public AuthDto selectAuth(String email) {
		return sqlSession.selectOne("authMapper.selectAuth", email);
	}
	
	@Override
	public int selectAuthCount(Map<String, String> paramMap) {
		return sqlSession.selectOne("authMapper.selectAuthCount", paramMap);
	}
	
	@Override
	public boolean selectAuthCheck(Map<String, String> paramMap) {
		return sqlSession.selectOne("authMapper.selectAuthCheck", paramMap);
	}
	
	@Override
	public void updateAuth(AuthDto dto) {
		sqlSession.update("authMapper.updateAuth",dto);
	}
	
	@Override
	public void deleteAuth(RequestEmailConfirmDto dto) {
		sqlSession.update("authMapper.deleteAuth",dto);
	}

}
