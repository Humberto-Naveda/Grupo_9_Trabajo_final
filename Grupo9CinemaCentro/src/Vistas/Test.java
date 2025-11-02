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
     Proyeccion p= pd.buscarProyeccion(10);
        System.out.println(p);
   
     /*  for(Proyeccion p:pd.listarProyeccion()){
       
           System.out.println(p);
       }*/
}
}