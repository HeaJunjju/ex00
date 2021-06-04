package org.zerock.ex00.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex00.common.paging.MyReplyPagingCreatorDTO;
import org.zerock.ex00.common.paging.MyReplyPagingDTO;
import org.zerock.ex00.domain.MyReplyVO;
import org.zerock.ex00.mapper.MyBoardMapper;
import org.zerock.ex00.mapper.MyReplyMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
//@AllArgsConstructor
public class MyReplyServiceImpl implements MyReplyService {

	private MyReplyMapper myReplyMapper;
	private MyBoardMapper myBoardMapper;
	
	//자동주입 방법 1: 생성자 자동 주입 방식으로 주입 시
	public MyReplyServiceImpl(MyReplyMapper myReplyMapper, MyBoardMapper myBoardMapper) {
		super();
		this.myReplyMapper = myReplyMapper;
		this.myBoardMapper = myBoardMapper;
	}
	
	//자동주입 방법 2: @Autowired 를 이용한 자동 주입(setter 자동 주입과 동일)
	// @Autowired
	// private MyReplyMapper myReplyMapper;
	//자동주입 방법 3: setter 를 이용한 자동 주입//lombok 어노테이션 이용
	// @Setter(onMethod_ = @Autowired)
	// private MyReplyMapper myReplyMapper;
	 
	 
	// private MyReplyMapper myReplyMapper;
	// 
	// @Autowired
	// public void setMapper(MyReplyMapper myReplyMapper;) {
	// this.myReplyMapper = myReplyMapper;
	// }
	// //특정 게시물에 대한 댓글 목록 조회(페이징 1-전체댓글 수 고려 안함)
	// @Override
	// public List<MyReplyVO> getReplyListByBno(MyReplyPagingDTO myReplyPaging) {
	// 
	// log.info("댓글-서비스-게시물에 대한 댓글 목록 조회 - 전달된 MyReplyPagingDTO: " + myReplyPaging);
	//
	// return myReplyMapper.selectMyReplyList(myReplyPaging);
	// }
	
	@Override
	public MyReplyPagingCreatorDTO getReplyListByBno(MyReplyPagingDTO myReplyPaging) {
//		log.info("댓글-서비스-게시물에 대한 댓글 목록 조회 - 전달된 MyReplyPagingDTO: "+myReplyPaging);
//		
//		MyReplyPagingCreatorDTO myReplyPagingCreator = 
//				new MyReplyPagingCreatorDTO(myReplyMapper.selectReplyTotalByBno(myReplyPaging), 
//				myReplyPaging,
//				myReplyMapper.selectMyReplyList(myReplyPaging));
//		
//		log.info("댓글-서비스-게시물에 대한 댓글 목록 조회 - 생성된 myReplyPagingCreatorDTO: " + myReplyPagingCreator); 
//		log.info("댓글-서비스-게시물에 대한 댓글 목록 조회 - myReplyPagingCreator 가 컨트롤러로 전달됨"); 
//
//		return myReplyPagingCreator;
		
		log.info("댓글-서비스-게시물에 대한 댓글 목록 조회 - 전달된 MyReplyPagingDTO: " + myReplyPaging); 
		 
		 
		int replyTotalByBno = myReplyMapper.selectReplyTotalByBno(myReplyPaging); 
		 
		 Integer pageNum = myReplyPaging.getPageNum(); 
		 
		if (replyTotalByBno == 0) { 
		 
		myReplyPaging.setPageNum(1); 
		 
		log.info("댓글-서비스- 댓글이 없는 경우, pageNum은 1: 수정된 myReplyPaging: " + myReplyPaging); 
		 
		 MyReplyPagingCreatorDTO myReplyPagingCreator = 
		 
		new MyReplyPagingCreatorDTO(replyTotalByBno, myReplyPaging, null); 
		 
		 
		return myReplyPagingCreator ; 
		 
		} else { 
			 if (pageNum == -1) { 
				 
				 pageNum = (int) Math.ceil(replyTotalByBno/(myReplyPaging.getRowAmountPerPage()*1.0)); 
				  
				 myReplyPaging.setPageNum(pageNum); 
				  
				 log.info("댓글-서비스-댓글추가 후, 마지막 댓글 페이지로 이동(myReplyPaging): " + myReplyPaging); 
			 } 
				  
			MyReplyPagingCreatorDTO myReplyPagingCreator = 
				  
				 new MyReplyPagingCreatorDTO( 
				  
				 myReplyMapper.selectReplyTotalByBno(myReplyPaging), 
				 myReplyPaging, 
				 myReplyMapper.selectMyReplyList(myReplyPaging)); 
				  
			log.info("댓글-서비스-게시물에 대한 댓글 목록 조회 - 생성된 myReplyPagingCreatorDTO: " + myReplyPagingCreator); 
				  
			log.info("댓글-서비스-게시물에 대한 댓글 목록 조회 - myReplyPagingCreator가 컨트롤러로 전달됨"); 
				  
			return myReplyPagingCreator; 
		}
	}
	
