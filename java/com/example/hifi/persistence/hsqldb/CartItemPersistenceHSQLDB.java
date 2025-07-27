package com.example.hifi.persistence.hsqldb;

import com.example.hifi.objects.CartItem;
import com.example.hifi.objects.Product;
import com.example.hifi.persistence.CartItemPersistence;

import org.hsqldb.jdbc.JDBCArrayBasic;
import org.hsqldb.types.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemPersistenceHSQLDB extends PersistenceHSQLDB implements CartItemPersistence {
    public CartItemPersistenceHSQLDB(final String dbPath) {
        super(dbPath);
    }
    
    private String[] mapToProductIds(List<CartItem> cartItems) {
        final String[] productIds = new String[cartItems.size()];
        int index = 0;
        
        for (CartItem cartItem : cartItems) {
            productIds[index++] = cartItem.getProduct().getProductID();
        }
        
        return productIds;
    }
    
    @Override
    public List<CartItem> getUserCartItems(String userId) {
        final List<CartItem> cartItems = new ArrayList<>();
        try (Connection connection = connection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT p.*, i.quantity FROM cartItem i " +
                            "RIGHT JOIN products p ON i.PRODID = p.PRODID " +
                            "WHERE i.USERID = ?");
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = ProductPersistenceHSQLDB.productFromResultSet(resultSet);
                int quantity = resultSet.getInt("quantity");
                cartItems.add(new CartItem(product, quantity));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
        return cartItems;
    }
    
    @Override
    public void mergeCartItems(String userId, List<CartItem> cartItems) {
        try (Connection connection = connection()) {
            // delete items that no longer exist in cartItems
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM cartItem WHERE userId = ? AND prodId NOT IN (UNNEST(?))"
            )) {
                String[] productIds = mapToProductIds(cartItems);
                JDBCArrayBasic productIdsArray = new JDBCArrayBasic(productIds, Type.SQL_VARCHAR);
                statement.setString(1, userId);
                statement.setArray(2, productIdsArray);
                statement.execute();
            }
            // insert and update db cart items from cartItems
            int rows = cartItems.size();
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
                try (PreparedStatement statement = connection.prepareStatement(
                        "MERGE INTO cartItem USING " + mergeQueryValues + " " +
                                "AS v(x,y,z) ON cartItem.userId = v.x AND cartItem.prodId = v.y " +
                                "WHEN MATCHED THEN UPDATE SET cartItem.quantity = v.z " +
                                "WHEN NOT MATCHED THEN INSERT VALUES v.x, v.y, v.z"
                )) {
                    for (int i = 0; i < rows; i++) {
                        CartItem item = cartItems.get(i);
                        statement.setString((i * 3) + 1, userId);
                        statement.setString((i * 3) + 2, item.getProduct().getProductID());
                        statement.setString((i * 3) + 3, Integer.toString(item.getQuantity()));
                    }
                    statement.execute();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
