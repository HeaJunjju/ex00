package org.zerock.ex00.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex00.common.paging.MyBoardPagingCreatorDTO;
import org.zerock.ex00.common.paging.MyBoardPagingDTO;
import org.zerock.ex00.domain.BoardAttachFileVO;
import org.zerock.ex00.domain.MyBoardVO;
import org.zerock.ex00.service.MyBoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller	//스프링 어노테이션
@RequestMapping("/myboard")	//스프링 어노테이션
@Log4j	//lombok 어노테이션
public class MyBoardController {
	@Setter(onMethod_ = @Autowired)
	private MyBoardService myBoardService;
	
	//빈 주입 방법1: 생성자 주입방법(권장), 단일 생성자만 존재해야 함.
	//주의: 기본 생성자가 존재 시에 오류 발생
	// private MyBoardService myBoardService;
	// public MyBoardController(MyBoardService myBoardService) {
	// this.myBoardService = myBoardService;
	// }
	 
	//빈 주입 방법2: @Autowired(내부적으로 setter를 이용)
	// @Autowired
	// private MyBoardService myBoardService;
	 
	//빈 주입 방법3: setter 주입방법: lombok에서의 @Setter(onMethod_ = @Autowired) 이용하는 방법과 동일
	// private MyBoardService myBoardService;
	//
	// @Autowired
	// public void setMyBoardService(MyBoardService myBoardService) {
	// this.myBoardService = myBoardService;
	// }

	//보통은 반환타입이 String이 아닌 void로 하면서 views/myboard/list.jsp를 찾도록 하는 경우가 많음
	//@RequestMapping(value="/list", method= {RequestMethod.GET})
//	@GetMapping("/list")
//	public void showBoardList(Model model) {
//		log.info("컨트롤러 - 게시물 목록 조회....");
//		model.addAttribute("boardList", myBoardService.getBoardList());
//	}
	
	//페이징 고려
	@GetMapping("/list")
	public void showBoardList(MyBoardPagingDTO myBoardPagingDTO, Model model) {
		log.info("컨트롤러 - 게시물 목록 조회 시작....."); 
		log.info("컨트롤러에 전달된 사용자의 페이징처리 데이터: " + myBoardPagingDTO); 
		model.addAttribute("boardList", myBoardService.getBoardList(myBoardPagingDTO)); 
		
		Long rowAmountTotal = myBoardService.getRowAmountTotal(myBoardPagingDTO); 
		log.info("컨트롤러에 전달된 게시물 총 개수: " + rowAmountTotal); 
		MyBoardPagingCreatorDTO myBoardPagingCreatorDTO = 
		 
		new MyBoardPagingCreatorDTO(rowAmountTotal, myBoardPagingDTO); 
		log.info("컨트롤러에서 생성된 MyBoardPagingCreatorDTO 객체 정보: " + myBoardPagingCreatorDTO.toString()); 
		model.addAttribute("pagingCreator", myBoardPagingCreatorDTO); 
		//model.addAttribute("myBoardCreatingPagingDTO", new MyBoardCreaingPagingDTO(rowAmountTotal,myBoardPagingDTO));
		log.info("컨트롤러 - 게시물 목록(페이징 고려) 조회 완료....."); 

	}
	
	@GetMapping("/register")
	public void showBoardRegisterPage() {
		log.info("컨트롤러 - 게시물 등록 페이지 호출");
	}
	
////	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String registerNewBoard(MyBoardVO myBoard, RedirectAttributes redirectAttr) {
		
		System.out.println("================ myBoardVO ================="); 
		 
		System.out.println("myBoardVO: "+myBoard.toString()); 
		System.out.println("============= attachFileInfo ============="); 

		if (myBoard.getAttachFileList() != null) { 
			 
			myBoard.getAttachFileList() 
			 .forEach(attachFile -> System.out.println("파일정보:"+attachFile.toString())); 
		} 

		System.out.println("======= End myBoard & attachFileInfo ======="); 

		
		log.info("컨트롤러 - 게시물 등록: "+myBoard);
		
		long bno = myBoardService.registerBoard(myBoard);
		
		log.info("등록된 게시물의 bno: "+bno);
		
