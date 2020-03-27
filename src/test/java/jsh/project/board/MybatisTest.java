package jsh.project.board;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context_test.xml")
public class MybatisTest {
	private static final Logger logger = LoggerFactory.getLogger(MybatisTest.class);

	@Inject
	private SqlSessionFactory sqlFactory;
	
	@Test
	public void sqlSessionFactory_테스트() throws Exception {
		logger.info("sqlSessionFactory : " + sqlFactory);
	}
	
	@Test
	public void sqlSession_테스트() throws Exception {
		//SqlSession(MyBatis에 정의된 sql문 호출하는 객체) 객체 테스트
		SqlSession session = sqlFactory.openSession();
		logger.info("sqlSesion : " + session);
	}
}
