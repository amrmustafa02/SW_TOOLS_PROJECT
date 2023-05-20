package com.redhat.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Restaurant.class)
public abstract class Restaurant_ {

	public static volatile SingularAttribute<Restaurant, User> owner;
	public static volatile SingularAttribute<Restaurant, String> name;
	public static volatile SetAttribute<Restaurant, Order> orders;
	public static volatile SingularAttribute<Restaurant, Integer> id;
	public static volatile SetAttribute<Restaurant, Meal> meals;

}

