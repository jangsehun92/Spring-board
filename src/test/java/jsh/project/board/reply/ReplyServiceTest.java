package jsh.project.board.reply;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jsh.project.board.reply.dao.ReplyDao;
import jsh.project.board.reply.domain.Reply;
import jsh.project.board.reply.dto.RequestReplyCreateDto;
import jsh.project.board.reply.dto.ResponseReplyDto;
import jsh.project.board.reply.service.ReplyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReplyServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(ReplyServiceTest.class);
	@Mock
	private ReplyDao ReplyDao;
	
	@InjectMocks
	private ReplyServiceImpl replyService;
	
	@Spy
	List<ResponseReplyDto> replys = new ArrayList<ResponseReplyDto>();
	
	
	@Before
	public void setUp() {
		log.info("setUp : replys");
		for(int i = 1; i <=10; i++) {
			ResponseReplyDto responseReplyDto = new ResponseReplyDto();
			responseReplyDto.setId(i);
			responseReplyDto.setArticleId(1);
			responseReplyDto.setAccountId(1);
			responseReplyDto.setNickname("tester");
			responseReplyDto.setContent("test"+i);
			replys.add(responseReplyDto);
		}
	}
	
	@Test
	public void 해당_게시글_모든_댓글_보기() {
		//given
		int articleId = 1;
		given(ReplyDao.selectReplys(articleId)).willReturn(replys);
		
		//when
		List<ResponseReplyDto> selectReplys = ReplyDao.selectReplys(articleId);
		
		//then
		verify(ReplyDao).selectReplys(articleId);
		assertThat(selectReplys, is(replys));
		
		for(ResponseReplyDto reply : selectReplys) {
			log.info("responseReplyDto { id : "+ reply.getId() + " articleId : " 
					+ reply.getArticleId()  + " accountId : " + reply.getAccountId() 
					+ " nickname : " + reply.getNickname() + " content : " + reply.getContent() + " }");
		}
	}
	
	@Test
	public void 댓글_작성() {
		//given
		RequestReplyCreateDto dto = new RequestReplyCreateDto();
		dto.setArticleId(1);
		dto.setAccountId(1);
		dto.setContent("test");
		Reply reply = dto.toReply();
		reply.setId(1);
		reply.setRegdate(new Date());
		reply.setEnabled(1);
		
		
		//when
		ReplyDao.insertReply(reply);
		
		//then
		verify(ReplyDao).insertReply(reply);
		assertThat(reply.getId(), is(1));
		assertThat(reply.getArticleId(), is(1));
		assertThat(reply.getAccountId(), is(1));
		assertThat(reply.getContent(), is("test"));
	}
	
	@Test
	public void 댓글_수정() {
		//given
		//when
		//then
	}
	
	@Test
	public void 댓글_삭제() {
		//given
		//when
		//then
	}

}
