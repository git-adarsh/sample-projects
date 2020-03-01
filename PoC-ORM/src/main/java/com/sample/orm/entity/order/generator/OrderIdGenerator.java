package com.sample.orm.entity.order.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.sample.orm.entity.Order;

public class OrderIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		Order order = (Order) arg1;
		return order.getUser().getUsername() + order.getP_id() + order.getOrderDate();
	}

	
	

}
