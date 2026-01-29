package com.example.commerce.product.dtos;

import com.example.commerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateDto {
    private String name;
    private int price;
    private String category;
    private int stockQuantuty;
    private String imagePath;

    public Product toEnntity(){
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .stockQuantity(this.stockQuantuty)
                .productImage(this.productImage)
                .build();
    }
}
