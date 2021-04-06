
package controller;

import java.util.List;
import org.json.JSONObject;

public interface Controller<T> {
    
    public List<T> list();

    public boolean add(JSONObject data);

    public boolean add(String strData);

    public int indexOf(JSONObject data);

    public int indexOf(String strData);

    public T get(int indice);

    public T set(int indice, JSONObject data);

    public T set(int indice, String strData);

    public T remove(int indice);

    public int size();

}
