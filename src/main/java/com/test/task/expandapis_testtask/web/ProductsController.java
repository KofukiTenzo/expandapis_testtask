package com.test.task.expandapis_testtask.web;

import com.test.task.expandapis_testtask.DTO.ProductsDTO;
import com.test.task.expandapis_testtask.Response.ResponseProducts;
import com.test.task.expandapis_testtask.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveRecords(@RequestBody ProductsDTO payload) {
        productsService.saveRecords(payload);
        return ResponseEntity.ok("Records saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseProducts> getRecordsFromTable() {
        ResponseProducts products = new ResponseProducts();
        products.setTable(productsService.getTableName());
        products.setRecords(productsService.getRecords());

        return ResponseEntity.ok(products);
    }
}