		//redirectAttr.addAttribute(redirectAttr);//redirectAttr에 저장된게 계속 남아있음
		redirectAttr.addFlashAttribute("result", bno);//페이지가 이동되면 끝(1회성 사용에 좋음)
		//redirectAttr.addFlashAttribute("result", myBoard.getBno());
		//redirectAttr.addFlashAttribute("result", myBoardService.registerBoard(myBoard));
		
		//redirect:가 있으면 jsp파일을 찾는게 아니라 controller의 /myboard/list로 매핑된 메소드 호출
		return "redirect:/myboard/list";
	}
	
//	@GetMapping({"/detail","/modify"})//2개한테 공통적으로 매필 걸 수 있음,
//	//2개나 3개 여러개에 매핑걸 때는 리턴타입없이 void로 해야함(jsp를 어디에 요청할지 모름)
//	public void showBoardDetail(@RequestParam("bno") Long bno, Model model) {
//		log.info("컨트롤러 - 게시물 조회/수정 페이지 호출: "+bno);
//		model.addAttribute("board", myBoardService.getBoard(bno));
//		log.info("컨트롤러 - 화면으로 전달할 model: "+model);
//	}
	
	@GetMapping("/detail")//2개한테 공통적으로 매필 걸 수 있음,
	//2개나 3개 여러개에 매핑걸 때는 리턴타입없이 void로 해야함(jsp를 어디에 요청할지 모름)
	public void showBoardDetail(@RequestParam("bno") Long bno, @ModelAttribute("myBoardPagingDTO") MyBoardPagingDTO myBoardPagingDTO, Model model) {
		log.info("컨트롤러 - 게시물 조회/수정 페이지 호출: "+bno);
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO); 
		model.addAttribute("board", myBoardService.getBoard(bno));
		log.info("컨트롤러 - 화면으로 전달할 model: "+model);
	}
	
	//################## 특정 게시물의 첨부파일 정보를 JSON으로 전달 #####################################
	@GetMapping(value = "/getAttachList", 
	 produces = {"application/json;charset=UTF-8" }) 
	@ResponseBody
	public ResponseEntity<List<BoardAttachFileVO>> listAttachFilesOfArticle(Long bno) { 
		 System.out.println("컨트롤러: 첨부파일관련 게시뭄번호(bno): " + bno); 
		 
		return new ResponseEntity<List<BoardAttachFileVO>>(myBoardService.listAttachFilesByBno(bno), 
		 HttpStatus.OK); 
	 } 

	
	@GetMapping("/modify")//2개한테 공통적으로 매필 걸 수 있음,
	//2개나 3개 여러개에 매핑걸 때는 리턴타입없이 void로 해야함(jsp를 어디에 요청할지 모름)
	public void showBoardModify(@RequestParam("bno") Long bno, @ModelAttribute("myBoardPagingDTO") MyBoardPagingDTO myBoardPagingDTO, Model model) {
		log.info("컨트롤러 - 게시물 조회/수정 페이지 호출: "+bno);
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO); 
		model.addAttribute("board", myBoardService.getBoard(bno));
		log.info("컨트롤러 - 화면으로 전달할 model: "+model);
	}
//	
	@PostMapping("/modify")
	//@PutMapping("/modify")//put은 전체update, fetch는 부분update 
	public String modifyBoard(MyBoardVO myBoard, MyBoardPagingDTO myBoardPagingDTO, RedirectAttributes redirectAttr) {
		log.info("컨트롤러 - 게시물 수정 전달된 myBoard 값: "+myBoard);
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO); 
		log.info("컨트롤러 - 게시물 수정 처리 결과(boolean): "+myBoardService.modifyBoard(myBoard));

		//redirectAttr.addAttribute("bno", myBoard.getBno());
		if(myBoardService.modifyBoard(myBoard)) {
			redirectAttr.addFlashAttribute("result", "successModify");//modify성공하면 "success"라고 보내기
		}
		redirectAttr.addAttribute("bno", myBoard.getBno()); 
		redirectAttr.addAttribute("pageNum", myBoardPagingDTO.getPageNum()); 
		redirectAttr.addAttribute("rowAmountPerPage", myBoardPagingDTO.getRowAmountPerPage()); 
		
		redirectAttr.addAttribute("scope", myBoardPagingDTO.getScope()); 
		redirectAttr.addAttribute("keyword", myBoardPagingDTO.getKeyword()); 
		 
		return "redirect:/myboard/detail";
		//return "redirect:/myboard/detail?bno="+myBoard.getBno();
	}
