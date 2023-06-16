package com.example;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;

public class Main {
    public static void main(String[] args) throws SQLException, EncryptedDocumentException, IOException {
        // Sistema acessa o banco de dados e pegas as colunas setando cada coluna
        Database database = new Database();
        database.connectionDatabase();
        SheetAcess sheetAcess = new SheetAcess();
        sheetAcess.acessSheet();
    }
}