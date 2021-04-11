package model;

import org.json.JSONObject;

public class Cliente {

    String identificacion, nombre, telefono;

    public Cliente() {

    }

    public Cliente(String identificacion, String nombre, String telefono) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Cliente(JSONObject data) {
        this. identificacion = data.getString("identificacion");
        this.nombre = data.getString("nombre");
        this.telefono = data.getString("telefono");
    }
    
       public JSONObject getJSONObject() {
        return new JSONObject()
            .put("identificacion", identificacion)
            .put("nombre", nombre)
            .put("telefono", telefono);
    } 

    public Cliente(String strData) {
        this(new JSONObject(strData));
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true;
        }
        
        if (!(o instanceof Cliente)) { 
            return false;
        }
        Cliente cliente = (Cliente) o; 
        return this.identificacion.equals(cliente.identificacion);
    }

    @Override
    public String toString() {
        return String.format("%s - %s %s", identificacion, nombre, telefono);
    }
    
    

}
