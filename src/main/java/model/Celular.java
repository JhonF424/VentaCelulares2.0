package model;

import org.json.JSONObject;

public class Celular {

    private int cantidad;
    private Tipo tipo;

    public Celular() {}

    public Celular(int cantidad, Tipo tipo) {
        this.cantidad = cantidad;
        this.tipo = tipo;
    }
    
    public Celular(Celular cel){
        this(cel.cantidad, cel.tipo);
    }
    
    public Celular(JSONObject data){
        this.cantidad = data.getInt("cantidad");
        this.tipo = data.getEnum(Tipo.class, "tipo");
    }
    
    public Celular(String strData){
        this(new JSONObject(strData));
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public String toString() {
        return "Celular{ " + "cantidad: " + cantidad + ", tipo: " + tipo + " }";
    }
    
    

}
