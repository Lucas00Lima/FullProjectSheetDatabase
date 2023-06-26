package com.example;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;

public class SheetAcess {
	private String filePath;

	public void acessSheet() throws EncryptedDocumentException, IOException {
//		filePath = "C:\\Users\\lukin\\OneDrive\\Área de Trabalho\\Planilha\\ClientesTeste.xlsx";
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getAbsolutePath();
			JOptionPane.showMessageDialog(null, "Aquivo selecionado " + getFilePath());
		}
	}
	public String getFilePath() {
		return filePath;
	}

}
