package Persistencia;

import Modelo.Ticket;
import Modelo.Lugar;
import Modelo.Comprador;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TicketData {

    private Connection conex = null;
    private CompradorData compradorData; 
    private LugarData lugarData; 

    public TicketData(Conexion conex) {
        this.conex = conex.conectar();
        this.compradorData = new CompradorData(conex); 
        this.lugarData = new LugarData(conex);
    }

    // Metodos CRUD

    public void generarTicket(Ticket ticket) {
        String sql = "INSERT INTO ticketcompra (idComprador, idLugar, fechaCompra, fechaFuncion, monto) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conex.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, ticket.getComprador().getIdComprador());
            ps.setInt(2, ticket.getAsiento().getIdLugar()); 
            
            ps.setDate(3, Date.valueOf(ticket.getFechaCompra())); 
            ps.setDate(4, Date.valueOf(ticket.getFechaFuncion()));
            
            ps.setDouble(5, ticket.getMonto());

            int filasAfectadas = ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setIdTicket(rs.getInt(1));
                }
            }
            
            if (filasAfectadas > 0) {
                lugarData.ocuparLugar(ticket.getAsiento().getIdLugar());
                JOptionPane.showMessageDialog(null, "Ticket Nº " + ticket.getIdTicket() + " generado con éxito.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el ticket: " + ex.getMessage());
        }
    }

    public Ticket buscarTicket(int idTicket) {
        Ticket ticket = null;
        String sql = "SELECT * FROM ticketcompra WHERE idTicket = ?";

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, idTicket);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket();
                    
                    ticket.setIdTicket(idTicket);
                    ticket.setFechaCompra(rs.getDate("fechaCompra").toLocalDate());
                    ticket.setFechaFuncion(rs.getDate("fechaFuncion").toLocalDate());
                    ticket.setMonto(rs.getDouble("monto"));
                    
                    int idComprador = rs.getInt("idComprador");
                    Comprador comprador = compradorData.buscarComprador(idComprador); 
                    ticket.setComprador(comprador);
                    
                    int idLugar = rs.getInt("idLugar");
                    Lugar asiento = lugarData.buscarButaca(idLugar); 
                    ticket.setAsiento(asiento);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el ticket con ID: " + idTicket);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar ticket: " + ex.getMessage());
        }
        return ticket;
    }
    
    public void anularTicket(int idTicket) {
        String sql = "UPDATE ticketcompra SET estado = ? WHERE idTicket = ?"; 
        
        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            
            ps.setBoolean(1, false); 
            ps.setInt(2, idTicket);
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                Ticket t = buscarTicket(idTicket); 
                if (t != null) {
                    lugarData.liberarLugar(t.getAsiento().getIdLugar());
                }
                
                JOptionPane.showMessageDialog(null, "Ticket Nº " + idTicket + " anulado y asiento liberado.");
            } else {
                JOptionPane.showMessageDialog(null, "Advertencia: No se encontró el ticket para anular.");
            }
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al anular ticket: " + ex.getMessage());
        }
    }
    
    public List<Ticket> listarTicketsPorComprador(int idComprador) {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM ticketcompra WHERE idComprador = ?";

        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, idComprador);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    
                    ticket.setIdTicket(rs.getInt("idTicket"));
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
}
    

