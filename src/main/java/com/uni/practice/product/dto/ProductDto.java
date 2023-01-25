package com.uni.practice.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long productCode;
    private String productName;
    private String productPrice;
    private String productDiscountedPrice;
    private String productDiscountRate;
    private String productDescription;
    private String productOrderable;
    private Long categoryCode;
    private String categoryName;
    private MultipartFile productImage;
    private String productImageUrl;
    private Long productStock;


}
