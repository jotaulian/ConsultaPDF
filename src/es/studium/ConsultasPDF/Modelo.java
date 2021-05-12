package es.studium.ConsultasPDF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Modelo
{

	// Conectar BD
	public Connection conectar()
	{
		Connection c = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/clientesh?serverTimezone=UTC";
		String login = "root";
		String password = "Lu1994Juli1993";
		
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			c = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		
		return (c);
	}

	
	// Desconectar BD
	public void cerrar(Connection conexion)
	{
		try {
			if(conexion!=null) {
				conexion.close();
			}
		} catch (Exception error) {
			System.out.println("Error 3-"+ error.getMessage());
		}
	}
	
	// Obtener datos
	public String consulta(Connection conexion)
	{
		String datos= "";
		Statement statement = null;
		ResultSet rs = null;
		// Crear una sentencia
		String sentencia = "SELECT idFactura, date_format(fechaFactura, '%d/%m/%Y') 'fecha', dniCliente, CONCAT(nombreCliente, CONCAT(\" \", apellidosCliente)) 'nombre' "
				+ "FROM clientesh.facturas JOIN clientes ON facturas.idClienteFK = clientes.idCliente;";
		try 
		{
			statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while(rs.next())
				{
				datos = datos + rs.getInt("idFactura") + "\t" + "\t";
				datos = datos + rs.getString("fecha")+ "\t";
				datos = datos + rs.getString("dniCliente")+ "\t";
				datos = datos + rs.getString("nombre") + "\n";
				}
		} 
		catch (SQLException er) 
		{
			System.out.println("Error 3-"+ er.getMessage());
		}
		
		return datos;
	}


	public ArrayList<String> consultarPDF(Connection conexion)
	{
		ArrayList<String> datos = new ArrayList<String>();
		Statement statement = null;
		ResultSet rs = null;
		// Crear una sentencia
		String sentencia = "SELECT idFactura, date_format(fechaFactura, '%d/%m/%Y') 'fecha', dniCliente, CONCAT(nombreCliente, CONCAT(\" \", apellidosCliente)) 'nombre' "
				+ "FROM clientesh.facturas JOIN clientes ON facturas.idClienteFK = clientes.idCliente;";
		try 
		{
			statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while(rs.next())
				{
				datos.add(""+rs.getInt("idFactura"));
				datos.add(""+rs.getString("fecha"));
				datos.add(""+rs.getString("dniCliente"));
				datos.add(""+rs.getString("nombre"));
				}
		} 
		catch (SQLException er) 
		{
			System.out.println("Error 3-"+ er.getMessage());
		}
		return datos;
	}
	
}
