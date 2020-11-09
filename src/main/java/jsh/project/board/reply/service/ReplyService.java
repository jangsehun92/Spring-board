package jsh.project.board.reply.service;

import java.util.List;

import jsh.project.board.reply.dto.request.RequestReplyCreateDto;
import jsh.project.board.reply.dto.request.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.request.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.response.ResponseReplyDto;

public interface ReplyService {
	public List<ResponseReplyDto> getReplys(int articleId);
	public void saveReply(RequestReplyCreateDto dto);
	public void modifyReply(RequestReplyUpdateDto dto);
	public void enabledReply(RequestReplyDeleteDto dto);
}
