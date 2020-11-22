package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;

@Repository
public class AccountDaoImpl implements AccountDao{
	
	private final SqlSession sqlSession;
	
	public AccountDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insertAccount(final Account dto) {
		sqlSession.insert("accountMapper.insertAccount",dto);
	}
	
	@Override
	public Account selectAccount(final String email) {
		return sqlSession.selectOne("accountMapper.selectAccount",email);
	}
	
	@Override
	public Account selectAccountInfo(final int id) {
		return sqlSession.selectOne("accountMapper.selectAccountInfo", id);
	}
	
	@Override
	public void updateAccount(final Account account) {
		sqlSession.update("accountMapper.updateAccount", account);
	}
	
	@Override
	public void updateLoginDate(final String email) {
		sqlSession.update("accountMapper.updateLoginDate",email);
	}
	
	@Override
	public void updatePassword(final Account account) {
		sqlSession.update("accountMapper.updatePassword", account);
	}
	
	@Override
	public int selectEmailCount(final RequestEmailDto dto) {
		return sqlSession.selectOne("accountMapper.selectEmailCount",dto);
	}
	
	@Override
	public List<Account> selectAccounts(final RequestFindAccountDto dto){
		return sqlSession.selectList("accountMapper.selectAccounts", dto);
	}
	
	@Override
	public void updateEnabled(final String email) {
		sqlSession.update("accountMapper.updateEnabled",email);
	}
	
	@Override
	public int selectFailureCount(final String email) {
		return sqlSession.selectOne("accountMapper.selectFailureCount", email);
	}
	
	@Override
	public void updateFailureCount(final Map<String, Object> paramMap) {
		sqlSession.update("accountMapper.updateFailureCount", paramMap);
	}
	
	@Override
	public void updateLocked(final Map<String, Object> paramMap) {
		sqlSession.update("accountMapper.updateLocked", paramMap);
	}
}
