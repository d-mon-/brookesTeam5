package com.brookes.garage.dao;

import com.brookes.garage.entity.Status;

public interface StatusDao {
	public Status getFirstStatus();
	public Status getEstimateStatus();
	public Status getInvoiceStatus();
	public void initialiseStatus();
}
