package model;

import java.text.ParseException;
import java.util.Calendar;
import org.json.JSONObject;
import resources.Helpers;

public class Venta {

    private int consecutivo;
    private Calendar fecha;
    private Cliente cliente;

    public Venta() {

    }

    public Venta(int consecutivo, Calendar fecha, Cliente cliente) {
        this.consecutivo = consecutivo;
        this.fecha = fecha;
        this.cliente = cliente;
    }

    public Venta(JSONObject data) throws ParseException{
        this.consecutivo = data.getInt("consecutivo");
        this.fecha = Helpers.getFecha(data.getString("fecha"));
        this.cliente = new Cliente(data.getJSONObject("cliente"));
    }
    
   public JSONObject getJSONObject() {
        return new JSONObject()
            .put("consecutivo", consecutivo)
            .put("cliente", cliente.getJSONObject())
            .put("fecha", Helpers.strFecha(fecha));
    } 

    public Venta(String strData) throws ParseException{
        this(new JSONObject(strData));
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true;
        }
        
        if (!(o instanceof Venta)) { 
            return false;
        }
        Venta venta = (Venta) o;
        return this.consecutivo == venta.consecutivo;
    }

    @Override
    public String toString() {
        return String.format("Factura N:   %d      Fecha: %s %n Cliente: %s", consecutivo, Helpers.strFecha(fecha), cliente);
    }
    
}
