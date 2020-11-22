package jsh.project.board.account;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import jsh.project.board.account.dao.AccountDao;
import jsh.project.board.account.dao.AuthDao;
import jsh.project.board.account.domain.Account;
import jsh.project.board.account.domain.Auth;
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
import jsh.project.board.account.exception.AccountNotFoundException;
import jsh.project.board.account.exception.EmailAlreadyUsedException;
import jsh.project.board.account.exception.PasswordCheckFailedException;
import jsh.project.board.account.exception.PasswordNotMatchException;
import jsh.project.board.account.service.AccountServiceImpl;
import jsh.project.board.account.service.AuthServiceImpl;
import jsh.project.board.global.infra.email.EmailService;
import jsh.project.board.global.infra.util.AuthKeyMaker;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@Mock
	private AccountDao accountDao;

	@Mock
	private AuthDao authDao;

	@InjectMocks
	private AccountServiceImpl accountService;

	@InjectMocks
	private AuthServiceImpl authService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private EmailService emailService;


	@Before
	public void setUp() {
		
	}

	@Test
	public void 이메일_중복체크() {
		// given
		RequestEmailDto dto = new RequestEmailDto();
		dto.setEmail("jangsehun1992@gmail.com");

		// when
		accountService.emailCheck(dto);

		// then
		assertNull(accountDao.selectAccount(dto.getEmail()));
	}

	@Test(expected = EmailAlreadyUsedException.class)
	public void 이메일_중복일경우() {
		// given
		RequestEmailDto dto = new RequestEmailDto();
		dto.setEmail("jangsehun1992@gmail.com");

		given(accountDao.selectEmailCount(dto)).willReturn(1);

		// when
		accountService.emailCheck(dto);

		// then
		assertThat(accountDao.selectAccount(dto.getEmail()), is(1));
	}

	@Test
	public void 회원가입() throws Exception {
		// given
		RequestAccountCreateDto dto = new RequestAccountCreateDto();
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setPassword("password");
		dto.setPasswordCheck("password");
		dto.setName("장세훈");
		dto.setNickname("tester");
		dto.setBirth("920409");

		// when
		accountService.register(dto);

		// then
		verify(accountDao, times(1)).insertAccount(any());
	}

	@Test
	public void 이메일_인증() throws Exception{
		// given
		String authKey = new AuthKeyMaker().getKey();
		AuthOption authOption = AuthOption.SIGNUP;

		RequestEmailConfirmDto dto = new RequestEmailConfirmDto();
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setAuthKey(authKey);
		dto.setAuthOption(authOption.getValue());
		
		Auth auth = Auth.from(dto);
		Field field = null;
		// Auth email값 설정
		field = Auth.class.getDeclaredField("email");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(auth, "jangsehun1992@gmail.com");
		
		// Auth authKey값 설정
		field = Auth.class.getDeclaredField("authKey");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(auth, authKey);

		// Auth authOption값 설정
		field = Auth.class.getDeclaredField("authOption");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(auth, authOption.getValue());

		// Auth expried값 설정
		field = Auth.class.getDeclaredField("expired");
		field.setAccessible(true); // access 가능하도록 변경
		field.setBoolean(auth, false);
		
		given(authDao.selectAuth(dto.getEmail())).willReturn(auth);

		// when
		authService.authConfirm(dto);

		// than
		verify(authDao).deleteAuth(any());
		assertThat(auth.getAuthKey(), is(authKey));
	}

	@Test
	public void 해당_계정_정보_보기() throws Exception {
		// given
		int accountId = 1;

		
		// 클래스 정보
		Class<Account> accountClass = Account.class;
		// 해당 클래스 생성자 불러오기 ( getDeclaredXXX() : 접근제한자에 상관없이 모든 속성을 가져올 수 있다. )
		Constructor<Account> constructors = accountClass.getDeclaredConstructor(new Class[] {});
		// 클래스 생성자 접근가능 처리 (private 타입의 생성자에 접근 제한을 허락)
		constructors.setAccessible(true); // access 가능하도록 변경
		// 생성자를 통한 인스턴스 생성
		Account account = (Account) constructors.newInstance();

		// 필드값 설정
		Field field = null;
		// Account id값 설정
		field = accountClass.getDeclaredField("id");
		field.setAccessible(true); // access 가능하도록 변경
		field.setInt(account, accountId);

		// Account nickname값 설정
		field = accountClass.getDeclaredField("nickname");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(account, "tester");
		System.out.println(account.toString());

		given(accountDao.selectAccountInfo(accountId)).willReturn(account);
		// when
		ResponseAccountInfoDto responseAccountDto = accountService.getAccountInfo(accountId);

		// then
		assertNotNull(responseAccountDto);
		assertThat(responseAccountDto.getId(), is(1));
		assertThat(responseAccountDto.getNickname(), is("tester"));
	}

	@Test
	public void 가입한_계정_찾기() throws Exception{
		// given
		RequestFindAccountDto requestDto = new RequestFindAccountDto();
		requestDto.setName("장세훈");
		requestDto.setBirth("920409");
		
		// 클래스 정보
		Class<Account> accountClass = Account.class;
		// 해당 클래스 생성자 불러오기 ( getDeclaredXXX() : 접근제한자에 상관없이 모든 속성을 가져올 수 있다. )
		Constructor<Account> constructors = accountClass.getDeclaredConstructor(new Class[] {});
		// 클래스 생성자 접근가능 처리 (private 타입의 생성자에 접근 제한을 허락)
		constructors.setAccessible(true); // access 가능하도록 변경
		// 생성자를 통한 인스턴스 생성
		Account account = (Account) constructors.newInstance();

		// 필드값 설정
		Field field = null;
		// Account email값 설정
		field = accountClass.getDeclaredField("email");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(account, "jangsehun1992@gmail.com");

		// Account regdate값 설정
		field = accountClass.getDeclaredField("regdate");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(account, new Date());
		System.out.println(account.toString());

		List<Account> accountList = new ArrayList<Account>();
		accountList.add(account);

		given(accountDao.selectAccounts(requestDto)).willReturn(accountList);

		// when
		List<ResponseFindAccountDto> responseAccountList = accountService.getAccounts(requestDto);

		// then
		assertThat(responseAccountList.size(), is(1));
		assertThat(responseAccountList.get(0).getEmail(), is("jangsehun1992@gmail.com"));
	}

	@Test(expected = AccountNotFoundException.class)
	public void 가입한_계정이_없는_경우() {
		// given
		RequestFindAccountDto requestDto = new RequestFindAccountDto();
		requestDto.setName("장세훈");
		requestDto.setBirth("920409");

		List<Account> accountList = new ArrayList<Account>();

		given(accountDao.selectAccounts(requestDto)).willReturn(accountList);

		// when
		accountService.getAccounts(requestDto);
	}

	@Test
	public void 회원_정보_수정() throws Exception{
		// given
		RequestAccountEditDto requestDto = new RequestAccountEditDto();
		requestDto.setNickname("changeNickname");
		
		// 클래스 정보
		Class<Account> accountClass = Account.class;
		// 해당 클래스 생성자 불러오기 ( getDeclaredXXX() : 접근제한자에 상관없이 모든 속성을 가져올 수 있다. )
		Constructor<Account> constructors = accountClass.getDeclaredConstructor(new Class[] {});
		// 클래스 생성자 접근가능 처리 (private 타입의 생성자에 접근 제한을 허락)
		constructors.setAccessible(true); // access 가능하도록 변경
		// 생성자를 통한 인스턴스 생성
		Account account = (Account) constructors.newInstance();

		// 필드값 설정
		Field field = null;
		// Account nickname값 설정
		field = accountClass.getDeclaredField("nickname");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(account, "nickname");
		System.out.println(account.toString());

		account.changeNickname(requestDto.getNickname());

		// when
		accountService.editAccount(account);

		// then
		verify(accountDao, times(1)).updateAccount(account);

		assertThat(account.getNickname(), is(requestDto.getNickname()));
	}

	@Test
	public void 비밀번호_변경() throws Exception{
		// given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("b_password");
		dto.setAfterPassword("a_password");
		dto.setAfterPasswordCheck("a_password");
		
		// 클래스 정보
		Class<Account> accountClass = Account.class;
		// 해당 클래스 생성자 불러오기 ( getDeclaredXXX() : 접근제한자에 상관없이 모든 속성을 가져올 수 있다. )
		Constructor<Account> constructors = accountClass.getDeclaredConstructor(new Class[] {});
		// 클래스 생성자 접근가능 처리 (private 타입의 생성자에 접근 제한을 허락)
		constructors.setAccessible(true); // access 가능하도록 변경
		// 생성자를 통한 인스턴스 생성
		Account account = (Account) constructors.newInstance();

		// 필드값 설정
		Field field = null;
		// Account password값 설정
		field = accountClass.getDeclaredField("password");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(account, "b_password");

		given(passwordEncoder.matches(any(), any())).willReturn(true);
		// when
		accountService.changePassword(account, dto);

		// then
		verify(accountDao, times(1)).updatePassword(account);
	}

	@Test(expected = PasswordCheckFailedException.class)
	public void 비밀번호_변경_할때_바꿀_비밀번호가_다른_경우() throws Exception{
		// given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("password");
		dto.setAfterPassword("password1234");
		dto.setAfterPasswordCheck("4321drowssap");
		
		// 클래스 정보
		Class<Account> accountClass = Account.class;
		// 해당 클래스 생성자 불러오기 ( getDeclaredXXX() : 접근제한자에 상관없이 모든 속성을 가져올 수 있다. )
		Constructor<Account> constructors = accountClass.getDeclaredConstructor(new Class[] {});
		// 클래스 생성자 접근가능 처리 (private 타입의 생성자에 접근 제한을 허락)
		constructors.setAccessible(true); // access 가능하도록 변경
		// 생성자를 통한 인스턴스 생성
		Account account = (Account) constructors.newInstance();

		// 필드값 설정
		Field field = null;
		// Account password값 설정
		field = accountClass.getDeclaredField("password");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(account, "password");
		System.out.println(account.toString());

		// when
		accountService.changePassword(account, dto);
	}

	@Test(expected = PasswordNotMatchException.class)
	public void 비밀변호_변경_할때_기존_비밀번호가_다른경우() throws Exception{
		// given
		RequestPasswordDto dto = new RequestPasswordDto();
		dto.setBeforePassword("password");
		dto.setAfterPassword("password1234");
		dto.setAfterPasswordCheck("password1234");
		
		// 클래스 정보
		Class<Account> accountClass = Account.class;
		// 해당 클래스 생성자 불러오기 ( getDeclaredXXX() : 접근제한자에 상관없이 모든 속성을 가져올 수 있다. )
		Constructor<Account> constructors = accountClass.getDeclaredConstructor(new Class[] {});
		// 클래스 생성자 접근가능 처리 (private 타입의 생성자에 접근 제한을 허락)
		constructors.setAccessible(true); // access 가능하도록 변경
		// 생성자를 통한 인스턴스 생성
		Account account = (Account) constructors.newInstance();

		// 필드값 설정
		Field field = null;
		// Account password값 설정
		field = accountClass.getDeclaredField("password");
		field.setAccessible(true); // access 가능하도록 변경
		field.set(account, "password_not_match");
		System.out.println(account.toString());

		// when
		accountService.changePassword(account, dto);
	}

	@Test
	public void 비밀번호_재설정() throws Exception{
		// given
		String authKey = new AuthKeyMaker().getKey();
		RequestPasswordResetDto dto = new RequestPasswordResetDto();
		dto.setAuthKey(authKey);
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setAuthOption(AuthOption.RESET.getValue());
		dto.setPassword("resetPassword");
		dto.setPasswordCheck("resetPassword");
		
		// 클래스 정보
		Class<Account> accountClass = Account.class;
		// 해당 클래스 생성자 불러오기 ( getDeclaredXXX() : 접근제한자에 상관없이 모든 속성을 가져올 수 있다. )
		Constructor<Account> constructors = accountClass.getDeclaredConstructor(new Class[] {});
		// 클래스 생성자 접근가능 처리 (private 타입의 생성자에 접근 제한을 허락)
		constructors.setAccessible(true); // access 가능하도록 변경
		// 생성자를 통한 인스턴스 생성
		Account account = (Account) constructors.newInstance();

		given(accountDao.selectAccount(dto.getEmail())).willReturn(account);

		// when
		accountService.resetPassword(dto);

		// then
		assertThat(dto.getPassword(), is(dto.getPasswordCheck()));
	}

	@Test(expected = AccountNotFoundException.class)
	public void 비밀번호_재설정_계정_정보를_찾을수_없는_경우() {
		// given
		String authKey = new AuthKeyMaker().getKey();
		RequestPasswordResetDto dto = new RequestPasswordResetDto();
		dto.setAuthKey(authKey);
		dto.setEmail("jangsehun1992@gmail.com");
		dto.setAuthOption(AuthOption.RESET.getValue());
		dto.setPassword("password");
		dto.setPasswordCheck("password");

		given(accountDao.selectAccount(dto.getEmail())).willReturn(null);

		// when
		accountService.resetPassword(dto);
	}

}
