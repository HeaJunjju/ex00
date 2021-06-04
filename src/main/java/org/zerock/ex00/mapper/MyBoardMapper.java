package org.zerock.ex00.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.ex00.common.paging.MyBoardPagingDTO;
import org.zerock.ex00.domain.MyBoardVO;

public interface MyBoardMapper {
	//public List<MyBoardVO> selectMyBoardList();
	public List<MyBoardVO> selectMyBoardList(MyBoardPagingDTO myBoardPagingDTO);//게시물 목록(페이징 고려)
	//게시물 조회 - 총 게시물 개수(페이징 데이터)
	public Long selectRowAmountTotal(MyBoardPagingDTO myBoardPagingDTO); 
	//public Long selectRowAmountTotal(); 
	public Integer insertMyBoard(MyBoardVO myBoard);
	public Integer insertMyBoardSelectKey(MyBoardVO myBoard);
	public MyBoardVO selectMyBoard(Long bno);
	public void updateBviewsCnt(Long bno);
	public int updateBdelFlag(Long bno);
	public int deleteMyBoard(Long bno);
	public int deleteAllBoardSetDeleted();
	public int updateMyBoard(MyBoardVO myBoard);
	public void updateBReplyCnt(@Param ("bno")long bno, @Param ("amount")int amount);
}
