package org.zerock.ex00.common.fileupload;

import lombok.Data;

@Data
public class AttachInfoDTO {
	private String fileName;
	private String uploadPath;
	private String uuid;
	private String fileType;
	//private String image;
}
