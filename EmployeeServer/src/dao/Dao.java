package dao;

import java.sql.*;

public abstract class Dao {
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    public Dao() {
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "insurance";
            String userName = "root";
            String password = "1234";
            connection = DriverManager.getConnection(url + dbName + "?useSSL=false", userName, password);
            System.out.println("DB is connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected boolean create(String query) {
        System.out.println(query);
        try {
            statement = connection.createStatement();
            if(!statement.execute(query)) System.out.println("insert OK!!");
            return true;
//            return !statement.execute(query);
        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    protected ResultSet retrieve(String query) {
        System.out.println(query);
        try {
            statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    protected boolean update(String query) {
        System.out.println(query);
        try {
            statement = connection.createStatement();
            if(!statement.execute(query)) System.out.println("update OK!!!");
            return true;
//            return !statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    protected boolean delete(String query) {
        System.out.println(query);
        try {
            statement = connection.createStatement();
            if(!statement.execute(query)) System.out.println("delete OK!!!");
            return true;
//            return !statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
