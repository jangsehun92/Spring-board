package jsh.project.board.account.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.CreateAccountDto;

@Repository
public class AccountDao{
	
	private SqlSession sqlSession;
	
	public AccountDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void save(CreateAccountDto dto) {
		sqlSession.insert("accountMapper.save",dto);
	}
	
	public Account getUserByEmail(String email) {
		return sqlSession.selectOne("accountMapper.selectByEmail",email);
	}
	
	public String findByEmail(String email) {
		return sqlSession.selectOne("accountMapper.auth_selectByEmail",email);
	}

}
