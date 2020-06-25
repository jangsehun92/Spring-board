package jsh.project.board.global.infra.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jsh.project.board.account.dto.AuthDto;
import jsh.project.board.account.enums.AuthOption;

@Component
public class EmailService {
	private MailUtils sendMail;
	
	public EmailService(JavaMailSender mailSender) throws Exception {
		this.sendMail = new MailUtils(mailSender);
	}
	
	public void sendEmail(AuthDto dto) throws Exception {
		if(dto.getAuthOption().equals(AuthOption.SIGNUP.getValue())) {
			signupEmail(dto);
		}
		
		if(dto.getAuthOption().equals(AuthOption.RESET.getValue())) {
			passwordResetEmail(dto);
		}
	}
	
	public void signupEmail(AuthDto dto) throws Exception {
		sendMail.setSubject("[ JSH Board Project ] 회원가입 이메일 인증");
		sendMail.setText(new StringBuffer().append("<h1>[회원가입 이메일 인증]</h1>")
				.append("<p>아래 링크를 클릭하시면 인증이 완료됩니다.</p>")
				.append("<a href='http://localhost:8081/account/emailConfirm?email=")
				.append(dto.getEmail())
				.append("&authKey=")
				.append(dto.getAuthKey())
				.append("&authOption=")
				.append(dto.getAuthOption())
				.append("' target='_blenk'>인증하기</a>")
				.toString());
		sendMail.setFrom("jangsehun1992@gmail.com", "관리자");
		sendMail.setTo(dto.getEmail());
		sendMail.send();
	}
	
	public void passwordResetEmail(AuthDto dto) throws Exception{
		sendMail.setSubject("[ JSH Board Project ] 비밀번호 재설정 이메일 인증");
		sendMail.setText(new StringBuffer().append("<h1>[비밀번호 재설정 이메일 인증]</h1>")
				.append("<p>아래 링크를 클릭하시면 인증이 완료됩니다.</p>")
				.append("<a href='http://localhost:8081/account/resetConfirm?email=")
				.append(dto.getEmail())
				.append("&authKey=")
				.append(dto.getAuthKey())
				.append("&authOption=")
				.append(dto.getAuthOption())
				.append("' target='_blenk'>인증하기</a>")
				.toString());
		sendMail.setFrom("jangsehun1992@gmail.com", "관리자");
		sendMail.setTo(dto.getEmail());
		sendMail.send();
	}
	
}