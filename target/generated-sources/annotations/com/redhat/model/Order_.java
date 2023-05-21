package com.redhat.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Double> totalPrice;
	public static volatile SingularAttribute<Order, String> orderStatus;
	public static volatile SingularAttribute<Order, Integer> id;
	public static volatile SingularAttribute<Order, Runner> runner;
	public static volatile ListAttribute<Order, Meal> meals;

}

