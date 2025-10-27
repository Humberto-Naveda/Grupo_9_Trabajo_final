/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import static java.util.Collections.list;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class DetalleTicket {
    private int idDetalle;
    private Proyeccion proyeccion; 
    private List<Lugar> lugar;
    private int cantidad;
    private double subtotal;

    public DetalleTicket(Proyeccion proyeccion, Lugar lugar, int cantidad, double subtotal) {
        this.proyeccion = proyeccion;
        this.lugar = (List<Lugar>) lugar;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public DetalleTicket() {
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }

    public Lugar getLugar() {
        return (Lugar) lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = (List<Lugar>) lugar;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetalleTicket{" + "idDetalle=" + idDetalle + ", proyeccion=" + proyeccion + ", lugar=" + lugar + ", cantidad=" + cantidad + ", subtotal=" + subtotal + '}';
    }
    
}
