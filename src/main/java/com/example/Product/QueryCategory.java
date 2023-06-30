package com.example.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class QueryCategory {
	public void queryCategory(Connection connection, String tableName, String sheetAcess)
			throws SQLException, EncryptedDocumentException, IOException {
		int i = 1;
		String filePath = sheetAcess;
		FileInputStream fileInput = new FileInputStream(filePath);
		Workbook workbook = WorkbookFactory.create(fileInput);
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		int rowIndex;
		for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			Cell categoryId = row.getCell(0);
			Cell categoryName = row.getCell(2);
			String categoryIdValue = dataFormatter.formatCellValue(categoryId);
			String categoryNameValue = dataFormatter.formatCellValue(categoryName);
			MethodoCategory methodoCategory = new MethodoCategory();
			List<String> defaultList = methodoCategory.getDefaultList();
			String insertQueryCategory = methodoCategory.methodoCategory(connection);
			PreparedStatement categoryStatement = connection.prepareStatement(insertQueryCategory);
			categoryStatement.setString(1, categoryIdValue);
			categoryStatement.setString(2, categoryNameValue);
			for (int j = 0; j < defaultList.size(); j++) {
				String categoryValue = defaultList.get(j);
				if (categoryValue.isEmpty()) {
					categoryStatement.setInt(j + 1, 0);
				} else {
					categoryStatement.setString(j + 1, categoryValue);
//						}
				}
			}
			System.out.println(categoryIdValue + " " + categoryNameValue);
			System.out.println(categoryStatement);
//			categoryStatement.execute();
		}
	}
}