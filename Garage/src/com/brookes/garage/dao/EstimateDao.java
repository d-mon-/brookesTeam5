package com.brookes.garage.dao;

import com.brookes.garage.entity.Estimate;
import java.util.List;


public interface EstimateDao {
	public List<Estimate> getAllEstimates();
	public void addEstimate(Estimate estimate);
	public void updateEstimate(Estimate estimate);
}
