package com.brookes.garage.dao;

import java.util.List;
import com.brookes.garage.entity.Repair;

public interface RepairDao {
	public List<Repair> getAllRepairs();
	public void addRepair(Repair repair);
	public void updateRepair(Repair repair);
}



