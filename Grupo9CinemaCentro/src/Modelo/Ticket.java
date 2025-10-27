/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Ticket {
   private int idTicket;
   private Comprador comprador;
   private LocalDate fechaCompra;
   private LocalDate fechaFuncion;
   private double monto;
   private Lugar asiento;

    public Ticket(Comprador comprador, LocalDate fechaCompra, LocalDate fechaFuncion, double monto, Lugar asiento) {
        this.comprador = comprador;
        this.fechaCompra = fechaCompra;
        this.fechaFuncion = fechaFuncion;
        this.monto = monto;
        this.asiento = asiento;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public LocalDate getFechaFuncion() {
        return fechaFuncion;
    }

    public void setFechaFuncion(LocalDate fechaFuncion) {
        this.fechaFuncion = fechaFuncion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Lugar getAsiento() {
        return asiento;
    }

    public void setAsiento(Lugar asiento) {
        this.asiento = asiento;
    }

    @Override
    public String toString() {
        return "Ticket{" + "idTicket=" + idTicket + ", comprador=" + comprador + ", fechaCompra=" + fechaCompra + ", fechaFuncion=" + fechaFuncion + ", monto=" + monto + ", asiento=" + asiento + '}';
    }

   
   
}