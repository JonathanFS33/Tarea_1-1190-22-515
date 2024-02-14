package com.mycompany._tarea_1.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonathan
 */
public class Conexion {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    Connection con;
    Statement stat = null;
    String Base = "Pacientes";
    String DB_URL = "jdbc:mysql://localhost:3306/" + Base + "?useSSL=false&serverTimezone=UTC";
    static final String user = "root";
    static final String pass = "Franc0#332004";

    public Conexion() {
        try {
            //Realiza la conexión a la base de datos
            con = DriverManager.getConnection(DB_URL, user, pass);
            System.out.println("CONECTADO A LA BASE DE DATOS");

        } catch (SQLException ex) {
            System.out.println("Error al conectarme por " + ex.getMessage());
        }
    }

    public void guardarPaciente(Pacientes paciente) {
        try {
            if (con != null) {
                //prepara la sentencia sql para insertar
                PreparedStatement st = con.prepareStatement("insert into paciente(nombre, edad, diagnostico, telefono, direccion) value (?, ?, ?, ?, ?)");
                // se asignan las variables
                st.setString(1, paciente.getNombre());
                st.setInt(2, paciente.getEdad());
                st.setString(3, paciente.getDiagnostico());
                st.setInt(4, paciente.getTelefono());
                st.setString(5, paciente.getDireccion());

                st.execute();
                //Cerramos la conexión a la consulta
                st.close();
                //Cerramos la conexión a la base de datos
                con.close();
                System.out.println("CONEXIÓN CERRADA");
            } else {
                System.out.println("No fue posible guardar el paciente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el paciente " + ex.getMessage());
        }
    }

    public boolean actualizarPaciente(Pacientes paciente, int id_paciente) {
        boolean res = false;
        try {
            if (con != null) {
                //prepara la sentencia sql para insertar
                PreparedStatement st = con.prepareStatement("update paciente set nombre=?, edad=?, diagnostico=?, telefono=?, direccion=? where id = " + id_paciente + "");
                st.setString(1, paciente.getNombre());
                st.setInt(2, paciente.getEdad());
                st.setString(3, paciente.getDiagnostico());
                st.setInt(4, paciente.getTelefono());
                st.setString(5, paciente.getDireccion());

                //ejecuta la sentencia sql
                st.executeUpdate();
                res = true;
                st.close();
                con.close();
                System.out.println("CONEXIÓN CERRADA");
            } else {
                res = false;
                System.out.println("No fue posible actualizar el paciente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el paciente " + ex.getMessage());
        }
        return res;
    }

    public void listar(DefaultTableModel tableModel) throws SQLException {
        // declaramos la variable para cargar los datos
        ResultSet resultado = null;
        //Declaramos un tablemodel para la carga de datos en una tabla en memoria
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        //prepara la consulta a la base de datos
        PreparedStatement st = con.prepareStatement("select * from paciente");
        try {
            // cargamos el resultado a la base de datos
            resultado = st.executeQuery();
            if (resultado != null) {
                // definimos el numero de columnas que tiene la data que se carga en resultado
                int numeroColumna = resultado.getMetaData().getColumnCount();
                // utilizamos un for para agregar el nombre que aparece en la tabla
                for (int i = 1; i <= numeroColumna; i++) {
                    // cargamos el resultado de la consulta
                    tableModel.addColumn(resultado.getMetaData().getColumnName(i));
                }
                // este while carga los datos de las filas
                while (resultado.next()) {
                    // se cargan los datos a un vector de tipo string
                    String[] datos = new String[numeroColumna];
                    for (int i = 1; i <= numeroColumna; i++) {
                        datos[i - 1] = (String) resultado.getString(i);
                    }
                    tableModel.addRow(datos);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al realizar la actualización por" + e.getMessage());
        } // finally se ejecutará siempre al final
        finally {
            try {
                st.close();
                con.close();
                System.out.println("CONEXIÓN CERRADA");

                if (resultado != null) {
                    resultado.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al realizar la actualización por" + e.getMessage());
            }
        }
    }

    public boolean eliminarPaciente(int id) {
        boolean respuesta = false;
        try {
            if (con != null) {
                PreparedStatement st = con.prepareStatement("delete from paciente where id=" + id + "");
                st.execute();
                respuesta = true;
                st.close();
                con.close();
                System.out.println("CONEXIÓN CERRADA");
            } else {
                JOptionPane.showMessageDialog(null, "Error no es posible realizar la operacion");
            }
            return respuesta;
        } catch (Exception ex) {
            System.out.println("Error al realizar la actualización por" + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al realizar la actualización por" + ex.getMessage());
            return respuesta;
        }
    }
}
