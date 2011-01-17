package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the member table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="member"
 */

public abstract class BaseMember  implements Serializable {

	public static String REF = "Member";
	public static String PROP_MEMBER_NUMBER = "MemberNumber";
	public static String PROP_MEMBER_DATE = "MemberDate";
	public static String PROP_FULL_NAME = "FullName";
	public static String PROP_NICK_NAME = "NickName";
	public static String PROP_ADDRESS = "Address";
	public static String PROP_CITY = "City";
	public static String PROP_POSTAL_CODE = "PostalCode";
	public static String PROP_PROVINCE = "Province";
	public static String PROP_TELEPHONE = "Telephone";
	public static String PROP_FAX = "Fax";
	public static String PROP_EMAIL = "Email";
	public static String PROP_ACTIVE = "Active";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";


	// constructors
	public BaseMember () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMember (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMember (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Location location,
		com.mpe.financial.model.MemberDiscount memberDiscount,
		java.lang.String memberNumber,
		java.util.Date memberDate,
		boolean active) {

		this.setId(id);
		this.setOrganization(organization);
		this.setLocation(location);
		this.setMemberDiscount(memberDiscount);
		this.setMemberNumber(memberNumber);
		this.setMemberDate(memberDate);
		this.setActive(active);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String memberNumber;
	private java.util.Date memberDate;
	private java.lang.String fullName;
	private java.lang.String nickName;
	private java.lang.String address;
	private java.lang.String city;
	private java.lang.String postalCode;
	private java.lang.String province;
	private java.lang.String telephone;
	private java.lang.String fax;
	private java.lang.String email;
	private boolean active;
	private java.lang.String mobile;
	private java.util.Date createOn;
	private java.util.Date changeOn;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.MemberDiscount memberDiscount;

	// collections
	private java.util.Set posOrders;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="member_id"
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
	 * Return the value associated with the column: member_date
	 */
	public java.util.Date getMemberDate () {
		return memberDate;
	}

	/**
	 * Set the value related to the column: member_date
	 * @param memberDate the member_date value
	 */
	public void setMemberDate (java.util.Date memberDate) {
		this.memberDate = memberDate;
	}



	/**
	 * Return the value associated with the column: full_name
	 */
	public java.lang.String getFullName () {
		return fullName;
	}

	/**
	 * Set the value related to the column: full_name
	 * @param fullName the full_name value
	 */
	public void setFullName (java.lang.String fullName) {
		this.fullName = fullName;
	}



	/**
	 * Return the value associated with the column: nick_name
	 */
	public java.lang.String getNickName () {
		return nickName;
	}

	/**
	 * Set the value related to the column: nick_name
	 * @param nickName the nick_name value
	 */
	public void setNickName (java.lang.String nickName) {
		this.nickName = nickName;
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
	 * Return the value associated with the column: mobile
	 */
	public java.lang.String getMobile () {
		return mobile;
	}

	/**
	 * Set the value related to the column: mobile
	 * @param mobile the mobile value
	 */
	public void setMobile (java.lang.String mobile) {
		this.mobile = mobile;
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
	 * Return the value associated with the column: location_id
	 */
	public com.mpe.financial.model.Location getLocation () {
		return location;
	}

	/**
	 * Set the value related to the column: location_id
	 * @param location the location_id value
	 */
	public void setLocation (com.mpe.financial.model.Location location) {
		this.location = location;
	}



	/**
	 * Return the value associated with the column: member_discount_id
	 */
	public com.mpe.financial.model.MemberDiscount getMemberDiscount () {
		return memberDiscount;
	}

	/**
	 * Set the value related to the column: member_discount_id
	 * @param memberDiscount the member_discount_id value
	 */
	public void setMemberDiscount (com.mpe.financial.model.MemberDiscount memberDiscount) {
		this.memberDiscount = memberDiscount;
	}



	/**
	 * Return the value associated with the column: PosOrders
	 */
	public java.util.Set getPosOrders () {
		return posOrders;
	}

	/**
	 * Set the value related to the column: PosOrders
	 * @param posOrders the PosOrders value
	 */
	public void setPosOrders (java.util.Set posOrders) {
		this.posOrders = posOrders;
	}

	public void addToPosOrders (com.mpe.financial.model.PosOrder posOrder) {
		if (null == getPosOrders()) setPosOrders(new java.util.HashSet());
		getPosOrders().add(posOrder);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Member)) return false;
		else {
			com.mpe.financial.model.Member member = (com.mpe.financial.model.Member) obj;
			return (this.getId() == member.getId());
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