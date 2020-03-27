package jsh.project.board;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context_test.xml")
public class MybatisTest {

	@Inject
	private SqlSessionFactory sqlFactory;
	
	@Test
	public void testFactory() throws Exception {
		//SqlSessionFactory 객체 테스트
		System.out.println("sqlFactory: " + sqlFactory);
	}
	@Test
	public void testSession() throws Exception {
		//SqlSession(MyBatis에 정의된 sql문 호출하는 객체) 객체 테스트
		SqlSession session = sqlFactory.openSession();
		System.out.println(session);
	}
}
