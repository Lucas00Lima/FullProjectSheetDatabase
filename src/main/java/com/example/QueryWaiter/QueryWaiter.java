package com.example.QueryWaiter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class QueryWaiter {
	public void queryWaiter(Connection connection) throws SQLException {
		String waiterDevices =  JOptionPane.showInputDialog("QUANTOS DISPOSITIVOS DESEJA HABILITAR ?");
		PreparedStatement waiterStatement = connection.prepareStatement("UPDATE app_config SET waiter_devices = " + waiterDevices);
		waiterStatement.execute();
	}
}
