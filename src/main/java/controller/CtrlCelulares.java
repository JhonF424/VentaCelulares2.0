package controller;

import java.util.ArrayList;
import java.util.List;
import model.Celular;
import model.Tipo;
import org.json.JSONObject;

public class CtrlCelulares implements Controller<Celular> {

    private List<Celular> lstCelulares;
    
    public CtrlCelulares(){
        lstCelulares = new ArrayList<>();
    }
    @Override
    public List<Celular> list() {
        return lstCelulares;
    }

    @Override
    public boolean add(JSONObject data) {
        boolean flag = false;
        try {
            if (indexOf(data) > -1) {
                System.out.println("El celular ya se encuentra registrado en la base de datos.");
                flag = false;
            } else {
                lstCelulares.add(new Celular(data));
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
        Tipo search = data.getEnum(Tipo.class,"tipo");
        for(Celular cel : lstCelulares){
            if(cel.getTipo().equals(search)){
                i = lstCelulares.indexOf(cel);
            }
        }
        
        return i;
    }

    @Override
    public int indexOf(String strData) {
        return indexOf(new JSONObject(strData));
    }

    @Override
    public Celular get(int indice) {
        return lstCelulares.get(indice);
    }

    @Override
    public Celular set(int indice, JSONObject data) {
        return lstCelulares.set(indice, new Celular(data));
    }

    @Override
    public Celular set(int indice, String strData) {
        return set(indice, new JSONObject(strData));
    }

    @Override
    public Celular remove(int indice) {
        return lstCelulares.remove(indice);
    }

    @Override
    public int size() {
        return lstCelulares.size();
    }

}
