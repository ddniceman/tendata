package com.gcexe.qqpro.utils;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 通用工具方法接口定义
 * 
 */
public interface ITools {

	/**
	 * MD5加密字符串
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return 返回加密后的字符串
	 */
	public String MD5Make(String str);

	/**
	 * AES加密
	 * 
	 * @param content
	 *            加密内容
	 * @param key
	 *            密钥
	 * @return 加密数据
	 */
	public String encrypt(String con);

	/**
	 * AES加密
	 * 
	 * @param content
	 *            加密内容
	 * @param key
	 *            密钥
	 * @return 加密数据
	 */
	public String decrypt(String con);

	/**
	 * 添加数据到cookie
	 * 
	 * @param response
	 *            响应对象
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param time
	 *            保存时间
	 * @return 返回cookie对象
	 */
	public Cookie addCookie(HttpServletResponse response, String name, String value, Integer time);

	/**
	 * 获取cookie
	 * 
	 * @param request
	 *            请求对象
	 * @param name
	 *            cookie名称
	 * @return 返回cookie对象
	 */
	public Cookie getCookieByName(HttpServletRequest request, String name);

	/**
	 * 获取cookie的值
	 * 
	 * @param cookie
	 *            cookie对象
	 * @return cookie中保存的值
	 */
	public String getCookieValue(Cookie cookie);

	/**
	 * 格式化时间
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            参数
	 * @return 格式化后的时间
	 */
	public String formatDate(Date date, String format);

	/**
	 * 字符串转时间
	 * 
	 * @param date
	 *            时间
	 * @param format
	 *            参数
	 * @return 格式化后的时间
	 */
	public Date formatDate(String date, String format);

	/**
	 * 获得起始行数
	 * 
	 * @param pagenum
	 *            页码数
	 * @param maxNum
	 *            最大结果集数
	 * @return 得到的起始行数
	 */
	public Integer getStartNum(Integer pagenum, Integer maxNum);

	/**
	 * 获得总页数
	 * 
	 * @param rowscount
	 *            结果总数
	 * @param maxNum
	 *            最大结果集数
	 * @return 总页数
	 */
	public Integer getAllPage(Integer rowscount, Integer maxNum);

	/**
	 * 字符串转Long型
	 * 
	 * @param str
	 *            待转字符串
	 * @return Long值
	 */
	public Long formatStringToLong(String str);

	/**
	 * 解析格式化字符串
	 * 
	 * @param str
	 *            形如：1；2；3；
	 * @param param
	 *            格式参数
	 * @return 返回字符串数组
	 */
	public String[] formatStringSplit(String str, String param);

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses);

	/**
	 * 根据两个位置的经纬度，来计算两地的距离（单位为KM） 参数为String类型
	 * 
	 * @param lat1
	 *            用户经度
	 * @param lng1
	 *            用户纬度
	 * @param lat2
	 *            商家经度
	 * @param lng2
	 *            商家纬度
	 * @return
	 */
	public String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str);

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param);

	/**
	 * 保留两位小数
	 * 
	 * @param d
	 * @return
	 */
	public Double getTwoPointNum(Double d);

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 *            待转字符串
	 * @return 整数
	 */
	public Integer formatStringToInteger(String str);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}