/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Vistas;

import Modelo.*;
import Persistencia.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Humberto
 */
public class Test {

    public static void main(String[] args) {
          Conexion conn = new Conexion("gp9_cinemacentro_basededatos","jdbc:mariadb://localhost/","root","","org.mariadb.jdbc.Driver");
      ProyeccionData pd=new ProyeccionData(conn);
   //  pd.buscarProyeccion(10);
       
        
        Pelicula peli = new Pelicula(
                4,                              // idPelicula
                "Inception",                    // título
                "Christopher Nolan",            // director
                "Leonardo DiCaprio, Ellen Page",// actores
                "EE.UU.",                       // origen
                "Ciencia Ficción",              // género
                LocalDate.of(2010, 7, 16),      // fecha de estreno
                true     
                
// en cartelera
        );
         Sala sala = new Sala(
                10,          // idSala
                1,          // nroSala
                true,       // apta3D
                120,        // capacidad
                "Disponible" // estado
        );
         Proyeccion pro = new Proyeccion(11,
                peli,
                sala,
                "l",
                true,                // es3D
                false,               // subtitulada
                LocalTime.of(20, 30), // horaInicio 20:30
                LocalTime.of(22, 50), // horaFin 22:50
                3500.00               // precio
        );
   pd.modificarProyeccion(pro);
     /*  for(Proyeccion p:pd.listarProyeccion()){
       
           System.out.println(p);
       }*/
}
}