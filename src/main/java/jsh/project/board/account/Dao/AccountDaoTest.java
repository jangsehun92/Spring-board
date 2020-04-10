package jsh.project.board.account.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.CreateAccountDto;

@Repository
public class AccountDaoTest {
	
	private SqlSession sqlSession;

	public AccountDaoTest(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public CreateAccountDto getUserByEmail(String email) {
		return sqlSession.selectOne("accountMapper.selectByEmail",email);
	}
	
	public String getUserAuthorityByEmail(String email) {
		return sqlSession.selectOne("accountMapper.auth_selectByEmail",email);
	}
}