//	
	@PostMapping("/delete")
	//@PutMapping("/delete")	-> RestAPI에서 사용가능
	public String setBoardDeleted(@RequestParam("bno") Long bno, MyBoardPagingDTO myBoardPagingDTO, //MyBoardVO myBoard,  
            					RedirectAttributes redirectAttr) {
		log.info("컨트롤러 - 게시물 삭제(bdelFlag값변경 글번호): "+bno);
		//log.info("컨트롤러 - 게시물 삭제(전달된 MyBoardVO): " + myBoard);
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO);
		
//		if(myBoardService.setBoardDeleted(myBoard.getBno())) {
//			redirectAttr.addFlashAttribute("result", "successRemove");
//		}
		if (myBoardService.setBoardDeleted(bno)) { 
			redirectAttr.addFlashAttribute("result", "successRemove"); 
		} 
		redirectAttr.addAttribute("pageNum", myBoardPagingDTO.getPageNum()); 
		redirectAttr.addAttribute("rowAmountPerPage", myBoardPagingDTO.getRowAmountPerPage()); 
		
		redirectAttr.addAttribute("scope", myBoardPagingDTO.getScope()); 
		redirectAttr.addAttribute("keyword", myBoardPagingDTO.getKeyword()); 

		return "redirect:/myboard/list";
	}
	

//	@PostMapping("/delete")  
//	public String setBoardDeleted(MyBoardVO myBoard, 
//		MyBoardPagingDTO myBoardPagingDTO,//전달된 페이징 값들을 받음
//		RedirectAttributes redirectAttr ){//전달할 페이징 값을 저장하는 객체
//		 
//		if (myBoardService.setBoardDeleted(myBoard.getBno())) { 
//		 
//		redirectAttr.addFlashAttribute("result", "successRemove"); 
//		} 
//		 
//		 
//		return "redirect:/myboard/list" + myBoardPagingDTO.addPagingParamsToURI(); 
//	 
//	}
	
	@PostMapping("/remove")
	public String removeBoard(@RequestParam("bno") Long bno, MyBoardPagingDTO myBoardPagingDTO, RedirectAttributes redirectAttr) {
		log.info("컨트롤러 - 게시물 삭제: 삭제되는 글번호: "+bno);
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO); 

		if(myBoardService.removeBoard(bno)) {
			redirectAttr.addFlashAttribute("result", "successRemove");
		}
		redirectAttr.addAttribute("pageNum", myBoardPagingDTO.getPageNum()); 
		redirectAttr.addAttribute("rowAmountPerPage", myBoardPagingDTO.getRowAmountPerPage()); 
		
		redirectAttr.addAttribute("scope", myBoardPagingDTO.getScope()); 
		redirectAttr.addAttribute("keyword", myBoardPagingDTO.getKeyword()); 

		return "redirect:/myboard/list";
	}
//	
	@PostMapping("/removeAll")
	public String removeAllDeletedBoard(//Model model, 
			MyBoardPagingDTO myBoardPagingDTO, RedirectAttributes redirectAttr) {
		//model.addAttribute("removedRowCnt", myBoardService.removeAllDeletedBoard());
		//log.info("관리자에 의해 삭제된 총 행수: "+model.getAttribute("removedRowCnt"));
		
		//redirectAttr.addFlashAttribute("result", "success");
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO); 
		//삭제된 행수를 removedRowCnt 이름으로 반환
		redirectAttr.addFlashAttribute("removedRowCnt", myBoardService.removeAllDeletedBoard());
		
        log.info("관리자에 의해 삭제된 총 행수: " + redirectAttr.getAttribute("removedRowCnt"));
        
        redirectAttr.addAttribute("pageNum", myBoardPagingDTO.getPageNum()); 
        redirectAttr.addAttribute("rowAmountPerPage", myBoardPagingDTO.getRowAmountPerPage()); 
        
        redirectAttr.addAttribute("scope", myBoardPagingDTO.getScope()); 
        redirectAttr.addAttribute("keyword", myBoardPagingDTO.getKeyword()); 

		return "redirect:/myboard/list";
	}
}
