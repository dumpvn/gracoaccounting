package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the running_number table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="running_number"
 */

public abstract class BaseRunningNumber  implements Serializable {

	public static String REF = "RunningNumber";
	public static String PROP_JOURNAL_PREFIX = "JournalPrefix";
	public static String PROP_JOURNAL_SUFFIX = "JournalSuffix";
	public static String PROP_JOURNAL_NUMBER = "JournalNumber";
	public static String PROP_ITEM_PREFIX = "ItemPrefix";
	public static String PROP_ITEM_SUFFIX = "ItemSuffix";
	public static String PROP_ITEM_NUMBER = "ItemNumber";
	public static String PROP_WAREHOUSE_PREFIX = "WarehousePrefix";
	public static String PROP_WAREHOUSE_SUFFIX = "WarehouseSuffix";
	public static String PROP_WAREHOUSE_NUMBER = "WarehouseNumber";
	public static String PROP_MUTATION_PREFIX = "MutationPrefix";
	public static String PROP_MUTATION_SUFFIX = "MutationSuffix";
	public static String PROP_MUTATION_NUMBER = "MutationNumber";
	public static String PROP_STOCK_OPNAME_PREFIX = "StockOpnamePrefix";
	public static String PROP_STOCK_OPNAME_SUFFIX = "StockOpnameSuffix";
	public static String PROP_STOCK_OPNAME_NUMBER = "StockOpnameNumber";
	public static String PROP_ITEM_USAGE_PREFIX = "ItemUsagePrefix";
	public static String PROP_ITEM_USAGE_SUFFIX = "ItemUsageSuffix";
	public static String PROP_ITEM_USAGE_NUMBER = "ItemUsageNumber";
	public static String PROP_LENDING_PREFIX = "LendingPrefix";
	public static String PROP_LENDING_SUFFIX = "LendingSuffix";
	public static String PROP_LENDING_NUMBER = "LendingNumber";
	public static String PROP_PURCHASE_REQUEST_PREFIX = "PurchaseRequestPrefix";
	public static String PROP_PURCHASE_REQUEST_SUFFIX = "PurchaseRequestSuffix";
	public static String PROP_PURCHASE_REQUEST_NUMBER = "PurchaseRequestNumber";
	public static String PROP_PURCHASE_ORDER_PREFIX = "PurchaseOrderPrefix";
	public static String PROP_PURCHASE_ORDER_SUFFIX = "PurchaseOrderSuffix";
	public static String PROP_PURCHASE_ORDER_NUMBER = "PurchaseOrderNumber";
	public static String PROP_RECEIVING_PREFIX = "ReceivingPrefix";
	public static String PROP_RECEIVING_SUFFIX = "ReceivingSuffix";
	public static String PROP_RECEIVING_NUMBER = "ReceivingNumber";
	public static String PROP_VENDOR_BILL_PREFIX = "VendorBillPrefix";
	public static String PROP_VENDOR_BILL_SUFFIX = "VendorBillSuffix";
	public static String PROP_VENDOR_BILL_NUMBER = "VendorBillNumber";
	public static String PROP_PAYMENT_TO_VENDOR_PREFIX = "PaymentToVendorPrefix";
	public static String PROP_PAYMENT_TO_VENDOR_SUFFIX = "PaymentToVendorSuffix";
	public static String PROP_PAYMENT_TO_VENDOR_NUMBER = "PaymentToVendorNumber";
	public static String PROP_PREPAYMENT_TO_VENDOR_PREFIX = "PrepaymentToVendorPrefix";
	public static String PROP_PREPAYMENT_TO_VENDOR_SUFFIX = "PrepaymentToVendorSuffix";
	public static String PROP_PREPAYMENT_TO_VENDOR_NUMBER = "PrepaymentToVendorNumber";
	public static String PROP_RETUR_TO_VENDOR_PREFIX = "ReturToVendorPrefix";
	public static String PROP_RETUR_TO_VENDOR_SUFFIX = "ReturToVendorSuffix";
	public static String PROP_RETUR_TO_VENDOR_NUMBER = "ReturToVendorNumber";
	public static String PROP_SALES_ORDER_PREFIX = "SalesOrderPrefix";
	public static String PROP_SALES_ORDER_SUFFIX = "SalesOrderSuffix";
	public static String PROP_SALES_ORDER_NUMBER = "SalesOrderNumber";
	public static String PROP_DELIVERY_ORDER_PREFIX = "DeliveryOrderPrefix";
	public static String PROP_DELIVERY_ORDER_SUFFIX = "DeliveryOrderSuffix";
	public static String PROP_DELIVERY_ORDER_NUMBER = "DeliveryOrderNumber";
	public static String PROP_INVOICE_PREFIX = "InvoicePrefix";
	public static String PROP_INVOICE_SUFFIX = "InvoiceSuffix";
	public static String PROP_INVOICE_NUMBER = "InvoiceNumber";
	public static String PROP_CUSTOMER_PAYMENT_PREFIX = "CustomerPaymentPrefix";
	public static String PROP_CUSTOMER_PAYMENT_SUFFIX = "CustomerPaymentSuffix";
	public static String PROP_CUSTOMER_PAYMENT_NUMBER = "CustomerPaymentNumber";
	public static String PROP_CUSTOMER_PREPAYMENT_PREFIX = "CustomerPrepaymentPrefix";
	public static String PROP_CUSTOMER_PREPAYMENT_SUFFIX = "CustomerPrepaymentSuffix";
	public static String PROP_CUSTOMER_PREPAYMENT_NUMBER = "CustomerPrepaymentNumber";
	public static String PROP_CUSTOMER_RETUR_PREFIX = "CustomerReturPrefix";
	public static String PROP_CUSTOMER_RETUR_SUFFIX = "CustomerReturSuffix";
	public static String PROP_CUSTOMER_RETUR_NUMBER = "CustomerReturNumber";
	public static String PROP_BANK_TRANSACTION_PREFIX = "BankTransactionPrefix";
	public static String PROP_BANK_TRANSACTION_SUFFIX = "BankTransactionSuffix";
	public static String PROP_BANK_TRANSACTION_NUMBER = "BankTransactionNumber";
	public static String PROP_STANDART_NPWP_TAX_NUMBER = "StandartNpwpTaxNumber";
	public static String PROP_SIMPLE_NPWP_TAX_NUMBER = "SimpleNpwpTaxNumber";
	public static String PROP_POS_ORDER_NUMBER = "PosOrderNumber";
	public static String PROP_MEMBER_NUMBER = "MemberNumber";


