package com.mpe.financial.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.Hibernate;

import com.mpe.common.Formater;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.base.BaseCustomersDAO;


public class CustomersDAO extends BaseCustomersDAO {

	/**
	 * Default constructor.  Can be used in place of getInstance()
	 */
	public CustomersDAO () {}
	
	public double getAccountReceivableAmount(Customers customers, Calendar fromDate, Calendar toDate) {
		double a = 0;
		String sql = "" +
				"select " +
				"(" +
				"select " +
				"IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from invoice_simple_customer_prepayment a where a.invoice_simple_id=d.invoice_simple_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_simple_id=d.invoice_simple_id),0) " +
				"from invoice_simple_detail c join invoice_simple d on c.invoice_simple_id=d.invoice_simple_id " +
				"where " +
				"d.customer_id="+customers.getId()+" and d.invoice_date>= :fromDate and d.invoice_date<= :toDate and d.status<>'CANCEL' and d.customer_payment_status <> 'CLOSE' " +
				") + (" +
				"select " +
				"IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from invoice_polos_customer_prepayment a where a.invoice_polos_id=d.invoice_polos_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_polos_id=d.invoice_polos_id),0) " +
				"from invoice_polos_detail c join invoice_polos d on c.invoice_polos_id=d.invoice_polos_id " +
				"where " +
				"d.customer_id="+customers.getId()+" and d.invoice_date>= :fromDate and d.invoice_date<= :toDate and d.status<>'CANCEL' and d.customer_payment_status <> 'CLOSE' " +
				") + (" +
				"select " +
				"IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=d.invoice_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=d.invoice_id),0) " +
				"from invoice_detail c join invoice d on c.invoice_id=d.invoice_id " +
				"where " +
				"d.customer_id="+customers.getId()+" and d.invoice_date>= :fromDate and d.invoice_date<= :toDate and d.status<>'CANCEL' and d.customer_payment_status <> 'CLOSE' " +
				") as A";
		a = ((Double)getInstance().getSession().createSQLQuery(sql)
				.addScalar("A", Hibernate.DOUBLE)
				.setDate("fromDate", fromDate.getTime())
				.setDate("toDate", toDate.getTime())
				.setMaxResults(1).uniqueResult()).doubleValue();
		return Formater.getFormatedOutputResult(customers.getNumberOfDigit(), a);
	}
	
	public boolean isOverCreditLimit(Customers customers, Calendar fromDate, Calendar toDate, double d) {
		if (toDate==null) toDate = new GregorianCalendar();
		if (!customers.isBlockOverCreditLimit()) return false;
		else {
			if (customers.getCreditLimit()<(getAccountReceivableAmount(customers, fromDate, toDate) + d)) return true;
			else return false;
		}
	}
	
	public double getAccountReceivableAmount(Customers customers, Date fromDate, Date toDate) {
		double a = 0;
		String sql = "" +
				"select " +
				"(" +
				"select " +
				"IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from invoice_simple_customer_prepayment a where a.invoice_simple_id=d.invoice_simple_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_simple_id=d.invoice_simple_id),0) " +
				"from invoice_simple_detail c join invoice_simple d on c.invoice_simple_id=d.invoice_simple_id " +
				"where " +
				"d.customer_id="+customers.getId()+" and d.invoice_date>= :fromDate and d.invoice_date<= :toDate and d.status<>'CANCEL' and d.customer_payment_status <> 'CLOSE' " +
				") + (" +
				"select " +
				"IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from invoice_polos_customer_prepayment a where a.invoice_polos_id=d.invoice_polos_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_polos_id=d.invoice_polos_id),0) " +
				"from invoice_polos_detail c join invoice_polos d on c.invoice_polos_id=d.invoice_polos_id " +
				"where " +
				"d.customer_id="+customers.getId()+" and d.invoice_date>= :fromDate and d.invoice_date<= :toDate and d.status<>'CANCEL' and d.customer_payment_status <> 'CLOSE' " +
				") + (" +
				"select " +
				"IFNULL(sum(((c.quantity*(c.price*c.exchange_rate-(c.price*c.exchange_rate*c.discount_procent/100)-c.discount_amount)*(c.tax_amount/100)*(1-(d.discount_procent/100)))-d.discount_amount)*(1+(d.tax_amount/100))),0) " +
				"-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=d.invoice_id),0) " +
				"-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=d.invoice_id),0) " +
				"from invoice_detail c join invoice d on c.invoice_id=d.invoice_id " +
				"where " +
				"d.customer_id="+customers.getId()+" and d.invoice_date>= :fromDate and d.invoice_date<= :toDate and d.status<>'CANCEL' and d.customer_payment_status <> 'CLOSE' " +
				") as A";
		a = ((Double)getInstance().getSession().createSQLQuery(sql)
				.addScalar("A", Hibernate.DOUBLE)
				.setDate("fromDate", fromDate)
				.setDate("toDate", toDate)
				.setMaxResults(1).uniqueResult()).doubleValue();
		return Formater.getFormatedOutputResult(customers.getNumberOfDigit(), a);
	}
	
	public boolean isOverCreditLimit(Customers customers, Date fromDate, Date toDate, double d) {
		if (toDate==null) toDate = new GregorianCalendar().getTime();
		if (!customers.isBlockOverCreditLimit()) return false;
		else {
			if (customers.getCreditLimit()<(getAccountReceivableAmount(customers, fromDate, toDate) + d)) return true;
			else return false;
		}
	}


}