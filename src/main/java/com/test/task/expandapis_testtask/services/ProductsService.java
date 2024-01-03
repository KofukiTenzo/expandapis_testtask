package com.test.task.expandapis_testtask.services;

import com.test.task.expandapis_testtask.DTO.ProductsDTO;
import com.test.task.expandapis_testtask.Response.ResponseRecords;
import com.test.task.expandapis_testtask.data.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    private String tableName;

    @Transactional
    public void saveRecords(ProductsDTO payload) {
        tableName = payload.getTable();

        productsRepository.createTableDynamically(tableName);

        productsRepository.buildInsertQuery(tableName, payload.getRecords());
    }

    public String getTableName() {
        return tableName;
    }

    public List<ResponseRecords> getRecords() {
        return productsRepository.getAllRecords(tableName);
    }
}
