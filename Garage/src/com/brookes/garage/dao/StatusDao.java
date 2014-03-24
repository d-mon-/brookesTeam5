package com.brookes.garage.dao;

import com.brookes.garage.entity.Status;

public interface StatusDao {
	public Status getFirstStatus();
	public Status getEstimateStatus();
	public void initialiseStatus();
}
