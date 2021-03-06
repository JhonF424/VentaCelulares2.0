package model;

import org.json.JSONObject;

public class DetalleVenta {

    private int cantidad;
    private double precio;
    private Venta venta;
    private Celular celular;

    public DetalleVenta(Venta venta, Celular celular, int cantidad) {
        this.venta = venta;
        this.celular = celular;
        this.cantidad = cantidad;
    }

    public DetalleVenta(JSONObject data) throws Exception {
        this.venta = new Venta(data.getJSONObject("venta"));
        this.celular = new Celular(data.getJSONObject("celular"));
        this.cantidad = data.getInt("cantidad");
    }

    public DetalleVenta(String strData) throws Exception {
        this(new JSONObject(strData));
    }

    public JSONObject getJSONObject() {
        return new JSONObject()
                .put("cantidad", cantidad)
                .put("precio", precio)
                .put("venta", venta.getJSONObject())
                .put("celular", celular.getJSONObject());
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }

    public double getTotal() {
        return precio * cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof DetalleVenta)) {
            return false;
        }
        DetalleVenta detalleVenta = (DetalleVenta) o;
        return this.venta.equals(detalleVenta.venta) && this.celular.equals(
                detalleVenta.celular);
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + "cantidad=" + cantidad + ", precio=" + precio + ", venta=" + venta + ", celular=" + celular + '}';
    }

}