	//특정 게시물의 댓글 총 개수확인
	@Override
	public long getReplyTotalByBno(MyReplyPagingDTO myReplyPaging) {
	 return myReplyMapper.selectReplyTotalByBno(myReplyPaging);
	}

	@Transactional
	@Override
	public long registerReplyForBoard(MyReplyVO myReply) {
		log.info("댓글-서비스-게시물에 대한 댓글 등록 시 처음 전달된 myReplyVO: " + myReply); 
		 
		//게시물 댓글 수 증가
		myBoardMapper.updateBReplyCnt(myReply.getBno(), 1); 
		 
		//게시물에 대한 댓글 등록 처리
		myReplyMapper.insertMyReplyForBoard(myReply); 
		 
		log.info("댓글-서비스 - 게시물에 대한 댓글 등록: 등록된 myReply: " + myReply); 
		log.info("댓글-서비스 - 게시물에 대한 댓글 등록: 등록된 rno(byGetter): " + myReply.getRno()); 
		log.info("댓글-서비스 - 게시물에 대한 댓글 등록: 등록된 rno 가 컨트롤러로 전달됨 " ); 
		
		return myReply.getRno();
	}

	@Transactional
	@Override
	public long registerReplyForReply(MyReplyVO myReply) {
		log.info("댓글-서비스-게시물에 대한 댓글 등록 시 myReplyVO: " + myReply); 
		 
		//게시물 댓글 수 증가
		myBoardMapper.updateBReplyCnt(myReply.getBno(), 1); 
		 
		//게시물에 대한 댓글 등록 처리
		//myReplyMapper.insertMyReplyForBoard(myReply); 

		//게시물의 댓글에 대한 댓글 등록 처리
		myReplyMapper.insertMyReplyForReply(myReply); 
		 
		log.info("댓글-서비스 - 댓글에 대한 댓글 등록: 등록된 myReply: " + myReply); 
		log.info("댓글-서비스 - 댓글에 대한 댓글 등록: 등록된 rno(ByGetter): " + myReply.getRno() ); 
		log.info("댓글-서비스 - 댓글에 대한 댓글 등록: 등록된 rno 가 컨트롤러로 전달됨 " ); 
		 
		return myReply.getRno(); 
	}

	@Override
	public MyReplyVO getReply(long bno, long rno) {
		log.info("댓글-서비스-댓글-조회 시 전달된 bno: " + bno); 
		log.info("댓글-서비스-댓글-조회 시 전달된 rno: " + rno); 
		 
		MyReplyVO myReply = myReplyMapper.selectMyReply(bno, rno); 
		 
		log.info("댓글-서비스-댓글-조회: 매퍼로부터 전달된 myReply: " + myReply); 
		log.info("댓글-서비스-댓글-조회: myReply 가 컨트롤러로 전달됨"); 
		 
		return myReply; 
	}

	@Override
	public int modifyReply(MyReplyVO myReply) {
		log.info("댓글-서비스-댓글-수정시 전달된 myReplyVO: " + myReply); 
		Integer modCnt = myReplyMapper.updateMyReply(myReply); 
		 
		log.info("댓글-서비스-수정된 댓글 개수: " + modCnt); 
		
		return modCnt;
	}

	@Transactional
	@Override
	public int removeReply(long bno, long rno) {
		log.info("댓글-서비스-댓글-삭제시 전달된 bno: " + bno); 
		log.info("댓글-서비스-댓글-삭제시 전달된 rno: " + rno); 
		 
		//댓글 삭제: 댓글 개수 감소
		myBoardMapper.updateBReplyCnt(bno, -1); 
		 
		Integer delCnt = myReplyMapper.deleteMyReply(bno, rno); 
		 
		log.info("댓글-서비스-삭제된 댓글 개수: " + delCnt); 
		 
		return delCnt;
	}


}
