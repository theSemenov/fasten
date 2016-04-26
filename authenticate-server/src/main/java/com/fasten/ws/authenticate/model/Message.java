package com.fasten.ws.authenticate.model;

import java.io.Serializable;


public interface Message<D> extends Serializable {

	String getType();
	String getSequenceId();
	D getData();
	
	void setType(String type);
	void setSequenceId(String id);
	void setData(D data);
}
