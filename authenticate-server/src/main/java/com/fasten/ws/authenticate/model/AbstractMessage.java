package com.fasten.ws.authenticate.model;


public abstract class AbstractMessage<D> implements Message<D>{
	
	private String type;
	private String sequenceId;

	@Override
	public String getType() {
		return  type;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
		
	}
	@Override
	public String getSequenceId() {
		return sequenceId;
	}	

	@Override
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
		
	}

}
