package com.example.Product;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
	public void queryPadrao(Connection connection, int tableName, String sheetAcess) {
		if (tableName == 0) {
			String table = "PRODUCT";
			try {
				MethodoProduct methodo = new MethodoProduct();
				String insertQuery = methodo.methodoProduct(connection, tableName);
				String filePath = sheetAcess;
				FileInputStream fileInput = new FileInputStream(filePath);
				Workbook workbook = WorkbookFactory.create(fileInput);
				Sheet sheet = workbook.getSheetAt(0);
				DataFormatter dataFormatter = new DataFormatter();
				List<String> defaultValues = methodo.getDefaultValues();
				int linhasInseridas = 0;

				//Pegas as celulas de cada coluna
				int rowIndex;
				for (rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
					Row row = sheet.getRow(rowIndex);
					Cell codeCell = row.getCell(0);
					Cell barcodeCell = row.getCell(1);
					Cell nameCell = row.getCell(2);
					Cell descriptionCell = row.getCell(3);
					Cell typeCell = row.getCell(4); //Origem
					Cell type2Cell = row.getCell(5); //Tipo
					Cell costCell = row.getCell(6);
					Cell priceCell = row.getCell(7);
					Cell currentStockCell = row.getCell(8);
					Cell ncmCell = row.getCell(9);
					Cell cfopCell = row.getCell(10);
					Cell cestCell = row.getCell(11);
					Cell cstCell = row.getCell(12);
					Cell icmsCell = row.getCell(13);
					Cell piscodCell = row.getCell(14);
					Cell pisCell = row.getCell(15);
					Cell cofinscodCell = row.getCell(16);
					Cell cofinsCell = row.getCell(17);

					String codeValue = dataFormatter.formatCellValue(codeCell);
					String barcodeValue = dataFormatter.formatCellValue(barcodeCell);
					String nameValue = dataFormatter.formatCellValue(nameCell);
					String descriptionValue = dataFormatter.formatCellValue(descriptionCell);

					String typeValueString = dataFormatter.formatCellValue(typeCell); //Origem
					int typeValue;
					if (typeValueString == "") {
						typeValue = 2;
					} else {
						typeValue = 1;
					}
					String type2ValueString = dataFormatter.formatCellValue(type2Cell); //Tipo
					int type2Value = 0;
					if (type2ValueString == "") {
						type2Value = 1;
					} if (type2ValueString == "2") {
						type2Value = 2;
					}
					String costValueString = dataFormatter.formatCellValue(costCell);
					int costValue = 0;// = Integer.parseInt(costValueString);
					if (costValueString.equals("")) {
						costValue = 10;
					}

					String priceValueString = dataFormatter.formatCellValue(priceCell);
					int priceValue = 0;
					if (priceValueString.equals("")) {
						priceValue = 5;
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
					int pisValue = 0;
					if (pisValueString.equals("")) {
						pisValue = 10;
					}

					String cofinscodValue = dataFormatter.formatCellValue(cofinscodCell);

					String cofinsValueString = dataFormatter.formatCellValue(cofinsCell);
					int cofinsValue = 0;
					if (cofinsValueString.equals("")) {
						cofinsValue = 5;
					}

					String currentStockValueString = dataFormatter.formatCellValue(currentStockCell);
					int currentStockValue = 0;
					if (currentStockValueString.equals("")) {
						currentStockValue = 50;
					}
					
					if (nameValue.equals("")) {
						continue;
					} else {
					PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
					preparedStatement.setString(1, codeValue);
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
					for (int j = 0; j < defaultValues.size(); j++) {
						String value = defaultValues.get(j);
						if (value.isEmpty()) {
							preparedStatement.setInt(j + 19, 1);
						} else {
							preparedStatement.setString(j + 19, value);
						}
					}
					preparedStatement.execute();
					linhasInseridas++;
					}
				}
				System.out.println(linhasInseridas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
