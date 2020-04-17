package jsh.project.board.account.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountEmailDto;

@Repository
public class AccountDao{
	
	private SqlSession sqlSession;
	
	public AccountDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//계정 관련
	public void save(AccountCreateDto dto) {
		sqlSession.insert("accountMapper.save",dto);
	}
	
	public Account getUserByEmail(String email) {
		return sqlSession.selectOne("accountMapper.selectByEmail",email);
	}
	
	public int findByEmail(String email) {
		return sqlSession.selectOne("accountMapper.checkEmail",email);
	}
	
	public void emailChecked(String email) {
		sqlSession.update("accountMapper.emailChecked",email);
	}
	
	//email 인증 관련 
	public void create(AccountEmailDto dto) {
		sqlSession.insert("authMapper.create",dto);
	}
	
	public String authKeySearch(String email) {
		return sqlSession.selectOne("authMapper.findByEmail",email);
	}
	
	public void expired(AccountEmailDto dto) {
		sqlSession.update("authMapper.expired",dto);
	}
	

}
