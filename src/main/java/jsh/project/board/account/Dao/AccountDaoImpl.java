package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.dto.response.ResponseAccountDto;

@Repository
public class AccountDaoImpl implements AccountDao{
	
	private SqlSession sqlSession;
	
	public AccountDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void save(RequestAccountCreateDto dto) {
		sqlSession.insert("accountMapper.save",dto);
	}
	
	@Override
	public Account findByEmail(String email) {
		return sqlSession.selectOne("accountMapper.findByEmail",email);
	}
	
	@Override
	public ResponseAccountDto findById(int id) {
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
	public int findEmail(RequestEmailDto dto) {
		return sqlSession.selectOne("accountMapper.emailCheck",dto);
	}
	
	@Override
	public List<ResponseFindAccountDto> findAccount(RequestFindAccountDto dto){
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
