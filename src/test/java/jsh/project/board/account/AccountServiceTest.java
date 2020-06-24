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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestAccountEditDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.dto.request.RequestFindAccountDto;
import jsh.project.board.account.dto.request.RequestPasswordDto;
import jsh.project.board.account.dto.response.ResponseAccountDto;
import jsh.project.board.account.dto.response.ResponseFindAccountDto;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.AccountNotFoundException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.account.exception.PasswordNotMatchException;
import jsh.project.board.account.service.AccountServiceImpl;
import jsh.project.board.global.infra.email.EmailService;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
	private static final Logger log = LoggerFactory.getLogger(AccountServiceTest.class);
	
	@Mock
	private AccountDao accountDao;
	
	@Mock
	private AuthDao authDao;
	
	@InjectMocks
	private AccountServiceImpl accountService;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private EmailService emailService;
	
	@Spy
	ResponseFindAccountDto responseDto = new ResponseFindAccountDto();
	
	@Spy
	Account account = new Account();
	
	@Before
	public void setUp() {
		//계정 
		account.setId(1);
		account.setName("장세훈");
		account.setNickname("tester");
		account.setPassword("password");
		account.setBirth("920409");
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
		assertNull(accountDao.findByEmail(dto.getEmail()));
	}
	
	@Test(expected = EmailAlreadyUsedException.class)
	public void 이메일_중복일경우() {
		//given
		RequestEmailDto dto = new RequestEmailDto();
		dto.setEmail("jangsehun1992@gmail.com");
		
		given(accountDao.findEmail(dto)).willReturn(1);
		
		//when
		accountService.emailCheck(dto);
		
		//then
		assertThat(accountDao.findByEmail(dto.getEmail()),is(1));
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
		
		//when
		accountService.register(dto);
		
		//then
		verify(accountDao, times(1)).save(dto);
		verify(passwordEncoder, times(1)).encode("1234");
		verify(authDao, times(1)).authSave(any());
		verify(emailService, times(1)).sendEmail(any());
	}
	
	@Test
	public void 해당_계정_정보_보기() {
		//given
		int accountId = 1;
		
		ResponseAccountDto responseDto = new ResponseAccountDto();
		responseDto.setId(1);
		responseDto.setNickname("tester");
		
		given(accountDao.findById(accountId)).willReturn(responseDto);
		//when
		ResponseAccountDto responseAccountDto = accountService.accountInfo(accountId);
		
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
		
		given(accountDao.findAccount(requestDto)).willReturn(accountList);
		
		//when
		accountService.findAccount(requestDto);
		
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

		given(accountDao.findAccount(requestDto)).willReturn(accountList);
		
		//when
		accountService.findAccount(requestDto);
	}
	
	@Test
	public void 회원_정보_수정() {
		//given
		RequestAccountEditDto requestDto = new RequestAccountEditDto();
		requestDto.setNickname("changeNickname");
		
		account.setNickname(requestDto.getNickname());
		
		//when
		accountService.accountEdit(account);
		
		//then
		verify(accountDao, times(1)).edit(account);
		
		assertThat(account.getNickname(), is(requestDto.getNickname()));
	}
	
	@Test
	public void 비밀번호_변경() {
		//given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("password");
		dto.setAfterPassword("1234");
		dto.setAfterPasswordCheck("1234");
		
		given(passwordEncoder.matches(any(), any())).willReturn(true);
		//when
		accountService.passwordChange(account, dto);
		
		//then
		verify(accountDao, times(1)).updatePassword(account);
	}
	
	@Test(expected = PasswordNotMatchException.class)
	public void 비밀변호_변경_할때_기존_비밀번호가_다른경우() {
		//given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("notMatch");
		dto.setAfterPassword("1234");
		dto.setAfterPasswordCheck("1234");
		
		//when
		accountService.passwordChange(account, dto);
	}
	
	

}
