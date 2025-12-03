package Modelo.Entidades;
import java.sql.Timestamp;

/**
 * @author deibiledesma
 */
public class Venta {
    private int id;
    private Timestamp fecha;
    private int usuarioId;
    private String nombreUsuario; // Para mostrar quién compró
    private double total;

    public Venta() {
    }

    public Venta(int id, Timestamp fecha, int usuarioId, String nombreUsuario, double total) {
        this.id = id;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.total = total;
    }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}