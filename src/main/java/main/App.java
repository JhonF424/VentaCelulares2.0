package main;

import org.json.JSONObject;
import java.util.*;
import controller.*;
import java.text.ParseException;
import model.*;
import resources.Helpers;
public class App {

    static Scanner scan = new Scanner(System.in);
    static JSONObject data = new JSONObject();
    static CtrlClientes ctrlClientes;
    static CtrlCelulares ctrlCelulares;
    static CtrlVenta ctrlVentas;
    static CtrlDetalleVenta ctrlDetallesVenta;
    

    public static void main(String[] args) throws ParseException{
        scan.useDelimiter("[\n]+|[\r\n]+");
        
        /*
        --------------------------------------------------------------
        TESTING
        --------------------------------------------------------------
        Cliente a = new Cliente("C01", "Carlos Cuesta", "3138447845");
        Cliente b = new Cliente("C01", "Ricardo Pérez", "3137771234");
        Cliente c = a;
        System.out.println(a.equals(b)); // deber mostrar true
        System.out.println(a.equals(c)); // deber mostrar true 
        
        Celular c1 = new Celular(Tipo.A, 111);
        Celular c2 = new Celular(Tipo.A, 777);
        Celular c3 = c2;
        System.out.println(c1.equals(c2)); // deber mostrar true
        System.out.println(c1.equals(c3)); // deber mostrar true 
        
        Venta v1 = new Venta(1, Calendar.getInstance(), a);
        Venta v2 = new Venta(1, Helpers.getFecha("1890-01-01"), b);
        Venta v3 = v1;
        System.out.println(v1.equals(v2)); // deber mostrar true
        System.out.println(v1.equals(v3)); // deber mostrar true 
        
        DetalleVenta dv1 = new DetalleVenta(v1, c1, 44);
        DetalleVenta dv2 = new DetalleVenta(v1, c1, 66);
        DetalleVenta dv3 = dv1;
        System.out.println(dv1.equals(dv2)); // deber mostrar true
        System.out.println(dv1.equals(dv3)); // deber mostrar true 
        
        ctrlCelulares.add(new JSONObject(new Celular(Tipo.A, 111)));
        ctrlClientes.add(new JSONObject(new Cliente("C01", "Carlos Cuesta", "3138447845")));
        ctrlVentas.add(new Venta(1, Calendar.getInstance(),ctrlClientes.get(0)).getJSONObject);
        
        System.out.println(ctrlDetallesVenta.getPrecioCelular(Tipo.A, 3, 99999)); //  150000
        System.out.println(ctrlDetallesVenta.getPrecioCelular(Tipo.A, 5, 99999)); //  120000
        System.out.println(ctrlDetallesVenta.getPrecioCelular(Tipo.B, 5, 99999)); //  400000
        System.out.println(ctrlDetallesVenta.getPrecioCelular(Tipo.B, 8, 99999)); //  300000
        System.out.println(ctrlDetallesVenta.getPrecioCelular(Tipo.C, 1, 1));     // 1200000
        System.out.println(ctrlDetallesVenta.getPrecioCelular(Tipo.C, 2, 1));     // 1000000
        --------------------------------------------------------------
        */
        
        
        iniciarControladores();
        ctrlCelulares.add(new JSONObject(new Celular(Tipo.A, 111)));
        ctrlClientes.add(new JSONObject(new Cliente("C01", "Carlos Cuesta", "3138447845")));
        data.put("consecutivo", 1);
        data.put("fecha", Calendar.getInstance());
        data.put("cliente", ctrlClientes.get(0));
        ctrlVentas.add(data);
        /*do {
            int opt = leerOpcion();
            switch (opt) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 99:
                    System.exit(0);
                    break;
            }
        } while (true);*/

    }
    
    public static void iniciarControladores(){
        ctrlClientes = new CtrlClientes();
        ctrlCelulares = new CtrlCelulares();
        ctrlVentas = new CtrlVenta();
        ctrlDetallesVenta = new CtrlDetalleVenta();
    }

    public static int leerOpcion() {
        String menu = "Menú de opciones\n"
                + "99. Salir";

        System.out.println("-".repeat(100));
        System.out.print(menu);
        System.out.println(" > ");
        int opcion = scan.nextInt();
        System.out.println();
        return opcion;
    }
}
