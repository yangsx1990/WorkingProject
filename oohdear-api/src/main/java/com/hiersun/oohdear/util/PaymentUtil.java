package com.hiersun.oohdear.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/** 支付的工具类
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2016年12月5日 下午2:22:32 
 * @version 1.0 
 */
public class PaymentUtil {
	/**
	 * 生成xml
	 * @param maps
	 * @return
	 */
	public static String generateXmlStr(LinkedHashMap<String, Object> maps){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<xml>");
        if(maps != null && maps.size() > 0){
            for(Map.Entry<String, Object> entry : maps.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                stringBuffer.append("<" + key + ">").append(value).append("</" + key + ">");
            }
        }
        stringBuffer.append("</xml>");
        return stringBuffer.toString();
    }

    /**
     * 获取XML的root元素
     * @param xmlStr
     * @return
     */
    public static Element getXmlRoot(String xmlStr){
        try {
            Document doc = DocumentHelper.parseText(xmlStr);
            return doc.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
	 /**
     * 根据传入的参数生成微信请求的签名
     * @param params 参数map
     * @return MD5签名结果
     */
    public static String generateSign(LinkedHashMap<String, Object> params){
        StringBuffer str = new StringBuffer();
        String result = "";
        if(params != null && params.size() > 0){
            for(Map.Entry<String, Object> entry : params.entrySet()){
                if(entry != null){
                    str.append(entry.getKey()).
                            append("=").
                            append(entry.getValue());
                }
            }
            String s = str.toString();
            result = MD5Utils.Encode(s,"UTF-8");
        }
        return result;
    }
	/**
	 * 生成随机字符串
	 * @return
	 */
	 public static String genNonceStr() {
	        Random random = new Random();
	        return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	   }
	 private final static String getMessageDigest(byte[] buffer) {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			try {
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(buffer);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} catch (Exception e) {
				return null;
			}
		}
	 /**
	  * 生成支付签名
	  * @param params
	  * @param order
	  * @return
	  */
	 public static String generateWeixinSign(Map params, boolean order,String apiSec){
	        Set set =  params.keySet();
	        Object[] keys = set.toArray();
	        if(order){
	            Arrays.sort(keys);
	        }
	        StringBuffer signStr = new StringBuffer();
	        for(int i = 0; i < keys.length; i ++){
	            if(i == 0){
	                signStr.append(keys[i]).append("=").append(params.get(keys[i]));
	            }else{
	                signStr.append("&").append(keys[i]).append("=").append(params.get(keys[i]));
	            }
	        }
	        signStr.append("&key=").append(apiSec);
	        System.out.println(signStr.toString());
	        return MD5Utils.Encode(signStr.toString(), "UTF-8");
	    }
}
