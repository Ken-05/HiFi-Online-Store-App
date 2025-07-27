package com.example.hifi.persistence.hsqldb;

import com.example.hifi.objects.OrderHistoryRecord;
import com.example.hifi.objects.Product;
import com.example.hifi.persistence.OrderHistoryPersistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryPersistenceHSQLDB extends PersistenceHSQLDB implements OrderHistoryPersistence {
    public OrderHistoryPersistenceHSQLDB(final String dbPath) {
        super(dbPath);
    }
    
    @Override
    public List<OrderHistoryRecord> getOrderHistoryRecords(String userId) {
        final List<OrderHistoryRecord> orderHistoryRecordList = new ArrayList<>();
        try (
                Connection connection = connection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT p.*, o.orderDate FROM orderHistory o " +
                                "RIGHT JOIN products p ON o.PRODID = p.PRODID " +
                                "WHERE o.USERID = ? " +
                                "ORDER BY o.orderDate DESC")
        ) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = ProductPersistenceHSQLDB.productFromResultSet(resultSet);
                Date orderDate = resultSet.getDate("orderDate");
                orderHistoryRecordList.add(new OrderHistoryRecord(product, orderDate));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return orderHistoryRecordList;
    }
    
    @Override
    public void addOrderHistoryRecords(String userId, List<OrderHistoryRecord> orderHistoryRecords) {
        int rows = orderHistoryRecords.size();
        if (rows != 0) {
            String mergeQueryValues;
            {
                StringBuilder mergeQueryValuesBuilder = new StringBuilder("(VALUES");
                for (int i = 0; i < rows; i++) {
                    mergeQueryValuesBuilder.append("(?,?,?)");
                    if (i != rows - 1) {
                        mergeQueryValuesBuilder.append(",");
                    }
                }
                mergeQueryValuesBuilder.append(")");
                mergeQueryValues = mergeQueryValuesBuilder.toString();
            }
            try (
                    Connection connection = connection();
                    PreparedStatement statement = connection.prepareStatement(
                            "MERGE INTO orderHistory USING " + mergeQueryValues + " " +
                                    "AS v(x,y,z) ON orderHistory.userId = v.x AND orderHistory.prodId = v.y " +
                                    "WHEN MATCHED THEN UPDATE SET orderHistory.ORDERDATE = v.z " +
                                    "WHEN NOT MATCHED THEN INSERT VALUES v.x, v.y, v.z"
                    )
            ) {
                for (int i = 0; i < rows; i++) {
                    OrderHistoryRecord record = orderHistoryRecords.get(i);
                    statement.setString((i * 3) + 1, userId);
                    statement.setString((i * 3) + 2, record.getProduct().getProductID());
                    statement.setString((i * 3) + 3, record.getOrderDate().toString());
                }
                statement.execute();
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }
    }
}
