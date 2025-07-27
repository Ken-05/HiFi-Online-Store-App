package com.example.hifi.persistence.hsqldb;

import com.example.hifi.objects.User;
import com.example.hifi.persistence.UserPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserPersistenceHSQLDB extends PersistenceHSQLDB implements UserPersistence {
    public UserPersistenceHSQLDB(final String dbPath) {
        super(dbPath);
    }

    private User fromResultSet(final ResultSet rs) throws SQLException {
        final String userID = rs.getString("userID");
        final String userName = rs.getString("userName");
        final String userEmail = rs.getString("userEmail");
        final String userPassword = rs.getString("userPassword");

        User user = new User(userName, userEmail, userPassword, userID);
        return user;
    }


    @Override
    public List<User> getUserSequential() {
        final List<User> users = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                final User user = fromResultSet(rs);
                users.add(user);
            }
            rs.close();
            st.close();

            return users;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User getUserById(String aUserId) {
        User resultUser = null;
        try (final Connection c = connection()) {    // what is c??
            final PreparedStatement st = c.prepareStatement("SELECT userID, userName, userEmail, userPassword FROM users WHERE userID = ?");
            st.setString(1, aUserId);
            final ResultSet rs = st.executeQuery();

            if (rs.next()) {
                final String userID = rs.getString("userID");
                final String userName = rs.getString("userName");
                final String userEmail = rs.getString("userEmail");
                final String userPassword = rs.getString("userPassword");

                resultUser = new User(userName, userEmail, userPassword, userID);

            }
            return resultUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public User getUserByEmail(String aUserEmail) {
        User resultUser = null;
        try (final Connection c = connection()) {    // what is c??
            final PreparedStatement st = c.prepareStatement("SELECT userID, userName, userEmail, userPassword FROM users WHERE userEmail = ?");
            st.setString(1, aUserEmail);
            final ResultSet rs = st.executeQuery();

            if (rs.next()) {
                final String userID = rs.getString("userID");
                final String userName = rs.getString("userName");
                final String userEmail = rs.getString("userEmail");
                final String userPassword = rs.getString("userPassword");

                resultUser = new User(userName, userEmail, userPassword, userID);

            }
            return resultUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User insertUser(User currentUser) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?)");
            st.setString(1, currentUser.getUserId());
            st.setString(2, currentUser.getUserName());
            st.setString(3, currentUser.getUserEmail());
            st.setString(4, currentUser.getUserPassword());
            st.executeUpdate();
            return currentUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public User updateUser(User currentUser) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE users SET userName = ?, userEmail = ?, userPassword = ? WHERE userID = ?");
            st.setString(1, currentUser.getUserName());
            st.setString(2, currentUser.getUserEmail());
            st.setString(3, currentUser.getUserPassword());
            st.setString(4, currentUser.getUserId());
            st.executeUpdate();
            return currentUser;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void deleteUser(User currentUser) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM users WHERE userID = ?");
            st.setString(1, currentUser.getUserId());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
