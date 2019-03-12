package com.oocl.springbootdemo.model;

public class Response {

	private String status;
	
	private String errorMsg;
	
	private Object result;
	
	private int updateCount;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", errorMsg=" + errorMsg + ", result=" + result + ", updateCount="
				+ updateCount + "]";
	}
	
}
