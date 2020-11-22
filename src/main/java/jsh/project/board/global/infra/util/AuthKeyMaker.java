package jsh.project.board.global.infra.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class AuthKeyMaker {

	private int size = 64;
	private boolean lowerCheck = false;
	
	public String getKey() {
		return init();
	}
	
	private String init() {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		
		int num = 0;
		
		while(sb.length() < size) {
			num = random.nextInt(75) + 48;
			
			if( (num >= 48 && num <=57) || (num >= 65 && num <= 90) || (num >= 97 && num <=122) ) {
				sb.append((char)num);
			}
		}
		
		if(lowerCheck) {
			return sb.toString().toLowerCase();
		}
		
		return sb.toString();
	}
}