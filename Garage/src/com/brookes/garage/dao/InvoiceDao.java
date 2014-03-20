package com.brookes.garage.dao;

import java.util.List;
import com.brookes.garage.entity.Invoice;

public interface InvoiceDao {
	public List<Invoice> getAllInvoices();
	public void addInvoice(Invoice invoice);
	public void updateInvoice(Invoice invoice);
}