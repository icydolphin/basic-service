package com.ctvit.httpconnector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpConnector {
	
	private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String _GET  = "GET"; // GET
    private static final String _POST = "POST";// POST
	/**
	 * get请求
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws Exception{
    	HttpURLConnection http = null;
        if (isHttps(url)) {
            http = initHttps(initParams(url, params), _GET, headers);
        } else {
            http = initHttp(initParams(url, params), _GET, headers);
        }
        http.connect();
        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
        String valueString = null;
        StringBuffer bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null){
            bufferRes.append(valueString);
        }
        in.close();
        if (http != null) {
            http.disconnect();// 关闭连接
        }
        return bufferRes.toString();
	}
    
    public static String get(String url, Map<String, String> params) throws Exception{
    	return get(url, params, null);
    }
    
    public static String get(String url) throws Exception{
    	return get(url, null);
    }
	
    /**
     * post请求
     * @param url
     * @param requestBody
     * @param headers
     * @return
     * @throws Exception
     */
	public static String post(String url,String requestBody,Map<String,String> headers) throws Exception{
		StringBuffer bufferRes = new StringBuffer();
		HttpURLConnection http = null;
        if (isHttps(url)) {
            http = initHttps(url, _POST, headers);
        } else {
            http = initHttp(url, _POST, headers);
        }
        http.connect();
        OutputStream out = http.getOutputStream();
        out.write(requestBody.getBytes(DEFAULT_CHARSET));
        out.flush();
        out.close();

        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
        String valueString = null;
        while ((valueString = read.readLine()) != null){
            bufferRes.append(valueString);
        }
        in.close();
        if (http != null) {
            http.disconnect();// 关闭连接
        }
        return bufferRes.toString();
	}
	
	 /**
     * 上传文件
     * @param url
     * @param file
     * @param param
     * @param header
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String url,Map<String,String> param,File file,Map<String,String> header) throws Exception{
    	 String boundary = "-----------------------------"+System.currentTimeMillis(); // 定义数据分隔线  
        StringBuffer bufferRes = null;
        HttpURLConnection conn = null;
        if (isHttps(url)) {
        	conn = initHttps(url, _POST, header);
        } else {
        	conn = initHttp(url, _POST, header);
        }
        conn.setUseCaches(false);  
        conn.setRequestProperty("connection", "Keep-Alive");  
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);  
        conn.connect();
        OutputStream out = new DataOutputStream(conn.getOutputStream());  
        StringBuilder sb1 = new StringBuilder();    
        sb1.append("--" + boundary + "\r\n") ;   
        //fileFormParam 为该form的名称
        sb1.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() + "\"\r\n");    
        sb1.append("Content-Type:application/octet-stream\r\n\r\n");
        out.write(sb1.toString().getBytes());  
        System.out.print(sb1.toString());
        DataInputStream fs = new DataInputStream(new FileInputStream(file));  
        int bytes = 0;  
        byte[] bufferOut = new byte[1024];  
        while ((bytes = fs.read(bufferOut)) != -1) {  
            out.write(bufferOut, 0, bytes);  
        }  
        out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个  
        fs.close(); 
        StringBuilder sb2 = new StringBuilder();    
        if(param!=null&&param.keySet().size()>0){
        	Iterator<String> it = param.keySet().iterator();
        	for(; it.hasNext();) {  
        		String key=it.next();
        		sb2.append("--" + boundary + "\r\n");  
        		sb2.append("Content-Disposition: form-data;name=\"");  
        		sb2.append(key);  
        		sb2.append("\";\r\n\r\n");  
        		sb2.append(param.get(key));  
        		sb2.append("\r\n");  
        	}          	
        }
        sb2.append("--"+boundary + "--\r\n\r\n");
        out.write(sb2.toString().getBytes());        
        System.out.print(sb2.toString());
        out.flush();    
        out.close();   

        // 定义BufferedReader输入流来读取URL的响应  
        InputStream in = conn.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
        String valueString = null;
        bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null){
            bufferRes.append(valueString);
        }
        in.close();
        if (conn != null) {
            // 关闭连接
            conn.disconnect();
        }
        return bufferRes.toString();
    }
    
    /**
     * 下载文件
     * @param url
     * @param path
     * @param uploadFileRoot 本地文件的根目录
     * @param httpDomain 
     * @return
     * @throws Exception
     */
    public static String downLoad(String url,String path,String localFileRoot,String httpDomain) throws Exception{
		JSONObject jsonObject = new JSONObject();
	 	HttpURLConnection http = null;
        if (isHttps(url)) {
            http = initHttps(initParams(url, null), _GET, null);
        } else {
            http = initHttp(initParams(url, null), _GET, null);
        }
        http.connect();
        InputStream in = http.getInputStream();
        //获取文件存储路径
        //String uploadFileRoot = ResourceLoader.getInstance().getConfig().getProperty("upload.dir");
        //获取文件后缀
        BufferedInputStream bis = new BufferedInputStream(in);
        //String extName = HttpURLConnection.guessContentTypeFromStream(bis);
        String extName=url.substring(url.lastIndexOf(".")+1);
        //String firstDir = getDirByFiletype(extName);
        java.util.GregorianCalendar gcalendar =  new java.util.GregorianCalendar();
        String year = gcalendar.get(Calendar.YEAR)+"";
		String month = gcalendar.get(Calendar.MONTH)+1 + "";
		String day = gcalendar.get(Calendar.DAY_OF_MONTH)+"";
		String fileDir = localFileRoot +File.separator + year + File.separator +month + File.separator + day + File.separator;
		String fileName =new java.util.Date().getTime() + "_" + (int)(1000 *Math.random())+"." +extName;
		mkDir(new File(fileDir));
		String filePath = fileDir+fileName;
		File outFile = new File(filePath); 
	  	//创建输出流 
	  	FileOutputStream outStream = new FileOutputStream(outFile);
	  	BufferedInputStream bi = new BufferedInputStream(bis);
	  	byte[] data = new byte[1024];  
	  	 	int len = -1;
        while ((len = bi.read(data)) != -1) {
       	 outStream.write(data, 0, len);
        }
        outStream.flush();   
	  	outStream.close();
	  	bi.close();
		in.close();
        if (http != null) {
            http.disconnect();// 关闭连接
        }
        jsonObject.put("filePath", filePath.replace(localFileRoot, ""));
        jsonObject.put("fileName", fileName);
        if(http.getHeaderFields().get("Content-Type")!=null&&http.getHeaderFields().get("Content-Type").size()>0){
        	jsonObject.put("fileType", getFileTypeByContentType((http.getHeaderFields().get("Content-Type").get(0))));
        }else{
        	jsonObject.put("fileType","file");
        }
       
        //获取url前缀
        //String httpDomain = ResourceLoader.getInstance().getConfig().getProperty("url.domain");
        if(StringUtils.isNotBlank(httpDomain)){
        	jsonObject.put("fileUrl", filePath.replace(localFileRoot, httpDomain));        	
        }
        return jsonObject.toJSONString();
    	
    }
    
    /**
     * 新建文件目录（迭代建立）
     * @param file
     */
    private static  void mkDir(File file) {  
        if (file.getParentFile().exists()) {  
            file.mkdir();  
        } else {  
            mkDir(file.getParentFile());  
            file.mkdir();    
        }  
    }
    /**
     * 获取文件的ContentType
     * @param contentType
     * @return
     */
    private static String getFileTypeByContentType(String contentType){
    	String fileType = "file";
    	if("image/jpeg".equals(contentType)){
    		fileType = "image";
    	}else if("audio/mpeg".equals(contentType)||"audio/amr".equals(contentType)){
    		fileType = "audio";
    	}else if("video/mp4".equals(contentType)||"video/mpeg4".equals(contentType)){
    		fileType = "video";
    	}
    	return fileType;
    }
	
	/**
     * 初始化http请求参数
     * @param url
     * @param method
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    private static HttpsURLConnection initHttps (String url, String method, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        TrustManager[] tm = { new MyX509TrustManager() };  
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
        sslContext.init(null, tm, new java.security.SecureRandom());  
        // 从上述SSLContext对象中得到SSLSocketFactory对象  
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        URL _url = new URL(url);
        HttpsURLConnection http = (HttpsURLConnection) _url.openConnection();
        // 设置域名校验
        http.setHostnameVerifier(new HttpConnector().new TrustAnyHostnameVerifier());
        // 连接超时
        http.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(25000);
        http.setRequestMethod(method);
        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (null != headers && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                http.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        http.setSSLSocketFactory(ssf);
        http.setDoOutput(true);
        http.setDoInput(true);
        //http.connect();
        return http;
    }
    
    /**
     * 初始化http请求参数
     * @param url
     * @param method
     * @param headers
     * @return
     * @throws IOException
     */
    private static HttpURLConnection initHttp (String url, String method, Map<String, String> headers) throws IOException {
        URL _url = new URL(url);
        HttpURLConnection http = (HttpURLConnection) _url.openConnection();
        // 连接超时
        http.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(25000);
        http.setRequestMethod(method);
        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (null != headers && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                http.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        http.setDoOutput(true);
        http.setDoInput(true);
        //http.connect();
        return http;
    }
    /**
     * https 域名校验
     * @param url
     * @param params
     * @return
     */
    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;// 直接返回true
        }
    }
    
    /**
     * 检测是否https
     * @param url
     */
    private static boolean isHttps (String url) {
        return url.startsWith("https");
    }
    /**
     * 功能描述: 构造请求参数
     * @return       返回类型: 
     * @throws UnsupportedEncodingException 
     */
    private static String initParams(String url, Map<String, String> params) throws UnsupportedEncodingException {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") == -1) {
            sb.append("?");
        }
        sb.append(map2Url(params));
        return sb.toString();
    }
    
    /**
     * map构造url
     * @return       返回类型: 
     * @throws UnsupportedEncodingException 
     */
    private static String map2Url(Map<String, String> paramToMap) throws UnsupportedEncodingException {
        if (null == paramToMap || paramToMap.isEmpty()) {
            return null;
        }
        StringBuffer url = new StringBuffer();
        boolean isfist = true;
        for (Entry<String, String> entry : paramToMap.entrySet()) {
            if (isfist) {
                isfist = false;
            } else {
                url.append("&");
            }
            url.append(entry.getKey()).append("=");
            String value = entry.getValue();
            if (value!=null&&(!"".equals(value))) {
                url.append(URLEncoder.encode(value, DEFAULT_CHARSET));
            }
        }
        return url.toString();
    }
}
/**
 * 证书管理
 */
class MyX509TrustManager implements X509TrustManager {

    public X509Certificate[] getAcceptedIssuers() {
        return null;  
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }
    
}
