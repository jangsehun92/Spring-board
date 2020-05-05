package jsh.project.board.article.dto;

import java.sql.Date;

public class ArticleResponseDto {
	private int id;
	private String nickname;
	private String title;
	private String content;
	private Date regdate;
	
	public ArticleResponseDto() {
		
	}
	
	public ArticleResponseDto(int id, String nickname, String title, String content, Date regdate) {
		this.id = id;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	

}
