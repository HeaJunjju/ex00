package org.zerock.ex00.domain;

import lombok.Data;

@Data
public class BoardAttachFileVO {
	private String uuid; 
	private String uploadPath; 
	private String fileName; 
	private String fileType; 
	private Long bno; 

}
