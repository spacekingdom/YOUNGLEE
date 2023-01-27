package com.uni.practice.product.dao;

import com.uni.practice.common.paging.SelectCriteria;
import com.uni.practice.product.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductDto selectProduct(String productCode);

    List<ProductDto> selectProductListAboutOuter();

    List<ProductDto> selectProductListAboutBag();

    List<ProductDto> selectProductListAboutPants();

    List<ProductDto> selectProductListAboutTop();

    List<ProductDto> selectProductListAboutEtc();


    int insertProduct(ProductDto product);

    int updateProduct(ProductDto product);

    List<ProductDto> productListWithSearchValue(String search);

    ProductDto selectProductForAdmin(Long productCode);

    int selectProductTotal();

    List<ProductDto> selectProductListWithPaging(SelectCriteria selectCriteria);

    int selectProductTotalForAdmin();

    List<ProductDto> selectProductListWithPagingForAdmin(SelectCriteria selectCriteria);

    Long deleteProduct(Long productCode);

}
