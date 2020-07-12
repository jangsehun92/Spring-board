package jsh.project.board.global.infra.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jsh.project.board.article.service.ArticleServiceImpl;

@Component
public class FileService {
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	private final String rootDirectory = "/Users/jangsehun/Documents/fileDirectory/";
	private final String pathUrl = "/article/images/";
	
	private String savedFileName;
	private String savedPath;
	
	public FileService() {
		
	}
	
	public String upload(MultipartFile file) {
		setFileDirectory();
		setSavedFileName(file.getOriginalFilename());
		writerFile(file);
		return getFileUrl();
	}
	
	// 날짜별로 폴더를 생성한다.
	public void setFileDirectory() {
		Calendar calendar = Calendar.getInstance();
		
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR)+"/");
		sb.append((calendar.get(Calendar.MONTH)+1) + "/");
		sb.append(calendar.get(Calendar.DATE) +"/");
		
		File fileDateDirectory = new File(rootDirectory + sb.toString());
		if(!fileDateDirectory.exists()) {
			fileDateDirectory.mkdirs();
			log.info(rootDirectory + sb + " 경로에 폴더 생성");
		}
		log.info("저장 경로 : " + rootDirectory + sb);
		this.savedPath = sb.toString();
	}
	
	// 랜덤하게 파일이름을 만든다.
	public void setSavedFileName(String originalFileName) {
		savedFileName = UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	
	// 저장경로 및 파일이름 리턴
	public String getFileUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append(pathUrl);
		sb.append(savedPath);
		sb.append(savedFileName);
		return sb.toString();
	}
	
	// 파일을 저장한다.
	public void writerFile(MultipartFile file) {
		//1.
		FileOutputStream fos = null;
		byte[] data;
		
		try {
			data = file.getBytes();
			fos = new FileOutputStream(rootDirectory + savedPath + savedFileName);
			fos.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
