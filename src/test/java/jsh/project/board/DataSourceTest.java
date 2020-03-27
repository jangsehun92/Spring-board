package jsh.project.board;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:*/spring/root-context.xml"})
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context_test.xml")
public class DataSourceTest {

	
	@Inject
	private DataSource ds;
	
	
	@Test
	public void testConnection() throws Exception {
		Connection conn = ds.getConnection();
		System.out.println("DB연결 성공!!");
	}
	
}
