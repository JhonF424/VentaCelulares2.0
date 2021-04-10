package controller;

import java.util.ArrayList;
import java.util.List;
import model.DetalleVenta;
import model.Tipo;
import org.json.JSONObject;

public class CtrlDetalleVenta implements Controller<DetalleVenta> {

    private List<DetalleVenta> lstDetallesVenta;

    public CtrlDetalleVenta() {
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
            if (data.getInt("cantidad") > 0) {
                int cantidad = data.getInt("cantidad");
                int indexDetalle = indexOf(data);
                if (indexDetalle > -1) {
                    DetalleVenta detalle = lstDetallesVenta.get(indexDetalle);
                    cantidad += detalle.getCantidad();
                    detalle.setCantidad(cantidad);
                    flag = true;
                } else {
                    flag = lstDetallesVenta.add(new DetalleVenta(data));
                }
                calcularPrecios(data);
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
        Enum search2 = data.getJSONObject("celular").getEnum(Tipo.class, "tipo");
        for (DetalleVenta detalle : lstDetallesVenta) {
            if (detalle.getVenta().getConsecutivo() == search && detalle.getCelular().getTipo().equals(search2)) {
                i = lstDetallesVenta.indexOf(detalle);
                break;
            }
        }
        return i;
    }

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
        try {
            return lstDetallesVenta.set(indice, new DetalleVenta(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        int cantidadA;
        int consecutivo = data.getJSONObject("venta").getInt("consecutivo");
        String jsonTipoA = String.format("{ \"venta\": { \"consecutivo\": %d }, \"celular\": { \"tipo\": \"A\" } }", consecutivo);
        int indexA = indexOf(jsonTipoA);
        
        if(indexA == -1){
            cantidadA = 0;
        } else{
            cantidadA = lstDetallesVenta.get(indexA).getCantidad();
        }
        
        for(DetalleVenta detalle : lstDetallesVenta){
            if(detalle.getVenta().getConsecutivo() == consecutivo){
                Tipo tipo = detalle.getCelular().getTipo();
                int cantidad = detalle.getCantidad();
                detalle.setPrecio(getPrecioCelular(tipo, cantidad, cantidadA));
            }
        }
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
