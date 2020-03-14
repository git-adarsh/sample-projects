package com.sample.orm;

import java.text.MessageFormat;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sample.orm.entity.Order;
import com.sample.orm.entity.Product;
import com.sample.orm.entity.User;
import com.sample.orm.factory.DefaultSessionFactory;

public class NameQueryOpsOrder {

	static Scanner sc = new Scanner(System.in);

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
			throws HibernateException, NoSuchFieldException, SecurityException, ClassNotFoundException {
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

		/** Create users */

		User user1 = new User();
		user1.setAuth("auth1");
		user1.setContact(980);
		user1.setEmail("someemail");
		user1.setFirstName("firstName");
		user1.setLastName("lastName");

		User user2 = new User();
		user2.setAuth("auth2");
		user2.setContact(981);
		user2.setEmail("moreemail");
		user2.setFirstName("firstName1");
		user2.setLastName("lastName1");

		/** Create Orders: user1 has 2 orders, user3 has 4 orders */

		// orders for user1
		Order o1 = new Order();
		o1.setProduct(p1);
		o1.setUser(user1);

		Order o2 = new Order();
		o2.setProduct(p2);
		o2.setUser(user1);

		// orders for user2

		Order o3 = new Order();
		o3.setProduct(p3);
		o3.setUser(user2);

		Order o4 = new Order();
		o4.setProduct(p4);
		o4.setUser(user2);

		Order o5 = new Order();
		o5.setProduct(p3);
		o5.setUser(user2);

		Order o6 = new Order();
		o6.setProduct(p5);
		o6.setUser(user2);

		Transaction t2 = s2.beginTransaction();
		s2.save(user1);
		s2.save(user2);

		s2.save(o1);
		s2.save(o2);
		s2.save(o3);
		s2.save(o4);
		s2.save(o5);
		s2.save(o6);

		t2.commit();

		s2.close();

		Session s3 = factory.openSession();

		Query q = executeQuery(Order.TOTAL_ORDERS_FOR_PRODUCT, s3, "productId", ParamType.Integer);

		System.out.println("#### [QUERY 1] Total orders for product ####");
		System.out.println(q.list().size());

		q = executeQuery(Order.ORDERS_BY_USER, s3, "authId", ParamType.String);

		System.out.println("#### [QUERY 2] Orders by a user ####");
		System.out.println(q.list().size());
		System.out.println("### Result of the [QUERY 2] : ###");
		q.list().forEach(System.out::println);

		q = executeQuery(Order.MAX_PRICE_OF_ORDER_BY_USER, s3, "authId", ParamType.String);

		System.out.println("#### [QUERY 3] Total orders for product ####");
		System.out.println(q.list().size());
		System.out.println("### Result of the [QUERY 3] : ###");
		q.list().forEach(System.out::println);

		sc.close();
		s3.close();
		factory.close();
	}

	static Query executeQuery(String constant, Session s3, String message, ParamType paramType)
			throws HibernateException, NoSuchFieldException, SecurityException, ClassNotFoundException {
		Query q = s3.getNamedQuery(constant);

		if (paramType == null)
			return q;

		switch (paramType) {
		case Integer:
			int in = Integer.parseInt(getInput(message));
			q.setInteger(message, in);
			break;
		case String:
			q.setString(message, getInput(message));
			break;
		default:
			throw new UnsupportedOperationException();
		}

		return q;
	}

	private static String getInput(String message) {
		String in = null;

		if (message != null) {
			System.out.println(MessageFormat.format("### Enter {0} ###", message));
			in = sc.next();
		}
		return in;
	}

	static enum ParamType {
		Integer, String
	}
}