package com.alura.jdbc.modelo;

public class Producto {
	private Integer id;
	
	private String nombre;
	
	private String descripcion;
	
	private Integer cantidad;
	
	private Integer categoria_id;
	
	
	public Producto(String nombre, String descripcion, int cantidad) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	public Producto(int id, String nombre, String descripcion, int cantidad) {
		this.nombre = nombre;
		this.id = id;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	public Producto(int id, String nombre, int cantidad) {
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public Integer getId() {
		return this.id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setId(int int1) {
		this.id = int1;
	}
	
	@Override
	public String toString() {
		return String.format("{nombre: %s, descripcion: %s, cantidad: %s}", 
				this.nombre,
				this.descripcion,
				this.cantidad);
	}

	public void setCategoriaId(int categoria_id) {
		this.categoria_id = categoria_id;
	}

	public int getCategoriaId() {
		return this.categoria_id;
	}
	
}
