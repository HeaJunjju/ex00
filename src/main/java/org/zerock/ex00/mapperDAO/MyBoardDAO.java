package org.zerock.ex00.mapperDAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.zerock.ex00.common.paging.MyBoardPagingDTO;
import org.zerock.ex00.domain.MyBoardVO;

@Repository
public class MyBoardDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	//게시물 목록 조회(페이징)
	public List<MyBoardVO> selectMyBoardList(MyBoardPagingDTO myBoardPagingDTO) throws DataAccessException {
		System.out.println("DAO 목록표시 메소드 시작");
		System.out.println("서비스로부터 전달받은 myBoardPagingDTO:" + myBoardPagingDTO);
		List<MyBoardVO> articlesList = sqlSession.selectList("MyBoardMapperDAO.selectMyBoardList", myBoardPagingDTO);
		return articlesList;
	}
	
    //총 게시물 개수(페이징)
    public Long selectRowAmountTotal(MyBoardPagingDTO myBoardPagingDTO) {
    	System.out.println("DAO 총 게시물 개수 조회 메소드 시작");
    	return sqlSession.selectOne("MyBoardMapperDAO.selectRowAmountTotal", myBoardPagingDTO);
    };
	
	//게시물 등록
	public Integer insertMyBoardSelectKey(MyBoardVO myBoard) {
		System.out.println("DAO 게시물 등록 메소드 시작");
		return sqlSession.insert("MyBoardMapperDAO.insertMyBoardSelectKey",myBoard);
	}



}
