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

public class Query {
	private int linhasInseridas;

	public void query() throws SQLException, EncryptedDocumentException, IOException {
		Database data = new Database();
		SheetAcess sheetAcess = new SheetAcess();
		sheetAcess.acessSheet();
		MethodoProduct methodo = new MethodoProduct();
		String insertQuery = methodo.methodoProduct();
		List<String> defaultValues = methodo.getDefaultValues();
		try (Connection connection = data.connectionDatabase()) {
			String filePath = sheetAcess.getFilePath();
			FileInputStream fileInputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormat = new DataFormatter();
			int rowIndex;
			for (rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Cell barcodeCell = row.getCell(0);
				Cell nameCell = row.getCell(1);
				Cell costCell = row.getCell(2);
				Cell priceCell = row.getCell(3);
				Cell currentStockCell = row.getCell(4);

				String barcodeValue = dataFormat.formatCellValue(barcodeCell);
				String nameValue = dataFormat.formatCellValue(nameCell);
				double costValue = costCell.getNumericCellValue();
				double priceValue = priceCell.getNumericCellValue();
				double currentStockValue = currentStockCell.getNumericCellValue();

				StringBuilder insertQueryBuilder = new StringBuilder(insertQuery);
				String finalInsertQuery = insertQueryBuilder.toString();
				PreparedStatement preparedStatement2 = connection.prepareStatement(finalInsertQuery);
				preparedStatement2.setString(1, barcodeValue);
				preparedStatement2.setString(2, nameValue);
				preparedStatement2.setDouble(3, costValue);
				preparedStatement2.setDouble(4, priceValue);
				preparedStatement2.setDouble(5, currentStockValue);

				for (int j = 0; j < defaultValues.size(); j++) {
					String value = defaultValues.get(j);
					if (value.isEmpty()) {
						preparedStatement2.setInt(j + 6, 0);
					} else {
						preparedStatement2.setString(j + 6, value);
				}
				}
				preparedStatement2.execute();
				linhasInseridas++;
			}
		}
	}

	public int getLinhasInseridas() {
		return linhasInseridas;
	}
}