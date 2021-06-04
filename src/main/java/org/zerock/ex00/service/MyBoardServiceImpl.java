/**
 * 
 */
package org.zerock.ex00.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.ex00.common.paging.MyBoardPagingDTO;
import org.zerock.ex00.domain.MyBoardVO;
import org.zerock.ex00.mapper.MyBoardMapper;
import org.zerock.ex00.mapperDAO.MyBoardDAO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * @author goott5
 *
 */

@Log4j
@Service
@AllArgsConstructor
//컴파일 되서 클래스 만들어질 때 모든 필드 초기화하는 생성자 만들어짐
public class MyBoardServiceImpl implements MyBoardService {

	private MyBoardMapper myBoardMapper;
	
	//DAO 빈 주입(생성자를 이용한 자동 주입)
	private MyBoardDAO myBoardDAO;
	
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

	@Override
	public long registerBoard(MyBoardVO myBoard) {
		log.info("MyBoardService.registerBoard()에 전달된 MyBoardVO: "+myBoard);
		
		myBoardMapper.insertMyBoardSelectKey(myBoard);
//		System.out.println("MyBoardService에서 등록된 게시물의 bno: "+myBoard.getBno());
//		
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

	@Override
	public boolean modifyBoard(MyBoardVO myBoard) {
		log.info("서비스에서의 게시물 수정 메소드(modify)"+myBoard);
		
		//게시물 정보 수정, 수정된 행수가 1 이면 True.
		return myBoardMapper.updateMyBoard(myBoard)==1;
	}

	@Override
	public boolean setBoardDeleted(Long bno) {
		log.info("MyBoardService.setBoardDeleted()에 전달된 bno: "+bno);
		
		return myBoardMapper.updateBdelFlag(bno)==1;
	}

	@Override
	public boolean removeBoard(Long bno) {
		log.info("MyBoardService.removeBoard()에 전달된 bno: "+bno);
		
		return myBoardMapper.deleteMyBoard(bno)==1;
	}

	@Override
	public int removeAllDeletedBoard() {
		log.info("MyBoardService.removeAllDeletedBoard() 실행");
		
		return myBoardMapper.deleteAllBoardSetDeleted();
	}



}