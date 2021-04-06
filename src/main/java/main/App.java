package main;

import org.json.JSONObject;
import java.util.*;
import model.*;
import controller.*;
public class App {
    static Scanner scan = new Scanner(System.in);
    static JSONObject data = new JSONObject();
    static CtrlClientes ctrlClientes = new CtrlClientes();
    public static void main(String[] args) {
        scan.useDelimiter("[\n]+|[\r\n]+");
    }
}
