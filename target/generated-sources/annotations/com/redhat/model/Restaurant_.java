package com.redhat.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Restaurant.class)
public abstract class Restaurant_ {

	public static volatile SingularAttribute<Restaurant, String> name;
	public static volatile SingularAttribute<Restaurant, Integer> id;
	public static volatile SingularAttribute<Restaurant, Integer> ownerId;
	public static volatile ListAttribute<Restaurant, Meal> meals;

}

