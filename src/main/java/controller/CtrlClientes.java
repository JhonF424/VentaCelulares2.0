package controller;

import java.util.*;
import org.json.JSONObject;
import model.*;

public class CtrlClientes implements Controller<Cliente> {

    private List<Cliente> lstClientes;
    
    public CtrlClientes(){
        lstClientes = new ArrayList<>();
    }
    
    @Override
    public List list() {
        return lstClientes;
    }

    @Override
    public boolean add(JSONObject data) {
        boolean flag = false;
        try {
            if (indexOf(data) > -1) {
                System.out.println("El cliente ya se encuentra registrado en la base de datos.");
                flag = false;
            } else {
                lstClientes.add(new Cliente(data));
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
        String search = data.getString("identificacion");
        for (Cliente cl : lstClientes) {
            if (cl.getIdentificacion().equals(search)) {
                i = lstClientes.indexOf(cl);
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
    public Cliente get(int indice) {
        return lstClientes.get(indice);
    }

    @Override
    public Cliente set(int indice, JSONObject data) {
        return lstClientes.set(indice, new Cliente(data));
    }

    @Override
    public Cliente set(int indice, String strData) {
        return set(indice, new JSONObject(strData));
    }

    @Override
    public Cliente remove(int indice) {
        return lstClientes.remove(indice);
    }

    @Override
    public int size() {
        return lstClientes.size();
    }

}
