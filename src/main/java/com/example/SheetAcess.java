package com.example;

import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SheetAcess {
	private String filePath;
    public void acessSheet() throws EncryptedDocumentException, IOException {
    	String filePath = "C:\\Users\\lukin\\OneDrive\\Área de Trabalho\\Planilha\\usaressa.xlsx";
    }

	/*
	 * JFileChooser fileChooser = new JFileChooser(); int result =
	 * fileChooser.showOpenDialog(null); if (result == JFileChooser.APPROVE_OPTION)
	 * { filePath = fileChooser.getSelectedFile().getAbsolutePath();
	 * JOptionPane.showMessageDialog(null, "Aquivo selecionado " + getFilePath()); }
	 * FileInputStream fileInputStream = new FileInputStream(getFilePath());
	 * Workbook workbook = WorkbookFactory.create(fileInputStream); Sheet sheet =
	 * workbook.getSheetAt(0); }
	 */
	public String getFilePath() {
		return filePath;
	}
}

