package jsh.project.board;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTest {
	private static final Logger logger = LoggerFactory.getLogger(JdbcTest.class);
	
	@Test
	public void jdbc_연결_테스트() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
			logger.info("DB연결 성공 | " + con);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				con.close();
			}
		}
	}
	
	@Test
	public void jdbc_연결_테스트2() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//try-with구문: JDK7버전부터 지원
		//형식) try(AutoCloseable객체){작업}catch(Exception e){예외처리}
		//---> finally{con.close(); } 문 생략하는 것이 가능
		try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle")){
			logger.info("DB연결 성공 | " + con);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
