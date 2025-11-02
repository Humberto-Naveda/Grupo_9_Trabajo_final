/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Pelicula;
import Modelo.Proyeccion;
import Modelo.Sala;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
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
    public Proyeccion buscarProyeccion(int Id){
        
 String sql = "SELECT "
        + "P.Id_proyeccion, "
        + "P.Id_pelicula, "
        + "P.idioma, "
        + "P.es3D, "
        + "P.subtitulada, "
        + "P.horaInicio, "
        + "P.horaFin, "
        + "P.precio, "
        + "S.Id_sala, "
        + "S.nroSala, "
        + "S.apta3D, "
        + "S.capacidad, "
        + "S.estado "
        + "FROM proyeccion P "
        + "JOIN sala S ON S.Id_sala = P.Id_sala "
        + "WHERE P.Id_proyeccion = ?";

    Proyeccion p=null;
        try {
            
            PreparedStatement ps=conec.prepareStatement(sql);
            ps.setInt(1, Id);
            
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                 
                
            p=new Proyeccion();
            p.setIdProyeccion(rs.getInt("Id_proyeccion"));
            
            p.setIdioma(rs.getNString("idioma"));
          p.setEs3D(rs.getBoolean("es3D"));
          p.setSubtitulada(rs.getBoolean("subtitulada"));
          p.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
          p.setHoraFin(rs.getTime("horaFin").toLocalTime());
          p.setPrecio(rs.getDouble("precio"));
       
        Sala sala=new Sala();
    
           
           sala.setIdSala(rs.getInt("Id_sala"));
           sala.setApta3D(rs.getBoolean("apta3D"));
           sala.setCapacidad(rs.getInt("capacidad"));
           sala.setEstado(rs.getString("estado"));
           
           sala.setNroSala(rs.getInt("nroSala"));
           
        p.setSala(sala);
        
            
          Pelicula pe= new Pelicula();
          pe.setIdPelicula(rs.getInt("Id_pelicula"));
          
        p.setPelicula(pe);

            JOptionPane.showMessageDialog(null, "busqueda exitosa");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar");
        }
        return p;
    
    }
    public List<Proyeccion> listarProyeccion() {
    String sql = "SELECT p.Id_proyeccion, p.idioma, p.es3D, p.subtitulada, p.horaInicio, p.horaFin, p.precio, " +
             "pe.Id_pelicula, pe.titulo, pe.director, pe.genero, pe.origen, pe.estreno, pe.enCartelera, " +
             "s.Id_sala, s.nroSala, s.apta3D, s.capacidad, s.estado " +
             "FROM proyeccion p " +
             "JOIN pelicula pe ON p.Id_pelicula = pe.Id_pelicula " +
             "JOIN sala s ON p.Id_sala = s.Id_sala";

    List<Proyeccion> lista = new ArrayList<>();

    try {
        PreparedStatement ps = conec.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Proyeccion p = new Proyeccion();

            
            Pelicula pel= new Pelicula();
            pel.setIdPelicula(rs.getInt("Id_pelicula"));
            p.setPelicula(pel);

            Sala sala = new Sala();
            sala.setIdSala(rs.getInt("Id_sala"));
            p.setSala(sala);

            
            p.setIdProyeccion(rs.getInt("Id_proyeccion"));
            p.setIdioma(rs.getString("idioma"));
            p.setEs3D(rs.getBoolean("es3D"));
            p.setSubtitulada(rs.getBoolean("subtitulada"));
            p.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
            p.setHoraFin(rs.getTime("horaFin").toLocalTime());
            p.setPrecio(rs.getDouble("precio"));
            

             sala.setIdSala(rs.getInt("Id_sala"));
           sala.setApta3D(rs.getBoolean("apta3D"));
           sala.setCapacidad(rs.getInt("capacidad"));
           sala.setEstado(rs.getString("estado"));
           
           sala.setNroSala(rs.getInt("nroSala"));
            
            
            
            lista.add(p);
        }

        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al mostrar la lista: " + ex.getMessage());
    }

    return lista;
}

    
}
