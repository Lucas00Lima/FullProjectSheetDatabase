package com.example;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;

public class Main {
    public static void main(String[] args) throws SQLException, EncryptedDocumentException, IOException {
        //Acessa o banco e a planilha
        Database database = new Database();
        database.connectionDatabase();
        SheetAcess sheetAcess = new SheetAcess();
        sheetAcess.acessSheet();

        
        String clientResponse = "2";//JOptionPane.showInputDialog("Produtos(1) ou Cliente(2)");
        
        if (clientResponse.equals("1")) {
        MethodoProduct product = new MethodoProduct();
        product.methodoProduct();
        QueryProduct queryProduct = new QueryProduct();
        queryProduct.query();
        QueryAddProduct queryAddProduct = new QueryAddProduct();
        queryAddProduct.queryAdd();
        int linhasInseridas = queryProduct.getLinhasInseridas();
        System.out.println("Affectd " + linhasInseridas);
        
        
        } else {
			MethodoClient methodoClient = new MethodoClient();
			methodoClient.methodoClient();
		}
    }
}