package com.example.Product;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.example.Connect.SheetAcess;

public class QueryPadrao {
	public void queryPadrao(Connection connection, int tableName) {
		if (tableName == 0) {
			String table = "PRODUCT";
			try {
				MethodoProduct methodo = new MethodoProduct();
				String insertQuery = methodo.methodoProduct(connection, tableName);
				SheetAcess sheetAcess = new SheetAcess();
				String filePath = sheetAcess.getFilePath();
				FileInputStream fileInput = new FileInputStream(filePath);
				Workbook workbook = WorkbookFactory.create(fileInput);
				Sheet sheet = workbook.getSheetAt(0);






				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			}




			catch (Exception e) {
				System.out.println("Erro Query Padrão");
			}
		}
	}
}
