package Persistencia;

import Modelo.Ticket;
import Modelo.Lugar;
import Modelo.Comprador;
import Modelo.Conexion;
import Modelo.Proyeccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TicketData {

    private Connection conex = null;
    private CompradorData compradorData; 
    private LugarData lugarData; 
    private ProyeccionData proyecciondata;

    public TicketData(Conexion conex) {
        this.conex = conex.conectar();
        this.compradorData = new CompradorData(conex); 
        this.lugarData = new LugarData(conex);
        this.proyecciondata=new ProyeccionData(conex);
    }

    // Metodos CRUD

    public void guardarTicket(Ticket ticket) {
        String sql = "INSERT INTO ticket (Id_comprador, Id_lugar, fechaCompra, fechaFuncion, monto, activo) VALUES (?, ?, ?, ?, ?,?)";

        try (PreparedStatement ps = conex.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, ticket.getComprador().getIdComprador());
            ps.setInt(2, ticket.getAsiento().getIdLugar()); 
            
            ps.setDate(3, Date.valueOf(ticket.getFechaCompra())); 
            ps.setDate(4, Date.valueOf(ticket.getFechaFuncion()));
            
            ps.setDouble(5, ticket.getMonto());
            ps.setBoolean(6, ticket.isActivo());

            int filasAfectadas = ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setIdTicket(rs.getInt(1));
                }
            }
            
            if (filasAfectadas > 0) {
                lugarData.reservarButaca(ticket.getAsiento());
                JOptionPane.showMessageDialog(null, "Ticket Nº " + ticket.getIdTicket() + " generado con éxito.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el ticket: " + ex.getMessage());
        }
    }

    public Ticket buscarTicket(int Id_ticket) {
        Ticket ticket = null;
        String sql = "SELECT * FROM ticket WHERE Id_ticket = ?";

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, Id_ticket);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket();
                    
                    ticket.setIdTicket(Id_ticket);
                    ticket.setFechaCompra(rs.getDate("fechaCompra").toLocalDate());
                    ticket.setFechaFuncion(rs.getDate("fechaFuncion").toLocalDate());
                    ticket.setMonto(rs.getDouble("monto"));
                    
                    int Id_comprador = rs.getInt("Id_comprador");
                    Comprador comprador = compradorData.buscarComprador(Id_comprador); 
                    ticket.setComprador(comprador);
                    
                    int Id_lugar = rs.getInt("Id_lugar");
                    Lugar asiento = lugarData.buscarButaca(Id_lugar); 
                    ticket.setAsiento(asiento);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el ticket con ID: " + Id_ticket);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar ticket: " + ex.getMessage());
        }
        return ticket;
    }
    
    
    
    public List<Ticket> listarTicketsPorComprador(int Id_comprador) {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE Id_comprador = ?";

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, Id_comprador);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    
                    ticket.setIdTicket(rs.getInt("Id_ticket"));
                    ticket.setFechaCompra(rs.getDate("fechaCompra").toLocalDate());
                    ticket.setMonto(rs.getDouble("monto"));
                    
                    lista.add(ticket);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar tickets: " + ex.getMessage());
        }
        return lista;
    }
    
    public List<Ticket> listarTickets() {
    List<Ticket> lista = new ArrayList<>();
    String sql = "SELECT * FROM ticket";

    try (PreparedStatement ps = conex.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Ticket ticket = new Ticket();

            ticket.setIdTicket(rs.getInt("Id_ticket"));
            ticket.setFechaCompra(rs.getDate("fechaCompra").toLocalDate());
            ticket.setFechaFuncion(rs.getDate("fechaFuncion").toLocalDate());
            ticket.setMonto(rs.getDouble("monto"));

          
            int Id_comprador = rs.getInt("Id_comprador");
            Comprador comprador = compradorData.buscarComprador(Id_comprador);
            ticket.setComprador(comprador);
            int Id_lugar = rs.getInt("Id_lugar");
            Lugar asiento = lugarData.buscarButaca(Id_lugar);
            ticket.setAsiento(asiento);

        
            lista.add(ticket);

    
}
    
    }   catch (SQLException ex) {
            Logger.getLogger(TicketData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    

public void anularTicket(int id){
String sql="UPDATE ticket SET activo=0 WHERE Id_ticket=?";
        try {
            PreparedStatement ps=conex.prepareStatement(sql);
            ps.setInt(1, id);
            int r= ps.executeUpdate();
            if (r > 0) {
            JOptionPane.showMessageDialog(null, " Ticket anulado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, " No se encontró un ticket con ese ID");
        }
            
        } catch (SQLException ex) {
            Logger.getLogger(TicketData.class.getName()).log(Level.SEVERE, null, ex);
        }

}



}