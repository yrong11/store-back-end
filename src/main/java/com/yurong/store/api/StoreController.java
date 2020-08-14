package com.yurong.store.api;

import com.yurong.store.domain.Product;
import com.yurong.store.repository.ProductRepository;
import com.yurong.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StoreController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    StoreService storeService;

    @GetMapping("/store")
    public ResponseEntity getProductList(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size){
        return ResponseEntity.ok(storeService.getProductList(page, size));
    }

    @PostMapping("/product")
    public ResponseEntity addProduct(@RequestBody @Valid Product product){
        storeService.addProduct(product);
        return ResponseEntity.ok().build();
    }




}
