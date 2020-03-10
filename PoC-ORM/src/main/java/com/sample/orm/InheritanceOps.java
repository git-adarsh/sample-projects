package com.sample.orm;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sample.orm.entity.PaidSubscriptionUser;
import com.sample.orm.entity.Subscription;
import com.sample.orm.entity.User;
import com.sample.orm.factory.DefaultSessionFactory;

public class InheritanceOps {
	public static void main(String[] args) {
		SessionFactory factory = DefaultSessionFactory.getSession();

		Session s1 = factory.openSession();

		// Unsubscribed user
		User user1 = new User();

		user1.setAuth("auth");
		user1.setContact(980);
		user1.setEmail("someemail");
		user1.setFirstName("firstName");
		user1.setLastName("lastName");

		// subscribed user

		PaidSubscriptionUser subs = new PaidSubscriptionUser();

		// for same authid, expect an exception
		subs.setAuth("auth1");
		subs.setContact(980);
		subs.setEmail("someemail");
		subs.setFirstName("subscribed");
		subs.setLastName("user");

		// create Subscription
		Subscription scheme = new Subscription();
		scheme.setDetails("You can watch unlimited movies with this pack");
		scheme.setPackValue(999);
		scheme.setSummary("Unlimited Pack");

		// set subscription related data
		subs.setActive(true);
		subs.setPack(scheme);

		Transaction tx = s1.beginTransaction();

		s1.save(scheme);
		s1.save(subs);
		s1.save(user1);

		tx.commit();

		s1.close();

		// begin another session

		Session s2 = factory.openSession();

		Criteria c1 = s2.createCriteria(PaidSubscriptionUser.class);

		@SuppressWarnings("unchecked")
		List<PaidSubscriptionUser> list = c1.list();

		System.out.println("###Subscribed user### Total: " + list.size());
		list.forEach(System.out::println);

		c1 = s2.createCriteria(User.class);

		@SuppressWarnings("unchecked")
		List<User> userList = c1.list();

		System.out.println("###Not subscribed user### Total: " + userList.size());
		userList.forEach(System.out::println);

		s2.close();
		factory.close();

	}
}
