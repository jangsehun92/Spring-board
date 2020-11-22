package jsh.project.board.account.service;

import jsh.project.board.account.domain.Account;
import jsh.project.board.account.domain.Auth;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.enums.AuthOption;

public interface AuthService {
	public Auth createAuth(final Account account, final AuthOption authOption);
	public void authConfirm(final RequestEmailConfirmDto dto);
	public Auth updateAuth(final String email);
	public void checkAuth(final RequestPasswordResetDto dto);
}
