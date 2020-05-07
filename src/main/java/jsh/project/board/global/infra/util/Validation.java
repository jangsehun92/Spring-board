package jsh.project.board.global.infra.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	private static Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$");
	private static Matcher matcher;
	
	public static boolean passwordValidation(String password) {
		matcher = passwordPattern.matcher(password);
		if(matcher.matches()) {
			return true;
		}
		return false;
	}

}
