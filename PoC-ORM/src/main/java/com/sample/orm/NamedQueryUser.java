package com.sample.orm;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sample.orm.NameQueryOpsOrder.ParamType;
import com.sample.orm.entity.PaidSubscriptionUser;
import com.sample.orm.entity.Subscription;
import com.sample.orm.factory.DefaultSessionFactory;

public class NamedQueryUser {
	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args)
			throws HibernateException, NoSuchFieldException, SecurityException, ClassNotFoundException {
		// create subscriptions
		Subscription sub1 = new Subscription();
		sub1.setDetails("You can watch unlimited movies with this pack");
		sub1.setPackValue(999);
		sub1.setSummary("Unlimited Pack");

		Subscription sub2 = new Subscription();
		sub2.setDetails("You can watch 2 GB of movie with this pack");
		sub2.setPackValue(499);
		sub2.setSummary("Limited Data Pack");

		PaidSubscriptionUser user1 = new PaidSubscriptionUser();
		user1.setAuth("auth1");
		user1.setContact(980);
		user1.setEmail("someemail");
		user1.setFirstName("subscribed");
		user1.setLastName("user1");
		// set subscription related data
		user1.setActive(true);
		user1.setPack(sub1);

		// currently, not active
		PaidSubscriptionUser user2 = new PaidSubscriptionUser();
		user2.setAuth("auth2");
		user2.setContact(981);
		user2.setEmail("someemail");
		user2.setFirstName("subscribed");
		user2.setLastName("user2");
		// set subscription related data
		user2.setActive(true);
		user2.setPack(sub1);

		PaidSubscriptionUser user3 = new PaidSubscriptionUser();
		user3.setAuth("auth3");
		user3.setContact(980);
		user3.setEmail("someemail");
		user3.setFirstName("subscribed");
		user3.setLastName("user3");
		// set subscription related data
		user3.setActive(false);
		user3.setPack(sub2);

		SessionFactory factory = DefaultSessionFactory.getSession();

		Session s1 = factory.openSession();
		Transaction t1 = s1.beginTransaction();

		s1.persist(sub1);
		s1.persist(sub2);
		s1.persist(user1);
		s1.persist(user2);
		s1.persist(user3);

		// commit to be able to access this data from other sessions
		t1.commit();
		s1.close();

		Session s2 = factory.openSession();
		System.out.println("### Showing packs for Input###");

		s2.createCriteria(Subscription.class).list().forEach(System.out::println);
		System.out.println("### USERS SUBSCRIBED TO: ");
		NameQueryOpsOrder.executeQuery(PaidSubscriptionUser.USERS_SUBSCRIBED_TO_SCHEME, s2, "packId", ParamType.String)
				.list().forEach(System.out::println);

		System.out.println("### ACTIVE USERS: ");

		NameQueryOpsOrder.executeQuery(PaidSubscriptionUser.ACTIVE_USERS, s2, null, null).list()
				.forEach(System.out::println);

		s2.close();
		factory.close();

	}
}