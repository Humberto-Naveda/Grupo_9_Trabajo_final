/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.*;
import Modelo.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author PC1
 */
public class LugarData {

    private Connection conex = null;

    public LugarData(Conexion conex) {
        this.conex = conex.conectar();
    }
    
    // Metodos CRUD

    public void insertButaca(Lugar asiento) {
        try {
            String insert = "INSERT INTO asiento (fila, numero, estado, idFuncion) VALUES (?,?,?,?)";

            PreparedStatement statement = conex.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, asiento.getFila());
            statement.setInt(2, asiento.getNumero());
            statement.setBoolean(3, asiento.isEstado());
            statement.setInt(4, asiento.getProyeccion().getIdProyeccion());
            int filasAgregadas = statement.executeUpdate();

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Asiento ingresado correctamente. Filas agregadas: " + filasAgregadas);
            }

            ResultSet rsId = statement.getGeneratedKeys();
            if (rsId.next()) {
                asiento.setIdLugar(rsId.getInt(1));
            } 

            rsId.close();
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar asiento. " + e.getMessage());
        }
    }

    public Lugar buscarButaca(int idLugar) {
        Lugar asiento = null;
        try {
            String search = "SELECT * FROM lugar WHERE Id_lugar = ?";

            PreparedStatement statement = conex.prepareStatement(search);
            statement.setInt(1, idLugar);

            ResultSet rsBuscar = statement.executeQuery();

            if (rsBuscar.next()) {
                asiento = new Lugar();
                asiento.setIdLugar(idLugar);
                asiento.setFila(rsBuscar.getInt("fila"));
                asiento.setNumero(rsBuscar.getInt("numero"));
                asiento.setEstado(rsBuscar.getBoolean("estado"));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar indicado.");
            }

            rsBuscar.close();
            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar asiento. " + e.getMessage());
        }
        return asiento;
    }

    public void borrarButaca(int idLugar) {
        try {
            String delete = "DELETE * FROM lugar WHERE Id_lugar = ?";

            PreparedStatement statement = conex.prepareStatement(delete);
            statement.setInt(1, idLugar);

            int filasAgregadas = statement.executeUpdate();

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Asiento con ID: " + idLugar + " eliminado correctamente. Filas agregadas: " + filasAgregadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para eliminar.");
            }

            statement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar asiento. " + e.getMessage());
        }
    }

    public void actualizarButaca(Lugar lugar) {
        try {
        String update = "UPDATE asiento SET fila = ?, numero = ?, estado = ?, idFuncion = ? WHERE idLugar = ?";

            PreparedStatement statement = conex.prepareStatement(update);             
            statement.setInt(1, lugar.getFila());
            statement.setInt(2, lugar.getNumero());
            statement.setBoolean(3, lugar.isEstado());
            statement.setInt(4, lugar.getProyeccion().getIdProyeccion()); 
            statement.setInt(5, lugar.getIdLugar());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, " Lugar con ID " + lugar.getIdLugar() + " actualizado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para actualizar.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lugar: " + ex.getMessage());
        }
    }
    
    // Metodos Adicionales
    
    public void reservarButaca(Lugar asiento) {
        try {
        String update = "UPDATE asiento SET estado = ? WHERE idLugar = ?";

            PreparedStatement statement = conex.prepareStatement(update);             
            
            statement.setBoolean(1, true);
            statement.setInt(2, asiento.getIdLugar());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, " Lugar con ID " + asiento.getIdLugar() + " reservado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para reservar.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lugar: " + ex.getMessage());
        }
    }

}
