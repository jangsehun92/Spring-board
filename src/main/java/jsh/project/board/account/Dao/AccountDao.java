package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.dto.response.ResponseAccountDto;

public interface AccountDao {
	public void save(RequestAccountCreateDto dto);
	
	public Account findByEmail(String email);
	
	public void edit(Account dto);
	
	public ResponseAccountDto findById(int id);
	
	public void updateLoginDate(String email);
	
	public void updatePassword(Account account);
	
	public int findEmail(RequestEmailDto dto);
	
	public List<ResponseFindAccountDto> findAccount(RequestFindAccountDto dto);
	
	public void activetion(String email);
	
	public int failureCount(String email);
	
	public void updateFailureCount(Map<String, Object> paramMap);
	
	public void updateLocked(Map<String, Object> paramMap);

}
