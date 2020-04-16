package jsh.project.board.global.infra.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	private MailUtils sendMail;
	
	public EmailService(JavaMailSender mailSender) throws Exception {
		this.sendMail = new MailUtils(mailSender);
	}
	
	public void sendEmail(String email, String authKey) throws Exception {
		sendMail.setSubject("[ JSH Board Project ] 회원가입 이메일 인증");
		sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
				.append("<p>아래 링크를 클릭하시면 인증이 완료됩니다.</p>")
				.append("<a href='http://localhost:8081/account/registerConfirm?email=")
				.append(email)
				.append("&authKey=")
				.append(authKey)
				.append("' target='_blenk'>인증하기</a>")
				.toString());
		sendMail.setFrom("jangsehun1992@gmail.com", "관리자");
		sendMail.setTo(email);
		sendMail.send();
	}
	
	public void singUpEmail(String email) throws Exception {
		sendMail.setSubject("[ JSH Board Project ] 이메일 인증 완료");
		sendMail.setText(new StringBuffer().append("<h1>[ 이메일 인증 완료 ]</h1>")
				.append("<p>인증이 완료 되었습니다 감사합니다.</p>")
				.toString());
		sendMail.setFrom("jangsehun1992@gmail.com", "관리자");
		sendMail.setTo(email);
		sendMail.send();
	}
	
	public void resendEmail(String email, String authKey) throws Exception {
		sendMail.setSubject("[ JSH Board Project ] (재발송)회원가입 이메일 인증");
		sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
				.append("<p>아래 링크를 클릭하시면 인증이 완료됩니다.</p>")
				.append("<a href='http://localhost:8081/account/registerConfirm?email=")
				.append(email)
				.append("&authKey=")
				.append(authKey)
				.append("' target='_blenk'>인증하기</a>")
				.toString());
		sendMail.setFrom("jangsehun1992@gmail.com", "관리자");
		sendMail.setTo(email);
		sendMail.send();
	}
}