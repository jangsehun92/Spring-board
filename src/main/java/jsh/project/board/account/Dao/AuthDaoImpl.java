package jsh.project.board.account.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AuthDto;

@Repository
public class AuthDaoImpl implements AuthDao{
	
	private SqlSession sqlSession;
	
	public AuthDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void authSave(AuthDto dto) {
		sqlSession.insert("authMapper.create",dto);
	}
	
	@Override
	public AuthDto findByEmail(String email) {
		return sqlSession.selectOne("authMapper.findByEmail", email);
	}
	
	@Override
	public int authCheck(Map<String, String> paramMap) {
		return sqlSession.selectOne("authMapper.authCheck", paramMap);
	}
	
	@Override
	public void updateAuthKey(AuthDto dto) {
		sqlSession.update("authMapper.update",dto);
	}
	
	@Override
	public void authKeyExpired(AccountAuthRequestDto dto) {
		sqlSession.update("authMapper.expired",dto);
	}

}
