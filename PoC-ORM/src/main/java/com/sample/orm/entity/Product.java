package com.sample.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "PRODUCT")
/**
 * Applying the feature of named-query
 */
@NamedQueries({
		/**
		 * IMP: fetchSize: It doesn't mean that get me n records. So, if there are 10
		 * records at db and fetchSize is 3. We will see 10 records and not 3 (what one
		 * initially understands. fetchSize is about asking jdbc to bring 3 records from
		 * db then go again to db to bring next 3. Unless really important, it should be
		 * left to be default.
		 */
		@NamedQuery(name = "Product_getAllProductInThePriceRange", query = "from Product p where p.productPrice between :rangeStart and :rangeEnd",
				// default is false anyway
				cacheable = false, fetchSize = 10),
		@NamedQuery(name = "Product_getAllProductOfOrLessThanThisPrice", query = "from Product p where p.productPrice <= :price", fetchSize = 10),

		@NamedQuery(name = "Product_getAllProductofOrMoreThanThisPrice", query = "from Product p where p.productPrice >= :price", fetchSize = 10),
		@NamedQuery(name = "Product_ByLowToHighPrice", query = "from Product p order by p.productPrice ASC"),
		@NamedQuery(name = "Product_ByHighToLowPrice", query = "from Product p order by p.productPrice DESC") })
public class Product implements Serializable {

	private static final long serialVersionUID = 9137995640803566434L;

	/* named Query Constant */
	/**
	 * <strong>Parameter name </strong> 1) <code> rangeStart </code> 2)
	 * <code> rangeEnd</code>
	 */
	public final static String PRODUCTS_IN_THE_RANGE = "Product_getAllProductInThePriceRange";
	/** <strong>Parameter name </strong> <code>price</code> */
	public final static String PRODUCTS_LESS_THAN_OR_EQUAL_TO = "Product_getAllProductOfOrLessThanThisPrice";
	/** <strong>Parameter name </strong> <code>price</code> */
	public final static String PRODUCTS_MORE_THAN_OR_EQUAL_TO = "Product_getAllProductofOrMoreThanThisPrice";

	public final static String PRODUCTS_LOW_TO_HIGH_PRICE = "Product_ByLowToHighPrice";
	public final static String PRODUCTS_HIGH_TO_LOW_PRICE = "Product_ByHighToLowPrice";

	/* Columns */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "P_ID", nullable = false, unique = true)
	private Integer productId;

	@Column(name = "P_NAME", nullable = false)
	private String productName;

	@Column(name = "P_PRICE", nullable = false)
	private int productPrice;

	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Product Id: ");
		sb.append(String.valueOf(productId));
		sb.append("\n");

		sb.append("Product Name:");
		sb.append(productName);
		sb.append("\n");

		sb.append("Product Price: ");
		sb.append(productPrice);
		sb.append("\n");

		return sb.toString();
	}
}
