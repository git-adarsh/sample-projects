package com.sample.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sample.orm.entity.Order;
import com.sample.orm.entity.Product;
import com.sample.orm.entity.User;
import com.sample.orm.factory.DefaultSessionFactory;

public class BasicOpsApp2 {
	public static void main(String[] args) {
		SessionFactory factory = DefaultSessionFactory.getSession();

		Session s1 = factory.openSession();

		Transaction tx = s1.beginTransaction();

		// initialize a couple of user instances
		User user1 = new User();
		User user2 = new User();

		// create orders
		List<Order> list1 = getOrders(user1);
		List<Order> list2 = getOrders(user2);

		user1.setAuth("auth1");
		user1.setContact(980);
		user1.setEmail("someemail");
		user1.setFirstName("firstName");
		user1.setLastName("lastName");

		user1.setOrders(list1);

		user2.setAuth("auth2");
		user2.setContact(980);
		user2.setEmail("someemail");
		user2.setFirstName("firstName");
		user2.setLastName("lastName");
		user2.setOrders(list2);

		s1.persist(user1);
		s1.persist(user2);

		tx.commit();
		s1.flush();
		s1.close();

		System.out.println("Done Persisting..");
		printAll(factory, Order.class, true);
		printAll(factory, User.class, false);

		// delete the order now.
		// Config info: Order has cascade ALL on the mapping
		Scanner sc = new Scanner(System.in);

		s1 = factory.openSession();
		Transaction t2 = s1.beginTransaction();

		Query q1 = s1.createQuery("Delete From Order o where o.orderId= :oid");
		System.out.println("Enter order id: ");
		q1.setString("oid", sc.next());

		sc.close();

		System.out.println("Updates: " + q1.executeUpdate());

		t2.commit();
		s1.close();
		System.out.println("Printing after deleting 1 order");
		printAll(factory, Order.class, true);
		printAll(factory, User.class, true);

		factory.close();
	}

	private static List<Order> getOrders(User user) {
		// create product
		Product p1 = new Product();
		p1.setProductName("p1");
		p1.setProductPrice(23);

		Product p2 = new Product();
		p2.setProductName("p2");
		p2.setProductPrice(25);

		Order o1 = new Order();
		o1.setProduct(p1);
		o1.setUser(user);

		Order o2 = new Order();
		o2.setProduct(p2);
		o2.setUser(user);
		List<Order> orderList = new ArrayList<>();
		orderList.add(o1);
		orderList.add(o2);

		return orderList;
	}

	private static void printAll(SessionFactory factory, Class<?> c, boolean printAllInfo) {
		Session s1 = factory.openSession();
		// will fetch all entries
		List<?> list = s1.createCriteria(c).list();

		if (printAllInfo) {
			// bug order date is inserting as null
			System.out.println("*************Total entries for " + c.getName() + ":" + list.size() + "***********");
			list.forEach(System.out::println);
			
		} else {
			System.out.println("*************Total entries for " + c.getName() + ":" + list.size() + "***********");
		}
		s1.close();
	}
}
