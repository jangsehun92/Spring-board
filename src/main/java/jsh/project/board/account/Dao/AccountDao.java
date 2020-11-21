package jsh.project.board.account.dao;

import java.util.List;
import java.util.Map;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;

public interface AccountDao {
	public void insertAccount(final Account dto);
	public Account selectAccount(final String email);
	public void updateAccount(final Account dto);
	public Account selectAccountInfo(final int id);
	public void updateLoginDate(final String email);
	public void updatePassword(final Account account);
	public int selectEmailCount(final RequestEmailDto dto);
	public List<Account> selectAccounts(final RequestFindAccountDto dto);
	public void updateEnabled(final String email);
	public int selectFailureCount(final String email);
	public void updateFailureCount(final Map<String, Object> paramMap);
	public void updateLocked(final Map<String, Object> paramMap);
}
