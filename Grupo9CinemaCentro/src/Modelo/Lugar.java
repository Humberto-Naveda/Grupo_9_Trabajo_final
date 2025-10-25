/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Usuario
 */
public class Lugar {
    private int idLugar;
    private Proyeccion proyeccion;
    private int fila;
    private int numero;
    private boolean estado;

    public Lugar(Proyeccion proyeccion, int fila, int numero, boolean estado) {
        this.proyeccion = proyeccion;
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }

    public Lugar() {
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Lugar{" + "idLugar=" + idLugar + ", proyeccion=" + proyeccion + ", fila=" + fila + ", numero=" + numero + ", estado=" + estado + '}';
    }

   
    
}
