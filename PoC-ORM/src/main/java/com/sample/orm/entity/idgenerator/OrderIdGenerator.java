package com.sample.orm.entity.idgenerator;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.sample.orm.entity.Order;

public class OrderIdGenerator implements IdentifierGenerator {
Random random = new Random();
	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		Order order = (Order) arg1;
		return order.getUser().getAuth() +  random.nextInt();
	}

	
	

}
