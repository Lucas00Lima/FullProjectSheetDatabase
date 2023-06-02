package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class Database {
    private String username;
    private String password;
    private String db;
    private String url;
    private String table;

    public void connectionDatabase() throws SQLException {
        username = JOptionPane.showInputDialog("Nome do usuario do banco de dados");
        password = JOptionPane.showInputDialog("Insira a senha do banco de dados");
        db = JOptionPane.showInputDialog("Nome do banco a qual deseja acessar");
        url = "jdbc:mysql://localhost:3306/" + db;
        table = "product";
        String defaultValue = "";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            StringBuilder insertQuery = new StringBuilder("INSERT INTO " + table
                    + "internal_code,barcode,name,category_id,description,cost,price,current_stock,type,type2");
            StringBuilder valuePlaceholders = new StringBuilder(" VALUES (?,?,?,?,?,?,?,?,?,?");
            List<String> defaultValues = new ArrayList<>(0);
            DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, table, null);
            int totalColumnsInDatabase = 10;
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                if (!isExcludedColumn(columnName)) {
                    if (totalColumnsInDatabase > 0) {
                        insertQuery.append(",");
                        valuePlaceholders.append(",");
                    }
                    insertQuery.append(columnName);
                    valuePlaceholders.append("?");
                    defaultValues.add(defaultValue);
                    totalColumnsInDatabase++;
                }
            }
        } catch (Error e) {
            throw new RuntimeErrorException(e);
        }
    }
    private boolean isExcludedColumn(String columnName) {
        String[] excludedColumns = {
            "internal_code","barcode","name","category_id","description","cost","price","current_stock","type","type2"};
        for (String excludedColumn : excludedColumns) {
            if (columnName.equals(excludedColumn)) {
                return true;
            }
        }     
        return false;
    }
}
