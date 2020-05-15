package programaGestion;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

public class GPF extends WindowAdapter implements ActionListener, WindowListener
{
	static int tipoUsuario = 0;
	
	GPF()
	{
		GPI.login.setLayout(new FlowLayout());
		GPI.login.setSize(270, 150);
		GPI.login.setResizable(false);
		GPI.login.setLocationRelativeTo(null);
		GPI.login.addWindowListener(this);
		GPI.btnAceptar.addActionListener(this);
		GPI.btnLimpiar.addActionListener(this);
		GPI.login.add(GPI.lblUsuario);
		GPI.login.add(GPI.txtUsuario);
		GPI.login.add(GPI.lblClave);
		GPI.txtClave.setEchoChar('*');
		GPI.login.add(GPI.txtClave);
		GPI.login.add(GPI.btnAceptar);
		GPI.login.add(GPI.btnLimpiar);
		GPI.login.setVisible(true);
	}

	// Declaro los eventos que suceden en el login
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(GPI.btnLimpiar)) 
		{
			GPI.txtUsuario.selectAll();
			GPI.txtUsuario.setText("");
			GPI.txtClave.selectAll();
			GPI.txtClave.setText("");
			GPI.txtUsuario.requestFocus();
		}

		else if(evento.getSource().equals(GPI.btnAceptar)) 
		{
			String cadenaEncriptada = GPCrypt.getSHA256(GPI.txtClave.getText());
			GPBD.sentencia = "SELECT * FROM usuarios WHERE nombreUsuario = '"+ GPI.txtUsuario.getText()+ "'AND claveUsuario = '"+ cadenaEncriptada+"'";

			try
			{
				GPBD.ConexionBD();
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				GPBD.rs = GPBD.statement.executeQuery(GPBD.sentencia);
				if(GPBD.rs.next())

				{
					// Para saber si el usuario es administrador o usuario b�sico
					GPBD.sentencia = "SELECT tipoUsuario FROM usuarios WHERE nombreUsuario = '" + GPI.txtUsuario.getText()+"'";
					GPBD.rs = GPBD.statement.executeQuery(GPBD.sentencia);
					GPBD.rs.next();
					tipoUsuario = GPBD.rs.getInt("tipoUsuario");
					if (tipoUsuario == 1)
					{
						GPI.menuPrincipal.setLayout(new FlowLayout());
						GPI.menuPrincipal.setSize(800, 400);
						GPI.menuPrincipal.setResizable(false);
						GPI.menuPrincipal.addWindowListener(this);
						GPI.menuPrincipal.setLocationRelativeTo(null);
						GPI.menuPrincipal.setVisible(true);
						GPI.menuPrincipal.setMenuBar(GPI.barraMenu);
						// A�ado el menu Productos
						GPI.menuProductos.add(GPI.mniProductosAlta);
						GPI.mniProductosAlta.addActionListener(this);
						GPI.menuProductos.add(GPI.mniProductosModificacion);
						GPI.mniProductosModificacion.addActionListener(this);
						GPI.menuProductos.add(GPI.mniProductosConsulta);
						GPI.mniProductosConsulta.addActionListener(this);
						// A�ado el menu Proveedores
						GPI.menuProveedores.add(GPI.mniProveedoresAlta);
						GPI.mniProveedoresAlta.addActionListener(this);
						GPI.menuProveedores.add(GPI.mniProveedoresBaja);
						GPI.mniProveedoresBaja.addActionListener(this);
						GPI.menuProveedores.add(GPI.mniProveedoresModificacion);
						GPI.mniProveedoresModificacion.addActionListener(this);
						GPI.menuProveedores.add(GPI.mniProveedoresConsulta);
						GPI.mniProveedoresConsulta.addActionListener(this);
						// A�ado el menu Locales
						GPI.menuLocales.add(GPI.mniLocalesAlta);
						GPI.mniLocalesAlta.addActionListener(this);
						GPI.menuLocales.add(GPI.mniLocalesConsulta);
						GPI.mniLocalesConsulta.addActionListener(this);
						// A�ado el menu Ventas
						GPI.menuVentas.add(GPI.mniVentasAlta);
						GPI.mniVentasAlta.addActionListener(this);
						GPI.menuVentas.add(GPI.mniVentasConsulta);
						GPI.mniVentasConsulta.addActionListener(this);
						// Asigno el men� a la barrra de men�
						GPI.barraMenu.add(GPI.menuProductos);
						GPI.barraMenu.add(GPI.menuProveedores);
						GPI.barraMenu.add(GPI.menuLocales);
						GPI.barraMenu.add(GPI.menuVentas);
						// Pongo invisible el login
						GPI.login.setVisible(false);
					}
					else if (tipoUsuario == 2)
					{
						GPI.menuPrincipal.setLayout(new FlowLayout());
						GPI.menuPrincipal.setSize(800, 400);
						GPI.menuPrincipal.setResizable(false);
						GPI.menuPrincipal.addWindowListener(this);
						GPI.menuPrincipal.setLocationRelativeTo(null);
						GPI.menuPrincipal.setVisible(true);
						GPI.menuPrincipal.setMenuBar(GPI.barraMenu);
						// A�ado el menu Productos
						GPI.menuProductos.add(GPI.mniProductosAlta);
						GPI.mniProductosAlta.addActionListener(this);
						// A�ado el menu Proveedores
						GPI.menuProveedores.add(GPI.mniProveedoresAlta);
						GPI.mniProveedoresAlta.addActionListener(this);
						// A�ado el menu Locales
						GPI.menuLocales.add(GPI.mniLocalesAlta);
						GPI.mniLocalesAlta.addActionListener(this);
						// A�ado el menu Ventas
						GPI.menuVentas.add(GPI.mniVentasAlta);
						GPI.mniVentasAlta.addActionListener(this);
						// Asigno el men� a la barrra de men�
						GPI.barraMenu.add(GPI.menuProductos);
						GPI.barraMenu.add(GPI.menuProveedores);
						GPI.barraMenu.add(GPI.menuLocales);
						GPI.barraMenu.add(GPI.menuVentas);
						// Pongo invisible el login
						GPI.login.setVisible(false);
					}
				}

				else
				{
					GPI.errorLogin.setLayout(new FlowLayout());
					GPI.errorLogin.setSize(200, 100);
					GPI.errorLogin.setResizable(false);
					GPI.errorLogin.addWindowListener(this);
					GPI.errorLogin.add(new Label("Credenciales Incorrectas"));
					Button btnVolver = new Button("Volver");
					btnVolver.addActionListener(this);
					GPI.errorLogin.add(btnVolver);
					GPI.errorLogin.setLocationRelativeTo(null);
					GPI.errorLogin.setVisible(true);
				}
			}

			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}

			finally
			{
				Log.registrarLog("Conexi�n de usuario realizada");
				try
				{
					if(GPBD.connection!=null)
					{
						GPBD.connection.close();
					}
				}
				catch (SQLException e)
				{
					System.out.println("Error 3-"+e.getMessage());
				}
			}
		}

		// Alta de productos
		else if(evento.getSource().equals(GPI.mniProductosAlta)) 
		{
			new AltaProductos();
		}
		// Modificacion de productos
		else if(evento.getSource().equals(GPI.mniProductosModificacion)) 
		{
			new ModificacionProductos();
		}
		// Consulta de productos;
		else if(evento.getSource().equals(GPI.mniProductosConsulta)) 
		{
			new ConsultaProductos();
		}
		// Alta de proveedores
		else if(evento.getSource().equals(GPI.mniProveedoresAlta)) 
		{
			new AltaProveedores();
		}
		// Baja de proveedores
		else if(evento.getSource().equals(GPI.mniProveedoresBaja)) 
		{
			new BajaProveedores(); 
		}
		// Modificacion de proveedores
		else if(evento.getSource().equals(GPI.mniProveedoresModificacion)) 
		{
			new ModificacionProveedores();
		}
		// Consulta de proveedores;
		else if(evento.getSource().equals(GPI.mniProveedoresConsulta)) 
		{
			new ConsultaProveedores();
		}	
		// Alta de locales
		else if(evento.getSource().equals(GPI.mniLocalesAlta)) 
		{
			new AltaLocales();
		}
		// Consulta de locales;
		else if(evento.getSource().equals(GPI.mniLocalesConsulta)) 
		{
			new ConsultaLocales();
		}
		// Alta de ventas
		else if(evento.getSource().equals(GPI.mniVentasAlta)) 
		{
			new AltaVentas();
		}
		// Consulta de ventas;
		else if(evento.getSource().equals(GPI.mniVentasConsulta)) 
		{
			new ConsultaVentas();
		}
		else
		{
			// Tareas del Volver
			GPI.errorLogin.setVisible(false);
		}
	}

	public void windowClosing(WindowEvent arg0)
	{
		if(GPI.errorLogin.isActive())
		{
			GPI.errorLogin.setVisible(false);
		}
		else
		{
			Log.registrarLog("Salida del programa");
			System.exit(0);
		}
	}
}