package com.example.Product.QueryNova;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.Connect.Database;

public class QueryAddProduct {
	public void queryAdd(Connection connection, String tableName) throws SQLException {
		try {
			Database data = new Database();
			PreparedStatement addStatement = connection.prepareStatement("UPDATE " + tableName + " SET internal_code = id");
			addStatement.addBatch("UPDATE " + tableName + " SET description = ''");
			addStatement.addBatch("UPDATE " + tableName + " SET type = 1");
			addStatement.addBatch("UPDATE " + tableName + " SET category_id = 2");
			addStatement.addBatch("UPDATE " + tableName + " SET department_id = 1");
			addStatement.addBatch("UPDATE " + tableName + " SET measure_unit = 'u'");
			addStatement.addBatch("UPDATE " + tableName + " SET production_group = 1");
			addStatement.addBatch("UPDATE " + tableName + " SET panel = 1");
			addStatement.addBatch("UPDATE " + tableName + " SET active = 1");
			addStatement.addBatch("UPDATE " + tableName + " SET hall_table = 1");
			addStatement.executeBatch();
		} catch (Exception e) {
			System.out.println("ERRO QUERY ADD");
		}
	}
}
