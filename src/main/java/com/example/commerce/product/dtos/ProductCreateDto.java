package com.example.commerce.product.dtos;

import com.example.commerce.member.domain.Member;
import com.example.commerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateDto {
    private String name;
    private int price;
    private String category;
    private int stockQuantity;
    private MultipartFile productImage;

    public Product toEntity(Member member) {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .stockQuantity(this.stockQuantity)
                .member(member)
                .build();
    }
}
