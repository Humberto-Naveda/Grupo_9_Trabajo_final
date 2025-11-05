/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.*;
import Modelo.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
        String insert = "INSERT INTO lugar (fila, numero, estado, idFuncion) VALUES (?,?,?,?)";

        try (PreparedStatement statement = conex.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, asiento.getFila());
            statement.setInt(2, asiento.getNumero());
            statement.setBoolean(3, asiento.isEstado());
            statement.setInt(4, asiento.getProyeccion().getIdProyeccion());
            int filasAgregadas = statement.executeUpdate();

            try (ResultSet rsId = statement.getGeneratedKeys()) {
                if (rsId.next()) {
                    asiento.setIdLugar(rsId.getInt(1));
                }
            }

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Asiento ingresado correctamente. Filas agregadas: " + filasAgregadas);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar asiento. Filas agregadas: " + filasAgregadas);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar asiento. " + e.getMessage());
        }
    }

    public Lugar buscarButaca(int idLugar) {
        Lugar asiento = null;

        String search = "SELECT * FROM lugar WHERE Id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(search)) {
            statement.setInt(1, idLugar);

            try (ResultSet rsBuscar = statement.executeQuery()) {

                if (rsBuscar.next()) {
                    asiento = new Lugar();
                    asiento.setIdLugar(idLugar);
                    asiento.setFila(rsBuscar.getInt("fila"));
                    asiento.setNumero(rsBuscar.getInt("numero"));
                    asiento.setEstado(rsBuscar.getBoolean("estado"));
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el lugar indicado.");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar asiento. " + e.getMessage());
        }

        return asiento;
    }

    // Baja Fisica
    public void borrarButaca(int idLugar) {
        String delete = "DELETE FROM lugar WHERE Id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(delete)) {
            statement.setInt(1, idLugar);

            int filasAgregadas = statement.executeUpdate();

            if (filasAgregadas > 0) {
                JOptionPane.showMessageDialog(null, "Asiento con ID: " + idLugar + " eliminado correctamente. Filas modificadas: " + filasAgregadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para eliminar. Filas modificadas: " + filasAgregadas);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar asiento. " + e.getMessage());
        }
    }

    public void actualizarButaca(Lugar lugar) {
        String update = "UPDATE lugar SET fila = ?, numero = ?, estado = ?, idFuncion = ? WHERE id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(update)) {
            statement.setInt(1, lugar.getFila());
            statement.setInt(2, lugar.getNumero());
            statement.setBoolean(3, lugar.isEstado());
            statement.setInt(4, lugar.getProyeccion().getIdProyeccion());
            statement.setInt(5, lugar.getIdLugar());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, " Lugar con ID " + lugar.getIdLugar() + " actualizado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para actualizar. Filas afectadas: " + filasAfectadas);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lugar: " + ex.getMessage());
        }
    }

    // Metodos Adicionales
    public void reservarButaca(Lugar asiento) {
        String update = "UPDATE lugar SET estado = ? WHERE id_lugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(update)) {

            statement.setBoolean(1, true);
            statement.setInt(2, asiento.getIdLugar());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, " Lugar con ID " + asiento.getIdLugar() + " reservado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para reservar. Filas afectadas: " + filasAfectadas);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lugar: " + ex.getMessage());
        }
    }

    public List<Lugar> listarButacas() {

        List<Lugar> lista = new ArrayList<>();

        String list = "SELECT * FROM lugar";

        try (PreparedStatement ps = conex.prepareStatement(list)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Lugar asiento = new Lugar();
                    Proyeccion proyeccion = new Proyeccion();
                    proyeccion.setIdProyeccion(rs.getInt("Id_proyeccion"));
                    asiento.setIdLugar(rs.getInt("Id_lugar"));
                    asiento.setProyeccion(proyeccion);
                    asiento.setFila(rs.getInt("fila"));
                    asiento.setNumero(rs.getInt("numero"));
                    asiento.setEstado(rs.getBoolean("estado"));
                    lista.add(asiento);
                }
            }
        } catch (SQLException ex) {
            System.out.println(" Error al listar lugares: " + ex.getMessage());
        }
        return lista;
    }

    // Alta Logica
    public void ocuparLugar(int idLugar) {

        String update = "UPDATE lugar SET estado = ? WHERE idLugar = ?";

        try (PreparedStatement statement = conex.prepareStatement(update)) {

            statement.setBoolean(1, true);
            statement.setInt(2, idLugar);

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Lugar con ID " + idLugar + " ocupado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el lugar para ocupar.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ocupar lugar: " + ex.getMessage());
        }
    }
    
    // Baja Logica
    public void liberarLugar(int idLugar) {
    
    String update = "UPDATE lugar SET estado = ? WHERE idLugar = ?";

    try (PreparedStatement statement = conex.prepareStatement(update)) {

        
        statement.setBoolean(1, false); 
        statement.setInt(2, idLugar);

        int filasAfectadas = statement.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "Lugar con ID " + idLugar + " liberado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el lugar para liberar.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al liberar lugar: " + ex.getMessage());
    }
}

}
