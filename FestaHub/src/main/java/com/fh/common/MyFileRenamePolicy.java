package com.fh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	// 기존의 파일을 전달받아서 파일명 수정작업 후
	// 수정된 파일을 반환해주는 메소드
	@Override
	public File rename(File originFile) {
		
		// 원본파일명 : bono.jpg
		// 수정파일명 : 20241014145520xxxxx.jpg
		// > 년월일시분초 + 5자리랜덤수 + 확장자명
		//   (최대한 파일명이 겹치지 않게!!)
		
		// 원본파일명 "bono.jpg"
		String originName = originFile.getName();
		
		// 1. 파일이 업로드된 시간 (년월일시분초)
		String currentTime 
			= new SimpleDateFormat("yyyyMMddHHmmss")
									.format(new Date());
		
		// 2. 5자리 랜덤수 (10000 ~ 99999)
		int ranNum = (int)(Math.random() * 90000 + 10000);
		
		// 3. 확장자명 (원본파일의 확장자명 그대로)
		// > 파일명 중간에 . 이 들어가는 경우도 있기 때문에
		//   원본파일명으로 부터 가장 맨 마지막의 . 을 기준으로
		//   파일명과 확장자명을 나눠야 함!!
		String ext 
			= originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		// "20241011415123012345.jpg"
		
		// 원본파일 (originFile) 을 수정된 파일명으로 적용시켜서
		// 파일객체로 변환 후 바로 리턴
		return new File(originFile.getParent(), changeName);
		
	}
}
