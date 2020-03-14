package com.sample.orm.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * There can be two kinds of users: 1) A normal user (free membership) 2)
 * Subscribed user (paid membership)
 * 
 * This demonstrates the inheritance concept for hibernate
 *
 */
@Entity
@Table(name = "PAID_SUBSCRIPTION_USER")
@DiscriminatorValue("Paid")
@NamedQueries({
		@NamedQuery(name = "PaidSubscriptionUser_UsersWhoSubscribedTo", query = "Select pu from PaidSubscriptionUser pu join pu.pack where pu.pack.id= :packId"),
		@NamedQuery(name = "PaidSubscriptionUser_UsersWhoseStatusIsActive", query = "Select pu from PaidSubscriptionUser pu where pu.isActive=true"),
		@NamedQuery(name = "PaidSubscriptionUser_UsersWhoseStatusIsNotActive", query = "Select pu from PaidSubscriptionUser pu where pu.isActive=false") })
public class PaidSubscriptionUser extends User {

	private static final long serialVersionUID = -3576144807817979722L;

	/** Query constants */
	public static final String USERS_SUBSCRIBED_TO_SCHEME = "PaidSubscriptionUser_UsersWhoSubscribedTo";
	public static final String ACTIVE_USERS = "PaidSubscriptionUser_UsersWhoseStatusIsActive";
	public static final String INACTIVE_USERS = "PaidSubscriptionUser_UsersWhoseStatusIsNotActive";

	@Column(name = "SUBSCRIPTION_STATUS")
	private boolean isActive;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Subscription pack;

	@Column(name = "LAST_RENEW_DATE")
	private Date renewDate;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Subscription getPack() {
		return pack;
	}

	public void setPack(Subscription pack) {
		this.pack = pack;
	}

	public Date getRenewDate() {
		return renewDate;
	}

	public void setRenewDate(Date renewDate) {
		this.renewDate = renewDate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("userId: ");
		sb.append(getAuth());
		sb.append("\n");

		sb.append("email: ");
		sb.append(getEmail());
		sb.append("\n");

		sb.append("firstname: ");
		sb.append(getFirstName());
		sb.append("\n");

		sb.append("lastname: ");
		sb.append(getLastName());
		sb.append("\n");

		sb.append("contact: ");
		sb.append(getContact());
		sb.append("\n");

		sb.append("isActive: ");
		sb.append(isActive());
		sb.append("\n");

		sb.append("pack: ");
		sb.append(getPack());
		sb.append("\n");

		sb.append("renewed date: ");
		sb.append(getRenewDate());
		sb.append("\n");

		return sb.toString();
	}
}