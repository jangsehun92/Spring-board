package jsh.project.board.reply.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.reply.dao.ReplyDao;
import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;

@Service
public class ReplyServiceImpl implements ReplyService{
	private static final Logger log = LoggerFactory.getLogger(ReplyServiceImpl.class);
	
	private final ReplyDao replyDao;
	
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
		//dto내에 그룹 값이 0이 아니면 대댓글로 본다.
		if(dto.getReplyGroup() != 0) {
			dto.setReplyGroupOrder(replyDao.selectGroupOrderCount(dto.getReplyGroup())+1);
			dto.setReplyDepth(1);
		}else { 
			dto.setReplyGroup(replyDao.selectGroupCount(dto.getArticleId())+1);
			dto.setReplyGroupOrder(1);
			dto.setReplyDepth(0);
		}
		log.info(dto.toString());
		Reply reply = dto.toReply();
		log.info(reply.toString());
		replyDao.insertReply(reply);
	}
	
	@Transactional
	@Override
	public void modifyReply(RequestReplyUpdateDto dto) {
		log.info(dto.toString());
		Reply reply = dto.toReply();
		log.info(reply.toString());
		replyDao.updateReply(reply);
	}
	
	@Transactional
	@Override
	public void enabledReply(RequestReplyDeleteDto dto) {
		log.info(dto.toString());
		Reply reply = dto.toReply();
		log.info(reply.toString());
		replyDao.deleteReply(reply);
	}
	
}
