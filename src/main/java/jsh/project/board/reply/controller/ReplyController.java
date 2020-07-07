package jsh.project.board.reply.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;
import jsh.project.board.reply.service.ReplyService;

@Controller
public class ReplyController {
	
	private final ReplyService replyService;
	
	public ReplyController(ReplyService resplyService) {
		this.replyService = resplyService;
	}
	
	// 해당 게시글 전체 댓글 가져오기
	@GetMapping("/replys/{articleId}")
	public ResponseEntity<List<ResponseReplyDto>> replys(@PathVariable("articleId")int articleId){
		return new ResponseEntity<>(replyService.getReplys(articleId), HttpStatus.OK);
	}
	
	// 해당 게시글 댓글 입력
	@PreAuthorize("(#dto.accountId == principal.id)")
	@PostMapping("/reply")
	public ResponseEntity<HttpStatus> createReply(@RequestBody @Valid RequestReplyCreateDto dto){
		replyService.saveReply(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// 해당 게시글 댓글 수정
	@PreAuthorize("(#dto.accountId == principal.id) and (#dto.id == #id)")
	@PatchMapping("/reply/{id}")
	public ResponseEntity<HttpStatus> updateReply(@PathVariable("id")int id, @RequestBody @Valid RequestReplyUpdateDto dto){
		replyService.modifyReply(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// 해당 게시글 댓글 삭제(비활성화)
	@PreAuthorize("((#dto.accountId == principal.id) and (#dto.id == #id)) or (hasAuthority('ROLE_ADMIN'))")
	@DeleteMapping("/reply/{id}")
	public ResponseEntity<HttpStatus> deleteReply(@PathVariable("id")int id, @RequestBody RequestReplyDeleteDto dto){
		replyService.enabledReply(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
