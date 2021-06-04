package org.zerock.ex00.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.zerock.ex00.common.paging.MyReplyPagingDTO;
import org.zerock.ex00.domain.MyReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
// Java Config
// @ContextConfiguration(classes = {com.spring5212.config.MybatisConfig.class} )
@Log4j
public class MyReplyMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private MyReplyMapper myReplyMapper;
	
//	//매퍼인스턴스 생성 테스트
	@Test
	public void testMapper() {

		log.info(myReplyMapper);
	}

	//특정 게시물에 대한 댓글 목록 조회 테스트
//	@Test
//	public void testSelectMyReplyList() {
//
//		Long targetBno = 229376L;
//
//		List<MyReplyVO> myReplies = myReplyMapper.selectMyReplyList(targetBno);
//		
//		myReplies.forEach(myReply -> System.out.println(myReply));
//
//	}
//	//특정 게시물에 대한 댓글 목록 조회(페이징) 테스트
//@Test
//public void testSelectMyReplyListPaging() {
//
//	Long targetBno = 229376L;
//			
//	MyReplyPagingDTO myReplyPagingDTO = new MyReplyPagingDTO(targetBno, 1);
//	
//	long totalReplyCnt = myReplyMapper.selectReplyTotalByBno(myReplyPagingDTO);
//	log.info("댓글 총 개수: "+ totalReplyCnt);
//	
//	List<MyReplyVO> myReplies = myReplyMapper.selectMyReplyList(myReplyPagingDTO);
//			
//	myReplies.forEach(myReply -> System.out.println(myReply));
//}	
//	
//	
//	
//	//특정 게시물에 대한 댓글 등록 테스트
//	@Test
//	public void testInsertMyReplyForBoard() {
//
//		MyReplyVO myReply = new MyReplyVO();
//		
//		myReply.setBno(229376L);
//		myReply.setRcontent("매퍼 댓글 입력 테스트 ");
//		myReply.setRwriter("replyer");
//		 
//		log.info("매퍼 - 추가 전 댓글 객체: " + myReply);
//		
//		myReplyMapper.insertMyReplyForBoard(myReply);
//		
//		log.info("매퍼 - 추가 후 댓글 객체: " + myReply);
//		
//		myReplyMapper.selectMyReply(229376L, myReply.getRno());
//
//	}
//	
//	
//	//특정 게시물에 대한 댓글의 댓글 등록 테스트
//	@Test
//	public void testInsertMyReplyForReply() {
//
//		MyReplyVO myReply = new MyReplyVO();
//		
//		myReply.setBno(229376L);
//		myReply.setRcontent("매퍼 댓글의 댓글 입력 테스트 ");
//		myReply.setRwriter("test03");
//		myReply.setPrno(1L);
//		 
//		log.info("매퍼 - 추가 전 댓글 객체: " + myReply);
//		
//		myReplyMapper.insertMyReplyForReply(myReply);
//		
//		log.info("매퍼 - 추가 후 댓글 객체: " + myReply);
//		
//		myReplyMapper.selectMyReply(229376L, myReply.getRno());
//
//	}
//
//	//특정 게시물에 대한 특정 댓글 조회 테스트
//	@Test
//	public void testSelectMyReply() {
//
//		Long targetBno = 229376L;
//		Long targetRno = 2L;
//		
//		//458752L
//		MyReplyVO myReply = myReplyMapper.selectMyReply(targetBno, targetRno);
//
//		log.info(myReply);
//
//	}
//	
//	//특정 게시물에 대한 특정 댓글 수정 테스트
//	@Test
//	public void testUpdateMyReply() {
//
//		Long targetBno = 229376L;
//		Long targetRno = 4L;
//
//		MyReplyVO myReply = myReplyMapper.selectMyReply(targetBno, targetRno);
//
//		myReply.setRcontent("매퍼 - 수정 테스트..");
//
//		int count = myReplyMapper.updateMyReply(myReply);
//
//		log.info("UPDATE COUNT: " + count);	
//		log.info(myReplyMapper.selectMyReply(targetBno, targetRno));
//	
//	}
//	
//	//특정 게시물에 대한 특정 댓글 삭제 테스트
//	@Test
//	public void testDeleteMyReply() {
//
//		Long targetBno = 229376L;
//		Long targetRno = 4L;
//
//
//	int count = myReplyMapper.deleteMyReply(targetBno, targetRno);
//
//	log.info("DELETE COUNT: " + count);
//	log.info(myReplyMapper.selectMyReply(targetBno, targetRno));
//
//	}

	
	
}
