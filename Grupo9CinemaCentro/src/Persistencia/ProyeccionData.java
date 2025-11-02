/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Proyeccion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ProyeccionData {
    Connection conec=null;

    public ProyeccionData(Conexion conexion) {
       conec=conexion.conectar();
    }
    public void agregarProyeccion(Proyeccion proyeccion){
    String sql="INSERT INTO `proyeccion`( `Id_pelicula`, `Id_sala`, `idioma`, `es3D`, `subtitulada`, `horaInicio`, `horaFin`, `precio`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=conec.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, proyeccion.getPelicula().getIdPelicula());
            ps.setInt(2, proyeccion.getSala().getIdSala());
            ps.setString(3, proyeccion.getIdioma());
            ps.setBoolean(4, proyeccion.isEs3D());
            ps.setBoolean(5, proyeccion.isSubtitulada());
            ps.setTime(6, Time.valueOf(proyeccion.getHoraInicio()));
            ps.setTime(7, Time.valueOf(proyeccion.getHoraFin()));
            ps.setDouble(8, proyeccion.getPrecio());
       ps.executeUpdate();
         ResultSet rs=ps.getGeneratedKeys();
        if(rs.next()){
        
        proyeccion.setIdProyeccion(rs.getInt(1));
         JOptionPane.showMessageDialog(null, "proyeccion guardada con id "+proyeccion.getIdProyeccion());
        } 
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al agregar fila");
        }
     
    }
    
}
