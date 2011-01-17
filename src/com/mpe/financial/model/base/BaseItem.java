package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item"
 */

public abstract class BaseItem  implements Serializable {

	public static String REF = "Item";
	public static String PROP_TYPE = "Type";
	public static String PROP_NAME = "Name";
	public static String PROP_CODE = "Code";
	public static String PROP_PLU = "Plu";
	public static String PROP_BARCODE = "Barcode";
	public static String PROP_WIDTH = "Width";
	public static String PROP_DEPTH = "Depth";
	public static String PROP_HEIGHT = "Height";
	public static String PROP_TEXT_DIMENSION = "TextDimension";
	public static String PROP_WEIGHT = "Weight";
	public static String PROP_ACTIVE = "Active";
	public static String PROP_COST_PRICE = "CostPrice";
	public static String PROP_IMAGE_CONTENT_TYPE = "ImageContentType";
	public static String PROP_IMAGE = "Image";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItem (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItem (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String name,
		boolean active) {

		this.setId(id);
		this.setOrganization(organization);
		this.setName(name);
		this.setActive(active);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String type;
	private java.lang.String name;
	private java.lang.String code;
	private java.lang.String plu;
	private java.lang.String barcode;
	private double width;
	private double depth;
	private double height;
	private java.lang.String textDimension;
	private double weight;
	private boolean active;
	private double costPrice;
	private java.lang.String imageContentType;
	private byte[] image;
	private java.lang.String description;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.ChartOfAccount costPriceAccount;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.ItemCategory itemCategory;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Tax purchaseTax;
	private com.mpe.financial.model.ItemStatus itemStatus;

	// collections
	private java.util.Set itemPrices;
	private java.util.Set itemGroups;
	private java.util.Set itemVendors;
	private java.util.Set itemCustomFields;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="item_id"
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
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
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
	 * Return the value associated with the column: plu
	 */
	public java.lang.String getPlu () {
		return plu;
	}

	/**
	 * Set the value related to the column: plu
	 * @param plu the plu value
	 */
	public void setPlu (java.lang.String plu) {
		this.plu = plu;
	}



	/**
	 * Return the value associated with the column: barcode
	 */
	public java.lang.String getBarcode () {
		return barcode;
	}

	/**
	 * Set the value related to the column: barcode
	 * @param barcode the barcode value
	 */
	public void setBarcode (java.lang.String barcode) {
		this.barcode = barcode;
	}



	/**
	 * Return the value associated with the column: width
	 */
	public double getWidth () {
		return width;
	}

	/**
	 * Set the value related to the column: width
	 * @param width the width value
	 */
	public void setWidth (double width) {
		this.width = width;
	}



	/**
	 * Return the value associated with the column: depth
	 */
	public double getDepth () {
		return depth;
	}

	/**
	 * Set the value related to the column: depth
	 * @param depth the depth value
	 */
	public void setDepth (double depth) {
		this.depth = depth;
	}



	/**
	 * Return the value associated with the column: height
	 */
	public double getHeight () {
		return height;
	}

	/**
	 * Set the value related to the column: height
	 * @param height the height value
	 */
	public void setHeight (double height) {
		this.height = height;
	}



	/**
	 * Return the value associated with the column: text_dimension
	 */
	public java.lang.String getTextDimension () {
		return textDimension;
	}

	/**
	 * Set the value related to the column: text_dimension
	 * @param textDimension the text_dimension value
	 */
	public void setTextDimension (java.lang.String textDimension) {
		this.textDimension = textDimension;
	}



	/**
	 * Return the value associated with the column: weight
	 */
	public double getWeight () {
		return weight;
	}

	/**
	 * Set the value related to the column: weight
	 * @param weight the weight value
	 */
	public void setWeight (double weight) {
		this.weight = weight;
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
	 * Return the value associated with the column: cost_price
	 */
	public double getCostPrice () {
		return costPrice;
	}

	/**
	 * Set the value related to the column: cost_price
	 * @param costPrice the cost_price value
	 */
	public void setCostPrice (double costPrice) {
		this.costPrice = costPrice;
	}



	/**
	 * Return the value associated with the column: image_content_type
	 */
	public java.lang.String getImageContentType () {
		return imageContentType;
	}

	/**
	 * Set the value related to the column: image_content_type
	 * @param imageContentType the image_content_type value
	 */
	public void setImageContentType (java.lang.String imageContentType) {
		this.imageContentType = imageContentType;
	}



	/**
	 * Return the value associated with the column: image
	 */
	public byte[] getImage () {
		return image;
	}

	/**
	 * Set the value related to the column: image
	 * @param image the image value
	 */
	public void setImage (byte[] image) {
		this.image = image;
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
	 * Return the value associated with the column: cost_price_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getCostPriceAccount () {
		return costPriceAccount;
	}

	/**
	 * Set the value related to the column: cost_price_account_id
	 * @param costPriceAccount the cost_price_account_id value
	 */
	public void setCostPriceAccount (com.mpe.financial.model.ChartOfAccount costPriceAccount) {
		this.costPriceAccount = costPriceAccount;
	}



	/**
	 * Return the value associated with the column: currency_id
	 */
	public com.mpe.financial.model.Currency getCurrency () {
		return currency;
	}

	/**
	 * Set the value related to the column: currency_id
	 * @param currency the currency_id value
	 */
	public void setCurrency (com.mpe.financial.model.Currency currency) {
		this.currency = currency;
	}



	/**
	 * Return the value associated with the column: item_category_id
	 */
	public com.mpe.financial.model.ItemCategory getItemCategory () {
		return itemCategory;
	}

	/**
	 * Set the value related to the column: item_category_id
	 * @param itemCategory the item_category_id value
	 */
	public void setItemCategory (com.mpe.financial.model.ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
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
	 * Return the value associated with the column: purchase_tax_id
	 */
	public com.mpe.financial.model.Tax getPurchaseTax () {
		return purchaseTax;
	}

	/**
	 * Set the value related to the column: purchase_tax_id
	 * @param purchaseTax the purchase_tax_id value
	 */
	public void setPurchaseTax (com.mpe.financial.model.Tax purchaseTax) {
		this.purchaseTax = purchaseTax;
	}



	/**
	 * Return the value associated with the column: item_status_id
	 */
	public com.mpe.financial.model.ItemStatus getItemStatus () {
		return itemStatus;
	}

	/**
	 * Set the value related to the column: item_status_id
	 * @param itemStatus the item_status_id value
	 */
	public void setItemStatus (com.mpe.financial.model.ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}



	/**
	 * Return the value associated with the column: ItemPrices
	 */
	public java.util.Set getItemPrices () {
		return itemPrices;
	}

	/**
	 * Set the value related to the column: ItemPrices
	 * @param itemPrices the ItemPrices value
	 */
	public void setItemPrices (java.util.Set itemPrices) {
		this.itemPrices = itemPrices;
	}

	public void addToItemPrices (com.mpe.financial.model.ItemPrice itemPrice) {
		if (null == getItemPrices()) setItemPrices(new java.util.HashSet());
		getItemPrices().add(itemPrice);
	}



	/**
	 * Return the value associated with the column: ItemGroups
	 */
	public java.util.Set getItemGroups () {
		return itemGroups;
	}

	/**
	 * Set the value related to the column: ItemGroups
	 * @param itemGroups the ItemGroups value
	 */
	public void setItemGroups (java.util.Set itemGroups) {
		this.itemGroups = itemGroups;
	}

	public void addToItemGroups (com.mpe.financial.model.ItemGroup itemGroup) {
		if (null == getItemGroups()) setItemGroups(new java.util.HashSet());
		getItemGroups().add(itemGroup);
	}



	/**
	 * Return the value associated with the column: ItemVendors
	 */
	public java.util.Set getItemVendors () {
		return itemVendors;
	}

	/**
	 * Set the value related to the column: ItemVendors
	 * @param itemVendors the ItemVendors value
	 */
	public void setItemVendors (java.util.Set itemVendors) {
		this.itemVendors = itemVendors;
	}

	public void addToItemVendors (com.mpe.financial.model.ItemVendor itemVendor) {
		if (null == getItemVendors()) setItemVendors(new java.util.HashSet());
		getItemVendors().add(itemVendor);
	}



	/**
	 * Return the value associated with the column: ItemCustomFields
	 */
	public java.util.Set getItemCustomFields () {
		return itemCustomFields;
	}

	/**
	 * Set the value related to the column: ItemCustomFields
	 * @param itemCustomFields the ItemCustomFields value
	 */
	public void setItemCustomFields (java.util.Set itemCustomFields) {
		this.itemCustomFields = itemCustomFields;
	}

	public void addToItemCustomFields (com.mpe.financial.model.ItemCustomField itemCustomField) {
		if (null == getItemCustomFields()) setItemCustomFields(new java.util.HashSet());
		getItemCustomFields().add(itemCustomField);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Item)) return false;
		else {
			com.mpe.financial.model.Item item = (com.mpe.financial.model.Item) obj;
			return (this.getId() == item.getId());
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