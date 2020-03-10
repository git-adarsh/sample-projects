package com.sample.orm.entity.idgenerator;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SubscriptionIdGenerator implements IdentifierGenerator {
	private Random random = new Random();

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		return "scheme" + random.nextInt();
	}

}
