package com.contactmanager.helper;

public class Message {
	private String msg;
	private String type;

	public Message(String msg, String type) {
		super();
		this.msg = msg;
		this.type = type;
	}

	public String getmsg() {
		return msg;
	}

	public void setmsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
