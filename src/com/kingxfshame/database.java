package com.kingxfshame;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class database {
    public String host, user, password,username;
    public Connection connection;

    public database(String host, String user, String password,String username) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.username = username;
    }

    public void addRecord(String name, int score) {
        try {
            String sql = String.format("INSERT INTO game(name,score) VALUES('%s',%d)", name, score);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public ArrayList<String> getRecords() {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("SELECT * from game");
            while (res.next()) {
                int id = res.getInt(1);
                String name = res.getString(2);
                int score = res.getInt(3);
                String date = res.getString(4);

                result.add(name + "at" + date + ": " + score);
            }
        }
        catch (Exception ex) {

        }
        return result;
    }
    public void init(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(host, user, password);
            System.out.println("Database Connected");

        }
        catch (Exception ex){

        }
    }
    public void databaseConnection()throws SQLException {

        try {


            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(host, user, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM game");
                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int score = resultSet.getInt(3);
                    String date = resultSet.getString(4);

                }


            }
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }
}
