package com.example;

import java.io.FileInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.*;

public class SheetAcess {
    private String filepath;
    private String rowIndex;
    private Integer totalLinhasInseridas;

    public void acessSheet() {
        rowIndex = null;
        totalLinhasInseridas = 0;
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        FileInputStream fileInputStream = new FileInputStream(filepath);
        Workbook Workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = Workbook.getSheetAt(0);
        

        if (result == JFileChooser.APPROVE_OPTION) {
            filepath = fileChooser.getSelectedFile().getAbsolutePath();
            JOptionPane.showMessageDialog(null, "Aquivo selecionado" + filepath);
        }
        for (rowIndex = 1; rowIndex <= )

    }
}
