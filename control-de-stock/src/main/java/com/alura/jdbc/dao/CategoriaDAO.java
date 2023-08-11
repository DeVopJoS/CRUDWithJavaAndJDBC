package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.mysql.cj.protocol.Resultset;

public class CategoriaDAO {
	Connection c;
	
	public CategoriaDAO(Connection c) {
		this.c = c;
	}
	
	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<>();
		
		try {
			PreparedStatement statement = c.prepareStatement(
					"SELECT ID, NOMBRE FROM CATEGORIA");
			
			try(statement){
				ResultSet resultset = statement.executeQuery();
				
				try(resultset){
					while(resultset.next()) {
						var categoria = new Categoria(resultset.getInt("ID"), resultset.getString("NOMBRE"));
						
						resultado.add(categoria);
					}
				};
			}
			
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		
		return resultado;
	}

	public List<Categoria> listarConproductos() {
		List<Categoria> resultado = new ArrayList<>();
		
		try {
			String querySelect = "SELECT C.ID, C.NOMBRE, P.ID , P.NOMBRE, P.CANTIDAD FROM CATEGORIA C "
					+ "INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID";
			System.out.println(querySelect);
			final PreparedStatement statement = c.prepareStatement(querySelect);
			
			
			try(statement){
				final ResultSet resultset = statement.executeQuery();
				
				try(resultset){
					while(resultset.next()) {
						Integer categoria_id = resultset.getInt("C.ID");
						String categoriaNombre = resultset.getString("C.NOMBRE");
						var categoria = resultado
								.stream()
								.filter(cat -> cat.getId().equals(categoria_id))
								.findAny().orElseGet(() -> {
									Categoria cat = new Categoria(categoria_id, categoriaNombre);
									resultado.add(cat);
									
									return cat;
								});
						Producto producto = new Producto(
								resultset.getInt("P.ID"),
								resultset.getString("P.NOMBRE"),
								resultset.getInt("P.CANTIDAD"));
						
						categoria.agregar(producto);
					}
				};
			}
			
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		
		return resultado;
	}
}
