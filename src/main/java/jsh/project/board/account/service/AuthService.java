package jsh.project.board.account.service;

import java.util.Map;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.auth.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.enums.AuthOption;

public interface AuthService {
	public AuthDto getAuth(final String email);
	public AuthDto createAuth(final Account account, final AuthOption authOption);
	public void authConfirm(final RequestEmailConfirmDto dto);
	public AuthDto updateAuthKey(final String email);
	public void checkAuth(Map<String,String> paramMap);
}
