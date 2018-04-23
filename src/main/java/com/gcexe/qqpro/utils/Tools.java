package com.gcexe.qqpro.utils;

import static com.gcexe.qqpro.utils.SecretKeyConstant.AES_SECRET_KEY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 通用工具方法接口实现
 */
@Component
public class Tools implements ITools {

	private double EARTH_RADIUS = 6378.137;

	// MD5加密字符串
	public String MD5Make(String str) {
		MessageDigest md5 = null;
		StringBuffer hexValue = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		if (str != null && !str.trim().equals("")) {
			char[] charArray = str.toCharArray();

			byte[] byteArray = new byte[charArray.length];

			for (int i = 0; i < charArray.length; i++)
				byteArray[i] = (byte) charArray[i];

			byte[] md5Bytes = md5.digest(byteArray);

			hexValue = new StringBuffer();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
		}
		return hexValue.toString();
	}

	// AES加密
	public String encrypt(String con) {
		if (con != null && !con.trim().equals("")) {
			try {
				KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(128, new SecureRandom(AES_SECRET_KEY.getBytes()));
				SecretKey secretKeyObj = kgen.generateKey();
				byte[] enCodeFormat = secretKeyObj.getEncoded();
				SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
				Cipher cipher = Cipher.getInstance("AES");// 创建密码器
				byte[] byteContent = con.getBytes("utf-8");
				cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
				byte[] result = cipher.doFinal(byteContent);

				return this.parseByte2HexStr(result); // 加密 .

			} catch (NoSuchAlgorithmException e) {
				return null;
			} catch (NoSuchPaddingException e) {
				return null;
			} catch (InvalidKeyException e) {
				return null;
			} catch (UnsupportedEncodingException e) {
				return null;
			} catch (IllegalBlockSizeException e) {
				return null;
			} catch (BadPaddingException e) {
				return null;
			}
		}
		return null;
	}

	// AES解密
	public String decrypt(String con) {
		if (con != null && !con.trim().equals("")) {
			try {
				byte[] content = this.parseHexStr2Byte(con);
				KeyGenerator kgen = KeyGenerator.getInstance("AES");
				kgen.init(128, new SecureRandom(AES_SECRET_KEY.getBytes()));
				SecretKey secretKey = kgen.generateKey();
				byte[] enCodeFormat = secretKey.getEncoded();
				SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
				Cipher cipher = Cipher.getInstance("AES");// 创建密码器
				cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
				byte[] result = cipher.doFinal(content);
				String strResult = new String(result, "UTF-8");
				return strResult; // 加密

			} catch (NoSuchAlgorithmException e) {
				return null;
			} catch (NoSuchPaddingException e) {
				return null;
			} catch (InvalidKeyException e) {
				return null;
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				return null;
			} catch (BadPaddingException e) {
				return null;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
		return null;
	}

	// 将二进制转换成16进制
	private String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	// 将16进制转换为二进制
	private byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	// 添加数据到cookie
	public Cookie addCookie(HttpServletResponse response, String name, String value, Integer time) {
		try {
			value = URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie(name, value);
		if (time != null) {
			cookie.setMaxAge(time);
		}
		response.addCookie(cookie);
		return cookie;
	}

	// 获取cookie
	public Cookie getCookieByName(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	// 获取cookie值
	public String getCookieValue(Cookie cookie) {
		if (cookie != null) {
			String value = cookie.getValue();
			try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
		}
		return null;
	}

	// 时间字符串
	public String formatDate(Date date, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		String folder = formater.format(date);
		return folder;
	}

	
	// 字符串转时间
	public Date formatDate(String date, String format) {

		SimpleDateFormat formater = new SimpleDateFormat(format);

		Date rdate = null;
		try {
			rdate = formater.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rdate;
	}

	// 将字符串转换成Long类型
	public Long formatStringToLong(String str) {
		Long num = null;
		try {
			num = Long.parseLong(str);
		} catch (Exception e) {
			return null;
		}
		return num;
	}

	/**
	 * 获得起始行数
	 * 
	 * @param pagenum
	 *            页码数
	 * @param maxNum
	 *            最大结果集数
	 * @return 得到的起始行数
	 */
	public Integer getStartNum(Integer pagenum, Integer maxNum) {
		if (pagenum == null) {
			pagenum = 1;
		}
		if (maxNum == null) {
			maxNum = 10;
		}
		Integer startNum = maxNum * (pagenum - 1);
		return startNum;
	}

	/**
	 * 获得总页数
	 * 
	 * @param rowscount
	 *            结果总数
	 * @param maxNum
	 *            最大结果集数
	 * @return 总页数
	 */
	public Integer getAllPage(Integer rowscount, Integer maxNum) {
		Integer result = 0;
		int results = rowscount / maxNum;
		if (rowscount % maxNum == 0) {
			result = results;
		} else {
			result = results + 1;
		}
		return result;
	}

	/**
	 * 解析格式化字符串
	 * 
	 * @param str
	 *            形如：1；2；3；
	 * @return 返回字符串数组
	 */
	public String[] formatStringSplit(String str, String param) {
		String[] result = null;
		try {
			result = str.split(param);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

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
	public JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	private double rad(double d) {
		return d * Math.PI / 180.0;
	}

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
	public String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
		Double lat1 = Double.parseDouble(lat1Str);
		Double lng1 = Double.parseDouble(lng1Str);
		Double lat2 = Double.parseDouble(lat2Str);
		Double lng2 = Double.parseDouble(lng2Str);

		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double difference = radLat1 - radLat2;
		double mdifference = rad(lng1) - rad(lng2);
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 10000) / 10000;
		String distanceStr = distance + "";
		distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));

		return distanceStr;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 保留两位小数
	 * 
	 * @param d
	 * @return
	 */
	public Double getTwoPointNum(Double d) {
		try {
			BigDecimal b = new BigDecimal(d);
			double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		} catch (Exception e) {
			return 0d;
		}

	}

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 *            待转字符串
	 * @return 整数
	 */
	public Integer formatStringToInteger(String str) {
		Integer result = 0;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			result = -1;
		}
		return result;
	}

}
