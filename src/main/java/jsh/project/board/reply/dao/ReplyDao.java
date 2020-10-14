package jsh.project.board.reply.dao;

import java.util.List;

import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.dto.ResponseReplyDto;

public interface ReplyDao {
	public List<ResponseReplyDto> selectReplys(int articleId);
	public int selectGroupCount(int articleId);
	public int selectGroupOrderCount(int replyGroup);
	public void insertReply(final Reply reply);
	public void updateReply(final Reply reply);
	public void deleteReply(final Reply reply);
	public int selectReplyCheck(int id);
}
