package jsh.project.board;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context_test.xml")
public class DataSourceTest {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceTest.class);

	@Inject
	private DataSource ds;
	
	@Test
	public void 데이터소스_연결테스트() throws Exception {
		Connection con = ds.getConnection();
		logger.info("DataSource : "+con);
	}
	
}
