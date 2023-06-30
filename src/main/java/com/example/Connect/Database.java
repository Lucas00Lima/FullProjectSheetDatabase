package com.example.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Database {
	private String username;
	private String password;
	private String db;
	private String url;
	private String tableName;
	public Connection connectionDatabase() throws SQLException {
		username = "root"; //JOptionPane.showInputDialog("Nome do usuario do banco de dados");
		password =  "@soma+"; //JOptionPane.showInputDialog("Insira a senha do banco de dados"); //
		db = "db000"; //JOptionPane.showInputDialog("Nome do banco a qual deseja acessar"); //
		url = "jdbc:mysql://localhost:3306/" + db;
		String[] optionTable = {"Product", "Client"};
		int table = 0; // JOptionPane.showOptionDialog(null, "Qual tabela deseja utilizar", "Tabela", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, optionTable,null);
		if (table == 0) {
			tableName = "product";
			JOptionPane.showMessageDialog(null, "Você selecionou a tabela PRODUCT");
		} else {
			tableName = "client";
			JOptionPane.showMessageDialog(null, "Você selecionou a tabela CLIENT");
		}
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
	public String getTableName() {
		return tableName;
	}
}
