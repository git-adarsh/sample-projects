package com.sample.orm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;

import com.sample.orm.entity.Order;
import com.sample.orm.entity.Product;
import com.sample.orm.entity.User;
import com.sample.orm.factory.DefaultSessionFactory;

public class App 
{
    public static void main( String[] args )
    {
        Session session = DefaultSessionFactory.getSession().openSession();
        
        session.beginTransaction();
        
        Product prod = new Product();
        
        prod.setProductName("ORM");
        prod.setProductPrice(54);
        
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setP_id(prod);
        
        
        Set<Order> orders = new HashSet<>();
        orders.add(order);
        User user = new User();
        user.setContact(3435);
        user.setEmail("ajfbf");
        user.setFirstName("first");
        user.setLastName("app");
        user.setOrder(orders);
        
     
        session.save(user);
        
        session.getTransaction().commit();
        
        DefaultSessionFactory.getSession().close();
     
    }
}
