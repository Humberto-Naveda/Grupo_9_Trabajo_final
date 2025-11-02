
package Persistencia;

import Modelo.*;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompradorData {
    private Connection conec = null;

    public CompradorData(Conexion conexion) {
        this.conec = conexion.conectar();
    }
    
    public void guardarComprador(Comprador c){
        String sql="INSERT INTO comprador(dni,nombre,fechaNac,password,medioPago) VALUES(?,?,?,?,?)";
        
        PreparedStatement ps;
        try {
            ps = conec.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
        
            ps.setInt(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setDate(3,Date.valueOf(c.getFechaNac()));
            ps.setInt(4, c.getPassword());
            ps.setString(5,c.getMedioPago());
         
            ps.executeUpdate();
            
              ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setIdComprador(rs.getInt(1));
                System.out.println("Comprador guardado con ID: " + c.getIdComprador());
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
                c.setPassword(rs.getInt("password"));
                c.setMedioPago(rs.getString("medioPago"));
                c.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                lista.add(c);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println(" Error al listar compradores: " + ex.getMessage());
        }
        return lista;
    } 
    
}    