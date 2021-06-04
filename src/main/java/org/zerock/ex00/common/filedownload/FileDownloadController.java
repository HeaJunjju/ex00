package org.zerock.ex00.common.filedownload;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileDownloadController {
	private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
	
	String strUploadFileRepoDir = "C:\\upload";
	
	//크롬/IE 브라우저에서 다운로드 서비스되도록
	//다운로드 파일이름에서 UUID 제거되도록
	@GetMapping(value = "/fileDownloadAjax"
			//다운로드 파일의 MIME타입지정(서버-->브라우저)
			 ,produces = {"application/octet-stream"} 
			//,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
			 )
	@ResponseBody
	public ResponseEntity<Resource> fileDownlaodActionAjax(
			//사용자 브라우저를 확인하기 위해 User-Agent 해더를 매개변수로 설정
			@RequestHeader("User-Agent") String userAgent,
			String fileName){
		logger.info("User-Agent 해더정보: "+ userAgent); 
		logger.info("처리전 파일이름: " + fileName); 
		 
		//톰캣 서버 운영체제에 있는 파일을 액세스할 수 있는 Resource 객체를 생성하기 위하여
		//스프링 Resource 인터페이스를 통해 FileSystemResource 구현객체를 생성합니다.
		Resource resource = new FileSystemResource(fileName);
		
		logger.info("resource :" + resource);
		//파일이 존재하지 않으면, 오류전송 후, 메소드 종료
		 
		if(resource.exists() == false) {  
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); 
		}
		//파일이 존재하면 아래의 내용 수행
		 
		String resourceFileName = resource.getFilename(); 
		 
		logger.info("UUID 제거전 resourceFileName :" + resourceFileName); 
		 
		//UUID가 제거된 파일이름
		 
		resourceFileName = resourceFileName.substring(resourceFileName.indexOf("_")+1); 
		 
		logger.info("UUID 제거후 resourceFileName :" + resourceFileName); 
		 
		//스프링의 HttpHeders 객체 생성
		HttpHeaders httpHeaders = new HttpHeaders(); 
		
		try { 
			 String downloadName = null; 
			 
			if (userAgent.contains("Trident")||userAgent.contains("MSIE")) { 
			 
				logger.info("IE 브라우저입니다"); 
				 
				downloadName = URLEncoder.encode(resourceFileName, "UTF8"); 
				 
				logger.info("IE에서의 파일이름: "+downloadName); 
			 } else if (userAgent.contains("Edge")||userAgent.contains("Edg") ) { 
			 
				logger.info("Edge 브라우저입니다"); 
				 
				downloadName = URLEncoder.encode(resourceFileName, "UTF8"); 
				 
				logger.info("Edge에서의 파일이름: "+downloadName); 
			 } else { 
			 
				logger.info("Chrome 브라우저입니다"); 
				 
				//UTF-8로 인코딩한 파일이름을 HTML기본 인코딩(ISO-8859-1)으로 디코딩될 수 있는 문자열 생성
				 
				//파일이름 디코딩을 "ISO-8859-1" 대신 "UTF-8"을 지정하면, 위와 같이 파라미터로 전달된 파일이름이 ____.jpg로 표시됩니다.
				 
				downloadName = new String(resourceFileName.getBytes("UTF-8"), "ISO-8859-1"); 
				 
				logger.info("Chrome에서의 파일이름: "+downloadName); 
			 } 
			 
			//생성된 HttpHeaers 객체에 파일을 다운로드 받을 수 있도록 Content-Disposition 해더 설정
			 
			//UTF-8로 인코딩한 파일이름을 HTML기본 인코딩(ISO-8859-1)으로 디코딩할 수있는 파일이름 문자열을 지정
			 
			httpHeaders.add("Content-Disposition", "attachment; filename=" + downloadName); 
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace(); 
		}
		//ResponseEntity<Resource>에 파일리소스, HttpHeaders객체, 상태를 담아서 반환
		return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);

	}
	

}
