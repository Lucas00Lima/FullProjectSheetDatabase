package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;

import com.example.Client.MethodoClient;
import com.example.Client.QueryAddClient;
import com.example.Client.QueryClient;
import com.example.Connect.Database;
import com.example.Connect.SheetAcess;
import com.example.Product.MethodoProduct;
import com.example.Product.QueryAddProduct;
import com.example.Product.QueryPadrao;
import com.example.Product.QueryProduct;

public class Main {
	public static void main(String[] args) throws SQLException, EncryptedDocumentException, IOException {
		// Acessa o banco e a planilha
		Database database = new Database();
		Connection connection = database.connectionDatabase();
		int tableName = database.getTableName();
		SheetAcess sheet = new SheetAcess();
		String sheetAcess = sheet.acessSheet();

		if (tableName == 0) {
			System.out.println("Rodando a Query de Product");
			MethodoProduct product = new MethodoProduct();
			product.methodoProduct(connection, tableName);
//			QueryPadrao queryPadrao = new QueryPadrao();
//			queryPadrao.queryPadrao(connection, tableName);
			QueryProduct queryProduct = new QueryProduct();
			queryProduct.query(connection, tableName);
			QueryAddProduct queryAddProduct = new QueryAddProduct();
			queryAddProduct.queryAdd(connection, tableName);
			int linhasInseridas = queryProduct.getLinhasInseridas();
			System.out.println("Affectd " + linhasInseridas);

		} else {
			System.out.println("Rodando a Query de Client");
			MethodoClient methodoClient = new MethodoClient();
			methodoClient.methodoClient(connection, tableName);
			QueryClient queryClient = new QueryClient();
			queryClient.queryClient(connection, tableName, sheetAcess);
			QueryAddClient queryAddClient = new QueryAddClient();
			queryAddClient.queryAddClient(connection, tableName, sheetAcess);
			connection.close();
			int linhasInseridas = queryClient.getLinhasInseridas();
			System.out.println("Affected = " + linhasInseridas);
		}
	}
}