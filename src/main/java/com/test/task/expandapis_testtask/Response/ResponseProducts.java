package com.test.task.expandapis_testtask.Response;

import java.util.List;

public class ResponseProducts {
    private String table;

    private List<ResponseRecords> records;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<ResponseRecords> getRecords() {
        return records;
    }

    public void setRecords(List<ResponseRecords> records) {
        this.records = records;
    }
}
