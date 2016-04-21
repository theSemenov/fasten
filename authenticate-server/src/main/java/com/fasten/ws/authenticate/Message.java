package com.fasten.ws.authenticate;

public interface Message<D> {

	String getType();
	String getSequenceId();
	D getData();
	
	void setType(String type);
	void setSequenceId(String id);
	void setData(D data);
}
