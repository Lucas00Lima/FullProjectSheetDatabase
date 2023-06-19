package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Database {
	private String username;
	private String password;
	private String db;
	private String url;
	private String table;

	public Connection connectionDatabase() throws SQLException {
		username = "root" ; //JOptionPane.showInputDialog("Nome do usuario do banco de dados");
		password = "@soma+";//JOptionPane.showInputDialog("Insira a senha do banco de dados");
		db = "db000";//JOptionPane.showInputDialog("Nome do banco a qual deseja acessar");
		url = "jdbc:mysql://localhost:3306/" + db;
		table = "product";
		return DriverManager.getConnection(url,username,password);
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getDb() {
		return db;
	}
	public String getUrl() {
		return url;
	}
	public String getTable() {
		return table;
	}
}
