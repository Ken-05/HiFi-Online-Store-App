package com.example.hifi.persistence.hsqldb;

import com.example.hifi.objects.Product;
import com.example.hifi.persistence.ProductPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductPersistenceHSQLDB extends PersistenceHSQLDB implements ProductPersistence {
    public ProductPersistenceHSQLDB(final String dbPath) {
        super(dbPath);
    }

    protected static Product productFromResultSet(final ResultSet resultSet) throws SQLException {
        final String productId = resultSet.getString("prodId");
    
        Product product = new Product(productId);
        product.setProductName(resultSet.getString("prodName"));
        product.setProductDes(resultSet.getString("prodDes"));
        product.setProductImageName(resultSet.getString("prodImage"));
        product.setProductPrice(resultSet.getInt("prodPrice"));
    
        String prodKeywords = resultSet.getString("prodKeywords");
        if (prodKeywords != null) product.setKeywords(prodKeywords);
    
        return product;
    }


    @Override
    public List<Product> getProductSequential() {
        final List<Product> products = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                final Product product = productFromResultSet(rs);
                products.add(product);
            }
            rs.close();
            st.close();

            return products;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Product getProductById(String productId) {
        Product resultProd = null;
        try (final Connection c = connection()) {    // what is c??
            final PreparedStatement st = c.prepareStatement("SELECT * FROM products WHERE prodID = ?");
            st.setString(1, productId);
            final ResultSet rs = st.executeQuery();

            if (rs.next()) {
                final String prodName = rs.getString("prodName");
                final String prodDes = rs.getString("prodDes");
                final String prodImage = rs.getString("prodImage");
                final int prodPrice = rs.getInt("prodPrice");
                final String prodKeywords = rs.getString("prodKeywords");
                resultProd =  new Product(productId, prodName, prodDes);
                resultProd.setProductImageName(prodImage);
                resultProd.setProductPrice(prodPrice);
                if (prodKeywords != null) resultProd.setKeywords(prodKeywords);
            }
            return resultProd;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Product insertProduct(Product currentProduct) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO products VALUES(?, ?, ?)");
            st.setString(1, currentProduct.getProductID());
            st.setString(2, currentProduct.getProductName());
            st.setString(3, currentProduct.getProductDes());
            st.executeUpdate();
            return currentProduct;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Product updateProduct(Product currentProduct) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE products SET prodName = ?, prodDes = ? WHERE prodID = ?");
            st.setString(1, currentProduct.getProductName());
            st.setString(2, currentProduct.getProductDes());
            st.setString(3, currentProduct.getProductID());
            st.executeUpdate();
            return currentProduct;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void deleteProduct(Product currentProduct) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM products WHERE prodID = ?");
            st.setString(1, currentProduct.getProductID());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
