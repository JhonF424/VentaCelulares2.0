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
    static int consecutivo = 0;

    public static void main(String[] args) throws ParseException {
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
        agregarDatosDePrueba();
        verDatosPrueba();
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
                case 5:
                    break;
                case 6:
                    break;
                case 99:
                    System.exit(0);
                    break;
            }
        } while (true);*/
    }
    
    private static void verDatosPrueba(){
        System.out.println("-".repeat(36));
        System.out.println("ID       NOMBRE      TELÉFONO");
        System.out.println("-".repeat(36));
        for(Cliente cliente : ctrlClientes.list()){
            System.out.printf("", cliente.getNombre());
        }
    }
    
    /*private static String mayorVenta(){
        Venta temporal = new Venta();
        
        for(Venta venta : ctrlVentas.list()){
            if(){
                
            }
        }
    }*/

    private static Tipo masVendido(int a, int b, int c) {
        Tipo masVendido = Tipo.INDEFINIDO;
        if (a > b && a > c) {
            masVendido = Tipo.A;
        } else if (b > a && b > c) {
            masVendido = Tipo.B;
        } else if (c > a && c > b) {
            masVendido = Tipo.C;
        }

        return masVendido;
    }

    private static Tipo intermedio(int a, int b, int c) {
        Tipo tipoIntermedio = Tipo.INDEFINIDO;
        
        if (a > c && a < b) {
            tipoIntermedio = Tipo.A;
        } else if (b > c && b < a) {
            tipoIntermedio = Tipo.B;
        } else if (c > b && c < a) {
            tipoIntermedio = Tipo.C;
        }

        return tipoIntermedio;
    }

    private static void listarVentas() {
        double ventaBase = 0;
        double ventasBaseT = 0;
        double ivaT = 0;
        double netoT = 0;
        double iva;
        double neto;
        for (Venta venta : ctrlVentas.list()) {
            System.out.println("--------------------------------------");
            System.out.printf("Factura No: %d   Cliente:  %s - %n", venta.getConsecutivo(), venta.getCliente());
            System.out.println("--------------------------------------");
            listarVenta(venta, 2);
            ventaBase = listarVenta(venta, 0);
            ventasBaseT += ventaBase;
            iva = ventaBase * 0.19;
            ivaT += iva;
            neto = ventaBase + iva;
            netoT += neto;
            System.out.println("--------------------------------------");
            System.out.printf("     Total venta base: %.0f %n", ventaBase);
            System.out.printf("     Total de IVA:     %.0f %n", iva);
            System.out.printf("     Total Neto:       %.0f %n", neto);
            System.out.println("--------------------------------------");
        }
        System.out.println("");
        System.out.println("--------------------------------------");
        System.out.printf("     Total venta base: %.0f %n", ventasBaseT);
        System.out.printf("     Total de IVA:     %.0f %n", ivaT);
        System.out.printf("     Total Neto:       %.0f %n", netoT);
        System.out.println("--------------------------------------");

    }

    private static double listarVenta(Venta v, int modo) {
        double valorVenta = 0;
        switch (modo) {

            case 0:
                for (DetalleVenta detalle : ctrlDetallesVenta.list()) {
                    if (detalle.getVenta().getConsecutivo() == v.getConsecutivo()) {
                        valorVenta += detalle.getTotal();
                    }
                }
                break;
            case 2:
                System.out.println("CANT. TIPO Vr.UNIT   Vr.TOTAL");
                for (DetalleVenta detalle : ctrlDetallesVenta.list()) {
                    if (detalle.getVenta().getConsecutivo() == v.getConsecutivo()) {
                        System.out.printf("%d       %s    %.0f    %.0f   %n", detalle.getCantidad(), detalle.getCelular().getTipo(), detalle.getPrecio(), detalle.getTotal());
                    }
                }
                break;
            case 1:
                System.out.println("ÍNDICE CANT. TIPO Vr.UNIT   Vr.TOTAL");
                int count = 0;
                for (DetalleVenta detalle : ctrlDetallesVenta.list()) {
                    count++;
                    if (detalle.getVenta().getConsecutivo() == v.getConsecutivo()) {
                        System.out.printf("%d       %d       %s    %.0f    %.0f   %n", count, detalle.getCantidad(), detalle.getCelular().getTipo(), detalle.getPrecio(), detalle.getTotal());
                    }
                }
                break;
        }

        return valorVenta;
    }

    public static void agregarCelulares() {
        ctrlCelulares.add(new JSONObject(new Celular(Tipo.A, 111)));
        ctrlCelulares.add(new JSONObject(new Celular(Tipo.B, 111)));
        ctrlCelulares.add(new JSONObject(new Celular(Tipo.C, 111)));
    }

    public static void agregarClientes() {
        ctrlClientes.add(new JSONObject(new Cliente("C01", "Carlos Cuesta", "3138447845")));
        ctrlClientes.add(new JSONObject(new Cliente("C02", "Ricardo Cuesta", "3138447845")));
        ctrlClientes.add(new JSONObject(new Cliente("C03", "Ricardo Pérez", "3137771234")));
        ctrlClientes.add(new JSONObject(new Cliente("C04", "Alberto Gomez", "3138447845")));
    }

    public static void agregarVentas() {
        if (ctrlVentas.add(new Venta(consecutivo, Calendar.getInstance(), ctrlClientes.get(0)).getJSONObject())) {
            consecutivo++;
            agregarDetalleVenta(0, 0, 2);
            agregarDetalleVenta(0, 1, 4);
            agregarDetalleVenta(0, 0, 1);
            agregarDetalleVenta(0, 1, 1);
            agregarDetalleVenta(0, 2, 1);

        }

        if (ctrlVentas.add(new Venta(consecutivo, Calendar.getInstance(), ctrlClientes.get(1)).getJSONObject())) {
            consecutivo++;
            agregarDetalleVenta(1, 0, 2);
            agregarDetalleVenta(1, 1, 4);
            agregarDetalleVenta(1, 0, 2);
            agregarDetalleVenta(1, 1, 3);
            agregarDetalleVenta(1, 2, 1);
        }

        if (ctrlVentas.add(new Venta(consecutivo, Calendar.getInstance(), ctrlClientes.get(2)).getJSONObject())) {
            consecutivo++;
            agregarDetalleVenta(2, 2, 2);
            agregarDetalleVenta(2, 0, 2);
            agregarDetalleVenta(2, 2, 2);
            agregarDetalleVenta(2, 1, 4);
            agregarDetalleVenta(2, 0, 2);
            agregarDetalleVenta(2, 1, 3);
        }

        if (ctrlVentas.add(new Venta(consecutivo, Calendar.getInstance(), ctrlClientes.get(3)).getJSONObject())) {
            consecutivo++;
            agregarDetalleVenta(3, 2, 1);
            agregarDetalleVenta(3, 1, 1);
            agregarDetalleVenta(3, 0, 1);
        }
    }

    public static void agregarDetalleVenta(int indiceVenta, int indiceCelular, int cantidad) {
        Celular celular = ctrlCelulares.get(indiceCelular);
        boolean flag = ctrlDetallesVenta.add(
                new JSONObject()
                        .put("venta", ctrlVentas.get(indiceVenta).getJSONObject())
                        .put("celular", celular.getJSONObject())
                        .put("cantidad", cantidad)
        );

        if (flag) {
            celular.setCantidad(celular.getCantidad() - cantidad);
        }
    }

    public static void agregarDatosDePrueba() {
        agregarClientes();
        agregarCelulares();
        agregarVentas();

        for (DetalleVenta detalle : ctrlDetallesVenta.list()) {
            System.out.println(detalle.getJSONObject());
        }

        try {
            int i = ctrlDetallesVenta.indexOf(
                    "{ \"venta\": { \"consecutivo\": 1 }, \"celular\": { \"tipo\": \"A\" } }"
            );
            System.out.println(i == -1 ? "No encontrado" : "Encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void iniciarControladores() {
        ctrlClientes = new CtrlClientes();
        ctrlCelulares = new CtrlCelulares();
        ctrlVentas = new CtrlVenta();
        ctrlDetallesVenta = new CtrlDetalleVenta();
    }

    public static int leerOpcion() {
        String menu = "Menú de opciones\n"
                + "1 - Ver datos de prueba\n"
                + "2 - Agregar existencias\n"
                + "3 - Agregar cliente\n"
                + "4 - Realizar ventas\n"
                + "5 - Registrar devolución\n"
                + "6 - Informe\n"
                + "99. Salir";

        System.out.println("-".repeat(100));
        System.out.print(menu);
        System.out.println(" > ");
        int opcion = scan.nextInt();
        System.out.println();
        return opcion;
    }
}
