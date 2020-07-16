package jsh.project.board.account;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestAccountEditDto;
import jsh.project.board.account.dto.request.RequestEmailConfirmDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.request.RequestPasswordResetDto;
import jsh.project.board.account.dto.response.ResponseAccountInfoDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.enums.AuthOption;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.AccountNotFoundException;
import jsh.project.board.account.exception.BadAuthRequestException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.account.exception.PasswordCheckFailedException;
import jsh.project.board.account.exception.PasswordNotMatchException;
import jsh.project.board.account.service.AccountServiceImpl;
import jsh.project.board.account.service.AuthServiceImpl;
import jsh.project.board.global.infra.email.EmailService;
import jsh.project.board.global.infra.util.AuthKey;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
	
	@Mock
	private AccountDao accountDao;
	
	@Mock
	private AuthDao authDao;
	
	@InjectMocks
	private AccountServiceImpl accountService;
	
	@Mock
	private AuthServiceImpl authService;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private EmailService emailService;
	
	@Spy
	ResponseFindAccountDto responseDto = new ResponseFindAccountDto();
	
	@Spy
	Account account = new Account("jangsehun1992@gmail", "password", "장세훈", "920409", "tester", "ROLE_ADMIN");
	
	
	@Before
	public void setUp() {
		//계정 
		account.setId(1);
		account.setRegdate(new Date());
		account.setLocked(0);
		account.setFailureCount(0);
		account.setLastLoginDate(new Date());
		
		//가입한 계정
		responseDto.setEmail("jangsehun1992@gmail.com");
		responseDto.setRegdate(new Date());
	}
	
	@Test
	public void 이메일_중복체크() {
		//given
		RequestEmailDto dto = new RequestEmailDto();
		dto.setEmail("jangsehun1992@gmail.com");
		
		//when
		accountService.emailCheck(dto);
		
		//then
		assertNull(accountDao.selectAccount(dto.getEmail()));
	}
	
	@Test(expected = EmailAlreadyUsedException.class)
	public void 이메일_중복일경우() {
		//given
		RequestEmailDto dto = new RequestEmailDto();
		dto.setEmail("jangsehun1992@gmail.com");
		
		given(accountDao.selectEmailCount(dto)).willReturn(1);
		
		//when
		accountService.emailCheck(dto);
		
		//then
		assertThat(accountDao.selectAccount(dto.getEmail()),is(1));
	}
	
	@Test
	public void 회원가입() throws Exception {
		//given
		RequestAccountCreateDto dto = new RequestAccountCreateDto();
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setPassword("1234");
		dto.setPasswordCheck("1234");
		dto.setName("장세훈");
		dto.setNickname("tester");
		dto.setBirth("920409");
		dto.setRole(Role.USER);
		
		AuthDto authDto = new AuthDto();
		authDto.setEmail("jangsehun1992@gmail.com");
		authDto.setAuthKey(new AuthKey().getKey());
		authDto.setAuthOption(AuthOption.RESET.getValue());
		authDto.setExpired(1);
		
		//when
		accountService.register(dto);
		
		//then
		verify(accountDao, times(1)).insertAccount(any());
		verify(passwordEncoder, times(1)).encode("1234");
		verify(authService, times(1)).createAuth(any(),any());
		verify(emailService, times(1)).sendEmail(any());
	}
	
	@Test
	public void 이메일_인증() {
		//given
		String authKey = new AuthKey().getKey();
		
		RequestEmailConfirmDto dto = new RequestEmailConfirmDto();
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setAuthKey(authKey);
		dto.setAuthOption(AuthOption.SIGNUP.getValue());
		
		AuthDto authDto = new AuthDto();
		authDto.setEmail("jangsehun1992@gmail.com");
		authDto.setAuthKey(authKey);
		authDto.setAuthOption(AuthOption.SIGNUP.getValue());
		authDto.setExpired(0);
		
		given(authService.getAuth(dto.getEmail())).willReturn(authDto);
		
		//when
		accountService.authConfirm(dto);
		
		//than
		verify(authService).expired(dto);
	}
	
	@Test
	public void 해당_계정_정보_보기() {
		//given
		int accountId = 1;
		
		ResponseAccountInfoDto responseDto = new ResponseAccountInfoDto();
		responseDto.setId(1);
		responseDto.setNickname("tester");
		
		given(accountDao.selectAccountInfo(accountId)).willReturn(responseDto);
		//when
		ResponseAccountInfoDto responseAccountDto = accountService.getAccountInfo(accountId);
		
		//then
		assertNotNull(responseAccountDto);
		assertThat(responseAccountDto.getId(), is(1));
		assertThat(responseAccountDto.getNickname(), is("tester"));
	}
	
	@Test
	public void 가입한_계정_찾기() {
		//given
		RequestFindAccountDto requestDto = new RequestFindAccountDto();
		requestDto.setName("장세훈");
		requestDto.setBirth("920409");
		
		List<ResponseFindAccountDto> accountList = new ArrayList<ResponseFindAccountDto>();
		accountList.add(responseDto);
		
		given(accountDao.selectAccounts(requestDto)).willReturn(accountList);
		
		//when
		accountService.getAccounts(requestDto);
		
		//then
		assertThat(accountList.size(), is(1));
		assertThat(accountList.get(0).getEmail(), is("jangsehun1992@gmail.com"));
	}
	
	@Test(expected = AccountNotFoundException.class)
	public void 가입한_계정이_없는_경우() {
		//given
		RequestFindAccountDto requestDto = new RequestFindAccountDto();
		requestDto.setName("장세훈");
		requestDto.setBirth("920409");
		
		List<ResponseFindAccountDto> accountList = new ArrayList<ResponseFindAccountDto>();

		given(accountDao.selectAccounts(requestDto)).willReturn(accountList);
		
		//when
		accountService.getAccounts(requestDto);
	}
	
	@Test
	public void 회원_정보_수정() {
		//given
		RequestAccountEditDto requestDto = new RequestAccountEditDto();
		requestDto.setNickname("changeNickname");
		
		account.setNickname(requestDto.getNickname());
		
		//when
		accountService.editAccount(account);
		
		//then
		verify(accountDao, times(1)).updateAccount(account);
		
		assertThat(account.getNickname(), is(requestDto.getNickname()));
	}
	
	@Test
	public void 비밀번호_변경() {
		//given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("password1234");
		dto.setAfterPassword("password1234");
		dto.setAfterPasswordCheck("password1234");
		
		given(passwordEncoder.matches(any(), any())).willReturn(true);
		//when
		accountService.changePassword(account, dto);
		
		//then
		verify(accountDao, times(1)).updatePassword(account);
	}
	
	@Test(expected = PasswordCheckFailedException.class)
	public void 비밀번호_변경_할때_바꿀_비밀번호가_다른_경우() {
		//given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("password");
		dto.setAfterPassword("password1234");
		dto.setAfterPasswordCheck("4321drowssap");
		
		//when
		accountService.changePassword(account, dto);
	}
	
	@Test(expected = PasswordNotMatchException.class)
	public void 비밀변호_변경_할때_기존_비밀번호가_다른경우() {
		//given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("password");
		dto.setAfterPassword("password1234");
		dto.setAfterPasswordCheck("password1234");
		
		//when
		accountService.changePassword(account, dto);
	}
	
	@Test
	public void 비밀번호_재설정() {
		//given
		String authKey = new AuthKey().getKey();
		RequestPasswordResetDto dto = new RequestPasswordResetDto();
		dto.setAuthKey(authKey);
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setAuthOption(AuthOption.RESET.getValue());
		dto.setPassword("password");
		dto.setPasswordCheck("password");
		
		AuthDto authDto = new AuthDto();
		authDto.setEmail("jangsehun1992@gmail.com");
		authDto.setAuthKey(authKey);
		authDto.setAuthOption(AuthOption.RESET.getValue());
		authDto.setExpired(0);
		
		given(accountDao.selectAccount(dto.getEmail())).willReturn(account);
		given(authService.checkAuth(dto.toAuthCheckMap())).willReturn(true);
		
		//when
		accountService.resetPassword(dto);
		
		//then
		assertThat(dto.getAuthKey(), is(authDto.getAuthKey()));
	}
	
	@Test(expected = AccountNotFoundException.class)
	public void 비밀번호_재설정_계정_정보를_찾을수_없는_경우() {
		//given
		String authKey = new AuthKey().getKey();
		RequestPasswordResetDto dto = new RequestPasswordResetDto();
		dto.setAuthKey(authKey);
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setAuthOption(AuthOption.RESET.getValue());
		dto.setPassword("password");
		dto.setPasswordCheck("password");
		
		AuthDto authDto = new AuthDto();
		authDto.setEmail("jangsehun1992@gmail.com");
		authDto.setAuthKey(authKey);
		authDto.setAuthOption(AuthOption.RESET.getValue());
		authDto.setExpired(0);
		
		given(accountDao.selectAccount(dto.getEmail())).willReturn(null);
		
		//when
		accountService.resetPassword(dto);
	}
	
	@Test(expected = BadAuthRequestException.class)
	public void 비밀번호_재설정_인증_정보_오류인_경우() {
		//given
		String authKey = new AuthKey().getKey();
		RequestPasswordResetDto dto = new RequestPasswordResetDto();
		dto.setAuthKey(authKey);
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setAuthOption(AuthOption.RESET.getValue());
		dto.setPassword("password");
		dto.setPasswordCheck("password");
		
		AuthDto authDto = new AuthDto();
		authDto.setEmail("jangsehun1992@gmail.com");
		authDto.setAuthKey(authKey);
		authDto.setAuthOption(AuthOption.RESET.getValue());
		authDto.setExpired(1);
		
		given(accountDao.selectAccount(dto.getEmail())).willReturn(account);
		
		//when
		accountService.resetPassword(dto);
	}
	

}
