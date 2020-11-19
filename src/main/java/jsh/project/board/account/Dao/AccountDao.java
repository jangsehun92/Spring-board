package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.response.ResponseAccountInfoDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;

public interface AccountDao {
	public void insertAccount(Account dto);
	public Account selectAccount(String email);
	public void updateAccount(Account dto);
	//public ResponseAccountInfoDto selectAccountInfo(int id);
	public Account selectAccountInfo(int id);
	public void updateLoginDate(String email);
	public void updatePassword(Account account);
	public int selectEmailCount(RequestEmailDto dto);
	public List<ResponseFindAccountDto> selectAccounts(RequestFindAccountDto dto);
	public void updateEnabled(String email);
	public int selectFailureCount(String email);
	public void updateFailureCount(Map<String, Object> paramMap);
	public void updateLocked(Map<String, Object> paramMap);
}
