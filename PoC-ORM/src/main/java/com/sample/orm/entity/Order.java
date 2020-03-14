package com.sample.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "ORDERS")
/**
 * The queries have a lot of optimization scope (may be). TO DO: Dig further to
 * optimize the queries
 */
@NamedQueries({
		/*
		 * An inner join. The usual "on" condition for a join is applied implicitly
		 * orders for a particular productId
		 */
		@NamedQuery(name = "Order_TotalOrderForThisProduct", query = "Select p.productName from Order o join o.product p where p.productId = :productId"),
		// Writing Select * gives error
		@NamedQuery(name = "Order_ByUserId", query = "Select o from Order o join o.user u where u.authId = :authId"),
		/*
		 * Writing with the assumption that authId and productId are the columns (the
		 * foreign key columns) in Orders give error. The query has to be written in
		 * below format
		 */
		@NamedQuery(name = "Order_TotalOrderByAUserForAProduct", query = "Select count(*) from Order o join o.product p join o.user u where p.productId= :productId and u.authId = :authId"),
		@NamedQuery(name = "Order_MostExpensiveOrderByAUser", query = "Select max(p.productPrice) from Order o join o.user u join o.product p where u.authId= :authId"),
		@NamedQuery(name = "Order_TotalOrdersByAUser", query = "Select count(*) from Order o join o.user u where u.authId = :authId") })
public class Order implements Serializable {

	private static final long serialVersionUID = -7581744138020193191L;

	/**
	 * Query Constants
	 */

	public final static String TOTAL_ORDERS_FOR_PRODUCT = "Order_TotalOrderForThisProduct";
	public final static String TOTAL_ORDERS_FOR_USER = "Order_TotalOrdersByAUser";
	public final static String TOTAL_ORDERS_FOR_PRODUCT_BY_USER = "Order_TotalOrderByAUserForAProduct";
	public final static String MAX_PRICE_OF_ORDER_BY_USER = "Order_MostExpensiveOrderByAUser";
	public final static String ORDERS_BY_USER = "Order_ByUserId";

	@Id
	@GeneratedValue(generator = "order-id-generator")
	@GenericGenerator(name = "order-id-generator", strategy = "com.sample.orm.entity.idgenerator.OrderIdGenerator")
	@Column(name = "O_ID", nullable = false)
	private String orderId;

	@Column(name = "ORDER_DATE")
	@Generated(GenerationTime.ALWAYS)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date orderDate = new java.sql.Date(new java.util.Date().getTime());

	/**
	 * Here, adding CascadeType.ALL helps in saving the product's instances
	 * implicitly. In real time scenarios, we wouldn't be creating a product when an
	 * order is being placed. So, to avoid
	 * <code>TransientObjectException: save transient instance first...</code>, we
	 * explicitly need to save the product first and then the order instance
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "productId")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "authId")
	private User user;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Order)) {
			return false;
		}
		Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null) {
				return false;
			}
		} else if (!orderId.equals(other.orderId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order Id: ");
		sb.append(orderId);
		sb.append("\n");

		sb.append("order date: ");
		sb.append(orderDate);
		sb.append("\n\n");

		sb.append("product: ");
		sb.append(product != null ? product.toString() : "NULL");
		sb.append("\n");

		sb.append("User: ");
		sb.append(user != null ? user.toString() : "NULL");
		sb.append("\n");

		return sb.toString();
	}
}
