package jsh.project.board.account.service;

import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.enums.AuthOption;

public interface AuthService {
	
	public AuthDto getAuth(String email);
	
	public AuthDto createAuth(String email, AuthOption authOption);
	
	public AuthDto updateAuthKey(String email);
	
	public void verification(RequestEmailConfirmDto dto);

}
