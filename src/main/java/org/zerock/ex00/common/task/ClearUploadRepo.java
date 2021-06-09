package org.zerock.ex00.common.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.ex00.domain.BoardAttachFileVO;
import org.zerock.ex00.mapper.BoardAttachFileMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClearUploadRepo {
	private BoardAttachFileMapper myBoardAttachFileMapper;
	
	//하루 전 폴더 문자열 생성 메서드
	 
	private String getStrOfYesterdayFolder() { 
	 
		//문자열 형식 지정
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
		 
		//달력객체(년/월/일)생성
		 Calendar calendar = Calendar.getInstance(); 
		 
		//달력에서의 하루전 날짜를 생성해서 달력 객체에 추가
		calendar.add(Calendar.DATE, -1); 
		 
		//달력객체에 설정된 밀리세컨트 값을 가져와서 날짜형식 문자열로 변환
		 String strYesterdayFolder = simpleDateFormat.format(calendar.getTime()); 
		 
		//문자열의 날짜구분자를 운영체제 디렉토리 구분자로 변경
		return strYesterdayFolder.replace("/", File.separator); 
	}
	
	//하루 전 날짜 폴더에 있는 파일 중, DB에 정보가 없는 필요없는 파일 삭제
	//spring-task 의 스케쥴 기능으로 자동으로 실행되는 메서드
	@Scheduled(cron = "0 0 3 * * *") //매일 새벽 3시에 자동으로 실행, 테스트 시에 "0 분 * * * *" 로 수정하세요.
	public void clearNeedlessFiles() throws Exception { 
		 String strUploadFileRepoDir = "C:\\upload" ; 
		 System.out.println("파일 검사 작업 시작................."); 
		 System.out.println("오늘 날짜: " + new Date()); 
		 
		//데이터베이스에 저장된 하루 전 첨부파일정보목록 생성(삭제하면 않되는 파일들)
		 List<BoardAttachFileVO> doNotDeleteFileList = myBoardAttachFileMapper.selectAttachFilesBeforeOneDay(); 
		//데이터베이스의 파일목록을 자바의 Stream 반복자로 처리하여 파일경로의 리스트 객체를 생성
		 //지우면 않되는 첨부파일들의 경로목록
		 List<Path> doNotDeleteFilePathList 
		 = doNotDeleteFileList

		//자바의 Stream 기능 사용
		 .stream() //java.util.Collection인터페이스의 stream 메소드를 사용하여
		//스트림의 중간단계 매핑기능으로
		//Paths.get(): 문자열 매개변수를 명시된 순서대로 합치면서
		//운영체제의 파일경로 객체를 생성
		//boardAttachFileVO를 생성된 경로객체로 하나씩 교체
		 .map(myBoardAttachFile -> Paths.get(//strUploadFileRepoDir,
					myBoardAttachFile.getUploadPath(), 
					myBoardAttachFile.getUuid() + "_" + 
					myBoardAttachFile.getFileName())) 	 
		//Paths객체들을, List 컬렉션 객체로 변환
		 .collect(Collectors.toList()); 
		 
		//썸네일 파일 정보 추가
		doNotDeleteFileList
		 
		//자바 Stream 기능 사용
		 .stream() 
		//파일정보객체 중, 이미지파일 정보만 골라냄
		 .filter(myBoardAttachFile -> myBoardAttachFile.getFileType().equals("I")) 
		 
		//이미지파일boardAttachFileVO 객체를 이미지의 썸네일 경로객체로 교체
		 .map(myBoardAttachFile -> Paths.get(//strUploadFileRepoDir,
					myBoardAttachFile.getUploadPath(), 
					"s_" + myBoardAttachFile.getUuid() + 
					"_" + myBoardAttachFile.getFileName())) 
		 
		//각각의 썸네일 경로객체를 경로 목록에 추가
		 .forEach(doNotDeleteFilePath -> doNotDeleteFilePathList.add(doNotDeleteFilePath)); 
		 System.out.println("==========================================="); 
		 
		//최종 지우면 않되는 파일(썸네일 포함)의 경로목록
		doNotDeleteFilePathList
		 
		//각 경로를 콘솔에 하나씩 출력(배포시엔 주석처리)
		 .forEach(doNotDeleteFilePath -> System.out.println(doNotDeleteFilePath)); 
		 
		//하루 전 날짜경로가 저장된 파일 객체
		 File dirBeforeOneDay = 
			 Paths.get(strUploadFileRepoDir, getStrOfYesterdayFolder()) //하루 전 날짜 경로 객체(Path 객체) 가져와서
			 .toFile(); //Path 객체를 File 객체로 변환
		 
		//하루 전 날짜폴더에 있는 모든 파일들을 DB로 부터 가져온 파일정보와 비교하여
		//정보가 없는 파일들의 File 객체가 저장된 배열 생성
		 File[] needlessFileArray
		 = dirBeforeOneDay //하루 전 날짜폴더 파일객체
		 .listFiles( 
				//각파일을 Path 객체로 변환하여
				//디렉토리의 각 파일의 Path 객체를
				//doNotDeleteFilePathList(DB에 정보가 있는 파일) 목록의
				//Path 객체와 비교하여 DB에 없는(false) 디렉토리 파일들의 File객체를
				//needlessFileArray File[] 배열에 저장
				 eachFile -> doNotDeleteFilePathList.contains(eachFile.toPath()) == false); 
		 System.out.println("-----------------------------------------"); 
		 
		//필요없는 파일 삭제
		 if (needlessFileArray == null) { 
			 System.out.println("===== 삭제할 파일이 없습니다.================"); 
			 return; 
		  } else { 
		  
			 for (File needlessFile : needlessFileArray) { 
			  
			  
				 //삭제파일 정보 표시(DB에 정보가 없는 파일)
				  System.out.println("=====다음의 파일들이 삭제됩니다.================"); 
				  System.out.println("필요없는 파일 이름: " + needlessFile.getAbsolutePath()); 
				  
				 //필요없는 파일 삭제
				  
				 needlessFile.delete(); 
			 } 
		  }
	 }
}
