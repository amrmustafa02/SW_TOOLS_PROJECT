package com.redhat.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Meal.class)
public abstract class Meal_ {

	public static volatile SingularAttribute<Meal, Double> price;
	public static volatile SingularAttribute<Meal, Restaurant> restaurant;
	public static volatile SingularAttribute<Meal, String> name;
	public static volatile ListAttribute<Meal, Orders> orders;
	public static volatile SingularAttribute<Meal, Integer> id;

}

