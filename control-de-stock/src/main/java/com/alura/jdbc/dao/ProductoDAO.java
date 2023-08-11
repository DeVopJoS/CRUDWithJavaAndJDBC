package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	final Connection c;
	
	public ProductoDAO(Connection con) {
		this.c = con;
	}
	
	public void guardar(Producto producto) {
		try(c){
			final PreparedStatement statement = c.prepareStatement("INSERT INTO producto(nombre, descripcion, cantidad, categoria_id)"
					+ "VALUES(? , ? ,?, ?)", Statement.RETURN_GENERATED_KEYS);
			try(statement){
				ejecutaRegistro(producto, statement);
			}
			}catch(SQLException e) {
				throw new RuntimeException();
			}
	}
	
	
	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
		
//		if(producto.getCantidad() < 50) {
//			throw new RuntimeException();
//		}
		
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
		statement.setInt(4, producto.getCategoriaId());
		
		statement.execute();
		
		System.out.println(statement);
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet){
			while (resultSet.next()) {
				producto.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto %s", producto));
			}
		}
	}

	public List<Producto> listar() {
		final Connection c =  new ConnectionFactory().recuperaConexion();
		try(c){
			final PreparedStatement statement =  c.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			
			try(statement){
				statement.execute();
				
				final ResultSet resulSet = statement.getResultSet();
				
				try(resulSet){
					List<Producto> resultado = new ArrayList<>();
					
					while(resulSet.next()) {
						Producto fila = new Producto(
							resulSet.getInt("ID"),
							resulSet.getString("NOMBRE"),
							resulSet.getString("DESCRIPCION"),
							resulSet.getInt("CANTIDAD"));
						resultado.add(fila);
					}
					return resultado;
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}

	public List<Producto> listar(Integer categoria_id){
		final Connection c =  new ConnectionFactory().recuperaConexion();
		try(c){
			final PreparedStatement statement =  c.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO "
					+ "WHERE categoria_id = ?");
			
			try(statement){
				statement.setInt(1, categoria_id);
				statement.execute();
				
				final ResultSet resulSet = statement.getResultSet();
				
				try(resulSet){
					List<Producto> resultado = new ArrayList<>();
					
					while(resulSet.next()) {
						Producto fila = new Producto(
							resulSet.getInt("ID"),
							resulSet.getString("NOMBRE"),
							resulSet.getString("DESCRIPCION"),
							resulSet.getInt("CANTIDAD"));
						resultado.add(fila);
					}
					return resultado;
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}
	public int eliminar(Integer id) {
		final Connection c =  new ConnectionFactory().recuperaConexion();
		try(c){
			final PreparedStatement statement = c.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
			try(statement){
				statement.setInt(1, id);
				
				statement.execute();
				
				int row = statement.getUpdateCount();
				
				return row;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void modificar(Producto producto) {
		final Connection c =  new ConnectionFactory().recuperaConexion();
		try(c){
			PreparedStatement statement = c.prepareStatement("UPDATE PRODUCTO SET "  
					+ "NOMBRE=?, DESCRIPCION=?,CANTIDAD =? WHERE ID=?");
			try(statement){
				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getId());
				
				statement.execute();
			}
		}catch(SQLException e) {
			throw new RuntimeException(e); 
		}
	}
}
