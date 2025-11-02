/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Pelicula;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class PeliculaData {
    Connection conec=null;

    public PeliculaData(Conexion co) {
        conec=co.conectar();
    }

    
    
    
    
    public void agregarPelicula(Pelicula p){
        String sql="    INSERT INTO `pelicula`( `titulo`, `director`, `actores`, `origen`, `genero`, `estreno`, `enCartelera`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=conec.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDirector());
            ps.setString(3, p.getActores());
            ps.setString(4, p.getOrigen());
            ps.setString(5, p.getGenero());
            ps.setDate(6, Date.valueOf(p.getEstreno()));
            ps.setBoolean(7, p.isEnCartelera());
            
           int fila= ps.executeUpdate();
            if(fila>0){
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                p.setIdPelicula(rs.getInt(1));

                  JOptionPane.showMessageDialog(null, "pelicula guardada con el id "+p.getIdPelicula());
            }
            }else{ JOptionPane.showMessageDialog(null, "no se guardo ninguna fila");}
            ps.close();
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "error al guardar pelicula ");
              ex.printStackTrace();
        }

    
    }
}
