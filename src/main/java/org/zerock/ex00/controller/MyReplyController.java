package org.zerock.ex00.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ex00.common.paging.MyReplyPagingCreatorDTO;
import org.zerock.ex00.common.paging.MyReplyPagingDTO;
import org.zerock.ex00.domain.MyReplyVO;
import org.zerock.ex00.service.MyReplyService;

import lombok.extern.log4j.Log4j;

@RequestMapping(value= {"/replies"})
@RestController//컨트롤러 클래스 내에 메소드들은 JSP파일을 호출할 수 없습니다.(이유: 데이터만 전달해 주므로)
@Log4j
//@AllArgsConstructor
public class MyReplyController {
	private MyReplyService myReplyService;
	
	public MyReplyController(MyReplyService myReplyService) {
		this.myReplyService=myReplyService;
	}
	
	/* 
	//- 컨트롤로의 매핑 URL 목록 
	//게시물에 대한 댓글 목록 조회: GET /replies/pages/{bno}/{page} 
	//게시물에 대한 댓글 등록(rno 반환):POST /replies/{bno}/new 
	//게시물에 대한 댓글의 댓글 등록: (rno 반환): POST /replies/{bno}/{prno}/new 
	//게시물에 대한 특정 댓글 조회: GET /replies/{bno}/{rno} 
	//게시물에 대한 특정 댓글 수정: PUT:PATCH /replies/{bno}/{rno} 
	//게시물에 대한 특정 댓글 삭제: DELETE:  /replies/{bno}/{rno} 
	*/ 
//	    //게시물에 대한 댓글 목록 조회(페이징 1-전체댓글 수 고려 안함) 
//	    @Override 
//	    @GetMapping(value = "/pages/{bno}/{page}", 
//	              //produces = { MediaType.APPLICATION_XML_VALUE, 
//	              //              MediaType.APPLICATION_JSON_UTF8_VALUE }) //5.2부터 Deprecated 됨 
//	                produces = { "application/xml; charset=UTF-8",  "application/json; charset=UTF-8" }) 
//	    public ResponseEntity<List<MyReplyVO>> showReplyList(@PathVariable("page") Integer pageNum, 
//	                                                         @PathVariable("bno") Long bno) { 
//	// 
//	        log.info("댓글-컨트롤러 댓글목록 표시-URI 추출 pageNum: " + pageNum); 
//	        log.info("댓글-컨트롤러 댓글목록 표시-URI 추출 bno: " + bno); 
//	 
//	        MyReplyPagingDTO myReplyPaging = new MyReplyPagingDTO(bno, pageNum); 
//	        log.info("댓글-컨트롤러 댓글목록 표시-생성된 MyReplyPagingDTO: " + myReplyPaging); 
//	 
//	        return new ResponseEntity<>(myReplyService.getReplyListByBno(myReplyPaging), HttpStatus.OK); 
//	    }
	
	 //게시물에 대한 댓글 목록 조회(페이징 2-전체댓글 수 고려)
	@GetMapping(value="/pages/{bno}/{page}", 
	produces= {"application/json;charset=UTF-8", "application/xml;charset=UTF-8"})
	public ResponseEntity<MyReplyPagingCreatorDTO> showReplyList(@PathVariable("bno") Long bno,
																@PathVariable("page") Integer pageNum){
		log.info("댓글-컨트롤러 - 댓글목록 표시-URI 추출 pageNum: " + pageNum); 
		log.info("댓글-컨트롤러 - 댓글목록 표시-URI 추출 bno: " + bno); 
		
		MyReplyPagingDTO myReplyPaging = new MyReplyPagingDTO(bno, pageNum);
		log.info("댓글-컨트롤러 - 댓글목록 표시 - 생성된 MyReplyPagingDTO: " + myReplyPaging);
		
		MyReplyPagingCreatorDTO replyPagingCreator = myReplyService.getReplyListByBno(myReplyPaging);
		
		ResponseEntity<MyReplyPagingCreatorDTO> responseEntity = new ResponseEntity<MyReplyPagingCreatorDTO>(replyPagingCreator, HttpStatus.OK);
		
		log.info("댓글-컨트롤러 - 댓글목록 표시 - 브라우저로 전달되는 ResponseEntity<>: " + responseEntity);
		
		return responseEntity;
		//return new ResponseEntity<>(replyPagingCreator, HttpStatus.OK);
	}
	
	//브라우저에서 http://localhost:8080/replies/pages/229376/1       <--XML형식으로 표시됨 
	//브라우저에서 http://localhost:8080/replies/pages/229376/1.json  <--JSON형식으로 표시됨
	
