package com.test.task.expandapis_testtask.data;

import com.test.task.expandapis_testtask.DTO.RecordsDTO;
import com.test.task.expandapis_testtask.Response.ResponseRecords;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void createTableDynamically(String tableName) {
        dropTableDynamically(tableName);

        String createTableQuery = "CREATE TABLE " + tableName +
                "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                "entry_date VARCHAR(255), " +
                "item_code INT, item_name VARCHAR(255), " +
                "item_quantity INT, " +
                "status VARCHAR(255))";
        entityManager.createNativeQuery(createTableQuery).executeUpdate();
    }

    public void buildInsertQuery(String tableName, List<RecordsDTO> records) {
        StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (entry_date, item_code, item_name, item_quantity, status) VALUES ");

        for (RecordsDTO record : records) {
            insertQuery.append("(")
                    .append("'" + record.getEntryDate() + "', ")
                    .append(record.getItemCode() + ", ")
                    .append("'" + record.getItemName() + "', ")
                    .append(record.getItemQuantity() + ", ")
                    .append("'" + record.getStatus() + "'")
                    .append("), ");
        }

        insertQuery.delete(insertQuery.length() - 2, insertQuery.length());

        entityManager.createNativeQuery(insertQuery.toString()).executeUpdate();
    }

    public List<ResponseRecords> getAllRecords(String tableName) {
        String selectQuery = "SELECT ENTRY_DATE, ITEM_CODE, ITEM_NAME, ITEM_QUANTITY, STATUS FROM " + tableName;
        List<Object[]> resultList = entityManager.createNativeQuery(selectQuery).getResultList();

        List<ResponseRecords> records = new ArrayList<>();

        for (Object[] result : resultList) {
            ResponseRecords record = new ResponseRecords();
            record.setEntryDate((String) result[0]);
            record.setItemCode((int) result[1]);
            record.setItemName((String) result[2]);
            record.setItemQuantity((int) result[3]);
            record.setStatus((String) result[4]);

            records.add(record);
        }

        return records;
    }

    public void dropTableDynamically(String tableName) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + tableName;
        entityManager.createNativeQuery(dropTableQuery).executeUpdate();
    }
}
