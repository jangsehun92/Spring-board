package jsh.project.board.reply.domain;

import java.util.Date;

import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.RequestReplyUpdateDto;

public class Reply {
	
	private int id;
	private int articleId;
	private int accountId;
	private int replyGroup;
	private int replyGroupOrder;
	private int replyDepth;
	private String content;
	private Date regdate;
	private Date modifyDate;
	private int enabled;
	
	public Reply(RequestReplyCreateDto dto) {
		this.articleId = dto.getArticleId();
		this.accountId = dto.getAccountId();
		this.replyGroup = dto.getReplyGroup();
		this.replyGroupOrder = dto.getReplyGroupOrder();
		this.replyDepth = dto.getReplyDepth();
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
	
	public int getReplyGroup() {
		return replyGroup;
	}

	public int getReplyGroupOrder() {
		return replyGroupOrder;
	}

	public int getReplyDepth() {
		return replyDepth;
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
		return "Reply { id : " + id 
					+ " articleId : " + articleId 
					+ " accountId : " + accountId
					+ " replyGroup : " + replyGroup 
					+ " replyGroupOrder : " + replyGroupOrder
					+ " replyDepth : " + replyDepth
					+ " content : " + content + " regdate : " + regdate 
					+ " modifyDate " + modifyDate 
					+ " enabled : " + enabled + " } ";
	}
	
	public interface ReplyConverter{
		Reply toReply();
	}
	
}
