package com.sample.orm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Two membership: Paid and Free This entity is for free user
 * 
 * Establishing relationship to demonstrate the inheritance part of ORM. The
 * Table per hierarchy (Single Table) is the preferred way (because of no
 * requirement of joins to fetch data) of establishing relation between classes.
 * 
 */

@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// default name: DType, Type: String
@DiscriminatorColumn(name = "USER_TYPE")
public class User implements Serializable {

	private static final long serialVersionUID = -5039625255700202489L;

	@Id
	@Column(name = "AUTH_ID")
	private String authId;

	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "CONTACT", nullable = false)
	private int contact;

	// to get all the orders of a user
	/*
	 * mappedby: go, look over the "user" field in Orders class for join related
	 * info Standard rule to make the "many" side the owner of the relationship. The
	 * side with @JoinCloum becomes the owner of the relationship
	 * 
	 * By default, the association is fetched eagerly, don't need it. Make it lazy
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getAuth() {
		return authId;
	}

	public void setAuth(String authId) {
		this.authId = authId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getContact() {
		return contact;
	}

	public void setContact(int contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("userId: ");
		sb.append(authId);
		sb.append("\n");

		sb.append("email: ");
		sb.append(email);
		sb.append("\n");

		sb.append("firstname: ");
		sb.append(firstName);
		sb.append("\n");

		sb.append("lastname: ");
		sb.append(lastName);
		sb.append("\n");

		sb.append("contact: ");
		sb.append(contact);
		sb.append("\n");

		return sb.toString();
	}
}
