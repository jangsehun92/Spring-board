package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountEmailDto;
import jsh.project.board.account.dto.AccountFindRequestDto;
import jsh.project.board.account.dto.AccountFindResponseDto;
import jsh.project.board.account.dto.AccountResponseDto;

@Repository
public class AccountDaoImpl implements AccountDao{
	
	private SqlSession sqlSession;
	
	public AccountDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void save(AccountCreateDto dto) {
		sqlSession.insert("accountMapper.save",dto);
	}
	
	@Override
	public Account findByEmail(String email) {
		return sqlSession.selectOne("accountMapper.findByEmail",email);
	}
	
	@Override
	public AccountResponseDto findById(int id) {
		return sqlSession.selectOne("accountMapper.findById", id);
	}
	
	@Override
	public void edit(Account dto) {
		sqlSession.update("accountMapper.edit", dto);
	}
	
	@Override
	public void updateLoginDate(String email) {
		sqlSession.update("accountMapper.updateLoginDate",email);
	}
	
	@Override
	public void updatePassword(Account account) {
		sqlSession.update("accountMapper.updatePassword", account);
	}
	
	@Override
	public int findEmail(AccountEmailDto dto) {
		return sqlSession.selectOne("accountMapper.emailCheck",dto);
	}
	
	@Override
	public List<AccountFindResponseDto> findAccount(AccountFindRequestDto dto){
		return sqlSession.selectList("accountMapper.findAccount", dto);
	}
	
	@Override
	public void activetion(String email) {
		sqlSession.update("accountMapper.activetion",email);
	}
	
	@Override
	public int failureCount(String email) {
		return sqlSession.selectOne("accountMapper.failureCount", email);
	}
	
	@Override
	public void updateFailureCount(Map<String, Object> paramMap) {
		sqlSession.update("accountMapper.updateFailureCount", paramMap);
	}
	
	@Override
	public void updateLocked(Map<String, Object> paramMap) {
		sqlSession.update("accountMapper.updateLocked", paramMap);
	}

	

	

	
}
