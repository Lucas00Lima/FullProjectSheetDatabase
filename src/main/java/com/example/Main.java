package com.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Sistema acessa o banco de dados e pegas as colunas setando cada coluna
        Database database = new Database();
        database.connectionDatabase();
        SheetAcess sheetAcess = new SheetAcess();
        sheetAcess.acessSheet();
    }
}