package Ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ejercicio1App {
	
	public static Connection conexion; // Como variable global

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scn = new Scanner(System.in);
		
		String db, query;
		int num, i;
		
		System.out.println("Di el nombre de la base de datos");
		db = scn.next();
		crearBD(db);
		System.out.println("Cuantas tablas quieres crear?");
		num = scn.nextInt();
		String[] tablas = new String[num]; // Aqui guardaremos el nombre de las tablas
		
		for (i = 0; i < num; i++) {
			System.out.println("Di el nombre de la tabla numero " + (i+1));
			tablas[i] = scn.next();
			scn.nextLine(); // Buffering, queda un enter en el buffer
			query = "CREATE TABLE " + tablas[i] +"";
			System.out.println("Escribe la query");
			query += scn.nextLine(); // Y si no hacemos buffering aqui se come el enter sin poder poner nada
			crearTabla(db, query);
		}
		
		for (i = 0; i < tablas.length; i++) {
			query = "Insert into " + tablas[i];
			System.out.println("Escribe la query para insertar datos en la tabla " + tablas[i]);
			query += scn.nextLine();
			insertarDatos(db, query);
		}
		
		scn.close();
		
	}

	// Para conectar al mysql
	public static void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://192.168.1.175:3306?userTimezone=true&serverTimezone=UTC","remote","Adri@n01");
			System.out.println("Connected!");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error 404");
			System.out.println(e);
		}
		
	}
	
	// Para desconectar del mysql
	public static void closeConnection() {
		try {
			conexion.close();
			System.out.println("Disconnected!");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	// Para crear una base de datos
	public static void crearBD(String nombre) {
		Connect();
		try {
			String Query = "Create Database " + nombre;
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("La base de datos " + nombre + " ha sido creada");
			closeConnection();
		} catch(SQLException e) {
			System.out.println("Error");
			System.out.println(e);
		}
	}
	
	// Para crear las tablas
	public static void crearTabla(String db, String Query) {
		Connect();
		try {
			String QueryDB = "USE " + db;
			Statement stdb = conexion.createStatement();
			stdb.executeUpdate(QueryDB);
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Tabla creada con exito");
			
		} catch (SQLException e) {
			System.out.println("Error");
			System.out.println(e);
		}
	}
	
	// Para instertar datos
	public static void insertarDatos (String db, String Query) {
		Connect();
		try {
			String QueryDB = "USE " + db;
			Statement stdb = conexion.createStatement();
			stdb.executeUpdate(QueryDB);
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Datos insertados correctamente");
			
		} catch (SQLException e) {
			System.out.println("Error");
			System.out.println(e);
		}
	}
}
