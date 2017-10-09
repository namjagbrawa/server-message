package com.bingo.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
	private static final Logger logger= LoggerFactory.getLogger(HttpClient.class);

    /**
     * get请求，url独立
     * @param url
     * @return
     */
    public static String sendRequestByGet(String url){
        return sendRequestByGet(url, true);
    }

    /**
     * get请求，url和参数独立
     * @param url
     * @param param
     * @return
     */
	public static String sendRequestByGet(String url,String param){
		return sendRequestByGet(url+"?"+param, true);
	}


    /**
	 * get请求，url拼接参数
	 * @param url
	 * @param isPrintLog 是否打印请求参数
     * @param encoding 可选参数，指定以某种字符集获取请求结果，默认utf-8
	 * @return
	 */
	public static String sendRequestByGet(String url, boolean isPrintLog,String ...encoding) {
		String result = "";
		BufferedReader in = null;
		HttpURLConnection connection = null;
        String encode="utf-8";
        if(encoding!=null&&encoding.length>0){
            encode=encoding[0];
        }
		try {
			if (isPrintLog) {
				logger.info("get请求参数url:" + url);
			}
			URL paostUrl = new URL(url);
			//请求配置
			connection = (HttpURLConnection) paostUrl.openConnection();
			connection.setRequestProperty("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),encode));
            String line = in.readLine();  
            while (line != null) {  
            	result += line;  
                line = in.readLine();  
            }  
		} catch (Exception e) {
			logger.error("get请求发生异常"+",url="+url, e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
					connection=null;
				}
			} catch (IOException e) {
				logger.error("response", e);
			}
		}
        logger.info("请求结果：{}",result);
		return result;
	}

    /**
	 * post请求 form表单方式
	 * @param url
	 * @param paramStr
	 * @return
	 */
	public static String sendRequestByPost(String url, String paramStr, boolean isPrintLog) {
	    return sendRequestByPostGiveContentType(url, paramStr, isPrintLog, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
	}

    /**
     * post请求 form表单方式,指定请求结果字符集
     * @param url
     * @param paramStr
     * @param encode
     * @return
     */
    public static String sendRequestByPost(String url, String paramStr, boolean isPrintLog,String encode) {
        return sendRequestByPostGiveContentType(url, paramStr, isPrintLog, MediaType.APPLICATION_FORM_URLENCODED_VALUE,encode);
    }
	
	/**
	 * post请求 Content-Type json
	 * @param url
	 * @param paramStr
	 * @param isPrintLog
	 * @return
	 */
	public static String sendRequestByPostAndJson(String url, String paramStr, boolean isPrintLog) {
        return sendRequestByPostGiveContentType(url, paramStr, isPrintLog, MediaType.APPLICATION_JSON_VALUE);
    }


    /**
     * post方式请求，可以指定contentType和请求结果的字符集
     * @param url
     * @param paramStr 参数
     * @param isPrintLog 是否打印请求参数
     * @param contentType 指定contentType
     * @param encode 指定以某种字符集获取请求结果
     * @return
        */
    public static String sendRequestByPostGiveContentType(String url, String paramStr, boolean isPrintLog, String contentType,String ... encode){
        String result = "";
        BufferedReader in = null;
        HttpURLConnection connection = null;
        OutputStream out = null;
        String encoding="utf-8";/*默认utf-8字符集*/
        if(encode!=null&&encode.length>0){
            encoding=encode[0];
        }
        try {
            if (isPrintLog) {
                logger.info("post请求参数url:" + url + ",paramStr:" + paramStr);
            }
            URL paostUrl = new URL(url);
        //参数配置
        connection = (HttpURLConnection) paostUrl.openConnection();
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true); //http正文内，因此需要设为true, 默认情况下是false
        connection.setDoInput(true); //设置是否从httpUrlConnection读入，默认情况下是true
        connection.setUseCaches(false); //Post 请求不能使用缓存
        connection.setInstanceFollowRedirects(true); //URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
        connection.setConnectTimeout(30000); //设置连接主机超时时间
        connection.setReadTimeout(30000); //设置从主机读取数据超时

        //打开连接
        out = connection.getOutputStream();
        out.write(paramStr.getBytes());
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
        String line = in.readLine();
        while (line != null) {
            result += line;
            line = in.readLine();
            }  
        } catch (Exception e) {
            logger.error("post请求发生异常"+",url="+url+",paramStr:"+paramStr, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                logger.error("response", e);
            }
        }
        logger.info("请求结果：{}",result);
        return result;
    
	}

}