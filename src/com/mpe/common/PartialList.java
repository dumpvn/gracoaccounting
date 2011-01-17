package com.mpe.common;


/**
 * @author Agung Hadiwaluyo
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
import java.util.*;
import java.io.*;

public class PartialList implements Serializable {
	private static final long serialVersionUID = 1L;
	int start, count, total;
	List list;
	
	/**
	 * Constructor for PartialList.
	 */
	public PartialList(int start, int count, int total, List list) {
		this.start = start;
		this.count = count;
		if (total==0) {
			this.total = list.size();
		} else this.total = total;
		this.list = list;
	}
	
	/** 
	 * Returns the start.
	 * @return int
	 */
	public int getStart() {
		return start;
	}
	
	/** 
	 * Returns the count.
	 * @return int
	 */
	public int getCount() {
		return count;
	}
	
	/** 
	 * Returns the total.
	 * @return int
	 */
	public int getTotal() {
		return total;
	}
	
	/** 
	 * Returns the list.
	 * @return List
	 */
	public List getList() {
		return list;
	}
}
