package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customers table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customers"
 */

public abstract class BaseCustomers  implements Serializable {

	public static String REF = "Customers";
	public static String PROP_COMPANY = "Company";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_CITY = "City";
	public static String PROP_AREA_CODE = "AreaCode";
	public static String PROP_POSTAL_CODE = "PostalCode";
	public static String PROP_PROVINCE = "Province";
	public static String PROP_COUNTRY = "Country";
	public static String PROP_TELEPHONE = "Telephone";
	public static String PROP_FAX = "Fax";
	public static String PROP_EMAIL = "Email";
	public static String PROP_ACTIVE = "Active";
	public static String PROP_STORE = "Store";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CODE = "Code";
	public static String PROP_NPWP = "Npwp";
	public static String PROP_CONTACT_PERSON = "ContactPerson";
	public static String PROP_POSITION = "Position";
	public static String PROP_BLOCK_OVER_CREDIT_LIMIT = "BlockOverCreditLimit";
	public static String PROP_CREDIT_LIMIT = "CreditLimit";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseCustomers () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomers (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomers (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String company,
		boolean blockOverCreditLimit) {

		this.setId(id);
		this.setOrganization(organization);
		this.setCompany(company);
		this.setBlockOverCreditLimit(blockOverCreditLimit);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String company;
	private java.lang.String address;
	private java.lang.String city;
	private java.lang.String areaCode;
	private java.lang.String postalCode;
	private java.lang.String province;
	private java.lang.String country;
	private java.lang.String telephone;
	private java.lang.String fax;
	private java.lang.String email;
	private boolean active;
	private boolean store;
	private java.lang.String description;
	private java.lang.String code;
	private java.lang.String npwp;
	private java.lang.String contactPerson;
	private java.lang.String position;
	private boolean blockOverCreditLimit;
	private double creditLimit;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.ChartOfAccount chartOfAccount;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.ItemPriceCategory itemPriceCategory;
	private com.mpe.financial.model.Customers customerAlias;
	private com.mpe.financial.model.CustomersCategory customersCategory;

	// collections
	private java.util.Set customersCommunications;
	private java.util.Set customersAddresses;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="customer_id"
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
	 * Return the value associated with the column: company
	 */
	public java.lang.String getCompany () {
		return company;
	}

	/**
	 * Set the value related to the column: company
	 * @param company the company value
	 */
	public void setCompany (java.lang.String company) {
		this.company = company;
	}



	/**
	 * Return the value associated with the column: address
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: address
	 * @param address the address value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}



	/**
	 * Return the value associated with the column: city
	 */
	public java.lang.String getCity () {
		return city;
	}

	/**
	 * Set the value related to the column: city
	 * @param city the city value
	 */
	public void setCity (java.lang.String city) {
		this.city = city;
	}



	/**
	 * Return the value associated with the column: area_code
	 */
	public java.lang.String getAreaCode () {
		return areaCode;
	}

	/**
	 * Set the value related to the column: area_code
	 * @param areaCode the area_code value
	 */
	public void setAreaCode (java.lang.String areaCode) {
		this.areaCode = areaCode;
	}



	/**
	 * Return the value associated with the column: postal_code
	 */
	public java.lang.String getPostalCode () {
		return postalCode;
	}

	/**
	 * Set the value related to the column: postal_code
	 * @param postalCode the postal_code value
	 */
	public void setPostalCode (java.lang.String postalCode) {
		this.postalCode = postalCode;
	}



	/**
	 * Return the value associated with the column: province
	 */
	public java.lang.String getProvince () {
		return province;
	}

	/**
	 * Set the value related to the column: province
	 * @param province the province value
	 */
	public void setProvince (java.lang.String province) {
		this.province = province;
	}



	/**
	 * Return the value associated with the column: country
	 */
	public java.lang.String getCountry () {
		return country;
	}

	/**
	 * Set the value related to the column: country
	 * @param country the country value
	 */
	public void setCountry (java.lang.String country) {
		this.country = country;
	}



	/**
	 * Return the value associated with the column: telephone
	 */
	public java.lang.String getTelephone () {
		return telephone;
	}

	/**
	 * Set the value related to the column: telephone
	 * @param telephone the telephone value
	 */
	public void setTelephone (java.lang.String telephone) {
		this.telephone = telephone;
	}



	/**
	 * Return the value associated with the column: fax
	 */
	public java.lang.String getFax () {
		return fax;
	}

	/**
	 * Set the value related to the column: fax
	 * @param fax the fax value
	 */
	public void setFax (java.lang.String fax) {
		this.fax = fax;
	}



	/**
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: is_active
	 */
	public boolean isActive () {
		return active;
	}

	/**
	 * Set the value related to the column: is_active
	 * @param active the is_active value
	 */
	public void setActive (boolean active) {
		this.active = active;
	}



	/**
	 * Return the value associated with the column: is_store
	 */
	public boolean isStore () {
		return store;
	}

	/**
	 * Set the value related to the column: is_store
	 * @param store the is_store value
	 */
	public void setStore (boolean store) {
		this.store = store;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: code
	 * @param code the code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
	}



	/**
	 * Return the value associated with the column: npwp
	 */
	public java.lang.String getNpwp () {
		return npwp;
	}

	/**
	 * Set the value related to the column: npwp
	 * @param npwp the npwp value
	 */
	public void setNpwp (java.lang.String npwp) {
		this.npwp = npwp;
	}



	/**
	 * Return the value associated with the column: contact_person
	 */
	public java.lang.String getContactPerson () {
		return contactPerson;
	}

	/**
	 * Set the value related to the column: contact_person
	 * @param contactPerson the contact_person value
	 */
	public void setContactPerson (java.lang.String contactPerson) {
		this.contactPerson = contactPerson;
	}



	/**
	 * Return the value associated with the column: position
	 */
	public java.lang.String getPosition () {
		return position;
	}

	/**
	 * Set the value related to the column: position
	 * @param position the position value
	 */
	public void setPosition (java.lang.String position) {
		this.position = position;
	}



	/**
	 * Return the value associated with the column: is_block_over_credit_limit
	 */
	public boolean isBlockOverCreditLimit () {
		return blockOverCreditLimit;
	}

	/**
	 * Set the value related to the column: is_block_over_credit_limit
	 * @param blockOverCreditLimit the is_block_over_credit_limit value
	 */
	public void setBlockOverCreditLimit (boolean blockOverCreditLimit) {
		this.blockOverCreditLimit = blockOverCreditLimit;
	}



	/**
	 * Return the value associated with the column: credit_limit
	 */
	public double getCreditLimit () {
		return creditLimit;
	}

	/**
	 * Set the value related to the column: credit_limit
	 * @param creditLimit the credit_limit value
	 */
	public void setCreditLimit (double creditLimit) {
		this.creditLimit = creditLimit;
	}



	/**
	 * Return the value associated with the column: create_on
	 */
	public java.util.Date getCreateOn () {
		return createOn;
	}

	/**
	 * Set the value related to the column: create_on
	 * @param createOn the create_on value
	 */
	public void setCreateOn (java.util.Date createOn) {
		this.createOn = createOn;
	}



	/**
	 * Return the value associated with the column: change_on
	 */
	public java.util.Date getChangeOn () {
		return changeOn;
	}

	/**
	 * Set the value related to the column: change_on
	 * @param changeOn the change_on value
	 */
	public void setChangeOn (java.util.Date changeOn) {
		this.changeOn = changeOn;
	}



	/**
	 * Return the value associated with the column: NumberOfDigit
	 */
	public int getNumberOfDigit () {
		return numberOfDigit;
	}

	/**
	 * Set the value related to the column: NumberOfDigit
	 * @param numberOfDigit the NumberOfDigit value
	 */
	public void setNumberOfDigit (int numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}



	/**
	 * Return the value associated with the column: create_by
	 */
	public com.mpe.financial.model.Users getCreateBy () {
		return createBy;
	}

	/**
	 * Set the value related to the column: create_by
	 * @param createBy the create_by value
	 */
	public void setCreateBy (com.mpe.financial.model.Users createBy) {
		this.createBy = createBy;
	}



	/**
	 * Return the value associated with the column: change_by
	 */
	public com.mpe.financial.model.Users getChangeBy () {
		return changeBy;
	}

	/**
	 * Set the value related to the column: change_by
	 * @param changeBy the change_by value
	 */
	public void setChangeBy (com.mpe.financial.model.Users changeBy) {
		this.changeBy = changeBy;
	}



	/**
	 * Return the value associated with the column: chart_of_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getChartOfAccount () {
		return chartOfAccount;
	}

	/**
	 * Set the value related to the column: chart_of_account_id
	 * @param chartOfAccount the chart_of_account_id value
	 */
	public void setChartOfAccount (com.mpe.financial.model.ChartOfAccount chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
	}



	/**
	 * Return the value associated with the column: organization_id
	 */
	public com.mpe.financial.model.Organization getOrganization () {
		return organization;
	}

	/**
	 * Set the value related to the column: organization_id
	 * @param organization the organization_id value
	 */
	public void setOrganization (com.mpe.financial.model.Organization organization) {
		this.organization = organization;
	}



	/**
	 * Return the value associated with the column: item_price_category_id
	 */
	public com.mpe.financial.model.ItemPriceCategory getItemPriceCategory () {
		return itemPriceCategory;
	}

	/**
	 * Set the value related to the column: item_price_category_id
	 * @param itemPriceCategory the item_price_category_id value
	 */
	public void setItemPriceCategory (com.mpe.financial.model.ItemPriceCategory itemPriceCategory) {
		this.itemPriceCategory = itemPriceCategory;
	}



	/**
	 * Return the value associated with the column: customer_alias_id
	 */
	public com.mpe.financial.model.Customers getCustomerAlias () {
		return customerAlias;
	}

	/**
	 * Set the value related to the column: customer_alias_id
	 * @param customerAlias the customer_alias_id value
	 */
	public void setCustomerAlias (com.mpe.financial.model.Customers customerAlias) {
		this.customerAlias = customerAlias;
	}



	/**
	 * Return the value associated with the column: customer_category_id
	 */
	public com.mpe.financial.model.CustomersCategory getCustomersCategory () {
		return customersCategory;
	}

	/**
	 * Set the value related to the column: customer_category_id
	 * @param customersCategory the customer_category_id value
	 */
	public void setCustomersCategory (com.mpe.financial.model.CustomersCategory customersCategory) {
		this.customersCategory = customersCategory;
	}



	/**
	 * Return the value associated with the column: CustomersCommunications
	 */
	public java.util.Set getCustomersCommunications () {
		return customersCommunications;
	}

	/**
	 * Set the value related to the column: CustomersCommunications
	 * @param customersCommunications the CustomersCommunications value
	 */
	public void setCustomersCommunications (java.util.Set customersCommunications) {
		this.customersCommunications = customersCommunications;
	}

	public void addToCustomersCommunications (com.mpe.financial.model.CustomersCommunication customersCommunication) {
		if (null == getCustomersCommunications()) setCustomersCommunications(new java.util.HashSet());
		getCustomersCommunications().add(customersCommunication);
	}



	/**
	 * Return the value associated with the column: CustomersAddresses
	 */
	public java.util.Set getCustomersAddresses () {
		return customersAddresses;
	}

	/**
	 * Set the value related to the column: CustomersAddresses
	 * @param customersAddresses the CustomersAddresses value
	 */
	public void setCustomersAddresses (java.util.Set customersAddresses) {
		this.customersAddresses = customersAddresses;
	}

	public void addToCustomersAddresses (com.mpe.financial.model.CustomersAddress customersAddress) {
		if (null == getCustomersAddresses()) setCustomersAddresses(new java.util.HashSet());
		getCustomersAddresses().add(customersAddress);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Customers)) return false;
		else {
			com.mpe.financial.model.Customers customers = (com.mpe.financial.model.Customers) obj;
			return (this.getId() == customers.getId());
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