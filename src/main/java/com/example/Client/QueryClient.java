package com.example.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.example.Connect.SheetAcess;
import com.ibm.icu.text.SimpleDateFormat;

public class QueryClient {
	private int linhasInseridas;

	public void queryClient(Connection connection, int tableName, String sheetAcess)
			throws SQLException, EncryptedDocumentException, IOException {
		if (tableName == 1) {
			MethodoClient methodo = new MethodoClient();
			String insertQuery = methodo.methodoClient(connection, tableName);
			List<String> defaultValues = methodo.getDefaultValues();
			try {
				String filePath = sheetAcess;
				FileInputStream fileInputStream = new FileInputStream(filePath);
				Workbook workbook = WorkbookFactory.create(fileInputStream);
				Sheet sheet = workbook.getSheetAt(0);
				DataFormatter dataFormatter = new DataFormatter();
				SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd.MM.yyyy\\yyyy\\yyyy\\yyyy");
				SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				int rowIndex;
				for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					Row row = sheet.getRow(rowIndex);
					Cell id = row.getCell(0);
					Cell name = row.getCell(1);
					Cell typeDoc = row.getCell(2);
					Cell numberDoc = row.getCell(3);
					Cell numberDoc1 = row.getCell(4);
					Cell cell_phone = row.getCell(5);
					Cell cell_phone2 = row.getCell(6);
					Cell gender = row.getCell(7);
					Cell email = row.getCell(8);
					Cell aniver = row.getCell(9);
					Cell registro = row.getCell(10);

					String idValue = dataFormatter.formatCellValue(id);
					int idDouble = Integer.parseInt(idValue);
					String nameValue = dataFormatter.formatCellValue(name);
					String type1Value = dataFormatter.formatCellValue(typeDoc);
					String numberDocValue = dataFormatter.formatCellValue(numberDoc);
					String numberDoc1Value = dataFormatter.formatCellValue(numberDoc1);
					String cell_phoneValue = dataFormatter.formatCellValue(cell_phone);
					String cell_phone2Value = dataFormatter.formatCellValue(cell_phone2);

					int tamanhophone = cell_phoneValue.length();
					if (tamanhophone < 9 || tamanhophone == 9) {
						cell_phoneValue = "";
					}
					int tamanhophone2 = cell_phone2Value.length();
					if (tamanhophone2 < 9 || tamanhophone2 == 9) {
						cell_phone2Value = "";
					}

					String genderValue = dataFormatter.formatCellValue(gender);

					if (genderValue.equals("F")) {
						genderValue = "2";
					} else {
						genderValue = "1";
					}

					String emailValue = dataFormatter.formatCellValue(email);
					String birthdayValue = dataFormatter.formatCellValue(aniver);
					String registerValue = dataFormatter.formatCellValue(registro);

					String yearValue = birthdayValue.substring(birthdayValue.lastIndexOf('\\') + 1);
					int year = Integer.parseInt(yearValue);
					Date birthdayDate = new Date(year - 1900, 0, 2);
					String formattedBirthday = outputDateFormat.format(birthdayDate);

					Date registerDate = new Date(year - 1900, 0, 2);
					String formattedRegister = outputDateFormat.format(registerDate);
					Integer typeResult;

					if (type1Value.equals("CPF")) {
						typeResult = 1;
					} else {
						typeResult = 2;
					}
					PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
					preparedStatement.setInt(1, idDouble); // Id
					preparedStatement.setString(2, nameValue); // Name
					preparedStatement.setInt(3, typeResult); // type1 CPF ou CNPJ
					if (type1Value.equals("CPF")) {
						preparedStatement.setString(4, numberDocValue); // CPF
						preparedStatement.setString(5, "");
						preparedStatement.setString(6, "");
					} else {
						preparedStatement.setString(4, ""); // CNPJ
						preparedStatement.setString(5, numberDocValue); // IE
						preparedStatement.setString(6, numberDoc1Value); // IE

					}
					preparedStatement.setString(7, cell_phoneValue); // Celular
					preparedStatement.setString(8, cell_phone2Value); // Celular2
					preparedStatement.setString(9, genderValue); // Masculino ou Feminino
					preparedStatement.setString(10, emailValue); // E-mail
					preparedStatement.setString(11, formattedBirthday); // Data de nascimento
					preparedStatement.setString(12, formattedRegister); // Data de Registro
					for (int j = 0; j < defaultValues.size(); j++) {
						String value = defaultValues.get(j);
						if (value.isEmpty()) {
							preparedStatement.setInt(j + 13, 0);
						} else {
							preparedStatement.setString(j + 13, value);
						}
					}
					preparedStatement.execute();
					linhasInseridas = getLinhasInseridas() + 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public int getLinhasInseridas() {
		return linhasInseridas;
	}

}
