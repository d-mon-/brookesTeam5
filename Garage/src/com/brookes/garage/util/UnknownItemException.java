package com.brookes.garage.util;

public class UnknownItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
		
	private Class<?> clazz;
	private Object id;
	
	public UnknownItemException(Class<?> clazz, Object id) {
		super(clazz.getName() + " with id=" + id + " doesn't exist.");
		this.clazz = clazz;
		this.id = id;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	public Object getId() {
		return id;
	}

}