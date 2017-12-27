package br.com.caelum.livraria.manager;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transaction
@Interceptor
public class ManagerTransaction implements Serializable{

	@Inject
	EntityManager manager;
	
	@AroundInvoke
	public Object executeManager(InvocationContext context) throws Exception {
		
		manager.getTransaction().begin();

		Object result = context.proceed();
		
		manager.getTransaction().commit();
		
		return result;
	}
}
