package Persistencia;

import Modelo.*;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CompradorData {

    private Connection conec = null;

    public CompradorData(Conexion conexion) {
        this.conec = conexion.conectar();
    }

    public void guardarComprador(Comprador c) {
        String sql = "INSERT INTO comprador(dni,nombre,fechaNac,password) VALUES(?,?,?,?)";

        PreparedStatement ps;
        try {
            ps = conec.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setDate(3, Date.valueOf(c.getFechaNac()));
            ps.setString(4, c.getPassword());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setIdComprador(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Comprador guardado con ID: " + c.getIdComprador());
            }

            ps.close();
        } catch (SQLException ex) {
            System.out.println(" Error al guardar comprador: " + ex.getMessage());
        }
    }

    public List<Comprador> listarCompradores() {
        List<Comprador> lista = new ArrayList<>();
        String sql = "SELECT * FROM comprador";
        try {
            PreparedStatement ps = conec.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comprador c = new Comprador();
                c.setIdComprador(rs.getInt("id_comprador"));
                c.setDni(rs.getInt("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setPassword(rs.getString("password"));
                c.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                lista.add(c);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error al listar compradores: " + ex.getMessage());
        }
        return lista;
    }

    public Comprador buscarComprador(int id) {
        Comprador comprador;

        String sql = " SELECT `Id_Comprador`, `DNI`, `nombre`, `password`, `fechaNac` FROM `comprador` WHERE id_Comprador=?";

        try {
            PreparedStatement ps = conec.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                comprador = new Comprador();
                comprador.setIdComprador(rs.getInt("Id_Comprador"));
                comprador.setDni(rs.getInt("DNI"));
                comprador.setNombre(rs.getNString("nombre"));
                comprador.setPassword(rs.getString("password"));
                comprador.setFechaNac(rs.getDate("fechaNac").toLocalDate());

                return comprador;

            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showInputDialog("Error al buscar comprador. " + ex.getMessage());

        }
        return null;

    }

    public void modificarComprador(Comprador comprador) {
        String sql = "UPDATE `comprador` SET `DNI`=?,`nombre`=?,`password`=?,`fechaNac`=? WHERE Id_Comprador=?";
        try (PreparedStatement ps = conec.prepareStatement(sql)) {
            ps.setInt(1, comprador.getDni());
            ps.setString(2, comprador.getNombre());
            ps.setString(3, comprador.getPassword());
            ps.setDate(4, Date.valueOf(comprador.getFechaNac()));
            ps.setInt(5, comprador.getIdComprador());
            int rs = ps.executeUpdate();

            if (rs > 0) {
                JOptionPane.showMessageDialog(null, "Se actualizaron los datos del cliente con ID N°: " + comprador.getIdComprador() + ".");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar ninguna fila.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la actualizacion del cliente. " + ex.getMessage());
        }

    }

    public void eliminarComprador(int id) {
        String sql = "DELETE FROM `comprador` WHERE Id_comprador=?";
        try {
            PreparedStatement ps = conec.prepareStatement(sql);
            ps.setInt(1, id);
            int r = ps.executeUpdate();
            if (r > 0) {
                JOptionPane.showInternalInputDialog(null, "Eliminacion exitosa");
            } else {
                JOptionPane.showInternalMessageDialog(null, "no se a encontrado comprador con ese id");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR");
        }

    }

    public Comprador buscarPorDni(int dni) {
        Comprador c = null;

        String sql = "SELECT * FROM comprador WHERE dni = ?";

        try {
            PreparedStatement ps = conec.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Comprador();
                c.setIdComprador(rs.getInt("id_Comprador"));
                c.setDni(rs.getInt("DNI"));
                c.setNombre(rs.getString("nombre"));
                c.setPassword(rs.getString("password"));
                c.setFechaNac(rs.getDate("fechaNac").toLocalDate());
            }
            ps.close();

        } catch (Exception e) {
            System.out.println("Error al buscar comprador por DNI: " + e.getMessage());
        }

        return c;
    }

}
