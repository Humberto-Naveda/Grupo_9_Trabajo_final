/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Sala;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class SalaData {

    Connection conec = null;

    public SalaData(Conexion conexion) {
        this.conec = conexion.conectar();
    }

    public void guardarSala(Sala sala) {
        String sql = "INSERT INTO `sala`( `nroSala`, `apta3D`, `capacidad`, `activa`) VALUES (?,?,?,?)";

        try {
            PreparedStatement ps = conec.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, sala.getNroSala());
            ps.setBoolean(2, sala.isApta3D());
            ps.setInt(3, sala.getCapacidad());
            ps.setBoolean(4, sala.getEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                sala.setIdSala(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "sala guardada con id " + sala.getIdSala());
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al guardar sala");
        }

    }

    public Sala buscarSala(int nroSala) {
        String sql = "SELECT  Id_sala,`nroSala`, `apta3D`, `capacidad`, `activa` FROM `sala` WHERE nroSala=?";
        Sala sala = null;
        try {

            PreparedStatement ps = conec.prepareStatement(sql);
            ps.setInt(1, nroSala);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                sala = new Sala();
                sala.setIdSala(rs.getInt("Id_sala"));
                sala.setNroSala(rs.getInt("nroSala"));
                sala.setApta3D(rs.getInt("apta3D") == 1);
                sala.setCapacidad(rs.getInt("capacidad"));
                sala.setEstado(rs.getBoolean("estado"));

            }
            JOptionPane.showMessageDialog(null, "busqueda exitosa");
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "busqueda erronea");
        }

        return sala;
    }

    public List<Sala> listarSalasActivas() {
        String sql = "SELECT `Id_sala`, `nroSala`, `apta3D`, `capacidad`, `activa` FROM `sala` WHERE activa=1 ";
        List<Sala> salas = null;
        try {
            PreparedStatement ps = conec.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            salas = new ArrayList<>();
            while (rs.next()) {
                Sala s = new Sala();

                s.setIdSala(rs.getInt("Id_sala"));
                s.setApta3D(rs.getBoolean("apta3D"));
                s.setCapacidad(rs.getInt("capacidad"));
                s.setEstado(rs.getBoolean("activa"));

                s.setNroSala(rs.getInt("nroSala"));

                salas.add(s);
            }

            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al listar salas");
        }
        return salas;
    }

    public void modificarSala(Sala sala) {
        String sql = "UPDATE `sala` SET `nroSala`=?,`apta3D`=?,`capacidad`=?, activa=? WHERE Id_sala=?";
        try {
            PreparedStatement ps = conec.prepareStatement(sql);
            ps.setInt(1, sala.getNroSala());
            ps.setBoolean(2, sala.isApta3D());
            ps.setInt(3, sala.getCapacidad());
            ps.setBoolean(4, sala.getEstado());
            ps.setInt(5, sala.getIdSala());
            int r = ps.executeUpdate();
            if (r > 0) {
                JOptionPane.showMessageDialog(null, "fila actualizada");

            } else {
                JOptionPane.showMessageDialog(null, "no existe esa fila para actualizar");
            }
            ps.close();
        } catch (SQLException ex) {
            {
                JOptionPane.showMessageDialog(null, "error al actualizar fila");
            }
        }

    }

    public void eliminarSala(int id) {
        String sql = "DELETE FROM `sala` WHERE Id_sala=?";
        try {
            PreparedStatement ps = conec.prepareStatement(sql);
            ps.setInt(1, id);
            int r = ps.executeUpdate();
            if (r > 0) {
                JOptionPane.showMessageDialog(null, "se elimino una fila");
            } else {
                JOptionPane.showMessageDialog(null, "no se elimino nada");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al eliminar");
        }
    }
    public void bajaLogicaSala(int idSala) {
    String sql = "UPDATE sala SET activa = 0 WHERE Id_sala = ?";
    try {
        PreparedStatement ps = conec.prepareStatement(sql);
        ps.setInt(1, idSala);

        int filas = ps.executeUpdate();
        if (filas > 0) {
            JOptionPane.showMessageDialog(null, "Sala dada de baja correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la sala con ese ID.");
        }
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al dar de baja la sala: ");
    }
}
    public void altaLogicaSala(int idSala) {
    String sql = "UPDATE sala SET activa = 1 WHERE Id_sala = ?";
    try {
        PreparedStatement ps = conec.prepareStatement(sql);
        ps.setInt(1, idSala);

        int filas = ps.executeUpdate();
        if (filas > 0) {
            JOptionPane.showMessageDialog(null, "Sala dada de alta correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la sala con ese ID.");
        }
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al dar de alta la sala: " );
    }
}



}
