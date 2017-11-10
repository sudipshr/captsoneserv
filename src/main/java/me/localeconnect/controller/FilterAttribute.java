package me.localeconnect.controller;

import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;

public class FilterAttribute {
	
	private String key;
	private ComparisonOperator op;
	private Object value;
	
	public FilterAttribute(){
		
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the op
	 */
	public ComparisonOperator getOp() {
		return op;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param op the op to set
	 */
	public void setOp(ComparisonOperator op) {
		this.op = op;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	

}
