package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

public class SheetAcess {
    private String filepath;
    private Integer rowIndex;
    private Integer totalLinhasInseridas;

    public void acessSheet() throws EncryptedDocumentException, IOException {
        rowIndex = null;
        totalLinhasInseridas = 0;
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        FileInputStream fileInputStream = new FileInputStream(filepath);
        Workbook Workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = Workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        if (result == JFileChooser.APPROVE_OPTION) {
            filepath = fileChooser.getSelectedFile().getAbsolutePath();
            JOptionPane.showMessageDialog(null, "Aquivo selecionado" + filepath);
        }
        for (rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            Cell codigo = row.getCell(0);
            Cell codigoBarra = row.getCell(1);
            Cell nome = row.getCell(2);
            Cell categoria = row.getCell(3);
            Cell descrição = row.getCell(4);
            Cell precoDeCompra = row.getCell(5);
            Cell precoDeVenda = row.getCell(6);
            Cell estoque = row.getCell(7);
            Cell origem = row.getCell(8);
            Cell tipo = row.getCell(9);
        }
    }
    //Quero criar uma forma que retorne os dados da planilha
}

