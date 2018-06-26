package com.ctvit;
import java.io.File;

import com.ctvit.httptool.HttpKit;

public class UploadTest {

	   
    public static void main(String[] args) throws Exception{
    	String url = "http://127.0.0.1:8080/basictool/servlet/upload";
    	File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\timg.jpg");
		System.out.println(HttpKit.upload(url, null, file, null));
	}

}