	// constructors
	public BaseRunningNumber () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRunningNumber (long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String journalPrefix;
	private java.lang.String journalSuffix;
	private java.lang.String journalNumber;
	private java.lang.String itemPrefix;
	private java.lang.String itemSuffix;
	private java.lang.String itemNumber;
	private java.lang.String warehousePrefix;
	private java.lang.String warehouseSuffix;
	private java.lang.String warehouseNumber;
	private java.lang.String mutationPrefix;
	private java.lang.String mutationSuffix;
	private java.lang.String mutationNumber;
	private java.lang.String stockOpnamePrefix;
	private java.lang.String stockOpnameSuffix;
	private java.lang.String stockOpnameNumber;
	private java.lang.String itemUsagePrefix;
	private java.lang.String itemUsageSuffix;
	private java.lang.String itemUsageNumber;
	private java.lang.String lendingPrefix;
	private java.lang.String lendingSuffix;
	private java.lang.String lendingNumber;
	private java.lang.String purchaseRequestPrefix;
	private java.lang.String purchaseRequestSuffix;
	private java.lang.String purchaseRequestNumber;
	private java.lang.String purchaseOrderPrefix;
	private java.lang.String purchaseOrderSuffix;
	private java.lang.String purchaseOrderNumber;
	private java.lang.String receivingPrefix;
	private java.lang.String receivingSuffix;
	private java.lang.String receivingNumber;
	private java.lang.String vendorBillPrefix;
	private java.lang.String vendorBillSuffix;
	private java.lang.String vendorBillNumber;
	private java.lang.String paymentToVendorPrefix;
	private java.lang.String paymentToVendorSuffix;
	private java.lang.String paymentToVendorNumber;
	private java.lang.String prepaymentToVendorPrefix;
	private java.lang.String prepaymentToVendorSuffix;
	private java.lang.String prepaymentToVendorNumber;
	private java.lang.String returToVendorPrefix;
	private java.lang.String returToVendorSuffix;
	private java.lang.String returToVendorNumber;
	private java.lang.String salesOrderPrefix;
	private java.lang.String salesOrderSuffix;
	private java.lang.String salesOrderNumber;
	private java.lang.String deliveryOrderPrefix;
	private java.lang.String deliveryOrderSuffix;
	private java.lang.String deliveryOrderNumber;
	private java.lang.String invoicePrefix;
	private java.lang.String invoiceSuffix;
	private java.lang.String invoiceNumber;
	private java.lang.String customerPaymentPrefix;
	private java.lang.String customerPaymentSuffix;
	private java.lang.String customerPaymentNumber;
	private java.lang.String customerPrepaymentPrefix;
	private java.lang.String customerPrepaymentSuffix;
	private java.lang.String customerPrepaymentNumber;
	private java.lang.String customerReturPrefix;
	private java.lang.String customerReturSuffix;
	private java.lang.String customerReturNumber;
	private java.lang.String bankTransactionPrefix;
	private java.lang.String bankTransactionSuffix;
	private java.lang.String bankTransactionNumber;
	private java.lang.String standartNpwpTaxNumber;
	private java.lang.String simpleNpwpTaxNumber;
	private java.lang.String posOrderNumber;
	private java.lang.String memberNumber;

	// one to one
	private com.mpe.financial.model.OrganizationSetup organizationSetup;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="organization_id"
     */
	public long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: journal_prefix
	 */
	public java.lang.String getJournalPrefix () {
		return journalPrefix;
	}

	/**
	 * Set the value related to the column: journal_prefix
	 * @param journalPrefix the journal_prefix value
	 */
	public void setJournalPrefix (java.lang.String journalPrefix) {
		this.journalPrefix = journalPrefix;
	}



	/**
	 * Return the value associated with the column: journal_suffix
	 */
	public java.lang.String getJournalSuffix () {
		return journalSuffix;
	}

	/**
	 * Set the value related to the column: journal_suffix
	 * @param journalSuffix the journal_suffix value
	 */
	public void setJournalSuffix (java.lang.String journalSuffix) {
		this.journalSuffix = journalSuffix;
	}



	/**
	 * Return the value associated with the column: journal_number
	 */
	public java.lang.String getJournalNumber () {
		return journalNumber;
	}

	/**
	 * Set the value related to the column: journal_number
	 * @param journalNumber the journal_number value
	 */
	public void setJournalNumber (java.lang.String journalNumber) {
		this.journalNumber = journalNumber;
	}



	/**
	 * Return the value associated with the column: item_prefix
	 */
	public java.lang.String getItemPrefix () {
		return itemPrefix;
	}

	/**
	 * Set the value related to the column: item_prefix
	 * @param itemPrefix the item_prefix value
	 */
	public void setItemPrefix (java.lang.String itemPrefix) {
		this.itemPrefix = itemPrefix;
	}



	/**
	 * Return the value associated with the column: item_suffix
	 */
	public java.lang.String getItemSuffix () {
		return itemSuffix;
	}

	/**
	 * Set the value related to the column: item_suffix
	 * @param itemSuffix the item_suffix value
	 */
	public void setItemSuffix (java.lang.String itemSuffix) {
		this.itemSuffix = itemSuffix;
	}



	/**
	 * Return the value associated with the column: item_number
	 */
	public java.lang.String getItemNumber () {
		return itemNumber;
	}

	/**
	 * Set the value related to the column: item_number
	 * @param itemNumber the item_number value
	 */
	public void setItemNumber (java.lang.String itemNumber) {
		this.itemNumber = itemNumber;
	}



	/**
	 * Return the value associated with the column: warehouse_prefix
	 */
	public java.lang.String getWarehousePrefix () {
		return warehousePrefix;
	}

	/**
	 * Set the value related to the column: warehouse_prefix
	 * @param warehousePrefix the warehouse_prefix value
	 */
	public void setWarehousePrefix (java.lang.String warehousePrefix) {
		this.warehousePrefix = warehousePrefix;
	}



	/**
	 * Return the value associated with the column: warehouse_suffix
	 */
	public java.lang.String getWarehouseSuffix () {
		return warehouseSuffix;
	}

	/**
	 * Set the value related to the column: warehouse_suffix
	 * @param warehouseSuffix the warehouse_suffix value
	 */
	public void setWarehouseSuffix (java.lang.String warehouseSuffix) {
		this.warehouseSuffix = warehouseSuffix;
	}



	/**
	 * Return the value associated with the column: warehouse_number
	 */
	public java.lang.String getWarehouseNumber () {
		return warehouseNumber;
	}

	/**
	 * Set the value related to the column: warehouse_number
	 * @param warehouseNumber the warehouse_number value
	 */
	public void setWarehouseNumber (java.lang.String warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}



	/**
	 * Return the value associated with the column: mutation_prefix
	 */
	public java.lang.String getMutationPrefix () {
		return mutationPrefix;
	}

	/**
	 * Set the value related to the column: mutation_prefix
	 * @param mutationPrefix the mutation_prefix value
	 */
	public void setMutationPrefix (java.lang.String mutationPrefix) {
		this.mutationPrefix = mutationPrefix;
	}



	/**
	 * Return the value associated with the column: mutation_suffix
	 */
	public java.lang.String getMutationSuffix () {
		return mutationSuffix;
	}

	/**
	 * Set the value related to the column: mutation_suffix
	 * @param mutationSuffix the mutation_suffix value
	 */
	public void setMutationSuffix (java.lang.String mutationSuffix) {
		this.mutationSuffix = mutationSuffix;
	}



	/**
	 * Return the value associated with the column: mutation_number
	 */
	public java.lang.String getMutationNumber () {
		return mutationNumber;
	}

	/**
	 * Set the value related to the column: mutation_number
	 * @param mutationNumber the mutation_number value
	 */
	public void setMutationNumber (java.lang.String mutationNumber) {
		this.mutationNumber = mutationNumber;
	}



	/**
	 * Return the value associated with the column: stock_opname_prefix
	 */
	public java.lang.String getStockOpnamePrefix () {
		return stockOpnamePrefix;
	}

	/**
	 * Set the value related to the column: stock_opname_prefix
	 * @param stockOpnamePrefix the stock_opname_prefix value
	 */
	public void setStockOpnamePrefix (java.lang.String stockOpnamePrefix) {
		this.stockOpnamePrefix = stockOpnamePrefix;
	}



	/**
	 * Return the value associated with the column: stock_opname_suffix
	 */
	public java.lang.String getStockOpnameSuffix () {
		return stockOpnameSuffix;
	}

	/**
	 * Set the value related to the column: stock_opname_suffix
	 * @param stockOpnameSuffix the stock_opname_suffix value
	 */
	public void setStockOpnameSuffix (java.lang.String stockOpnameSuffix) {
		this.stockOpnameSuffix = stockOpnameSuffix;
	}



	/**
	 * Return the value associated with the column: stock_opname_number
	 */
	public java.lang.String getStockOpnameNumber () {
		return stockOpnameNumber;
	}

	/**
	 * Set the value related to the column: stock_opname_number
	 * @param stockOpnameNumber the stock_opname_number value
	 */
	public void setStockOpnameNumber (java.lang.String stockOpnameNumber) {
		this.stockOpnameNumber = stockOpnameNumber;
	}



	/**
	 * Return the value associated with the column: item_usage_prefix
	 */
	public java.lang.String getItemUsagePrefix () {
		return itemUsagePrefix;
	}

	/**
	 * Set the value related to the column: item_usage_prefix
	 * @param itemUsagePrefix the item_usage_prefix value
	 */
	public void setItemUsagePrefix (java.lang.String itemUsagePrefix) {
		this.itemUsagePrefix = itemUsagePrefix;
	}



	/**
	 * Return the value associated with the column: item_usage_suffix
	 */
	public java.lang.String getItemUsageSuffix () {
		return itemUsageSuffix;
	}

	/**
	 * Set the value related to the column: item_usage_suffix
	 * @param itemUsageSuffix the item_usage_suffix value
	 */
	public void setItemUsageSuffix (java.lang.String itemUsageSuffix) {
		this.itemUsageSuffix = itemUsageSuffix;
	}



	/**
	 * Return the value associated with the column: item_usage_number
	 */
	public java.lang.String getItemUsageNumber () {
		return itemUsageNumber;
	}

	/**
	 * Set the value related to the column: item_usage_number
	 * @param itemUsageNumber the item_usage_number value
	 */
	public void setItemUsageNumber (java.lang.String itemUsageNumber) {
		this.itemUsageNumber = itemUsageNumber;
	}



	/**
	 * Return the value associated with the column: lending_prefix
	 */
	public java.lang.String getLendingPrefix () {
		return lendingPrefix;
	}

	/**
	 * Set the value related to the column: lending_prefix
	 * @param lendingPrefix the lending_prefix value
	 */
	public void setLendingPrefix (java.lang.String lendingPrefix) {
		this.lendingPrefix = lendingPrefix;
	}



	/**
	 * Return the value associated with the column: lending_suffix
	 */
	public java.lang.String getLendingSuffix () {
		return lendingSuffix;
	}

	/**
	 * Set the value related to the column: lending_suffix
	 * @param lendingSuffix the lending_suffix value
	 */
	public void setLendingSuffix (java.lang.String lendingSuffix) {
		this.lendingSuffix = lendingSuffix;
	}



	/**
	 * Return the value associated with the column: lending_number
	 */
	public java.lang.String getLendingNumber () {
		return lendingNumber;
	}

	/**
	 * Set the value related to the column: lending_number
	 * @param lendingNumber the lending_number value
	 */
	public void setLendingNumber (java.lang.String lendingNumber) {
		this.lendingNumber = lendingNumber;
	}



	/**
	 * Return the value associated with the column: purchase_request_prefix
	 */
	public java.lang.String getPurchaseRequestPrefix () {
		return purchaseRequestPrefix;
	}

	/**
	 * Set the value related to the column: purchase_request_prefix
	 * @param purchaseRequestPrefix the purchase_request_prefix value
	 */
	public void setPurchaseRequestPrefix (java.lang.String purchaseRequestPrefix) {
		this.purchaseRequestPrefix = purchaseRequestPrefix;
	}



	/**
	 * Return the value associated with the column: purchase_request_suffix
	 */
	public java.lang.String getPurchaseRequestSuffix () {
		return purchaseRequestSuffix;
	}

	/**
	 * Set the value related to the column: purchase_request_suffix
	 * @param purchaseRequestSuffix the purchase_request_suffix value
	 */
	public void setPurchaseRequestSuffix (java.lang.String purchaseRequestSuffix) {
		this.purchaseRequestSuffix = purchaseRequestSuffix;
	}



	/**
	 * Return the value associated with the column: purchase_request_number
	 */
	public java.lang.String getPurchaseRequestNumber () {
		return purchaseRequestNumber;
	}

	/**
	 * Set the value related to the column: purchase_request_number
	 * @param purchaseRequestNumber the purchase_request_number value
	 */
	public void setPurchaseRequestNumber (java.lang.String purchaseRequestNumber) {
		this.purchaseRequestNumber = purchaseRequestNumber;
	}



	/**
	 * Return the value associated with the column: purchase_order_prefix
	 */
	public java.lang.String getPurchaseOrderPrefix () {
		return purchaseOrderPrefix;
	}

	/**
	 * Set the value related to the column: purchase_order_prefix
	 * @param purchaseOrderPrefix the purchase_order_prefix value
	 */
	public void setPurchaseOrderPrefix (java.lang.String purchaseOrderPrefix) {
		this.purchaseOrderPrefix = purchaseOrderPrefix;
	}



	/**
	 * Return the value associated with the column: purchase_order_suffix
	 */
	public java.lang.String getPurchaseOrderSuffix () {
		return purchaseOrderSuffix;
	}

	/**
	 * Set the value related to the column: purchase_order_suffix
	 * @param purchaseOrderSuffix the purchase_order_suffix value
	 */
	public void setPurchaseOrderSuffix (java.lang.String purchaseOrderSuffix) {
		this.purchaseOrderSuffix = purchaseOrderSuffix;
	}



	/**
	 * Return the value associated with the column: purchase_order_number
	 */
	public java.lang.String getPurchaseOrderNumber () {
		return purchaseOrderNumber;
	}

	/**
	 * Set the value related to the column: purchase_order_number
	 * @param purchaseOrderNumber the purchase_order_number value
	 */
	public void setPurchaseOrderNumber (java.lang.String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}



	/**
	 * Return the value associated with the column: receiving_prefix
	 */
	public java.lang.String getReceivingPrefix () {
		return receivingPrefix;
	}

	/**
	 * Set the value related to the column: receiving_prefix
	 * @param receivingPrefix the receiving_prefix value
	 */
	public void setReceivingPrefix (java.lang.String receivingPrefix) {
		this.receivingPrefix = receivingPrefix;
	}



	/**
	 * Return the value associated with the column: receiving_suffix
	 */
	public java.lang.String getReceivingSuffix () {
		return receivingSuffix;
	}

	/**
	 * Set the value related to the column: receiving_suffix
	 * @param receivingSuffix the receiving_suffix value
	 */
	public void setReceivingSuffix (java.lang.String receivingSuffix) {
		this.receivingSuffix = receivingSuffix;
	}



	/**
	 * Return the value associated with the column: receiving_number
	 */
	public java.lang.String getReceivingNumber () {
		return receivingNumber;
	}

	/**
	 * Set the value related to the column: receiving_number
	 * @param receivingNumber the receiving_number value
	 */
	public void setReceivingNumber (java.lang.String receivingNumber) {
		this.receivingNumber = receivingNumber;
	}



	/**
	 * Return the value associated with the column: vendor_bill_prefix
	 */
	public java.lang.String getVendorBillPrefix () {
		return vendorBillPrefix;
	}

	/**
	 * Set the value related to the column: vendor_bill_prefix
	 * @param vendorBillPrefix the vendor_bill_prefix value
	 */
	public void setVendorBillPrefix (java.lang.String vendorBillPrefix) {
		this.vendorBillPrefix = vendorBillPrefix;
	}



	/**
	 * Return the value associated with the column: vendor_bill_suffix
	 */
	public java.lang.String getVendorBillSuffix () {
		return vendorBillSuffix;
	}

	/**
	 * Set the value related to the column: vendor_bill_suffix
	 * @param vendorBillSuffix the vendor_bill_suffix value
	 */
	public void setVendorBillSuffix (java.lang.String vendorBillSuffix) {
		this.vendorBillSuffix = vendorBillSuffix;
	}



	/**
	 * Return the value associated with the column: vendor_bill_number
	 */
	public java.lang.String getVendorBillNumber () {
		return vendorBillNumber;
	}

	/**
	 * Set the value related to the column: vendor_bill_number
	 * @param vendorBillNumber the vendor_bill_number value
	 */
	public void setVendorBillNumber (java.lang.String vendorBillNumber) {
		this.vendorBillNumber = vendorBillNumber;
	}



	/**
	 * Return the value associated with the column: payment_to_vendor_prefix
	 */
	public java.lang.String getPaymentToVendorPrefix () {
		return paymentToVendorPrefix;
	}

	/**
	 * Set the value related to the column: payment_to_vendor_prefix
	 * @param paymentToVendorPrefix the payment_to_vendor_prefix value
	 */
	public void setPaymentToVendorPrefix (java.lang.String paymentToVendorPrefix) {
		this.paymentToVendorPrefix = paymentToVendorPrefix;
	}



	/**
	 * Return the value associated with the column: payment_to_vendor_suffix
	 */
	public java.lang.String getPaymentToVendorSuffix () {
		return paymentToVendorSuffix;
	}

	/**
	 * Set the value related to the column: payment_to_vendor_suffix
	 * @param paymentToVendorSuffix the payment_to_vendor_suffix value
	 */
	public void setPaymentToVendorSuffix (java.lang.String paymentToVendorSuffix) {
		this.paymentToVendorSuffix = paymentToVendorSuffix;
	}



	/**
	 * Return the value associated with the column: payment_to_vendor_number
	 */
	public java.lang.String getPaymentToVendorNumber () {
		return paymentToVendorNumber;
	}

	/**
	 * Set the value related to the column: payment_to_vendor_number
	 * @param paymentToVendorNumber the payment_to_vendor_number value
	 */
	public void setPaymentToVendorNumber (java.lang.String paymentToVendorNumber) {
		this.paymentToVendorNumber = paymentToVendorNumber;
	}



	/**
	 * Return the value associated with the column: prepayment_to_vendor_prefix
	 */
	public java.lang.String getPrepaymentToVendorPrefix () {
		return prepaymentToVendorPrefix;
	}

	/**
	 * Set the value related to the column: prepayment_to_vendor_prefix
	 * @param prepaymentToVendorPrefix the prepayment_to_vendor_prefix value
	 */
	public void setPrepaymentToVendorPrefix (java.lang.String prepaymentToVendorPrefix) {
		this.prepaymentToVendorPrefix = prepaymentToVendorPrefix;
	}



	/**
	 * Return the value associated with the column: prepayment_to_vendor_suffix
	 */
	public java.lang.String getPrepaymentToVendorSuffix () {
		return prepaymentToVendorSuffix;
	}

	/**
	 * Set the value related to the column: prepayment_to_vendor_suffix
	 * @param prepaymentToVendorSuffix the prepayment_to_vendor_suffix value
	 */
	public void setPrepaymentToVendorSuffix (java.lang.String prepaymentToVendorSuffix) {
		this.prepaymentToVendorSuffix = prepaymentToVendorSuffix;
	}



	/**
	 * Return the value associated with the column: prepayment_to_vendor_number
	 */
	public java.lang.String getPrepaymentToVendorNumber () {
		return prepaymentToVendorNumber;
	}

	/**
	 * Set the value related to the column: prepayment_to_vendor_number
	 * @param prepaymentToVendorNumber the prepayment_to_vendor_number value
	 */
	public void setPrepaymentToVendorNumber (java.lang.String prepaymentToVendorNumber) {
		this.prepaymentToVendorNumber = prepaymentToVendorNumber;
	}



	/**
	 * Return the value associated with the column: retur_to_vendor_prefix
	 */
	public java.lang.String getReturToVendorPrefix () {
		return returToVendorPrefix;
	}

	/**
	 * Set the value related to the column: retur_to_vendor_prefix
	 * @param returToVendorPrefix the retur_to_vendor_prefix value
	 */
	public void setReturToVendorPrefix (java.lang.String returToVendorPrefix) {
		this.returToVendorPrefix = returToVendorPrefix;
	}



	/**
	 * Return the value associated with the column: retur_to_vendor_suffix
	 */
	public java.lang.String getReturToVendorSuffix () {
		return returToVendorSuffix;
	}

	/**
	 * Set the value related to the column: retur_to_vendor_suffix
	 * @param returToVendorSuffix the retur_to_vendor_suffix value
	 */
	public void setReturToVendorSuffix (java.lang.String returToVendorSuffix) {
		this.returToVendorSuffix = returToVendorSuffix;
	}



	/**
	 * Return the value associated with the column: retur_to_vendor_number
	 */
	public java.lang.String getReturToVendorNumber () {
		return returToVendorNumber;
	}

	/**
	 * Set the value related to the column: retur_to_vendor_number
	 * @param returToVendorNumber the retur_to_vendor_number value
	 */
	public void setReturToVendorNumber (java.lang.String returToVendorNumber) {
		this.returToVendorNumber = returToVendorNumber;
	}



	/**
	 * Return the value associated with the column: sales_order_prefix
	 */
	public java.lang.String getSalesOrderPrefix () {
		return salesOrderPrefix;
	}

	/**
	 * Set the value related to the column: sales_order_prefix
	 * @param salesOrderPrefix the sales_order_prefix value
	 */
	public void setSalesOrderPrefix (java.lang.String salesOrderPrefix) {
		this.salesOrderPrefix = salesOrderPrefix;
	}



	/**
	 * Return the value associated with the column: sales_order_suffix
	 */
	public java.lang.String getSalesOrderSuffix () {
		return salesOrderSuffix;
	}

	/**
	 * Set the value related to the column: sales_order_suffix
	 * @param salesOrderSuffix the sales_order_suffix value
	 */
	public void setSalesOrderSuffix (java.lang.String salesOrderSuffix) {
		this.salesOrderSuffix = salesOrderSuffix;
	}



	/**
	 * Return the value associated with the column: sales_order_number
	 */
	public java.lang.String getSalesOrderNumber () {
		return salesOrderNumber;
	}

	/**
	 * Set the value related to the column: sales_order_number
	 * @param salesOrderNumber the sales_order_number value
	 */
	public void setSalesOrderNumber (java.lang.String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}



	/**
	 * Return the value associated with the column: delivery_order_prefix
	 */
	public java.lang.String getDeliveryOrderPrefix () {
		return deliveryOrderPrefix;
	}

	/**
	 * Set the value related to the column: delivery_order_prefix
	 * @param deliveryOrderPrefix the delivery_order_prefix value
	 */
	public void setDeliveryOrderPrefix (java.lang.String deliveryOrderPrefix) {
		this.deliveryOrderPrefix = deliveryOrderPrefix;
	}



	/**
	 * Return the value associated with the column: delivery_order_suffix
	 */
	public java.lang.String getDeliveryOrderSuffix () {
		return deliveryOrderSuffix;
	}

	/**
	 * Set the value related to the column: delivery_order_suffix
	 * @param deliveryOrderSuffix the delivery_order_suffix value
	 */
	public void setDeliveryOrderSuffix (java.lang.String deliveryOrderSuffix) {
		this.deliveryOrderSuffix = deliveryOrderSuffix;
	}



	/**
	 * Return the value associated with the column: delivery_order_number
	 */
	public java.lang.String getDeliveryOrderNumber () {
		return deliveryOrderNumber;
	}

	/**
	 * Set the value related to the column: delivery_order_number
	 * @param deliveryOrderNumber the delivery_order_number value
	 */
	public void setDeliveryOrderNumber (java.lang.String deliveryOrderNumber) {
		this.deliveryOrderNumber = deliveryOrderNumber;
	}



	/**
	 * Return the value associated with the column: invoice_prefix
	 */
	public java.lang.String getInvoicePrefix () {
		return invoicePrefix;
	}

	/**
	 * Set the value related to the column: invoice_prefix
	 * @param invoicePrefix the invoice_prefix value
	 */
	public void setInvoicePrefix (java.lang.String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}



	/**
	 * Return the value associated with the column: invoice_suffix
	 */
	public java.lang.String getInvoiceSuffix () {
		return invoiceSuffix;
	}

	/**
	 * Set the value related to the column: invoice_suffix
	 * @param invoiceSuffix the invoice_suffix value
	 */
	public void setInvoiceSuffix (java.lang.String invoiceSuffix) {
		this.invoiceSuffix = invoiceSuffix;
	}



	/**
	 * Return the value associated with the column: invoice_number
	 */
	public java.lang.String getInvoiceNumber () {
		return invoiceNumber;
	}

	/**
	 * Set the value related to the column: invoice_number
	 * @param invoiceNumber the invoice_number value
	 */
	public void setInvoiceNumber (java.lang.String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}



	/**
	 * Return the value associated with the column: customer_payment_prefix
	 */
	public java.lang.String getCustomerPaymentPrefix () {
		return customerPaymentPrefix;
	}

	/**
	 * Set the value related to the column: customer_payment_prefix
	 * @param customerPaymentPrefix the customer_payment_prefix value
	 */
	public void setCustomerPaymentPrefix (java.lang.String customerPaymentPrefix) {
		this.customerPaymentPrefix = customerPaymentPrefix;
	}



	/**
	 * Return the value associated with the column: customer_payment_suffix
	 */
	public java.lang.String getCustomerPaymentSuffix () {
		return customerPaymentSuffix;
	}

	/**
	 * Set the value related to the column: customer_payment_suffix
	 * @param customerPaymentSuffix the customer_payment_suffix value
	 */
	public void setCustomerPaymentSuffix (java.lang.String customerPaymentSuffix) {
		this.customerPaymentSuffix = customerPaymentSuffix;
	}



	/**
	 * Return the value associated with the column: customer_payment_number
	 */
	public java.lang.String getCustomerPaymentNumber () {
		return customerPaymentNumber;
	}

	/**
	 * Set the value related to the column: customer_payment_number
	 * @param customerPaymentNumber the customer_payment_number value
	 */
	public void setCustomerPaymentNumber (java.lang.String customerPaymentNumber) {
		this.customerPaymentNumber = customerPaymentNumber;
	}



	/**
	 * Return the value associated with the column: customer_prepayment_prefix
	 */
	public java.lang.String getCustomerPrepaymentPrefix () {
		return customerPrepaymentPrefix;
	}

	/**
	 * Set the value related to the column: customer_prepayment_prefix
	 * @param customerPrepaymentPrefix the customer_prepayment_prefix value
	 */
	public void setCustomerPrepaymentPrefix (java.lang.String customerPrepaymentPrefix) {
		this.customerPrepaymentPrefix = customerPrepaymentPrefix;
	}



	/**
	 * Return the value associated with the column: customer_prepayment_suffix
	 */
	public java.lang.String getCustomerPrepaymentSuffix () {
		return customerPrepaymentSuffix;
	}

	/**
	 * Set the value related to the column: customer_prepayment_suffix
	 * @param customerPrepaymentSuffix the customer_prepayment_suffix value
	 */
	public void setCustomerPrepaymentSuffix (java.lang.String customerPrepaymentSuffix) {
		this.customerPrepaymentSuffix = customerPrepaymentSuffix;
	}



	/**
	 * Return the value associated with the column: customer_prepayment_number
	 */
	public java.lang.String getCustomerPrepaymentNumber () {
		return customerPrepaymentNumber;
	}

	/**
	 * Set the value related to the column: customer_prepayment_number
	 * @param customerPrepaymentNumber the customer_prepayment_number value
	 */
	public void setCustomerPrepaymentNumber (java.lang.String customerPrepaymentNumber) {
		this.customerPrepaymentNumber = customerPrepaymentNumber;
	}



	/**
	 * Return the value associated with the column: customer_retur_prefix
	 */
	public java.lang.String getCustomerReturPrefix () {
		return customerReturPrefix;
	}

	/**
	 * Set the value related to the column: customer_retur_prefix
	 * @param customerReturPrefix the customer_retur_prefix value
	 */
	public void setCustomerReturPrefix (java.lang.String customerReturPrefix) {
		this.customerReturPrefix = customerReturPrefix;
	}



	/**
	 * Return the value associated with the column: customer_retur_suffix
	 */
	public java.lang.String getCustomerReturSuffix () {
		return customerReturSuffix;
	}

	/**
	 * Set the value related to the column: customer_retur_suffix
	 * @param customerReturSuffix the customer_retur_suffix value
	 */
	public void setCustomerReturSuffix (java.lang.String customerReturSuffix) {
		this.customerReturSuffix = customerReturSuffix;
	}



	/**
	 * Return the value associated with the column: customer_retur_number
	 */
	public java.lang.String getCustomerReturNumber () {
		return customerReturNumber;
	}

	/**
	 * Set the value related to the column: customer_retur_number
	 * @param customerReturNumber the customer_retur_number value
	 */
	public void setCustomerReturNumber (java.lang.String customerReturNumber) {
		this.customerReturNumber = customerReturNumber;
	}



	/**
	 * Return the value associated with the column: bank_transaction_prefix
	 */
	public java.lang.String getBankTransactionPrefix () {
		return bankTransactionPrefix;
	}

	/**
	 * Set the value related to the column: bank_transaction_prefix
	 * @param bankTransactionPrefix the bank_transaction_prefix value
	 */
	public void setBankTransactionPrefix (java.lang.String bankTransactionPrefix) {
		this.bankTransactionPrefix = bankTransactionPrefix;
	}



	/**
	 * Return the value associated with the column: bank_transaction_suffix
	 */
	public java.lang.String getBankTransactionSuffix () {
		return bankTransactionSuffix;
	}

	/**
	 * Set the value related to the column: bank_transaction_suffix
	 * @param bankTransactionSuffix the bank_transaction_suffix value
	 */
	public void setBankTransactionSuffix (java.lang.String bankTransactionSuffix) {
		this.bankTransactionSuffix = bankTransactionSuffix;
	}



	/**
	 * Return the value associated with the column: bank_transaction_number
	 */
	public java.lang.String getBankTransactionNumber () {
		return bankTransactionNumber;
	}

	/**
	 * Set the value related to the column: bank_transaction_number
	 * @param bankTransactionNumber the bank_transaction_number value
	 */
	public void setBankTransactionNumber (java.lang.String bankTransactionNumber) {
		this.bankTransactionNumber = bankTransactionNumber;
	}



	/**
	 * Return the value associated with the column: standart_npwp_tax_number
	 */
	public java.lang.String getStandartNpwpTaxNumber () {
		return standartNpwpTaxNumber;
	}

	/**
	 * Set the value related to the column: standart_npwp_tax_number
	 * @param standartNpwpTaxNumber the standart_npwp_tax_number value
	 */
	public void setStandartNpwpTaxNumber (java.lang.String standartNpwpTaxNumber) {
		this.standartNpwpTaxNumber = standartNpwpTaxNumber;
	}



	/**
	 * Return the value associated with the column: simple_npwp_tax_number
	 */
	public java.lang.String getSimpleNpwpTaxNumber () {
		return simpleNpwpTaxNumber;
	}

	/**
	 * Set the value related to the column: simple_npwp_tax_number
	 * @param simpleNpwpTaxNumber the simple_npwp_tax_number value
	 */
	public void setSimpleNpwpTaxNumber (java.lang.String simpleNpwpTaxNumber) {
		this.simpleNpwpTaxNumber = simpleNpwpTaxNumber;
	}



	/**
	 * Return the value associated with the column: pos_order_number
	 */
	public java.lang.String getPosOrderNumber () {
		return posOrderNumber;
	}

	/**
	 * Set the value related to the column: pos_order_number
	 * @param posOrderNumber the pos_order_number value
	 */
	public void setPosOrderNumber (java.lang.String posOrderNumber) {
		this.posOrderNumber = posOrderNumber;
	}



	/**
	 * Return the value associated with the column: member_number
	 */
	public java.lang.String getMemberNumber () {
		return memberNumber;
	}

	/**
	 * Set the value related to the column: member_number
	 * @param memberNumber the member_number value
	 */
	public void setMemberNumber (java.lang.String memberNumber) {
		this.memberNumber = memberNumber;
	}



	/**
	 * Return the value associated with the column: OrganizationSetup
	 */
	public com.mpe.financial.model.OrganizationSetup getOrganizationSetup () {
		return organizationSetup;
	}

	/**
	 * Set the value related to the column: OrganizationSetup
	 * @param organizationSetup the OrganizationSetup value
	 */
	public void setOrganizationSetup (com.mpe.financial.model.OrganizationSetup organizationSetup) {
		this.organizationSetup = organizationSetup;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.RunningNumber)) return false;
		else {
			com.mpe.financial.model.RunningNumber runningNumber = (com.mpe.financial.model.RunningNumber) obj;
			return (this.getId() == runningNumber.getId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}