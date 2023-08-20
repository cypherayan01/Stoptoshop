package com.project.ecommerce.config;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
//		// TODO Auto-generated method stub
//		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
		HttpMethod[] theUnsupportedActions= {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
		
		//Disable HTTP methods for Product 
		config.getExposureConfiguration()
			.forDomainType(Product.class)
			.withItemExposure((metadata,httpMethods)->httpMethods.disable(theUnsupportedActions))
			.withCollectionExposure((metadata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		
		//Disable HTTP methods for ProductCategory :
		config.getExposureConfiguration()
		.forDomainType(ProductCategory.class)
		.withItemExposure((metadata,httpMethods)->httpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metadata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		//call an internal helper method
		exposeIds(config);
	}

	private void exposeIds(RepositoryRestConfiguration config) {
		//expose entity ids
		
		//- get a list of all entity ids from entity manager
		Set<EntityType<?>> entities=entityManager.getMetamodel().getEntities();
		
		// - create a array of entity
		List<Class> entityClasses = new ArrayList<>();
		
		// - get the entity types for entity 
		for(EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		// - expose the entity ids for the array of entity/ domain types
		Class[] domainTypes=entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
		
	}
	

}
