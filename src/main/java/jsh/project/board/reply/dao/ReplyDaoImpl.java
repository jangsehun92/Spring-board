package jsh.project.board.reply.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.dto.ResponseReplyDto;

@Repository
public class ReplyDaoImpl implements ReplyDao{
	
	private final SqlSession sqlSession;
	
	public ReplyDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public List<ResponseReplyDto> selectReplys(int articleId) {
		return sqlSession.selectList("replyMapper.selectReplys", articleId);
	}
	
	@Override
	public int selectGroupCount(int articleId) {
		return sqlSession.selectOne("replyMapper.selectGroupCount",articleId);
	}
	
	@Override
	public int selectGroupOrderCount(int group) {
		return sqlSession.selectOne("replyMapper.selectGroupOrderCount", group);
	}
	
	@Override
	public void insertReply(final Reply reply) {
		sqlSession.insert("replyMapper.insertReply", reply);
	}
	
	@Override
	public void updateReply(final Reply reply) {
		sqlSession.update("replyMapper.updateReply", reply);
	}
	
	@Override
	public void deleteReply(final Reply reply) {
		sqlSession.update("replyMapper.deleteReply", reply);
	}

}
