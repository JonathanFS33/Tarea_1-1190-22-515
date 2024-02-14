
package com.mycompany._tarea_1.controller;

import javax.swing.table.DefaultTableModel;
import com.mycompany._tarea_1.modelo.Conexion;
import com.mycompany._tarea_1.modelo.Pacientes;

/**
 *
 * @author Jonathan
 */
public class Controller {
    public void Guardar(Pacientes paciente) {
        Conexion c = new Conexion();
        try {
            c.guardarPaciente(paciente);
        } catch (Exception ex) {

        }
    }
    
    public void Consultar(DefaultTableModel model) {
        try {
            Conexion c = new Conexion();
            c.listar(model);
        } catch (Exception ex) {

        }

    }
    
    public boolean Editar(Pacientes p, int id_paciente) {
       Conexion c = new Conexion();
       if(c.actualizarPaciente(p,id_paciente))
       {
           return true;
       }
       else
       {
           return false;
       }
       
    }
    
    public boolean Eliminar(int id) {
        Conexion c = new Conexion();
        if (c.eliminarPaciente(id)) {
            return true;
        } else {
            return false;
        }

    }
}
