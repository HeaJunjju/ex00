package org.zerock.ex00.service;

import java.util.List;

import org.zerock.ex00.common.paging.MyBoardPagingDTO;
import org.zerock.ex00.domain.MyBoardVO;

public interface MyBoardService {
	//public List<MyBoardVO> getBoardList();
	public List<MyBoardVO> getBoardList(MyBoardPagingDTO myBoardPagingDTO);//페이징 고려
	//게시물 총 개수 조회 서비스 - 페이징 시 필요
	public Long getRowAmountTotal(MyBoardPagingDTO myBoardPagingDTO); 
	public long registerBoard(MyBoardVO myBoard);
	public MyBoardVO getBoard(Long bno);
	public boolean modifyBoard(MyBoardVO myBoard);
	public boolean setBoardDeleted(Long bno);
	public boolean removeBoard(Long bno);
	public int removeAllDeletedBoard();
}
