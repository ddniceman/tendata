package com.gcexe.qqdata.uitls;

/**
 * 返回json对象
 */
public class ResultCodeVo  {
	/** 是否成功(true:成功,false:失败) */
	private boolean success = false;
	/** 错误代码 */
	private int code;
	/** 消息 */
	private String msg;
	/** 数据 */
	private Object data;

	/**
	 * action通用返回json对象
	 */
	public ResultCodeVo() {

	}

	/**
	 * action通用返回json对象
	 * @param success 是否成功
	 */
	public ResultCodeVo(boolean success) {
		this.success = success;
	}

	/**
	 * action通用返回json对象
	 * @param success 是否成功
	 * @param msg 消息
	 */
	public ResultCodeVo(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	/**
	 * action通用返回json对象
	 * @param success 是否成功
	 * @param data 数据
	 */
	public ResultCodeVo(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}

	/**
	 * action通用返回json对象
	 * @param success 是否成功
	 * @param msg 消息
	 * @param data 数据
	 */
	public ResultCodeVo(boolean success, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * action通用返回json对象
	 * @param success 是否成功
	 * @param code 错误代码
	 * @param msg 消息
	 */
	public ResultCodeVo(boolean success, int code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	/**
	 * action通用返回json对象
	 * @param success 是否成功
	 * @param code 错误代码
	 * @param msg 消息
	 * @param data 数据
	 */
	public ResultCodeVo(boolean success, int code, String msg, Object data) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * 获取是否成功
	 * @return 是否成功
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * 设置是否成功
	 * @param success 是否成功
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 获取错误代码
	 * @return 错误代码
	 */
	public int getcode() {
		return code;
	}

	/**
	 * 设置错误代码
	 * @param code 错误代码
	 */
	public void setcode(int code) {
		this.code = code;
	}

	/**
	 * 获取消息
	 * @return 消息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置消息
	 * @param msg 消息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 获取数据
	 * @return 数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置数据
	 * @param data 数据
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
