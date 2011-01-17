package com.mpe.financial.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.mpe.common.Formater;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.base.BaseVendorsDAO;


public class VendorsDAO extends BaseVendorsDAO {
	Log log = LogFactory.getFactory().getInstance(this.getClass());

	/**
	 * Default constructor.  Can be used in place of getInstance()
	 */
	public VendorsDAO () {}

	public double getAccountPayableAmount(Vendors vendors, Calendar fromDate, Calendar toDate) {
		double a = 0;
		String sql = "" +
				"select " +
				"(IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=d.vendor_bill_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=d.vendor_bill_id),0)) as A " +
				"from vendor_bill_detail c join vendor_bill d on c.vendor_bill_id=d.vendor_bill_id " +
				"where " +
				"d.vendor_id="+vendors.getId()+" and d.bill_date>= :fromDate and d.bill_date<= :toDate and d.status<>'CANCEL' and d.payment_to_vendor_status <> 'CLOSE' " +
				"";
		a = ((Double)getInstance().getSession().createSQLQuery(sql)
				.addScalar("A", Hibernate.DOUBLE)
				.setDate("fromDate", fromDate.getTime())
				.setDate("toDate", toDate.getTime())
				.setMaxResults(1).uniqueResult()).doubleValue();
		return Formater.getFormatedOutputResult(vendors.getNumberOfDigit(), a);
	}
	
	public boolean isOverCreditLimit(Vendors vendors, Calendar fromDate, Calendar toDate, double d) {
		if (toDate==null) toDate = new GregorianCalendar();
		if (!vendors.isBlockOverCreditLimit()) return false;
		else {
			if (vendors.getCreditLimit()<(getAccountPayableAmount(vendors, fromDate, toDate) + d)) return true;
			else return false;
		}
	}
	
	public double getAccountPayableAmount(Vendors vendors, Date fromDate, Date toDate) {
		double a = 0;
		String sql = "" +
				"select " +
				"(IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=d.vendor_bill_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=d.vendor_bill_id),0)) as A " +
				"from vendor_bill_detail c join vendor_bill d on c.vendor_bill_id=d.vendor_bill_id " +
				"where " +
				"d.vendor_id="+vendors.getId()+" and d.bill_date>= :fromDate and d.bill_date<= :toDate and d.status<>'CANCEL' and d.payment_to_vendor_status <> 'CLOSE' " +
				"";
		a = ((Double)getInstance().getSession().createSQLQuery(sql)
				.addScalar("A", Hibernate.DOUBLE)
				.setDate("fromDate", fromDate)
				.setDate("toDate", toDate)
				.setMaxResults(1).uniqueResult()).doubleValue();
		return Formater.getFormatedOutputResult(vendors.getNumberOfDigit(), a);
	}
	
	public boolean isOverCreditLimit(Vendors vendors, Date fromDate, Date toDate, double d) {
		if (toDate==null) toDate = new GregorianCalendar().getTime();
		if (vendors!=null && !vendors.isBlockOverCreditLimit()) return false;
		else if (vendors!=null) {
			//log.info(" R : "+vendors.getCreditLimit()+"//"+(getAccountPayableAmount(vendors, fromDate, toDate) + d));
			if (vendors.getCreditLimit()<(getAccountPayableAmount(vendors, fromDate, toDate) + d)) return true;
			else return false;
		} else return false;
	}

}