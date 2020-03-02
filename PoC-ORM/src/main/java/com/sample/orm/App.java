package com.sample.orm;

import java.io.Serializable;
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
        User user = new User();
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setProduct(prod);
        order.setUser(user);
        
        Set<Order> orders = new HashSet<>();
        orders.add(order);
//        User user = new User();
        user.setContact(3435);
        user.setEmail("ajfbf");
        user.setFirstName("first");
        user.setLastName("app");
        user.setUsername("fn85");
        user.setOrder(orders);
        
       Serializable pS =  session.save(prod);
       Serializable oS= session.save(order);
        
     
       Serializable uS = session.save(user);
    
   
        
        
        session.getTransaction().commit();
        session.close();
        
        
        //open another session to see items
        Session s2 = DefaultSessionFactory.getSession().openSession();
        
       User rU =  (User) s2.get(User.class, uS);
        Order rO = (Order) s2.get(Order.class, oS);
        Product rP = (Product) s2.get(Product.class, pS);
        
        System.out.println(rU);
        
        DefaultSessionFactory.getSession().close();
        
        
     
    }
}
