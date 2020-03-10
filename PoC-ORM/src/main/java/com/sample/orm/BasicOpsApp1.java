package com.sample.orm;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sample.orm.entity.Auth;
import com.sample.orm.entity.Order;
import com.sample.orm.entity.Product;
import com.sample.orm.entity.User;
import com.sample.orm.factory.DefaultSessionFactory;

public class BasicOpsApp1 {
	public static void main(String[] args) {

		// Get session factory
		SessionFactory factory = DefaultSessionFactory.getSession();

		// open a session
		Session session1 = factory.openSession();

		// begin transaction
		Transaction tx;
		tx = (tx = session1.getTransaction()) == null ? session1.getTransaction() : tx;

		tx.begin();

		// user first registers
		System.out.println("Registering user...");

		Auth auth = new Auth();
		auth.setAuthId("auth");
		auth.setPwd("pwd");

		session1.save(auth);

		User user = new User();
		user.setAuth(auth.getAuthId());
		user.setContact(980);
		user.setEmail("someemail");
		user.setFirstName("firstName");
		user.setLastName("lastName");

		System.out.println("Persisting user...");

		session1.save(user);

		System.out.println("User with id: auth registered..");

		tx.commit();
		session1.close();

		System.out.println("Begining to fetch user profile..");

		Session session2 = factory.openSession();

		System.out.println("/***Correct authid/pwd: auth, pwd***/");

		System.out.println("Attempt to login with wrong credentials");

		Auth authWrong = (Auth) session2.get(Auth.class, "auth1");

		if (authWrong != null) {
			// check if the password for this id is correct
			System.out.println(authWrong.getPwd().equals("wrongPwd") ? "Cannot come here" : "Wrong pwd provided");
		} else {
			System.out.println("User doesn't exist");
		}

		Auth authCorrect = (Auth) session2.get(Auth.class, "auth");

		if (authCorrect != null) {
			// check if the password for this id is correct
			System.out
					.println(authCorrect.getPwd().equals("wrongPwd") ? "Credentials validated" : "Wrong pwd provided");
		} else {
			System.out.println("User doesn't exist");
		}

		session2.close();

		/*
		 * Closing the factory makes H2 discard all the table info. So, further to this
		 * tables won't be "recognized"
		 */
		// factory.close();

		System.out.println("/*********Starting operation regarding Order**************/");
		Session s3 = factory.openSession();

		System.out.println("Creating order..");
		Order order = new Order();

		System.out.println("Creating Product..");
		Product product = new Product();

		product.setProductName("Mask");
		product.setProductPrice(233);

		order.setProduct(product);
		order.setUser(user);

		Serializable oId = s3.save(order);

		System.out.println("Fetching without commit but in the same session..");

		// gives expected data
		System.out.println(((Order) s3.get(Order.class, oId)).toString());

		s3.close();

		// begin another transaction
		Session s4 = factory.openSession();

		System.out.println("Fetching Order without saving in different session..");

		// will throw NPE as the transaction wasn't committed in the s3 transaction.
		System.out.println(((Order) s4.get(Order.class, oId)).toString());
		factory.close();

	}
}
