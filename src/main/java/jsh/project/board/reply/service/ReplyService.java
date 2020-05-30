package jsh.project.board.reply.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jsh.project.board.reply.dao.ReplyDao;
import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;

@Service
public class ReplyService {
	
	private ReplyDao replyDao;
	
	public ReplyService(ReplyDao replyDao) {
		this.replyDao = replyDao;
	}
	
	public List<ResponseReplyDto> getReplys(int articleId) {
		return replyDao.selectReplys(articleId);
	}
	
	public void saveReply(RequestReplyCreateDto dto) {
		replyDao.insertReply(dto.toReply());
	}
	
	public void modifyReply(RequestReplyUpdateDto dto) {
		replyDao.updateReply(dto.toReply());
	}
	
	public void enabledReply(RequestReplyDeleteDto dto) {
		replyDao.deleteReply(dto.toReply());
	}
	
}
