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
	public void queryCategory(Connection connection, String sheetAcess)
			throws SQLException, EncryptedDocumentException, IOException {
		String filePath = sheetAcess;
		FileInputStream fileInput = new FileInputStream(filePath);
		Workbook workbook = WorkbookFactory.create(fileInput);
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		int rowIndex;
		int panel_position = 1;
		for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			Cell categoryId = row.getCell(0);
			Cell categoryName = row.getCell(2);
			Cell categorySubType = row.getCell(5);
			if (categoryId != null && categoryId.getCellType() != CellType.BLANK) {
				String categoryIdValue = dataFormatter.formatCellValue(categoryId);
				String categoryNameValue = dataFormatter.formatCellValue(categoryName);
				String categorySubTypeValue = dataFormatter.formatCellValue(categorySubType);
				System.out.println(categorySubTypeValue);
				if (categorySubTypeValue.equals("")) {
					categorySubTypeValue = "1";}
				MethodoCategory methodoCategory = new MethodoCategory();
				List<String> defaultLists = methodoCategory.getDefaultList();
				String insertQueryCategory = methodoCategory.methodoCategory(connection);
				PreparedStatement categoryStatement = connection.prepareStatement(insertQueryCategory);
				categoryStatement.setString(1, categoryIdValue);
				categoryStatement.setString(2, categoryNameValue);
				categoryStatement.setString(3, ""); //Descrição
				categoryStatement.setInt(4, 1);
				categoryStatement.setString(5, categorySubTypeValue);
				categoryStatement.setInt(6, panel_position);
				categoryStatement.setInt(7, 1);
				categoryStatement.setInt(8, 0);
				categoryStatement.setInt(9, 1); //Active
				categoryStatement.setInt(10, 1);
				categoryStatement.setString(11, "");
				categoryStatement.setInt(12, 0);
				categoryStatement.setString(13, categoryNameValue);
				categoryStatement.setInt(14, 0);
				categoryStatement.setInt(15, 0);
				categoryStatement.setString(16, "");

				panel_position++;
				System.out.println(categoryStatement);
				categoryStatement.execute();
			}
		}
	}

}