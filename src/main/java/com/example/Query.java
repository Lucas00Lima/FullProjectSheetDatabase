package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//"internal_code", "barcode", "name", "category_id", "description", "cost", "price",
//"current_stock", "type", "type2" };

public class Query {
	private Connection connection;
	public void query() throws SQLException, EncryptedDocumentException, IOException {
		Database data = new Database();
		SheetAcess sheetAcess = new SheetAcess();
		sheetAcess.acessSheet();
		try (Connection connection = data.connectionDatabase()) {
			String filePath = sheetAcess.getFilePath();
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			int rowIndex;
			for (rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Cell codeCell = row.getCell(1);
				Cell barcodeCell = row.getCell(2);
				Cell nameCell = row.getCell(3);
				Cell categoryCell = row.getCell(4);
				Cell descriptionCell = row.getCell(5);
				Cell costCell = row.getCell(6);
				Cell priceCell = row.getCell(7);
				Cell currentStockCell = row.getCell(8);
				Cell typeCell = row.getCell(9);
				Cell type2Cell = row.getCell(10);
			}
			System.out.println(filePath);
		}
	}
}