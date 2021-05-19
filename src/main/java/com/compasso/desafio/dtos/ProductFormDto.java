package com.compasso.desafio.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compasso.desafio.models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor	
@Data
public class ProductFormDto {

    @NotNull
    @NotEmpty
    public String name;

    public String description;

    @NotNull 
    public Double price;

    public Product getProduct(){
        
        return Product.builder()					
                        .name( getName() )
                        .description( getDescription() )
                        .price( getPrice() )
                        .build();

    }
	
}


