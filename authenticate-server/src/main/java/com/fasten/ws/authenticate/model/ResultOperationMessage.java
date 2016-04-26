package com.fasten.ws.authenticate.model;

import com.google.gson.annotations.SerializedName;

public class ResultOperationMessage extends AbstractMessage<ResultOperationModel> {
	@SerializedName("data")
	private ResultOperationModel data;
	{
		setType(MessageTypes.RESULT_OPERATION_TYPE);
	}
	
	
	public ResultOperationMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResultOperationMessage(String result) {
		super();
		data = new ResultOperationModel();
		data.setResult(result);
	}
	
	@Override
	public ResultOperationModel getData() {
		return data;
	}

	@Override
	public void setData(ResultOperationModel data) {
		this.data = data;
		
	}
	
}
