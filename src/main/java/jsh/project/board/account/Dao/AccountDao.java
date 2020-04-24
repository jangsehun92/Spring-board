package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import jsh.project.board.account.dto.Account;
import jsh.project.board.account.dto.AccountCreateDto;
import jsh.project.board.account.dto.AccountFindRequestDto;
import jsh.project.board.account.dto.AccountFindResponseDto;

public interface AccountDao {
	public void save(AccountCreateDto dto);
	
	public Account findByEmail(String email);
	
	public void updatePassword(Account account);
	
	public int findEmail(String email);
	
	public List<AccountFindResponseDto> findAccount(AccountFindRequestDto dto);
	
	public void activetion(String email);
	
	public int failureCount(String email);
	
	public void updateFailureCount(Map<String, Object> paramMap);
	
	public void updateLocked(Map<String, Object> paramMap);

}
