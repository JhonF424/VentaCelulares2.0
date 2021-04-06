package model;

import java.util.Calendar;
import org.json.JSONObject;

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

    public Venta(JSONObject data) {
        this.consecutivo = data.getInt("consecutivo");
        //this.fecha = ????
        this.cliente = new Cliente(new JSONObject("cliente"));
    }

    public Venta(String strData) {
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
        return false;
    }

    @Override
    public String toString() {
        return "Venta{ " + "consecutivo: " + consecutivo + ", fecha: " + fecha + ", cliente: " + cliente +" }";
    }
    

}
