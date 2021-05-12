package es.studium.ConsultasPDF;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Controlador implements WindowListener, ActionListener
{
	Vista vista;
	Modelo modelo;
	
	Connection conexion = null;
	String informacion = "";
	
	public Controlador(Vista v, Modelo m) {
		this.vista = v;
		this.modelo = m;
		
		this.vista.ventana.addWindowListener(this);
		this.vista.btnPDF.addActionListener(this);
		
		// Conectar a la BD
		conexion = this.modelo.conectar();
		// Realizar consulta
		informacion = this.modelo.consulta(conexion);
		// Rellenar TextArea
		this.vista.txaConsulta.append(informacion);
		// Cerrar conexion
		this.modelo.cerrar(conexion);
	}
	
	// ACTION LISTENER
	@Override
	public void actionPerformed(ActionEvent e)
		{
		// Se crea el documento
//				Document documento = new Document();
//				try
//				{
//					// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
//					FileOutputStream ficheroPdf = new FileOutputStream("fichero.pdf");
//					//Se asocia el documento al OutputStream y se indica que el espaciado entre
//					//líneas será de 20. Esta llamada debe hacerse antes de abrir el documento
//					PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
//					//Se abre el documento.
//					documento.open();
//					documento.add(new Paragraph("Listado de Facturas"));
//					documento.add(new Paragraph(this.vista.txaConsulta.getText()));
//					documento.add(Chunk.NEWLINE);
//					documento.close();
//					//Abrimos el archivo PDF recién creado
//					try
//					{
//						File path = new File("fichero.pdf");
//						Desktop.getDesktop().open(path);
//					} catch (IOException ex)
//					{
//						System.out.println("Se ha producido un error al abrir el archivo PDF");
//					}
//				} catch (Exception error)
//				{
//					error.printStackTrace();
//				}
		
		conexion = this.modelo.conectar();
		ArrayList<String> datos = this.modelo.consultarPDF(conexion);
		Document documento = new Document();
		try
		{
			// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
			FileOutputStream ficheroPdf = new FileOutputStream("fichero.pdf");
			//Se asocia el documento al OutputStream y se indica que el espaciado entre
			//líneas será de 20. Esta llamada debe hacerse antes de abrir el documento
			PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
			//Se abre el documento.
			documento.open();
			PdfPTable tabla = new PdfPTable(4);
			tabla.addCell("Nº Factura");
			tabla.addCell("Fecha");
			tabla.addCell("DNI");
			tabla.addCell("Nombre");
			for (int i = 0; i < datos.size(); i++) {
				tabla.addCell(datos.get(i));
			}
			documento.add(tabla);
			documento.close();
			//Abrimos el archivo PDF recién creado
			try
			{
				File path = new File("fichero.pdf");
				Desktop.getDesktop().open(path);
			} catch (IOException ex)
			{
				System.out.println("Se ha producido un error al abrir el archivo PDF");
			}
		} catch (Exception error)
		{
			error.printStackTrace();
		}
		
		this.modelo.cerrar(conexion);
		
				
		}	
		
	//WINDOW LISTENER
	@Override
	public void windowActivated(WindowEvent arg0){}
	@Override
	public void windowClosed(WindowEvent arg0){}
	@Override
	public void windowClosing(WindowEvent arg0){
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent arg0){}
	@Override
	public void windowDeiconified(WindowEvent arg0){}
	@Override
	public void windowIconified(WindowEvent arg0){}
	@Override
	public void windowOpened(WindowEvent arg0){}

}
