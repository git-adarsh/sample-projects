package com.sample.orm;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.sample.orm.entity.Subscription;
import com.sample.orm.factory.DefaultSessionFactory;

public class FirstLevelCachingOps {
	public static void main(String[] args) {
		// first level cahe demo

		SessionFactory factory = DefaultSessionFactory.getSession();
		// create a session
		Session s1 = factory.openSession();

		// create a subscription object
		Subscription sub1 = new Subscription();

		sub1.setDetails("Testing first level cache");
		sub1.setPackValue(90);
		sub1.setSummary("Testing");

		s1.beginTransaction();
		// store at db
		Serializable id = s1.save(sub1);

		s1.getTransaction().commit();
		s1.close();

		// fetch form db
		// can see hibernating firing a Select query
		Session s2 = factory.openSession();

		// using load
		System.out.println("### Expect a Select Query on Console ####");
		System.out.println("[S2: LOAD]");
		System.out.println(s2.load(Subscription.class, id).toString());

		// fetch again
		System.out.println("### Don't Expect a Select Query on Console ####");

		System.out.println("[S2: LOAD]");
		System.out.println(s2.load(Subscription.class, id).toString());

		// create another session

		Session s3 = factory.openSession();

		// session s3 doesn't have access to the first level cahe of the s1
		// so, this session will make a trip to db on first query
		System.out.println("### Expect a Select Query on Console ####");

		System.out.println("[S3: LOAD]");
		System.out.println(s3.load(Subscription.class, id).toString());

		System.out.println("### [S3] Don't Expect a Select Query on Console ####");

		System.out.println("[S3: LOAD]");
		System.out.println(s3.load(Subscription.class, id).toString());

		s2.close();
		s3.close();

		factory.close();
	}
}