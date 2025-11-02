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
          
       Pelicula pelicula = new Pelicula(
    "Inception",
    "Christopher Nolan",
    "Leonardo  Ellen Page",
    "Estados Unidos",
    "Ciencia ficción",
    LocalDate.of(2010, 7, 16),
    true
);

Sala sala = new Sala(0, 5, true, 120, "Disponible");



SalaData s=new SalaData(conn);

    PeliculaData     o= new PeliculaData(conn);
s.guardarSala(sala);
o.agregarPelicula(pelicula);


   
    Proyeccion proyeccion = new Proyeccion(
            pelicula,
            sala,
            "Español",
            true,     // es3D
            false,    // subtitulada
            LocalTime.of(21, 30),
            LocalTime.of(23, 15),
            350.0
        );
ProyeccionData p=new ProyeccionData(conn);
  p.agregarProyeccion(proyeccion);
    }
    
}
