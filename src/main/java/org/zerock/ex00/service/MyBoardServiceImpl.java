/**
 * 
 */
package org.zerock.ex00.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex00.common.paging.MyBoardPagingDTO;
import org.zerock.ex00.domain.BoardAttachFileVO;
import org.zerock.ex00.domain.MyBoardVO;
import org.zerock.ex00.mapper.BoardAttachFileMapper;
import org.zerock.ex00.mapper.MyBoardMapper;
import org.zerock.ex00.mapperDAO.MyBoardDAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j;

/**
 * @author goott5
 *
 */

@Log4j
@Service
@AllArgsConstructor
@Data
//컴파일 되서 클래스 만들어질 때 모든 필드 초기화하는 생성자 만들어짐
public class MyBoardServiceImpl implements MyBoardService {

	private MyBoardMapper myBoardMapper;
	
	//DAO 빈 주입(생성자를 이용한 자동 주입)
	private MyBoardDAO myBoardDAO;
	
	private BoardAttachFileMapper boardAttachFileMapper; 
	
//	@Override
//	public List<MyBoardVO> getBoardList() {
//		log.info("MyBoardService.getBoardList() 실행");
//		
//		return myBoardMapper.selectMyBoardList();
//	}
	
	@Override
	public List<MyBoardVO> getBoardList(MyBoardPagingDTO myBoardPagingDTO) {
		log.info("MyBoardService.getBoardList() 실행");
		
		return myBoardMapper.selectMyBoardList(myBoardPagingDTO);
		//return myBoardDAO.selectMyBoardList(myBoardPagingDTO);
	}
	
	@Override
	public Long getRowAmountTotal(MyBoardPagingDTO myBoardPagingDTO) {
		log.info("MyBoardService.getRowAmountTotal()에 전달된 MyBoardPagingDTO: " + myBoardPagingDTO); 
		return myBoardMapper.selectRowAmountTotal(myBoardPagingDTO); 
		//return myBoardDAO.selectRowAmountTotal(myBoardPagingDTO); 
	}

	@Transactional(propagation = Propagation.REQUIRED) 
	@Override
	public long registerBoard(MyBoardVO myBoard) {
		log.info("MyBoardService.registerBoard()에 전달된 MyBoardVO: "+myBoard);
		
		myBoardMapper.insertMyBoardSelectKey(myBoard);
//		System.out.println("MyBoardService에서 등록된 게시물의 bno: "+myBoard.getBno());
//		
		//첨부파일이 없는 경우, 메서드 종료
		 
		if (myBoard.getAttachFileList() == null || myBoard.getAttachFileList().size() <= 0) { 
		 
			return myBoard.getBno(); 
		} 
		 //첨부파일이 있는 경우, boardVO의 bno 값을 첨부파일 정보 VO에 저장 후, tbl_myAttachFiles 테이블에 입력
		 
		 myBoard.getAttachFileList().forEach(attachFile -> { 
		 attachFile.setBno(myBoard.getBno()); 
		 	boardAttachFileMapper.insertAttachFile(attachFile); 
		 }); 

		
//		return myBoard.getBno();
//		myBoardDAO.insertMyBoardSelectKey(myBoard);
		System.out.println("MyBoardService에서 등록된 게시물의 bno: "+myBoard.getBno());
		
		return myBoard.getBno();
	}

	@Override
	public MyBoardVO getBoard(Long bno) {
		log.info("MyBoardService.getBoard()에 전달된 bno: "+bno);
		
		myBoardMapper.updateBviewsCnt(bno);
		
		return myBoardMapper.selectMyBoard(bno);
	}
	
	//게시물의 첨부파일 조회
	@Override
	public List<BoardAttachFileVO> listAttachFilesByBno(Long bno) { 
		 System.out.println("첨부파일에 대한 게시물번호(bno): " + bno); 
		 
		return boardAttachFileMapper.selectAttachFilesByBno(bno); 
	} 

	@Transactional
	@Override
	public boolean modifyBoard(MyBoardVO myBoard) {
		log.info("서비스에서의 게시물 수정 메소드(modify)"+myBoard);
		
		Long bno = myBoard.getBno();
		
		//게시물 변경 시, 기존 첨부파일을 모두 삭제되어 전달된 첨부파일 정보가 없는 경우도 있으므로
		//첨부파일 삭제는 무조건 처리합니다.
		boardAttachFileMapper.deleteAttachFilesByBno(bno); 
		
		//게시물 수정: tbl_myboard 테이블에 수정
		boolean boardModifyResult = myBoardMapper.updateMyBoard(myBoard) == 1 ; 

		//게시물 수정이 성공하고, 첨부파일이 있는 경우에만 다음작업 수행
		//첨부파일 정보 저장: tbl_myAttachFiles 테이블에 저장
		//if (boardModifyResult && myBoard.getAttachFileList().size() > 0) { 
		if (boardModifyResult && myBoard.getAttachFileList() != null) {
			myBoard.getAttachFileList().forEach( 
				attachFile -> { 
					attachFile.setBno(bno); 
					boardAttachFileMapper.insertAttachFile(attachFile); 
				} 
			); 
		} 
		//게시물 정보 수정, 수정된 행수가 1 이면 True.
		return boardModifyResult;
	}

	@Override
	public boolean setBoardDeleted(Long bno) {
		log.info("MyBoardService.setBoardDeleted()에 전달된 bno: "+bno);
		
		return myBoardMapper.updateBdelFlag(bno)==1;
	}

	@Transactional
	@Override
	public boolean removeBoard(Long bno) {
		log.info("MyBoardService.removeBoard()에 전달된 bno: "+bno);
		//데이터베이스 첨부파일 정보 삭제
		//첨부파일이 없어도 SQL은 정상처리됨(0개 행이 삭제)
		//테이블의 외래키(FK)에 ON DELETE CASCADE 옵션이 사용된 경우,
		//첨부파일 정보 삭제는 데이터베이스에 의해 자동으로 처리되므로
		//아래의 실행문은 필요없습니다.
		 
		boardAttachFileMapper.deleteAttachFilesByBno(bno); 

		//게시물정보 삭제가 정상처리되면, true 반환
		return myBoardMapper.deleteMyBoard(bno)==1;
	}

	@Override
	public int removeAllDeletedBoard() {
		log.info("MyBoardService.removeAllDeletedBoard() 실행");
		
		return myBoardMapper.deleteAllBoardSetDeleted();
	}



}
