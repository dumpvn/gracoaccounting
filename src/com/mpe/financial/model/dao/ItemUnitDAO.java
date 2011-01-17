package com.mpe.financial.model.dao;

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpe.common.Formater;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.ItemUnitExchangeFK;
import com.mpe.financial.model.base.BaseItemUnitDAO;


public class ItemUnitDAO extends BaseItemUnitDAO {
	Log log = LogFactory.getFactory().getInstance(this.getClass());

	/**
	 * Default constructor.  Can be used in place of getInstance()
	 */
	public ItemUnitDAO () {}
	
	public double getConversion(ItemUnit fromItemUnit, ItemUnit toItemUnit, Item item) {
		double a = 1;
		boolean found = false;
		if (fromItemUnit!=null && toItemUnit!=null) {
			Iterator iterator = fromItemUnit.getItemUnitExchangesByFromItemUnit()!=null?fromItemUnit.getItemUnitExchangesByFromItemUnit().iterator():new LinkedHashSet().iterator();
			while (iterator.hasNext()) {
				ItemUnitExchangeFK itemUnitExchangeFK = (ItemUnitExchangeFK)iterator.next();
				if (itemUnitExchangeFK.getToItemUnit().getId()==toItemUnit.getId() && itemUnitExchangeFK.getItem()==null) {
					a = itemUnitExchangeFK.getConversion();
					found = true;
				} else if (itemUnitExchangeFK.getToItemUnit().getId()==toItemUnit.getId() && itemUnitExchangeFK.getItem()!=null && itemUnitExchangeFK.getItem().getId()==item.getId()) {
					a = itemUnitExchangeFK.getConversion();
					found = true;
				}
			}
			if (!found) {
				iterator = toItemUnit.getItemUnitExchangesByFromItemUnit()!=null?toItemUnit.getItemUnitExchangesByFromItemUnit().iterator():new LinkedHashSet().iterator();
				while (iterator.hasNext()) {
					ItemUnitExchangeFK itemUnitExchangeFK = (ItemUnitExchangeFK)iterator.next();
					if (itemUnitExchangeFK.getToItemUnit().getId()==fromItemUnit.getId() && itemUnitExchangeFK.getItem()==null) {
						a = 1 / itemUnitExchangeFK.getConversion();
						//found = true;
					} else if (itemUnitExchangeFK.getToItemUnit().getId()==fromItemUnit.getId() && itemUnitExchangeFK.getItem()!=null && itemUnitExchangeFK.getItem().getId()==item.getId()) {
						a = 1 / itemUnitExchangeFK.getConversion();
						//found = true;
					}
				}
			}
		}
		log.info("[ Unit conversion = "+a+" ]");
		return Formater.getFormatedOutputResult(5, a);
	}


}