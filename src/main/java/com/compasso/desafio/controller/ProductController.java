package com.compasso.desafio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.desafio.dtos.FormErrorDto;
import com.compasso.desafio.dtos.ProductDto;
import com.compasso.desafio.dtos.ProductFormDto;
import com.compasso.desafio.models.Product;
import com.compasso.desafio.services.ProductService;

import com.compasso.desafio.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){

        List<ProductDto> products =  productService.getAllProducts().stream().map(product -> ProductDto.getInstance(product))
                        .collect(Collectors.toList());

        return ResponseEntity.ok( products );		
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById( @PathVariable Integer id ){

        ProductDto product = null;

        try
        {
            product = ProductDto.getInstance( productService.getProductById( id ) );

        }
        catch (ObjectNotFoundException e)
        {
            return ResponseEntity.status( HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok( product );			
    }

    @GetMapping("/search")	
    public ResponseEntity<List<ProductDto>> search( @RequestParam(required = false) String q, @RequestParam(required = false, name="min_price") Double minPrice, @RequestParam(required = false, name="max_price") Double max_price  ){

        List<ProductDto> products =  productService.search( q, minPrice, max_price ).stream().map( product -> ProductDto.getInstance( product ) )
                            .collect(Collectors.toList());

        return ResponseEntity.ok( products );		
    }

    @PostMapping
    public ResponseEntity<Object> addProduct( @RequestBody ProductFormDto form ){

        String validationError = validateForm( form );	

        if ( validationError != null )
        {											
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body( FormErrorDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message( validationError )
                            .build());
        }
        else
        {
            Product product = form.getProduct();

            productService.save( product );

            return new ResponseEntity( ProductDto.getInstance( product ), HttpStatus.CREATED );           
        }		
    }

    @PutMapping("/{id}")        
    public ResponseEntity<Object> updateProduct( @PathVariable Integer id, @RequestBody ProductFormDto form ){
        
        String validationError = validateForm( form );

        if ( validationError != null )
        {											
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body( FormErrorDto.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message( validationError )
                            .build());
        }
        else
        {			
            try
            {									
                return ResponseEntity.ok( ProductDto.getInstance( productService.update( id, form ) ) );			
            }
            catch( ObjectNotFoundException e )
            {
                return ResponseEntity.status( HttpStatus.NOT_FOUND).build();
            }									
        }				
    }

    @DeleteMapping("/{id}")	
    public ResponseEntity<Object> deleteProduct( @PathVariable Integer id ){		

        try
        {					
            productService.delete( id );

            return ResponseEntity.noContent().build();
        }
        catch (ObjectNotFoundException e)
        {
            return ResponseEntity.status( HttpStatus.NOT_FOUND).build();
        }										
    }
    
    private String validateForm( ProductFormDto form )
    {
        return ( form.getName() == null || form.getName().isEmpty() ) 
                ? "Name must have a value!"
                : ( form.getDescription() == null || form.getDescription().isEmpty() ) 
                    ? "Description must have a value!"
                    : ( form.getPrice() == null ) 
                        ? "Price must have a value!" 	
                        : ( form.getPrice() <= 0 )
                            ? "Price must be greater then zero"
                            : null;
    }
}
