package com.example.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryAddClient {
	public void queryAddClient(Connection connection, String tableName, String sheetAcess) {
		try {
			String inserAdd = "UPDATE ";
			PreparedStatement addStatement = connection.prepareStatement("UPDATE " + tableName + " SET phone = ''");
			addStatement.addBatch(inserAdd + tableName + " SET doc_name = ''");
			addStatement.addBatch(inserAdd + tableName + " SET owner_name = ''");
			addStatement.addBatch(inserAdd + tableName + " SET occupation = ''");
			addStatement.addBatch(inserAdd + tableName + " SET phone2 = ''");
			addStatement.addBatch(inserAdd + tableName + " SET street_name = ''");
			addStatement.addBatch(inserAdd + tableName + " SET street_number = ''");
			addStatement.addBatch(inserAdd + tableName + " SET zipcode = ''");
			addStatement.addBatch(inserAdd + tableName + " SET complement = ''");
			addStatement.addBatch(inserAdd + tableName + " SET notes = ''");
			addStatement.addBatch(inserAdd + tableName + " SET coordinates = ''");
			addStatement.addBatch(inserAdd + tableName + " SET expiration_day = 10");
			addStatement.addBatch(inserAdd + tableName + " SET active = 1");
			addStatement.addBatch(inserAdd + tableName + " SET locked = 1");
			addStatement.addBatch(inserAdd + tableName + " SET payments = ''");
			addStatement.addBatch(inserAdd + tableName + " SET external_id = ''");
			addStatement.addBatch(inserAdd + tableName + " SET badge = ''");
			addStatement.addBatch(inserAdd + tableName + " SET external_system_id = ''");
			addStatement.executeUpdate();
			addStatement.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
