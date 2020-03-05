package com.sample.orm.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DefaultSessionFactory {

	// prevent external instantiation
	private DefaultSessionFactory() {
	}

	private static class LazySessionFactoryHolder {

		private static SessionFactory sessionFactory = getSession();

		private static SessionFactory getSession() {
			System.out.println("Creating Session Factory..");
			try {
				return new AnnotationConfiguration()
						.configure(DefaultSessionFactory.class.getResource("/hibernate.cgf.xml")).buildSessionFactory();

			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
	}

	public static SessionFactory getSession() {
		return LazySessionFactoryHolder.sessionFactory;
	}

}
