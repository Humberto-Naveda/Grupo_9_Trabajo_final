/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Vistas;

import Modelo.*;
import Persistencia.*;
import java.time.LocalDate;

/**
 *
 * @author Humberto
 */
public class Test {

    public static void main(String[] args) {
          Conexion conn = new Conexion("gp9_cinemacentro_basededatos","jdbc:mariadb://localhost/","root","","org.mariadb.jdbc.Driver");
          CompradorData cd = new CompradorData(conn);
        Comprador nuevo1 = new Comprador(1,33, " Zarate", 134, " Debito", LocalDate.of(1995, 10, 15));
          Comprador nuevo = new Comprador(44221133, "Juan Zarate", 1234, "Tarjeta Debito", LocalDate.of(1995, 5, 15));
       //   cd.guardarComprador(nuevo);
        
     
        //cd.modificarComprador(nuevo1);
        cd.eliminarComprador(44221133);
       
       /*   System.out.println("Lista de compradores:");
          for (Comprador c : cd.listarCompradores()) {
             System.out.println(c);
          } */
         
    }
    
}
