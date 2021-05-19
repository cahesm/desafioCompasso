package com.compasso.desafio.dtos;

import com.compasso.desafio.models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor	
public class ProductDto {

    public int id;
    public String name;
    public String description;
    public Double price;


    public static ProductDto getInstance( Product product )
    {
            return ProductDto.builder()
                                .id( product.getId() )
                                .name( product.getName() )
                                .description( product.getDescription() )
                                .price( product.getPrice() )
                                .build();

    }
}


