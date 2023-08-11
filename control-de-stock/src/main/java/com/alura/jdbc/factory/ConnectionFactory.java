package com.alura.jdbc.factory;

import java.sql.Connection;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ConnectionFactory {
	
	private DataSource datasource;
	
	public ConnectionFactory(){
		var pooledDAtaSource = new ComboPooledDataSource();
		pooledDAtaSource.setJdbcUrl("jdbc:mysql://localhost/control_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDAtaSource.setUser("root");
		pooledDAtaSource.setPassword("");
		pooledDAtaSource.setMaxPoolSize(10); // conexiones simultaneas permitidas 
		
		this.datasource = pooledDAtaSource;
	}
	public Connection recuperaConexion(){
		try {
			return this.datasource.getConnection();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
