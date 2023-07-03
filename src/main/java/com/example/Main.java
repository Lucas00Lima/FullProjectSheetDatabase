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
import com.example.Product.QueryAddPadraoProduct;
import com.example.Product.QueryAddProduct;
import com.example.Product.QueryPadrao;
import com.example.Product.QueryProduct;
import com.example.Product.Category.MethodoCategory;
import com.example.QueryWaiter.QueryWaiter;

public class Main {
	public static void main(String[] args) throws SQLException, EncryptedDocumentException, IOException {
		Database database = new Database();
		Connection connection = database.connectionDatabase();
		int table = database.getTable();
		int tableProduct = database.getTableProduct();
		String tableName = database.getTableName();
		SheetAcess sheet = new SheetAcess();
		String sheetAcess = sheet.acessSheet();

		if (table == 0) {
			System.out.println("Tabela Product");
			MethodoProduct product = new MethodoProduct();
			product.methodoProduct(connection, tableName);
			if (tableProduct == 0) {
				System.out.println("Query Padrão");
				MethodoCategory methodoCategory = new MethodoCategory();
				methodoCategory.methodoCategory(connection);
				QueryPadrao queryPadrao = new QueryPadrao();
				queryPadrao.queryPadrao(connection, tableName, sheetAcess);
				QueryAddPadraoProduct queryAddPadraoProduct = new QueryAddPadraoProduct();
				queryAddPadraoProduct.queryAddPadraoProduct(connection, tableName);


			} else {
				System.out.println("Query Especializada");
				QueryProduct queryProduct = new QueryProduct();
				queryProduct.query(connection, tableName);
				QueryAddProduct queryAddProduct = new QueryAddProduct();
				queryAddProduct.queryAdd(connection, tableName);
				QueryAddPadraoProduct query = new QueryAddPadraoProduct();
				query.queryAddPadraoProduct(connection,tableName);
				System.out.println("Affectd " + queryProduct.getLinhasInseridas());
			}
		} else {
//			System.out.println("Tabela Client");
//			System.out.println("Rodando a Query de Client");
//			MethodoClient methodoClient = new MethodoClient();
//			methodoClient.methodoClient(connection, tableName, sheetAcess);
//			QueryClient queryClient = new QueryClient();
//			queryClient.queryClient(connection, tableName, sheetAcess);
//			QueryAddClient queryAddClient = new QueryAddClient();
//			queryAddClient.queryAddClient(connection, tableName, sheetAcess);
//			connection.close();
//			int linhasInseridas = queryClient.getLinhasInseridas();
//			System.out.println("Affected = " + linhasInseridas);
		}
		QueryWaiter queryWaiter = new QueryWaiter();
		queryWaiter.queryWaiter(connection);
		System.out.println("Query executada");
	}
}