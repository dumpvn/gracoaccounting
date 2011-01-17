package com.mpe.financial.model.dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpe.common.Formater;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.CurrencyExchange;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.base.BaseCurrencyDAO;


public class CurrencyDAO extends BaseCurrencyDAO {
    Log log = LogFactory.getFactory().getInstance(this.getClass());

	/**
	 * Default constructor.  Can be used in place of getInstance()
	 */
	public CurrencyDAO () {}
	
	public double getExchangeRateFromCurrencyToCurrency(Currency fromCurrency, Currency toCurrency, OrganizationSetup organizationSetup, Calendar calendar) {
		double x = 0;
		try {
			if (fromCurrency!=null && toCurrency!=null && organizationSetup!=null) {
				if (fromCurrency.getId()!=toCurrency.getId()) {
					Set set = fromCurrency.getCurrencyExchangesByFromCurrency();
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						CurrencyExchange currencyExchange = (CurrencyExchange)iterator.next();
						//log.info("X : "+currencyExchange.getId().getCurrencyExchangeRateType().getType()+"//"+organizationSetup.getCurrencyExchangeType()+"//"+calendar.getTime().getTime()+"//"+currencyExchange.getId().getValidFrom().getTime()+"//"+currencyExchange.getId().getValidTo().getTime());
						if (currencyExchange.getId().getCurrencyExchangeRateType().getType().equalsIgnoreCase(organizationSetup.getCurrencyExchangeType()) && calendar.getTime().getTime()>= currencyExchange.getId().getValidFrom().getTime() && calendar.getTime().getTime()<= currencyExchange.getId().getValidTo().getTime() && currencyExchange.getId().getToCurrency().getId()==toCurrency.getId()) {
							x = currencyExchange.getExchangeRate();
							//log.info("[ Exchange rate #1 : "+x+" ]");
						}
					}
					// cek x still zero?
					if (x == 0) {
						Set set2 = toCurrency.getCurrencyExchangesByFromCurrency();
						Iterator iterator2 = set2.iterator();
						while (iterator2.hasNext()) {
							CurrencyExchange currencyExchange = (CurrencyExchange)iterator2.next();
							//log.info("X : "+currencyExchange.getId().getCurrencyExchangeRateType().getType()+"//"+organizationSetup.getCurrencyExchangeType()+"//"+calendar.getTime().getTime()+"//"+currencyExchange.getId().getValidFrom().getTime()+"//"+currencyExchange.getId().getValidTo().getTime());
							if (currencyExchange.getId().getCurrencyExchangeRateType().getType().equalsIgnoreCase(organizationSetup.getCurrencyExchangeType()) && calendar.getTime().getTime()>= currencyExchange.getId().getValidFrom().getTime() && calendar.getTime().getTime()<= currencyExchange.getId().getValidTo().getTime() && currencyExchange.getId().getToCurrency().getId()==fromCurrency.getId()) {
								if (currencyExchange.getExchangeRate()>0) x = 1 / currencyExchange.getExchangeRate();
								//log.info("[ Exchange rate #2 : "+x+" ]");
							}
						}
					}
				} else {
					x = 1;
				}
			}
			x = Formater.getFormatedOutputResult(5, x);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
		}
		return x;
	}


}