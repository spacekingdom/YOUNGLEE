package com.uni.practice.product.controller;

import com.uni.practice.common.ResponseDto;
import com.uni.practice.common.paging.Pagenation;
import com.uni.practice.common.paging.ResponseDtoWithPaging;
import com.uni.practice.common.paging.SelectCriteria;
import com.uni.practice.product.dto.ProductDto;
import com.uni.practice.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//  회원 제품조회
    @GetMapping("/products")
    public ResponseEntity<ResponseDto> selectProductListWithPaging(@RequestParam(name="offset", defaultValue="1") String offset) {

        log.info("[ProductController] selectProductListWithPaging : " + offset);

//      페이지네이션을 위한 모든제품리스트 카운트조회
        int totalCount = productService.selectProductTotal();
        int limit = 10;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);;

        log.info("[ProductController] selectCriteria : " + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(productService.selectProductListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

//  관리자 제품조회
    @GetMapping("/products-management")
    public ResponseEntity<ResponseDto> selectProductListWithPagingForAdmin(@RequestParam(name="offset", defaultValue="1") String offset) {

        log.info("[ProductController] selectProductListWithPagingForAdmin : " + offset);

        int totalCount = productService.selectProductTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);;

        log.info("[ProductController] selectCriteria : " + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(productService.selectProductListWithPagingForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

//  관리자 제품코드로 제품조회
    @GetMapping("/products-management/{productCode}")
    public ResponseEntity<ResponseDto> selectProductDetailForAdmin(@PathVariable Long productCode) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상품 상세정보 조회 성공",  productService.selectProductForAdmin(productCode)));
    }

//  outer 조회
    @GetMapping("/products/outer")
    public ResponseEntity<ResponseDto> selectProductListAboutOuter() {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공",  productService.selectProductListAboutOuter()));
    }

//  bag  조회
    @GetMapping("/products/bag")
    public ResponseEntity<ResponseDto> selectProductListAboutBag() {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공",  productService.selectProductListAboutBag()));
    }

//  pants  조회
    @GetMapping("/products/pants")
    public ResponseEntity<ResponseDto> selectProductListAboutPants() {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공",  productService.selectProductListAboutPants()));
    }

//  top 조회
    @GetMapping("/products/top")
    public ResponseEntity<ResponseDto> selectProductListAboutTop() {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공",  productService.selectProductListAboutTop()));
    }

//  etc 조회
    @GetMapping("/products/etc")
    public ResponseEntity<ResponseDto> selectProductListAboutEtc() {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공",  productService.selectProductListAboutEtc()));
    }

//  상품 검색
    @GetMapping("/products/search")
    public ResponseEntity<ResponseDto> selectSearchList(@RequestParam(name="s", defaultValue="all") String search) {
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공",  productService.selectSearchProductList(search)));
    }

//  상품 상세정보 조회
    @GetMapping("/products/{productCode}")
    public ResponseEntity<ResponseDto> selectProductDetail(@PathVariable String productCode) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상품 상세정보 조회 성공",  productService.selectProduct(productCode)));
    }

//  상품 등록
    @PostMapping(value = "/products-management")
    public ResponseEntity<ResponseDto> insertProduct(@ModelAttribute ProductDto productDto) {
        log.info("[ProductController] PostMapping productDto : " + productDto);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상품 등록 성공",  productService.insertProduct(productDto)));
    }
//  상품 수정
    @PutMapping(value = "/products-management")
    public ResponseEntity<ResponseDto> updateProduct(@ModelAttribute ProductDto productDto) {
        log.info("[ProductController]PutMapping productDto : " + productDto);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상품 업데이트 성공",  productService.updateProduct(productDto)));
    }
//  상품 삭제
    @DeleteMapping(value = "/products-management/{productCode}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable Long productCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상품 삭제 성공",  productService.deleteProduct(productCode)));
    }
}
