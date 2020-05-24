package jsh.project.board.reply.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jsh.project.board.account.dto.Account;
import jsh.project.board.article.exception.NonLoginException;
import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;
import jsh.project.board.reply.service.ReplyService;

@Controller
public class ReplyController {
	
	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	
	private ReplyService replyService;
	
	public ReplyController(ReplyService resplyService) {
		this.replyService = resplyService;
	}
	
	//해당 게시글 전체 댓글 가져오기
	@GetMapping("/replys/{articleId}")
	public ResponseEntity<List<ResponseReplyDto>> replys(@PathVariable("articleId")int articleId){
		log.info("GET /replys/"+articleId);
		return new ResponseEntity<>(replyService.getReplys(articleId), HttpStatus.OK);
	}
	
	//해당 게시글 댓글 입력
	@PostMapping("/reply")
	public ResponseEntity<HttpStatus> createReply(@RequestBody @Valid RequestReplyCreateDto dto, Principal principal){
		if(principal == null) {
			throw new NonLoginException();
		}
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		dto.setAccountId(account.getId());
		log.info("POST /reply "+ dto.toString());
		//principal값과 dto의 accountId값이 같아야하는지 확인해야하는데 어캐하징
		replyService.saveReply(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//해당 게시글 댓글 수정
	@PatchMapping("/reply/{id}")
	public ResponseEntity<HttpStatus> updateReply(@PathVariable("id")int id, @RequestBody @Valid RequestReplyUpdateDto dto, Principal principal){
		if(principal == null) {
			throw new NonLoginException();
		}
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		dto.setId(id);
		dto.setAccountId(account.getId());
		log.info("PATCH /reply/"+id + " " + dto.toString());
		replyService.modifyReply(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//해당 게시글 댓글 삭제(비활성화)
	@DeleteMapping("/reply/{id}")
	public ResponseEntity<HttpStatus> deleteReply(@PathVariable("id")int id,@RequestBody RequestReplyDeleteDto dto, Principal principal){
		if(principal == null) {
			throw new NonLoginException();
		}
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		dto.setId(id);
		dto.setAccountId(account.getId());
		log.info("DELETE /reply/"+id + " " + dto.toString());
		replyService.enabledReply(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
