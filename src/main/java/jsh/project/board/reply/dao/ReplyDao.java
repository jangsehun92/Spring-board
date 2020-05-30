package jsh.project.board.reply.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.dto.ResponseReplyDto;

@Repository
public class ReplyDao {
	
	private SqlSession sqlSession;
	
	public ReplyDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<ResponseReplyDto> selectReplys(int articleId) {
		return sqlSession.selectList("replyMapper.selectReplys", articleId);
	}
	
	public void insertReply(final Reply reply) {
		sqlSession.insert("replyMapper.insertReply", reply);
	}
	
	public void updateReply(final Reply reply) {
		sqlSession.update("replyMapper.updateReply", reply);
	}
	
	public void deleteReply(final Reply reply) {
		sqlSession.update("replyMapper.deleteReply", reply);
	}

}
