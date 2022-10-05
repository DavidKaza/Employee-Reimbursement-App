package com.revature.webapp.repository;

import com.revature.webapp.model.User;

import java.sql.*;

public class UserRepository {
    public User addUser(User user) throws SQLException {
        try(Connection connectionObject = ConnectionFactory.createConnection()){
            String sql = "insert into project1.users(username, password, firstname, lastname, role_id) values (?,?,?,?,?)";
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setInt(5, user.getRoleId());

            int numberOfRecordsAdded = pstmt.executeUpdate();
            ResultSet rs  = pstmt.getGeneratedKeys();
            rs.next();
            int id= rs.getInt(1);

            return new User(id, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRoleId());
        }
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException{
        try(Connection connectionObj = ConnectionFactory.createConnection()){
            String sql = "select * from project1.users as u where lower(u.username) = ? and u.password = ?";
            PreparedStatement pstmt = connectionObj.prepareStatement(sql);
            pstmt.setString(1, username.toLowerCase());
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("user_id");
                String un = rs.getString("username");
                String pw = rs.getString("password");
                String firstName = rs.getString(("firstname"));
                String lastName = rs.getString("lastname");
                int roleId = rs.getInt("role_id");

                return new User(id, un, pw, firstName, lastName, roleId);
            }else{
                return null;
            }
        }
    }
    public User getUserInfo(String username) throws SQLException{
        try(Connection connectionObject = ConnectionFactory.createConnection()){
            String sql = "select user_id, username, firstname, lastname, role_id from project1.users as u where lower(u.username) = ?";
            PreparedStatement pstmt = connectionObject.prepareStatement(sql);
            pstmt.setString(1, username.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt("user_id");
                String un = rs.getString("username");
                String firstName = rs.getString(("firstname"));
                String lastName = rs.getString("lastname");
                int roleId = rs.getInt("role_id");

                return new User(id, un, firstName, lastName, roleId);
            }else{
                return null;
            }
        }
        }

}
