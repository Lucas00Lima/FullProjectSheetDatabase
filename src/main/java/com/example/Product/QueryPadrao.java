package com.example.Product;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.example.Connect.SheetAcess;
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
				DataFormatter dataFormatter = new DataFormatter();

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

					int codeValue = (int) codeCell.getNumericCellValue();
					String barcodeValue = dataFormatter.formatCellValue(barcodeCell);
					String nameValue = dataFormatter.formatCellValue(nameCell);
					String descriptionValue = dataFormatter.formatCellValue(descriptionCell);
					
					String typeValue = dataFormatter.formatCellValue(typeCell); //Origem
					String type2Value = dataFormatter.formatCellValue(type2Cell); //Tipo
					
					String costValue = dataFormatter.formatCellValue(costCell);
					String priceValue = dataFormatter.formatCellValue(priceCell);
					
					String ncmValue = dataFormatter.formatCellValue(ncmCell);
					String cfopValue = dataFormatter.formatCellValue(cfopCell);
					String cestValue = dataFormatter.formatCellValue(cestCell);
					String cstValue = dataFormatter.formatCellValue(cstCell);
					String icmsValue = dataFormatter.formatCellValue(icmsCell);
					String piscodeValue = dataFormatter.formatCellValue(piscodCell);
					String pisValue = dataFormatter.formatCellValue(pisCell);
					String cofinscodValue = dataFormatter.formatCellValue(cofinscodCell);
					String cofinsValue = dataFormatter.formatCellValue(cofinsCell);
					
					String currentStockValue = dataFormatter.formatCellValue(currentStockCell);

					PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
					preparedStatement.setInt(1, codeValue);
					preparedStatement.setString(2, barcodeValue);
					preparedStatement.setString(3, nameValue);
					preparedStatement.setString(4, descriptionValue);
					preparedStatement.setString(5, typeValue);
					preparedStatement.setString(6, type2Value);
					preparedStatement.setString(7, costValue);
					preparedStatement.setString(8, priceValue);
					
					preparedStatement.setString(9, ncmValue);
					preparedStatement.setString(10, cfopValue);
					preparedStatement.setString(11, cestValue);
					preparedStatement.setString(12, cstValue);
					preparedStatement.setString(13, icmsValue);
					preparedStatement.setString(14, piscodeValue);
					preparedStatement.setString(15, pisValue);
					preparedStatement.setString(16, cofinscodValue);
					preparedStatement.setString(17, cofinsValue);
					preparedStatement.setString(18, currentStockValue);
					System.out.println(insertQuery);
				}
			}
			catch (Exception e) {
				System.out.println("Erro Query Padrão");
			}
		}
	}
}
