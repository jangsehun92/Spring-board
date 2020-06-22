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
import java.util.List;

import org.hamcrest.core.IsNull;
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
import jsh.project.board.account.dto.request.RequestAccountCreateDto;
import jsh.project.board.account.dto.request.RequestEmailDto;
import jsh.project.board.account.enums.Role;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
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
	
	
	@Before
	public void setUp() {
		
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
	

}
