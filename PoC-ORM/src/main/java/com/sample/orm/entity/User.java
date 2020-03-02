package com.sample.orm.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5039625255700202489L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;

	@Column(name = "U_NAME", unique = true, length = 5)
	private String username;

	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "CONTACT", nullable = false)
	private int contact;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "IDS")
	private Set<Order> order;

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		sb.append("username: ");
		sb.append(username);
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

		sb.append("Total Orders: ");
		sb.append(order.size());
		sb.append("\n");

		sb.append("Orders: ");
		order.forEach(e -> e.toString());
		sb.append("\n");
		
		return sb.toString();

	}
}
