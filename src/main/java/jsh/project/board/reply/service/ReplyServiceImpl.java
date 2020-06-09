package jsh.project.board.reply.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.reply.dao.ReplyDao;
import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	private ReplyDao replyDao;
	
	public ReplyServiceImpl(ReplyDao replyDao) {
		this.replyDao = replyDao;
	}
	
	@Override
	public List<ResponseReplyDto> getReplys(int articleId) {
		return replyDao.selectReplys(articleId);
	}
	
	@Transactional
	@Override
	public void saveReply(RequestReplyCreateDto dto) {
		replyDao.insertReply(dto.toReply());
	}
	
	@Transactional
	@Override
	public void modifyReply(RequestReplyUpdateDto dto) {
		replyDao.updateReply(dto.toReply());
	}
	
	@Transactional
	@Override
	public void enabledReply(RequestReplyDeleteDto dto) {
		replyDao.deleteReply(dto.toReply());
	}
	
}
