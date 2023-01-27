package com.uni.practice.product.service;

import com.uni.practice.common.paging.SelectCriteria;
import com.uni.practice.product.dao.ProductMapper;
import com.uni.practice.product.dto.ProductDto;
import com.uni.practice.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class ProductService {


    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ProductDto selectProduct(String productCode) {
        log.info("[ProductService] selectProduct Start ===================================");
        ProductDto productDto = productMapper.selectProduct(productCode);
        productDto.setProductImageUrl(IMAGE_URL + productDto.getProductImageUrl());
        log.info("[ProductService] selectProduct End ===================================");
        return productDto;
    }


    public int selectProductTotal() {
        log.info("[ProductService] selectProductTotal Start ===================================");
        int result = productMapper.selectProductTotal();

        log.info("[ProductService] selectProductTotal End ===================================");
        return result;
    }

    public Object selectProductListWithPaging(SelectCriteria selectCriteria) {
        log.info("[ProductService] selectProductListWithPaging Start ===================================");
        List<ProductDto> productList = productMapper.selectProductListWithPaging(selectCriteria);

        for(int i = 0 ; i < productList.size() ; i++) {
            productList.get(i).setProductImageUrl(IMAGE_URL + productList.get(i).getProductImageUrl());
        }
        log.info("[ProductService] selectProductListWithPaging End ===================================");
        return productList;
    }

    public Object selectProductListWithPagingForAdmin(SelectCriteria selectCriteria) {
        log.info("[ProductService] selectProductListWithPagingForAdmin Start ===================================");
        List<ProductDto> productList = productMapper.selectProductListWithPagingForAdmin(selectCriteria);

        for(int i = 0 ; i < productList.size() ; i++) {
            productList.get(i).setProductImageUrl(IMAGE_URL + productList.get(i).getProductImageUrl());
        }
        log.info("[ProductService] selectProductListWithPagingForAdmin End ===================================");
        return productList;
    }


    public Object selectProductForAdmin(Long productCode) {
        log.info("[ProductService] selectProductForAdmin Start ===================================");
        ProductDto productDto = productMapper.selectProductForAdmin(productCode);
        productDto.setProductImageUrl(IMAGE_URL + productDto.getProductImageUrl());
        log.info("[ProductService] selectProductForAdmin End ===================================");
        return productDto;
    }

    public int selectProductTotalForAdmin() {
        log.info("[ProductService] selectProductTotal Start ===================================");
        int result = productMapper.selectProductTotalForAdmin();

        log.info("[ProductService] selectProductTotal End ===================================");
        return result;
    }


    @Transactional
    public Object updateProduct(ProductDto productDto) {
        log.info("[ProductService] updateProduct Start ===================================");
        log.info("[ProductService] productDto : " + productDto);
        String replaceFileName = null;
        int result = 0;

        try {
            String oriImage = productMapper.selectProduct(String.valueOf(productDto.getProductCode())).getProductImageUrl();
            log.info("[updateProduct] oriImage : " + oriImage);

            if(productDto.getProductImage() != null){
                // 이미지 변경 진행
                String imageName = UUID.randomUUID().toString().replace("-", "");
                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productDto.getProductImage());

                log.info("[updateProduct] IMAGE_DIR!!"+ IMAGE_DIR);
                log.info("[updateProduct] imageName!!"+ imageName);

                log.info("[updateProduct] InsertFileName : " + replaceFileName);
                productDto.setProductImageUrl(replaceFileName);

                log.info("[updateProduct] deleteImage : " + oriImage);
                boolean isDelete = FileUploadUtils.deleteFile(IMAGE_DIR, oriImage);
                log.info("[update] isDelete : " + isDelete);
            } else {
                // 이미지 변경 없을 시
                productDto.setProductImageUrl(oriImage);
            }

            result = productMapper.updateProduct(productDto);

        } catch (IOException e) {
            log.info("[updateProduct] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        log.info("[ProductService] updateProduct End ===================================");
        log.info("[ProductService] result > 0 성공: "+ result);

        return (result > 0) ? "상품 업데이트 성공" : "상품 업데이트 실패";
    }

    @Transactional
    public String insertProduct(ProductDto productDto) {
        log.info("[ProductService] insertProduct Start ===================================");
        log.info("[ProductService] productDto : " + productDto);
        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        int result = 0;
        log.info("[ProductService] IMAGE_DIR : " + IMAGE_DIR);
        log.info("[ProductService] imageName : " + imageName);
        try {
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productDto.getProductImage());
            log.info("[ProductService] replaceFileName : " + replaceFileName);

            productDto.setProductImageUrl(replaceFileName);

            log.info("[ProductService] insert Image Name : "+ replaceFileName);

            result = productMapper.insertProduct(productDto);

        } catch (IOException e) {
            log.info("[ProductService] IOException IMAGE_DIR : "+ IMAGE_DIR);

            log.info("[ProductService] IOException deleteFile : "+ replaceFileName);

            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        log.info("[ProductService] result > 0 성공: "+ result);
        return (result > 0) ? "상품 입력 성공" : "상품 입력 실패";
    }


    public List<ProductDto> selectProductListAboutOuter() {
        log.info("[ProductService] selectProductListAboutOuter Start ===================================");

        List<ProductDto> productListAboutOuter = productMapper.selectProductListAboutOuter();

        for(int i = 0 ; i < productListAboutOuter.size() ; i++) {
            productListAboutOuter.get(i).setProductImageUrl(IMAGE_URL + productListAboutOuter.get(i).getProductImageUrl());
        }

        log.info("[ProductService] selectProductListAboutOuter End ==============================");

        return productListAboutOuter;
    }

    public List<ProductDto> selectProductListAboutBag() {
        log.info("[ProductService] selectProductListAboutBag Start ===================================");

        List<ProductDto> productListAboutBag = productMapper.selectProductListAboutBag();

        for(int i = 0 ; i < productListAboutBag.size() ; i++) {
            productListAboutBag.get(i).setProductImageUrl(IMAGE_URL + productListAboutBag.get(i).getProductImageUrl());
        }

        log.info("[ProductService] selectProductListAboutBag End ==============================");

        return productListAboutBag;
    }

    public List<ProductDto> selectProductListAboutPants() {

        log.info("[ProductService] selectProductListAboutPants Start ===================================");

        List<ProductDto> productListAboutPants = productMapper.selectProductListAboutPants();

        for(int i = 0 ; i < productListAboutPants.size() ; i++) {
            productListAboutPants.get(i).setProductImageUrl(IMAGE_URL + productListAboutPants.get(i).getProductImageUrl());
        }

        log.info("[ProductService] selectProductListAboutPants End ==============================");

        return productListAboutPants;
    }
    public List<ProductDto> selectProductListAboutTop() {

        log.info("[ProductService] selectProductListAboutTop Start ===================================");

        List<ProductDto> productListAboutTop = productMapper.selectProductListAboutTop();

        for(int i = 0 ; i < productListAboutTop.size() ; i++) {
            productListAboutTop.get(i).setProductImageUrl(IMAGE_URL + productListAboutTop.get(i).getProductImageUrl());
        }

        log.info("[ProductService] selectProductListAboutTop End ==============================");

        return productListAboutTop;
    }

    public List<ProductDto> selectProductListAboutEtc() {

        log.info("[ProductService] selectProductListAboutEtc Start ===================================");

        List<ProductDto> productListAboutEtc = productMapper.selectProductListAboutEtc();

        for(int i = 0 ; i < productListAboutEtc.size() ; i++) {
            productListAboutEtc.get(i).setProductImageUrl(IMAGE_URL + productListAboutEtc.get(i).getProductImageUrl());
        }

        log.info("[ProductService] selectProductListAboutEtc End ==============================");

        return productListAboutEtc;
    }


    public List<ProductDto> selectSearchProductList(String search) {
        log.info("[ProductService] selectSearchProductList Start ===================================");
        log.info("[ProductService] searchValue : " + search);
        List<ProductDto> productListWithSearchValue = productMapper.productListWithSearchValue(search);
        log.info("[ProductService] productListWithSearchValue : " + productListWithSearchValue);

        for(int i = 0 ; i < productListWithSearchValue.size() ; i++) {
            productListWithSearchValue.get(i).setProductImageUrl(IMAGE_URL + productListWithSearchValue.get(i).getProductImageUrl());
        }
        log.info("[ProductService] selectSearchProductList End ===================================");

        return productListWithSearchValue;
    }


    public Long deleteProduct(Long productCode) {
        return productMapper.deleteProduct(productCode);
    }

}
