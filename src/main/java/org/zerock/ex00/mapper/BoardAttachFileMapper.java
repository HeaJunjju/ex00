package org.zerock.ex00.mapper;

import java.util.List;

import org.zerock.ex00.domain.BoardAttachFileVO;

public interface BoardAttachFileMapper {
	public void insertAttachFile(BoardAttachFileVO boardAttachFileVO);
	public void deleteAttachFile(String uuid);
	public List<BoardAttachFileVO> selectAttachFilesByBno(Long bno);
	public void deleteAttachFilesByBno(Long bno);
}
