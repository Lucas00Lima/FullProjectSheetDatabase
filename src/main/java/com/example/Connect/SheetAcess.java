package com.example.Connect;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;

public class SheetAcess {
	private String filePath;

	public String acessSheet() throws EncryptedDocumentException, IOException {
		return filePath = "C:\\Users\\lukin\\OneDrive\\Área de Trabalho\\Planilha\\produtos.xlsx";
//		JFileChooser fileChooser = new JFileChooser();
//		int result = fileChooser.showOpenDialog(null);
//		if (result == JFileChooser.APPROVE_OPTION) {
//			filePath = fileChooser.getSelectedFile().getAbsolutePath();
//			JOptionPane.showMessageDialog(null, "Aquivo selecionado " + getFilePath());
//		}
//		return filePath;
	}
	public String getFilePath() {
		return filePath;
	}

}
