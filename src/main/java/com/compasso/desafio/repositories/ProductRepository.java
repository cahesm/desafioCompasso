package com.compasso.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.compasso.desafio.models.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
 
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, JpaSpecificationExecutor<Product>{
 
	@Query("SELECT p FROM Product p WHERE ( p.name = :n or p.description = :n) ")  			 
	List<Product> searchByTerm( @Param("n") String n );
	
        @Query("SELECT p FROM Product p WHERE ( p.price >= :minPrice ) ")  			 
        List<Product> searchByMinPrice( @Param("minPrice") Double minPrice );
	
        @Query("SELECT p FROM Product p WHERE ( p.price <= :maxPrice ) ")  			 
        List<Product> searchByMaxPrice( @Param("maxPrice") Double maxPrice );
	
        @Query("SELECT p FROM Product p WHERE ( p.name = :n or p.description = :n) and ( p.price >= :minPrice ) ")  			 
        List<Product> searchByTermAndMinPrice( @Param("n") String n, @Param("minPrice") Double minPrice );
	
        @Query("SELECT p FROM Product p WHERE ( p.name = :n or p.description = :n) and ( p.price <= :maxPrice ) ")  			 
        List<Product> searchByTermAndMaxPrice( @Param("n") String n, @Param("maxPrice") Double maxPrice );
	
        @Query("SELECT p FROM Product p WHERE ( p.price >= :minPrice ) and  ( p.price <= :maxPrice ) ")  			 
        List<Product> searchByMinPriceAndMaxPrice( @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice );
	        
        @Query("SELECT p FROM Product p WHERE ( p.name = :n or p.description = :n) and ( p.price >= :minPrice ) and  ( p.price <= :maxPrice ) ")  			 
        List<Product> searchByAll(@Param("n") String n, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice );		
}
