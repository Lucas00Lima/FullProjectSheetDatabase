package com.example.Product;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.example.Product.Category.QueryCategory;

import java_cup.internal_error;
//print_production = 1 para imprimir a comanda de produção

/*Documentação

	codeCell = internal_code = Código Interno
	barcodeCell = barcode = Código de Barra
	nameCell = name = Nome
	descriptionCell = description = descrição
	typeCell = type = Origem
	type2Cell = type2 = Tipo (Produto, combo e etc)
	costCell = cost = valor de compra
	priceCell = price = valor de venda
	currentStockCel = current_stock = Estoque atual
	ncmCell = ncm = NCM
	cfopCell = cfop = CFOP
	cestCell = cest = tax4_code
	cstCell = cst = tax1_code
	icmsCell = icms = tax1 * 10000
	piscodCell = pisCo = tax2_code
	pisCell = pis = tax2
	cofinscodCell = cofinscod = tax3_code
	cofinsCell = cofins = tax3

*/

public class QueryPadrao {
	private int linhasInseridas;
	public void queryPadrao(Connection connection, String tableName, String sheetAcess) {
		try {
			MethodoProduct methodo = new MethodoProduct();
			String insertQuery = methodo.methodoProduct(connection, tableName);
			String filePath = sheetAcess;
			FileInputStream fileInput = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fileInput);
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			List<String> defaultValues = methodo.getDefaultValues();
			QueryCategory queryCategory = new QueryCategory();

			// Pegas as celulas de cada coluna
			String codeValue = "";
			String previousCodeValue = "";
			int internal_codeAdd = 0;
			int rowIndex;
			int counter = 0;
			for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				Cell codeCell = row.getCell(0);
				Cell barcodeCell = row.getCell(1);
				Cell nameCell = row.getCell(2);
				Cell descriptionCell = row.getCell(3);
				Cell typeCell = row.getCell(4); // Origem
				Cell type2Cell = row.getCell(5); // Tipo
				Cell costCell = row.getCell(6);
				Cell priceCell = row.getCell(7);
				Cell ncmCell = row.getCell(8);
				Cell cfopCell = row.getCell(9);
				Cell cestCell = row.getCell(10);
				Cell cstCell = row.getCell(11);
				Cell icmsCell = row.getCell(12);
				Cell piscodCell = row.getCell(13);
				Cell pisCell = row.getCell(14);
				Cell cofinscodCell = row.getCell(15);
				Cell cofinsCell = row.getCell(16);
				Cell currentStockCell = row.getCell(17);

				if (nameCell.getCellType() == CellType.BLANK && codeCell.getCellType() == CellType.BLANK) {
					continue;
				}
				String barcodeValue = dataFormatter.formatCellValue(barcodeCell);
				String nameValue = dataFormatter.formatCellValue(nameCell);
				String descriptionValue = dataFormatter.formatCellValue(descriptionCell);

				String typeValueString = dataFormatter.formatCellValue(typeCell); // Origem
				int typeValue;
				if (typeValueString == "") {
					typeValue = 1;
				} else {
					typeValue = 2;
				}

				String type2ValueString = dataFormatter.formatCellValue(type2Cell); // Tipo
				int type2Value = 0;
				if (type2ValueString == "") {
					type2Value = 0;
				}
				if (type2ValueString == "1") {
					type2Value = 1;
				}
				if (type2ValueString == "2") {
					type2Value = 2;
				}

				String costValueString = dataFormatter.formatCellValue(costCell);
				int costValue = 0;
				if (costValueString.equals("")) {
					costValue = 0;
				} else {
					costValue = Integer.parseInt(costValueString) * 100;
				}

				String priceValueString = dataFormatter.formatCellValue(priceCell);
				int priceValue = 0;
				if (priceValueString.equals("")) {
					priceValue = 0;
				} else {
					priceValue = Integer.parseInt(priceValueString);
				}

				String ncmValue = dataFormatter.formatCellValue(ncmCell);
				String cfopValue = dataFormatter.formatCellValue(cfopCell);
				String cestValue = dataFormatter.formatCellValue(cestCell);
				String cstValue = dataFormatter.formatCellValue(cstCell);
				String icmsValueString = dataFormatter.formatCellValue(icmsCell);
				int icmsValue = 0;
				if (icmsValueString.equals("")) {
					icmsValue = 10 * 1000;
				}

				String piscodeValue = dataFormatter.formatCellValue(piscodCell);
				String pisValueString = dataFormatter.formatCellValue(pisCell);
				int pisValue;
				if (pisValueString.equals("")) {
					pisValue = 0;
				} else {
					pisValue = Integer.parseInt(pisValueString);
				}

				String cofinscodValue = dataFormatter.formatCellValue(cofinscodCell);
				String cofinsValueString = dataFormatter.formatCellValue(cofinsCell);
				int cofinsValue;
				if (cofinsValueString.equals("")) {
					cofinsValue = 0;
				} else {
					cofinsValue = Integer.parseInt(cofinsValueString);
				}

				String currentStockValueString = dataFormatter.formatCellValue(currentStockCell);
				int currentStockValue;
				if (currentStockValueString.equals("")) {
					currentStockValue = 0;
				} else {
					currentStockValue = Integer.parseInt(currentStockValueString);
				}

				if (codeCell != null && codeCell.getCellType() != CellType.BLANK) {
					codeValue = dataFormatter.formatCellValue(codeCell);
					if (!checkIfCodeExists(connection, codeValue)) {
						queryCategory.queryCategory(connection, sheetAcess);
					}
				} else {
					int internalcode = Integer.parseInt(codeValue) * 100;
					if (!codeValue.equals(previousCodeValue)) {
						internal_codeAdd = 0;
						counter = 0;
					}
					PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
					preparedStatement.setString(1, codeValue); // category_id
					preparedStatement.setString(2, barcodeValue);
					preparedStatement.setString(3, nameValue);
					preparedStatement.setString(4, descriptionValue);
					preparedStatement.setInt(5, typeValue);
					preparedStatement.setInt(6, type2Value);
					preparedStatement.setInt(7, costValue);
					preparedStatement.setInt(8, priceValue);

					preparedStatement.setString(9, ncmValue);
					preparedStatement.setString(10, cfopValue);
					preparedStatement.setString(11, cestValue);
					preparedStatement.setString(12, cstValue);
					preparedStatement.setInt(13, icmsValue);
					preparedStatement.setString(14, piscodeValue);
					preparedStatement.setInt(15, pisValue);
					preparedStatement.setString(16, cofinscodValue);
					preparedStatement.setInt(17, cofinsValue);
					preparedStatement.setInt(18, currentStockValue);
					preparedStatement.setInt(19, internalcode + internal_codeAdd); // Internal_Code
					for (int j = 0; j < defaultValues.size(); j++) {
						String value = defaultValues.get(j);
						if (value.isEmpty()) {
							preparedStatement.setInt(j + 20, 0);
						} else {
							preparedStatement.setString(j + 20, value);
						}
					}
					preparedStatement.execute();
					counter++;
					internal_codeAdd++;
					previousCodeValue = codeValue;
					linhasInseridas = getLinhasInseridas() + 1;
				}
			}
			PreparedStatement vincularCodigo = connection.prepareStatement(insertQuery);
			int id = queryCategory.getId();
			vincularCodigo.setInt(1, id); // category_id
			vincularCodigo.setString(2, "");
			vincularCodigo.setString(3, "Vincular codigo PDV");
			vincularCodigo.setString(4, "");
			vincularCodigo.setInt(5, 1);
			vincularCodigo.setInt(6, 0);
			vincularCodigo.setInt(7, 0);
			vincularCodigo.setInt(8, 0);
			vincularCodigo.setString(9, "");
			vincularCodigo.setString(10, "");
			vincularCodigo.setString(11, "");
			vincularCodigo.setString(12, "");
			vincularCodigo.setInt(13, 0);
			vincularCodigo.setString(14, "");
			vincularCodigo.setInt(15, 0);
			vincularCodigo.setString(16, "");
			vincularCodigo.setInt(17, 0);
			vincularCodigo.setInt(18, 0);
			vincularCodigo.setInt(19, 999);
			for (int j = 0; j < defaultValues.size(); j++) {
				String value = defaultValues.get(j);
				if (value.isEmpty()) {
					vincularCodigo.setInt(j + 20, 0);
				} else {
					vincularCodigo.setString(j + 20, value);
				}
			}
			vincularCodigo.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkIfCodeExists(Connection connection, String codeValue) throws Exception {
		String tableNameCategory = "category";
		String query = "SELECT COUNT(*) FROM " + tableNameCategory + " WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, codeValue);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				resultSet.next();
				int count = resultSet.getInt(1);
				return count > 0;
			}
		}
	}

	public int inserIdProduct(Connection connection) {
		String product = "product";
		String query = "SELECT COUNT(*) AS record_count FROM ";
		int totalProduct = -1;
		try {
			PreparedStatement queryInserId = connection.prepareStatement(query + product);
			ResultSet resultSet = queryInserId.executeQuery();
			if (resultSet.next()) {
				totalProduct = resultSet.getInt("record_count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalProduct;
	}

	public int inserIdCategory(Connection connection) {
		String category = "category";
		String query = "SELECT COUNT(*) AS record_count FROM ";
		int totalCategory = -1;
		try {
			PreparedStatement queryInserId = connection.prepareStatement(query + category);
			ResultSet resultSet = queryInserId.executeQuery();
			if (resultSet.next()) {
				totalCategory = resultSet.getInt("record_count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCategory;
	}

	public int getLinhasInseridas() {
		return linhasInseridas;
	}
}
