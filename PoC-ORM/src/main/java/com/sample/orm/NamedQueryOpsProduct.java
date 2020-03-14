package com.sample.orm;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sample.orm.entity.Product;
import com.sample.orm.factory.DefaultSessionFactory;

public class NamedQueryOpsProduct {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws HibernateException, NoSuchFieldException, SecurityException, ClassNotFoundException {
		SessionFactory factory = DefaultSessionFactory.getSession();
		Session s1 = factory.openSession();
		System.out.println("Creating products..");
		// create products

		Product p1 = new Product();
		p1.setProductName("Phone1");
		p1.setProductPrice(2000);

		Product p2 = new Product();
		p2.setProductName("Phone2");
		p2.setProductPrice(3000);

		Product p3 = new Product();
		p3.setProductName("Phone3");
		p3.setProductPrice(2400);

		Product p4 = new Product();
		p4.setProductName("Phone4");
		p4.setProductPrice(2200);

		Product p5 = new Product();
		p5.setProductName("Phone5");
		p5.setProductPrice(1000);

		// begin transaction
		Transaction tx = s1.beginTransaction();
		s1.persist(p1);
		s1.persist(p2);
		s1.persist(p3);
		s1.persist(p4);
		s1.persist(p5);
		tx.commit();

		s1.close();

		// open a different session
		Session s2 = factory.openSession();

		// queries on products

		Query q1 = s2.getNamedQuery(Product.PRODUCTS_IN_THE_RANGE);
		q1.setInteger("rangeStart", 2000);
		q1.setInteger("rangeEnd", 4000);

		Query q2 = s2.getNamedQuery(Product.PRODUCTS_LESS_THAN_OR_EQUAL_TO);
		q2.setInteger("price", 2000);

		Query q3 = s2.getNamedQuery(Product.PRODUCTS_MORE_THAN_OR_EQUAL_TO);
		q3.setInteger("price", 3000);

		System.out.println("#### Fetching products within proce range 2000 - 4000 ####");
		List<Product> listofProductsInTherange = q1.list();
		System.out.println();
		listofProductsInTherange.forEach(System.out::println);

		System.out.println("#### Fetching products within price <= 2000 ####");
		List<Product> listofProductsLessThanOrEqualTo = q2.list();
		System.out.println();
		listofProductsLessThanOrEqualTo.forEach(System.out::println);

		System.out.println("#### Fetching products within price >= 3000 ####");
		List<Product> listofProductsMoreThanOrEqualTo = q3.list();
		System.out.println();
		listofProductsMoreThanOrEqualTo.forEach(System.out::println);
		
	Query q = NameQueryOpsOrder.executeQuery(Product.PRODUCTS_LOW_TO_HIGH_PRICE, s2, null, null);
		
		System.out.println("#### [QUERY 4] Products by low-to-high price ####");
		System.out.println(q.list().size());
		System.out.println("### Result of the [QUERY 4] : ###");
		q.list().forEach(System.out::println);
		
		q = NameQueryOpsOrder.executeQuery(Product.PRODUCTS_HIGH_TO_LOW_PRICE, s2, null, null);
		
		System.out.println("#### [QUERY 5] Products by high-to-low price ####");
		System.out.println(q.list().size());
		System.out.println("### Result of the [QUERY 5] : ###");
		q.list().forEach(System.out::println);

	}
}