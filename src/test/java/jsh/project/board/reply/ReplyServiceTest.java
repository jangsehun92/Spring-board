package jsh.project.board.reply;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
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
import jsh.project.board.reply.dto.request.RequestReplyCreateDto;
import jsh.project.board.reply.dto.request.RequestReplyDeleteDto;
import jsh.project.board.reply.dto.request.RequestReplyUpdateDto;
import jsh.project.board.reply.dto.response.ResponseReplyDto;
import jsh.project.board.reply.service.ReplyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReplyServiceTest {
	private static final Logger log = LoggerFactory.getLogger(ReplyServiceTest.class);
	
	@Mock
	private ReplyDao replyDao;
	
	@InjectMocks
	private ReplyServiceImpl replyService;
	
	@Spy
	private List<ResponseReplyDto> replys = new ArrayList<ResponseReplyDto>();
	
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
		given(replyDao.selectReplys(articleId)).willReturn(replys);
		
		//when
		List<ResponseReplyDto> selectReplys = replyService.getReplys(articleId);
		
		//then
		verify(replyDao, times(1)).selectReplys(articleId);
		assertThat(selectReplys, is(replys));
		
	}
	
	@Test
	public void 댓글_작성() {
		//given
		RequestReplyCreateDto dto = new RequestReplyCreateDto();
		dto.setArticleId(1);
		dto.setAccountId(1);
		dto.setContent("test");
		
		//when
		replyService.saveReply(dto);
		
		//then
		verify(replyDao, times(1)).insertReply(any());
	}
	
	@Test
	public void 댓글_수정() {
		//given
		RequestReplyUpdateDto dto = new RequestReplyUpdateDto();
		dto.setId(1);
		dto.setAccountId(1);
		dto.setArticleId(1);
		dto.setContent("수정");
		
		//when
		replyService.modifyReply(dto);
		
		//then
		verify(replyDao, times(1)).updateReply(any());
	}
	
	@Test
	public void 댓글_삭제() {
		//given
		RequestReplyDeleteDto dto = new RequestReplyDeleteDto();
		dto.setId(1);
		dto.setArticleId(1);
		
		//when
		replyService.enabledReply(dto);
		
		//then
		verify(replyDao, times(1)).deleteReply(any());
	}
	
}
