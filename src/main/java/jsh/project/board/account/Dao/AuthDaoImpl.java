package jsh.project.board.account.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;

@Repository
public class AuthDaoImpl implements AuthDao{
	
	private final SqlSession sqlSession;
	
	public AuthDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insertAuth(AuthDto dto) {
		sqlSession.insert("authMapper.create",dto);
	}
	
	@Override
	public AuthDto selectAuth(String email) {
		return sqlSession.selectOne("authMapper.findByEmail", email);
	}
	
	@Override
	public int checkAuth(Map<String, String> paramMap) {
		return sqlSession.selectOne("authMapper.authCheck", paramMap);
	}
	
	@Override
	public void updateAuth(AuthDto dto) {
		sqlSession.update("authMapper.update",dto);
	}
	
	@Override
	public void deleteAuth(RequestEmailConfirmDto dto) {
		sqlSession.update("authMapper.expired",dto);
	}

}
