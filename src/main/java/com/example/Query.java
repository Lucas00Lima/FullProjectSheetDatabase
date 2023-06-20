package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Workbook;

public class Query {
	private Connection connection;

	public void query() throws SQLException, FileNotFoundException {
		Database data = new Database();
		try (Connection connection = data.connectionDatabase()) {
			
		}
	}
}