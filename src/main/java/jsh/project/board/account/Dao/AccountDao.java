package jsh.project.board.account.Dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.account.dto.Account;

@Repository
public class AccountDao{
	
	private SqlSession sqlSession;
	
	public AccountDao(SqlSession sqlSession) {
		System.out.println("Dao생성됨");
		this.sqlSession = sqlSession;
	}
	
	public Account getUserByEmail(String email) {
		System.out.println("다오로 들어옴.getUserByEmail!!!!" + email);
		return sqlSession.selectOne("accountMapper.selectByEmail",email);
	}

}
