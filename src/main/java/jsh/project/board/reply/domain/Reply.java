package jsh.project.board.reply.domain;

import java.util.Date;

import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;

public class Reply {
	
	private int id;
	private int articleId;
	private int accountId;
	private String content;
	private Date regdate;
	private Date modifyDate;
	private int enabled;
	
	public Reply(RequestReplyCreateDto dto) {
		this.articleId = dto.getArticleId();
		this.accountId = dto.getAccountId();
		this.content = dto.getContent();
		this.regdate = new Date();
		this.enabled = 1;
	}
	
	public Reply(RequestReplyUpdateDto dto) {
		this.id = dto.getId();
		this.articleId = dto.getArticleId();
		this.accountId = dto.getAccountId();
		this.content = dto.getContent();
		this.modifyDate = new Date();
	}
	
	public Reply(RequestReplyDeleteDto dto) {
		this.id = dto.getId();
		this.articleId = dto.getArticleId();
		this.enabled = 0;
	}
	
	public int getId() {
		return id;
	}
	
	public int getArticleId() {
		return articleId;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getContent() {
		return content;
	}

	public Date getRegdate() {
		return regdate;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public int getEnabled() {
		return enabled;
	}
	
	@Override
	public String toString() {
		return "Reply { id : " + id + " articleId : " + articleId 
					+ " accountId : " + accountId + " content : " + content 
					+ " regdate : " + regdate + " modifyDate " + modifyDate 
					+ " enabled : " + enabled + " } ";
	}
	
	public interface ReplyConverter{
		Reply toReply();
	}
	
}
