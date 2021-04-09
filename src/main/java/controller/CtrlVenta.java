package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import model.*;
import org.json.JSONObject;

public class CtrlVenta implements Controller<Venta> {

    private List<Venta> lstVentas;

    public CtrlVenta() {
        lstVentas = new ArrayList<>();
    }

    @Override
    public List<Venta> list() {
        return lstVentas;
    }

    @Override
    public boolean add(JSONObject data) {
        boolean flag = false;
        try {
            if (indexOf(data) > -1) {
                System.out.println("Esta venta ya fue hecha con anterioridad, por favor"
                        + " revise el consecutivo en la base de datos.");
                flag = false;
            } else {
                lstVentas.add(new Venta(data));
                flag = true;
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
        int search = data.getInt("consecutivo");
        for (Venta vn : lstVentas) {
            if (vn.getConsecutivo() == search) {
                i = lstVentas.indexOf(vn);
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
    public Venta get(int indice) {
        return lstVentas.get(indice);
    }

    @Override
    public Venta set(int indice, JSONObject data) {
        try {
            return lstVentas.set(indice, new Venta(data));
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Venta set(int indice, String strData) {
        return set(indice, new JSONObject(strData));
    }

    @Override
    public Venta remove(int indice) {
        return lstVentas.remove(indice);
    }

    @Override
    public int size() {
        return lstVentas.size();
    }

}