	//게시물에 대한 댓글 등록: rno 반환 
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/{bno}/new", produces= {"text/plain; charset=UTF-8"})
	public ResponseEntity<String> registerReplyForBoard(@PathVariable("bno") Long bno,
														@RequestBody MyReplyVO myReply){
		log.info("댓글-컨트롤러-게시물에 대한 댓글 등록-URI 추출 bno: " + bno); 
		log.info("댓글-컨트롤러-게시물에 대한 댓글 등록-myReply.getBno: " + myReply.getBno()); 
		log.info("댓글-컨트롤러-게시물에 대한 댓글 등록-서비스로 전달되는 MyReplyVO: " + myReply);
		
		//myReply.setBno(bno);
		Long registerdRno = myReplyService.registerReplyForBoard(myReply);
		log.info("댓글-컨트롤러-게시물에 대한 댓글 등록-반환된 rno(registerdRno): " + registerdRno); 
		log.info("댓글-컨트롤러-게시물에 대한 댓글 등록-myReply.getRno: " + myReply.getRno()); 
		
		//반환값을 삼항연산자로 처리
		return registerdRno != null? new ResponseEntity<String>("게시물의 댓글 등록 성공", HttpStatus.OK)
							: new ResponseEntity<String>("게시물의 댓글 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//게시물에 대한 댓글의 답글 등록: rno 반환 
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/{bno}/{prno}/new", 
			//웹브라우저로부터 받아서 메소드가 사용하는 값의 MIME유형을 지정
				consumes= {"application/json; charset=UTF-8"},
			//이 메소드가 웹브라우저로 전달할 데이터의 MIME-유형을 지정	
				produces= {"text/plain; charset=UTF-8"})	//{ MediaType.TEXT_PLAIN_VALUE }
	public ResponseEntity<String> registerReplyForReply(@PathVariable("bno") Long bno,
														@PathVariable("prno") Long prno,
														@RequestBody MyReplyVO myReply){
		log.info("댓글-컨트롤러 - 댓글에 대한 답글 등록-URI 추출 bno: " + bno); 
		log.info("댓글-컨트롤러 - 댓글에 대한 답글 등록-myReply.getBno: " + myReply.getBno()); 
		log.info("댓글-컨트롤러 - 댓글에 대한 답글 등록-URI 추출 bno: " + prno); 
		log.info("댓글-컨트롤러 - 댓글에 대한 답글 등록-myReply.getRrno: " + myReply.getPrno()); 
		//myReply.setBno(bno); 
		//myReply.setPrno(prno); 
		log.info("댓글-컨트롤러 - 댓글에 대한 답글 등록-전달된 MyReplyVO: " + myReply); 
		        Long registerdRno = myReplyService.registerReplyForReply(myReply); 
		log.info("댓글-컨트롤러 - 댓글에 대한 답글 등록-반환된 rno(registerdRno): " + registerdRno); 
		log.info("댓글-컨트롤러 - 댓글에 대한 답글 등록-myReply.getRno: " + myReply.getRno()); 
		
		//반환값을 삼항연산자로 처리 
		return registerdRno != null 
		                ? new ResponseEntity<>("댓글에 대한 답글 등록 성공", HttpStatus.OK) 
		                : new ResponseEntity<>("댓글에 대한 답글 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	//게시물에 대한 특정 댓글 조회  
	@GetMapping(value = "/{bno}/{rno}",  
	                produces = { "application/json; charset=UTF-8", "application/xml; charset=UTF-8" }) 
	public ResponseEntity<MyReplyVO> showReply(@PathVariable("bno") Long bno,  
	@PathVariable("rno") Long rno) { 
	log.info("댓글-컨트롤러 - 댓글 조회- URI추출 bno: " + bno); 
	log.info("댓글-컨트롤러 - 댓글 조회- URI추출 rno: " + rno); 
	        MyReplyVO myReply = myReplyService.getReply(bno, rno); 
	log.info("댓글-컨트롤러 댓글 조회 - 브라우저로 전달되는 MyReplyVO: " + myReply);
	
	return new ResponseEntity<>(myReply, HttpStatus.OK); 
	//return new ResponseEntity<>(myReplyService.getReply(bno, rno), HttpStatus.OK); 
	} 
	
	//HTTP, PUT: 리소스의 모든 것을 업데이트 한다,  PATCH : 리소스의 일부를 업데이트 한다. 
	//  따라서, PUT은 누락된 값 --> null로 처리 -> db에서 DEFAULT 등으로 처리해서 차이가 거의 없다. //게시물에 대한 특정 댓글 수정 
	@PreAuthorize("isAuthenticated() && principal.username == #myReply.rwriter") 
	@RequestMapping(value = "/{bno}/{rno}",  
	                method = { RequestMethod.PUT, RequestMethod.PATCH },  
	                consumes = "application/json; charset=UTF-8",  
	                produces = "text/plain; charset=UTF-8" ) 
	public ResponseEntity<String> modifyReply(@PathVariable("bno") Long bno, 
											@PathVariable("rno") Long rno, 
											@RequestBody MyReplyVO myReply) { 
		log.info("댓글-컨트롤러 - 댓글 조회-URI 추출 bno: " + bno); 
		log.info("댓글-컨트롤러 - 댓글 조회-URI 추출 rno: " + rno); 
		log.info("댓글-컨트롤러 - 게시물에 대한 댓글 수정-전달된 MyReplyVO: " + myReply); 
		//put의 경우 
		myReply.setBno(bno); 
		myReply.setRno(rno); 
		int modCnt = myReplyService.modifyReply(myReply); 
		log.info("댓글-컨트롤러 댓글 수정 - 수정된 댓글 수: " + modCnt); 
		
		return modCnt == 1 
	            ? new ResponseEntity<>("댓글 수정 성공", HttpStatus.OK) 
	            : new ResponseEntity<>("댓글 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
	} 

	//게시물에 대한 특정 댓글 삭제 
	@PreAuthorize("isAuthenticated() && principal.username == #myReply.rwriter") 
	@DeleteMapping(value = "/{bno}/{rno}", produces = { "text/plain; charset=UTF-8" }) 
	public ResponseEntity<String> removeReply(@PathVariable("bno") Long bno,  
											@PathVariable("rno") Long rno, 
											@RequestBody MyReplyVO myReply) {  
		log.info("댓글-컨트롤러 - 댓글 삭제-URI 추출 bno: " + bno); 
		log.info("댓글-컨트롤러 - 댓글 삭제-URI 추출 rno: " + rno); 
		int delCnt = myReplyService.removeReply(bno, rno); 
		log.info("댓글-컨트롤러 - 댓글 삭제-삭제된 댓글 수: " + delCnt); 
		
		return delCnt == 1 
		                ? new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK) 
		                : new ResponseEntity<>("댓글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
	} 
}
