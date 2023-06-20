package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
		MethodoProduct methodo = new MethodoProduct();
		String insertQuery = methodo.methodoProduct();
		List<String> defaultValues = methodo.getDefaultValues();
		int totalColumnDataBase = methodo.getTotalColumnInDataBase();
		try (Connection connection = data.connectionDatabase()) {
			String filePath = sheetAcess.getFilePath();
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormat = new DataFormatter();
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

				String codeValue = dataFormat.formatCellValue(codeCell);
				String barcodeValue = dataFormat.formatCellValue(barcodeCell);
				String nameValue = dataFormat.formatCellValue(nameCell);
				String categoryValue = dataFormat.formatCellValue(categoryCell);
				String descriptionValue = dataFormat.formatCellValue(descriptionCell);
				String costValue = dataFormat.formatCellValue(costCell);
				String priceValue = dataFormat.formatCellValue(priceCell);
				String currentStockValue = dataFormat.formatCellValue(currentStockCell);
				String typeValue = dataFormat.formatCellValue(typeCell);
				String type2Value = dataFormat.formatCellValue(type2Cell);

				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString());
				preparedStatement.setString(1, codeValue);
				preparedStatement.setString(2, barcodeValue);
				preparedStatement.setString(3, nameValue);
				preparedStatement.setString(4, categoryValue);
				preparedStatement.setString(5, descriptionValue);
				preparedStatement.setString(6, costValue);
				preparedStatement.setString(7, priceValue);
				preparedStatement.setString(8, currentStockValue);
				preparedStatement.setString(9, typeValue);
				preparedStatement.setString(10, type2Value);

				for (int j = 0; j < defaultValues.size(); j++) {
					String value = defaultValues.get(j);
					if (value.isEmpty()) {
						preparedStatement.setString(j + totalColumnDataBase, "");
					} else {
						preparedStatement.setInt(j + totalColumnDataBase, 0);
					}
				}
				preparedStatement.execute();
			}
			System.out.println(insertQuery.toString());
		}
	}
}