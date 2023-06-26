package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

import com.ibm.icu.text.SimpleDateFormat;

public class QueryClient {
	private int linhasInseridas;

	public void queryClient(Connection connection, String table) throws SQLException, EncryptedDocumentException, IOException {
		SheetAcess sheetAcess = new SheetAcess();
		sheetAcess.acessSheet();
		MethodoClient methodo = new MethodoClient();
		String insertQuery = methodo.methodoClient(connection, table);
		List<String> defaultValues = methodo.getDefaultValues();
		try {
			String filePath = sheetAcess.getFilePath();
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
				Cell typeDoc1 = row.getCell(4);
				Cell numberDoc1 = row.getCell(5);
				Cell cell_phone = row.getCell(6);
				Cell cell_phone2 = row.getCell(7);
				Cell gender = row.getCell(8);
				Cell email = row.getCell(9);
				Cell aniver = row.getCell(10);
				Cell registro = row.getCell(11);

				String idValue = dataFormatter.formatCellValue(id);
				int idDouble = Integer.parseInt(idValue);
				String nameValue = dataFormatter.formatCellValue(name);
				String type1Value = dataFormatter.formatCellValue(typeDoc);
				String numberDocValue = dataFormatter.formatCellValue(numberDoc);
				String typeDoc1Value = dataFormatter.formatCellValue(typeDoc1);
				String numberDoc1Value = dataFormatter.formatCellValue(numberDoc1);
				String cell_phoneValue = dataFormatter.formatCellValue(cell_phone);
				String cell_phone2Value = dataFormatter.formatCellValue(cell_phone2);
				String genderValue = dataFormatter.formatCellValue(gender);
				if (genderValue.equals("F") ) {
					genderValue = "2";
				}else {
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
				preparedStatement.setInt(1, idDouble); //Id
				preparedStatement.setString(2, nameValue); //Name
				preparedStatement.setInt(3, typeResult); //type1 CPF ou CNPJ
				if (type1Value.equals("CPF")) {
					preparedStatement.setString(4, numberDocValue); //CPF
					preparedStatement.setString(5, "");
				}else {
					preparedStatement.setString(4, "");
					preparedStatement.setString(5, numberDocValue); //CNPJ
				}
				preparedStatement.setString(6, numberDoc1Value); //IE
				preparedStatement.setString(7, cell_phoneValue); //Celular
				preparedStatement.setString(8, cell_phone2Value); //Celular2
				preparedStatement.setString(9, genderValue); // M & F
				preparedStatement.setString(10, emailValue); // Email
				preparedStatement.setString(11, formattedBirthday); // Data de nascimento
				preparedStatement.setString(12, formattedRegister); // Registro
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

	public int getLinhasInseridas() {
		return linhasInseridas;
	}

}
