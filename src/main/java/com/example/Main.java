package com.example;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;

public class Main {
    public static void main(String[] args) throws SQLException, EncryptedDocumentException, IOException {
        //Acessa o banco e a planilha
        Database database = new Database();
        database.connectionDatabase();
        SheetAcess sheetAcess = new SheetAcess();
        sheetAcess.acessSheet();
        
        MethodoProduct product = new MethodoProduct();
        String resultado = product.methodoProduct();
        System.out.println(resultado);
    }
}