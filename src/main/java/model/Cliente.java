package model;

import org.json.JSONObject;

public class Cliente {

    String identificacion, nombre, telefono;

    Cliente() {

    }

    Cliente(String identificacion, String nombre, String telefono) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    Cliente(JSONObject data) {
        this(data.getString("identificacion"), data.getString("nombre"), data.getString("telefono"));
    }

    Cliente(String strData) {
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

    public JSONObject getJSONObject() {
        return new JSONObject().put("identificacion", identificacion)
                .put("nombre", nombre)
                .put("telefono", telefono);
    }
    
    @Override
    public boolean equals(Object o){
        return false;
    }

    @Override
    public String toString() {
        return "Cliente{ " + "identificacion: " + identificacion + ", nombre: " + nombre + 
                ", telefono: " + telefono + " }";
    }
    
    

}
