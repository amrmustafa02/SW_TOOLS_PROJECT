package com.redhat.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Orders.class)
public abstract class Orders_ {

	public static volatile SingularAttribute<Orders, Restaurant> orderRes;
	public static volatile SingularAttribute<Orders, Integer> orderId;
	public static volatile SingularAttribute<Orders, Integer> totalPrice;
	public static volatile SingularAttribute<Orders, String> orderStatus;
	public static volatile SingularAttribute<Orders, Runner> runner;
	public static volatile SetAttribute<Orders, Meal> meals;

}

