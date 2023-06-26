package com.example.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.Connect.Database;

public class QueryAddProduct {
	public void queryAdd(Connection connection, int tableName) throws SQLException {
		if (tableName == 1) {
		try {
			Database data = new Database();
			String table = "Product";
			PreparedStatement addStatement = connection.prepareStatement("UPDATE " + table + " SET internal_code = id");
			addStatement.addBatch("UPDATE " + table + " SET description = ''");
			addStatement.addBatch("UPDATE " + table + " SET type = 1");
			addStatement.addBatch("UPDATE " + table + " SET category_id = 2");
			addStatement.addBatch("UPDATE " + table + " SET department_id = 1");
			addStatement.addBatch("UPDATE " + table + " SET measure_unit = 'u'");
			addStatement.addBatch("UPDATE " + table + " SET production_group = 1");
			addStatement.addBatch("UPDATE " + table + " SET panel = 1");
			addStatement.addBatch("UPDATE " + table + " SET active = 1");
			addStatement.addBatch("UPDATE " + table + " SET hall_table = 1");
			addStatement.executeBatch();
		} catch (Exception e) {
			System.out.println("ERRO QUERY ADD");
		}
	}
	}
}
