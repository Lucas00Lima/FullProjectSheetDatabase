package com.example.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.example.Connect.Database;
import com.example.Connect.SheetAcess;

public class QueryProduct {
	private int linhasInseridas;

	public void query(Connection connection, String tableName)
			throws SQLException, EncryptedDocumentException, IOException {
			SheetAcess sheetAcess = new SheetAcess();
			MethodoProduct methodo = new MethodoProduct();
			String insertQuery = methodo.methodoProduct(connection, tableName);
			List<String> defaultValues = methodo.getDefaultValues();

			try {
				String filePath = sheetAcess.getFilePath();
				FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = WorkbookFactory.create(fileInputStream);
				Sheet sheet = workbook.getSheetAt(0);
				DataFormatter dataFormat = new DataFormatter();
				int rowIndex;
				Set<String> nomeLido = new HashSet<>();

				for (rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
					Row row = sheet.getRow(rowIndex);
					Cell barcodeCell = row.getCell(0);
					Cell nameCell = row.getCell(1);
					Cell costCell = row.getCell(2);
					Cell priceCell = row.getCell(3);
					Cell currentStockCell = row.getCell(4);

					String nameValue = dataFormat.formatCellValue(nameCell);
					if (nomeLido.contains(nameValue)) {
						continue;
					}
					nomeLido.add(nameValue);

					String barcodeValue = dataFormat.formatCellValue(barcodeCell);
					double costValue = costCell.getNumericCellValue();
					double priceValue = priceCell.getNumericCellValue();
					double currentStockValue = currentStockCell.getNumericCellValue();
					if (currentStockValue < 0 || currentStockValue == 0) {
						continue;
					}
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
			} catch (Exception e) {
				System.out.println("ERRO NA QUERYPRODUCT");
			}
		}

	public int getLinhasInseridas() {
		return linhasInseridas;
	}
}