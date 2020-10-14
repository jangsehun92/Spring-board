package jsh.project.board.reply.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.board.article.dao.ArticleDao;
import jsh.project.board.article.exception.ArticleNotFoundException;
import jsh.project.board.reply.dao.ReplyDao;
import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;
import jsh.project.board.reply.exception.ReplyNotFoundException;
import jsh.project.board.reply.exception.ReplysNotFoundException;

@Service
public class ReplyServiceImpl implements ReplyService{
	private static final Logger log = LoggerFactory.getLogger(ReplyServiceImpl.class);
	
	private final ReplyDao replyDao;
	private final ArticleDao articleDao;
	
	public ReplyServiceImpl(ReplyDao replyDao, ArticleDao articleDao) {
		this.replyDao = replyDao;
		this.articleDao = articleDao;
	}
	
	@Override
	public List<ResponseReplyDto> getReplys(int articleId) {
		List<ResponseReplyDto> responseDto = replyDao.selectReplys(articleId);
		if(responseDto.isEmpty()) throw new ReplysNotFoundException();
		return responseDto;
	}
	
	@Transactional
	@Override
	public void saveReply(RequestReplyCreateDto dto) {
		if(articleDao.selectArticleCheck(dto.getArticleId()) == 0) throw new ArticleNotFoundException();
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
		if(articleDao.selectArticleCheck(dto.getArticleId()) == 0) throw new ArticleNotFoundException();
		if(replyDao.selectReplyCheck(dto.getId()) == 0) throw new ReplyNotFoundException();
		log.info(dto.toString());
		Reply reply = dto.toReply();
		log.info(reply.toString());
		replyDao.updateReply(reply);
	}
	
	@Transactional
	@Override
	public void enabledReply(RequestReplyDeleteDto dto) {
		if(articleDao.selectArticleCheck(dto.getArticleId()) == 0) throw new ArticleNotFoundException();
		if(replyDao.selectReplyCheck(dto.getId()) == 0) throw new ReplyNotFoundException();
		log.info(dto.toString());
		Reply reply = dto.toReply();
		log.info(reply.toString());
		replyDao.deleteReply(reply);
	}
	
}
