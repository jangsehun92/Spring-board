package jsh.project.board.reply.service;

import java.util.List;

import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;

public interface ReplyService {
	
	public List<ResponseReplyDto> getReplys(int articleId);
	public void saveReply(RequestReplyCreateDto dto);
	public void modifyReply(RequestReplyUpdateDto dto);
	public void enabledReply(RequestReplyDeleteDto dto);
}
