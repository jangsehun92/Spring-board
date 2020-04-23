package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountAuthRequestDto;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountFindRequestDto;
import jsh.project.board.account.dto.AccountFindResponseDto;
import jsh.project.board.account.dto.AuthDto;

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
	
	public Account findByEmail(String email) {
		return sqlSession.selectOne("accountMapper.findByEmail",email);
	}
	
	public void updatePassword(Account account) {
		sqlSession.update("accountMapper.updatePassword", account);
	}
	
	public int findEmail(String email) {
		return sqlSession.selectOne("accountMapper.emailCheck",email);
	}
	
	public List<AccountFindResponseDto> findAccount(AccountFindRequestDto dto){
		return sqlSession.selectList("accountMapper.findAccount", dto);
	}
	
	public void activetion(String email) {
		sqlSession.update("accountMapper.activetion",email);
	}
	
	public int failureCount(String email) {
		return sqlSession.selectOne("accountMapper.failureCount", email);
	}
	
	public void updateFailureCount(Map<String, Object> paramMap) {
		sqlSession.update("accountMapper.updateFailureCount", paramMap);
	}
	
	public void updateLocked(Map<String, Object> paramMap) {
		sqlSession.update("accountMapper.updateLocked", paramMap);
	}
	
	//email 인증 관련 
	public void authSave(AuthDto dto) {
		sqlSession.insert("authMapper.create",dto);
	}
	
	public AuthDto findByAuth(String email) {
		return sqlSession.selectOne("authMapper.findByAuth", email);
	}
	
	public int authCheck(Map<String, String> paramMap) {
		return sqlSession.selectOne("authMapper.authCheck", paramMap);
	}
	
	public void updateAuthKey(AuthDto dto) {
		sqlSession.update("authMapper.update",dto);
	}
	
	public void authKeyExpired(AccountAuthRequestDto dto) {
		sqlSession.update("authMapper.expired",dto);
	}
	

}
