package com.sample.orm.factory;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DefaultSessionFactory {

	ClassLoader classLoader = getClass().getClassLoader();
	static File file;

	// prevent external instantiation
	private DefaultSessionFactory() {
		file = new File(classLoader.getResource("/hibernate.cgf.xml").getFile());
	}

	private static class LazySessionFactoryHolder {

		private static SessionFactory sessionFactory = getSession();

		private static SessionFactory getSession() {/*
													 * System.out.println("Creating Session Factory.."); Configuration
													 * config = loadConfig(); ServiceRegistry serviceRegistry = new
													 * StandardServiceRegistryBuilder().applySettings(config.
													 * getProperties()) .build(); sessionFactory =
													 * config.buildSessionFactory(serviceRegistry); return
													 * sessionFactory;
													 */
			System.out.println("Creating Session Factory..");
			try {
				// Create the SessionFactory from hibernate.cfg.xml
				return new AnnotationConfiguration().configure(DefaultSessionFactory.class.getResource("/hibernate.cgf.xml")).buildSessionFactory();

			} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

/*		private static Configuration loadConfig() {
			Configuration configuration = new Configuration();
			// Hibernate settings equivalent to hibernate.cfg.xml's properties

			Properties settings = new Properties();
			settings.put(Environment.DRIVER, "org.h2.Driver");
			settings.put(Environment.URL, "jdbc:h2:mem:testdb");
			settings.put(Environment.USER, "sa");
			settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
			settings.put(Environment.SHOW_SQL, "true");
			settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
			configuration.setProperties(settings);

			return configuration;

		}*/
	}

	public static SessionFactory getSession() {
		return LazySessionFactoryHolder.sessionFactory;
	}

}
