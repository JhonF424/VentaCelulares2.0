package model;

import org.json.JSONObject;

public class Celular {

    private int cantidad;
    private Tipo tipo;

    public Celular() {}

    public Celular(Tipo tipo, int cantidad) {
        this.cantidad = cantidad;
        this.tipo = tipo;
    }
    
    public Celular(Celular cel){
        this(cel.tipo, cel.cantidad);
    }
    
    public Celular(JSONObject data){
        this.tipo = data.getEnum(Tipo.class, "tipo");
        this.cantidad = data.getInt("cantidad");
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
        if (o == this) { 
            return true;
        }
        
        if (!(o instanceof Celular)) { 
            return false;
        }
        Celular celular = (Celular) o; 
        return this.tipo.equals(celular.tipo);
    }

    @Override
    public String toString() {
        return "Celular{ " + "cantidad: " + cantidad + ", tipo: " + tipo + " }";
    }
    
    

}
