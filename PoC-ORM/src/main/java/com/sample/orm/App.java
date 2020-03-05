package com.sample.orm;

import java.io.Serializable;

import org.hibernate.Session;

import com.sample.orm.entity.Auth;
import com.sample.orm.factory.DefaultSessionFactory;

public class App {
	public static void main(String[] args) {
		Session s1 = DefaultSessionFactory.getSession().openSession();
		Session s2 = DefaultSessionFactory.getSession().openSession();
		s2.getTransaction().begin();

		s1.getTransaction().begin();

		Auth auth = new Auth();
		auth.setPwd("pwd");
		auth.setAuthId("123");

		s1.save(auth);

		// create another session

		// data is not visible to s2 session, yet
		// this section of code will throw error
		try {
			Auth a;
			a = (a = (Auth) s2.get(Auth.class, "123")) == null ? new Auth() : a;
			System.out.println(a.toString());
		} catch (Exception e) {
			System.out.println("Exception from s2: The to-be-fetched data is not available yet.");
		}

		System.out.println("S1 has access, though");
		Auth ret = (Auth) s1.get(Auth.class, "123");

		if (ret != null)
			System.out.println(ret.toString());
		else
			System.out.println("ret is null!!");

		// commit this data. It's then visible to all sessions
		s1.getTransaction().commit();

		try {
			Auth a;
			a = (a = (Auth) s2.get(Auth.class, "123")) == null ? new Auth() : a;
			System.out.println(a.toString());
		} catch (Exception e) {
			System.out.println("Exception from s2: Really not excpeted here.");
		}

		System.out.println("Closing s1");
		s1.close();

		Session s3 = DefaultSessionFactory.getSession().openSession();
		Session s4 = DefaultSessionFactory.getSession().openSession();

		s3.getTransaction().begin();
		Auth auth1 = new Auth();
		auth1.setPwd("pwd1");
		auth1.setAuthId("123");

		//issue: Not updating
		s3.update(auth1);
		s3.getTransaction().commit();

		System.out.println("Update by s3");
		Auth a = (Auth) s3.get(Auth.class, "123");

		if (a != null)
			System.out.println(ret.toString());
		else
			System.out.println("a is null!!");

		System.out.println("Update not visible to s4");

		Auth a4 = (Auth) s4.get(Auth.class, "123");

		if (a4 != null)
			System.out.println(ret.toString());
		else
			System.out.println("a is null!!");

		s3.close();
		s4.close();
		s2.close();

		DefaultSessionFactory.getSession().close();

	}
}
