package com.redhat.rest;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.redhat.model.Calculation;
import com.redhat.model.Result;

import java.util.List;

@Stateless
@Path("/")
public class CalculationService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("calc")
	public Result calculate(Calculation obj) {
		int sum;
		
		try {
			if(obj != null) {
				entityManager.persist(obj);
			}
		}catch (Exception e) {
			throw new EJBException(e);
		}
		if(obj.getOperation().equals("+"))
		{  
			sum = obj.getNumber1() + obj.getNumber2();
			return new Result(sum); 
		}
		if(obj.getOperation().equals("-"))
		{  
			sum = obj.getNumber1() - obj.getNumber2();
			return new Result(sum); 
		}
		if(obj.getOperation().equals("*"))
		{  
			sum = obj.getNumber1() * obj.getNumber2();
			return new Result(sum); 
		}
			
		if(obj.getOperation().equals("/"))
		{  
			sum = obj.getNumber1() / obj.getNumber2();
			return new Result(sum); 
		}
		else
			throw new IllegalArgumentException("Invalid");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("calculations")
	public List<Calculation> getCalc() {
		TypedQuery<Calculation> query = entityManager.createQuery("SELECT c from Calculation c",Calculation.class);
		List<Calculation> calcs = query.getResultList();
		return calcs;
	}
	
	
	
	
	
	
	
	
	
}
