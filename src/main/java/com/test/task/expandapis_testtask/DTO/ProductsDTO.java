package com.test.task.expandapis_testtask.DTO;

import java.util.List;

public class ProductsDTO {
    private String table;
    private List<RecordsDTO> records;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<RecordsDTO> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsDTO> records) {
        this.records = records;
    }
}
