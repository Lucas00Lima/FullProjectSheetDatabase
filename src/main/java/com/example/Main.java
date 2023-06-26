package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;

public class Main {
	public static void main(String[] args) throws SQLException, EncryptedDocumentException, IOException {
		// Acessa o banco e a planilha
		Database database = new Database();
		Connection connection = database.connectionDatabase();
		String table = database.getTable();

		String clientResponse = JOptionPane.showInputDialog("Produtos(1) ou Cliente(2)");

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
			methodoClient.methodoClient(connection, table);
			QueryClient queryClient = new QueryClient();
			queryClient.queryClient(connection, table);
			QueryAddClient queryAddClient = new QueryAddClient();
			queryAddClient.queryAddClient(connection, table);
			connection.close();
			int linhasInseridas = queryClient.getLinhasInseridas();
			System.out.println("Affected = " + linhasInseridas);
		}
	}
}