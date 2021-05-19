package com.compasso.desafio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compasso.desafio.dtos.ProductFormDto;
import com.compasso.desafio.exceptions.ObjectNotFoundException;
import com.compasso.desafio.models.Product;
import com.compasso.desafio.repositories.ProductRepository;

@Service
public class ProductService {
 
    @Autowired
    ProductRepository repository;
 
    public void save(final Product product) {
        
    	repository.save(product);    	
    }
    
    public Product update( int id, ProductFormDto form ) {
        
    	Product product = getProductById( id );
    	
    	product.setName( form.getName() );
    	product.setDescription( form.getDescription() );
    	product.setPrice( form.getPrice() );
    	    	    
    	return repository.save( product );    	
    }
    
    public void delete( int id ) {
        
    	Product product = getProductById( id );
    	
    	repository.deleteById( product.getId() );    	    	  
    }
     
    public List<Product> getAllProducts() {
        
    	final List<Product> products = new ArrayList<>();
        
    	repository.findAll().forEach( product -> products.add( product ) );
        
    	return products;
    }
    
    public List<Product> search( String n, Double minPrice, Double maxPrice) {
        
    	final List<Product> products = new ArrayList<>();
        
        if ( n != null && minPrice != null && maxPrice != null )
        {
            repository.searchByAll( n, minPrice, maxPrice ).forEach( product -> products.add( product ) );
        }
        else if ( n!= null && minPrice != null  )
        {
            repository.searchByTermAndMinPrice( n, minPrice ).forEach( product -> products.add( product ) );
        }
        else if ( n!= null && maxPrice != null  )
        {
            repository.searchByTermAndMaxPrice( n, maxPrice ).forEach( product -> products.add( product ) );
        }
        else if ( minPrice!= null && maxPrice != null )
        {
            repository.searchByMinPriceAndMaxPrice( minPrice, maxPrice ).forEach( product -> products.add( product ) );
        }
        else if ( n!= null )
        {
            repository.searchByTerm( n ).forEach( product -> products.add( product ) );
        }
        else if ( minPrice!= null )
        {
            repository.searchByMinPrice( minPrice ).forEach( product -> products.add( product ) );
        }
        else if ( maxPrice != null )
        {
            repository.searchByMaxPrice( maxPrice ).forEach( product -> products.add( product ) );  
        }
                     
    	return products;
    }
    
    public Product getProductById( int id ) {
        
        Optional<Product> product = repository.findById( id );
        
        return product.orElseThrow( () -> new ObjectNotFoundException( "Produto não encontrado!"));        
    }
}
