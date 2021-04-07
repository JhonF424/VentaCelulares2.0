package controller;

import java.util.ArrayList;
import java.util.List;
import model.DetalleVenta;
import model.Tipo;
import org.json.JSONObject;

public class CtrlDetalleVenta implements Controller<DetalleVenta>{
    
    private List<DetalleVenta> lstDetallesVenta;
    
    public CtrlDetalleVenta(){
        lstDetallesVenta = new ArrayList<>();
    }
    
    @Override
    public List<DetalleVenta> list() {
        return lstDetallesVenta;
    }

    @Override
    public boolean add(JSONObject data) {
        boolean flag = false;
        try {
            if(indexOf(data) > -1){
                System.out.println("Esta factura ya fue emitida, se sugiere revisar "
                        + "el consecutivo en la base de datos.");
                flag = true;
            } else {
                lstDetallesVenta.add(new DetalleVenta(data));
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean add(String strData) {
        add(new JSONObject(strData));
        boolean flag = true;
        return flag;
    }

    @Override
    public int indexOf(JSONObject data) {
        int i = -1;
        int search = data.getJSONObject("venta").getInt("consecutivo");
        for(DetalleVenta detalle : lstDetallesVenta){
            if(detalle.getVenta().getConsecutivo() == search){
                i = lstDetallesVenta.indexOf(detalle);
            }
        }
        return i;
    }
    
    /*public int indexOf(JSONObject data) throws JSONException, ParseException {
        int i = -1;

        int consecutivo = data.getJSONObject("venta").getInt("consecutivo");
        Enum tipoBuscado = data.getJSONObject("celular").getEnum(Tipo.class, "tipo");

        for (DetalleVenta dv : lstDetallesVentas) {

            if (dv.getVenta().getConsecutivo() == consecutivo && dv.getCelular().getTipo().equals(tipoBuscado)) {

                i = lstDetallesVentas.indexOf(dv);
                break;
            }

        }

        return i;
    }*/

    @Override
    public int indexOf(String strData) {
        return indexOf(new JSONObject(strData));
    }

    @Override
    public DetalleVenta get(int indice) {
        return lstDetallesVenta.get(indice);
    }

    @Override
    public DetalleVenta set(int indice, JSONObject data) {
        return lstDetallesVenta.set(indice, new DetalleVenta(data));
    }

    @Override
    public DetalleVenta set(int indice, String strData) {
        return set(indice, new JSONObject(strData));
    }

    @Override
    public DetalleVenta remove(int indice) {
        return lstDetallesVenta.remove(indice);
    }

    @Override
    public int size() {
        return lstDetallesVenta.size();
    }
    
    private void calcularPrecios(JSONObject data) {

    }

    private double getPrecioCelular(Tipo tipo, int cantidad, int cantidadA) {

        double precio = 0;

        if (tipo.equals(Tipo.A)) {
            if (cantidad > 3) {
                precio = 120000;
            } else {
                precio = 150000;
            }
        }

        if (tipo.equals(Tipo.B)) {
            if (cantidad > 5) {
                precio = 300000;
            } else {
                precio = 400000;
            }
        }

        if (tipo.equals(Tipo.C)) {
            if (cantidad >= 2) {
                if (cantidadA >= 1) {
                    precio = 1000000;
                }
            } else {
                precio = 1200000;
            }
        }
        return precio;
    }
    
}
