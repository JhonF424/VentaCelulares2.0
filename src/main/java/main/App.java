package main;

import org.json.JSONObject;
import java.util.*;
import controller.*;
import java.text.ParseException;
import model.*;

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

        iniciarControladores();
        agregarDatosDePrueba();

        do {
            int opt = leerOpcion();
            switch (opt) {
                case 1:
                    verDatosPrueba();
                    break;
                case 2:
                    agregarExistencias();
                    break;
                case 3:
                    agregarCliente();
                    break;
                case 4:
                    realizarVenta();
                    break;
                case 5:
                    agregarDevoluciones();
                    break;
                case 6:
                    estadisticas();
                    break;
                case 99:
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    private static void estadisticas() {
        System.out.println("Estadísticas: \n");
        int countA = 0, countB = 0, countC = 0;
        for (DetalleVenta detalle : ctrlDetallesVenta.list()) {

            if (detalle.getCelular().getTipo() == Tipo.A) {
                countA++;
            } else if (detalle.getCelular().getTipo() == Tipo.B) {
                countB++;
            } else if (detalle.getCelular().getTipo() == Tipo.C) {
                countC++;
            }
        }

        System.out.println("Celulares vendidos del tipo A: " + countA);
        System.out.println("Celulares vendidos del tipo B: " + countB);
        System.out.println("Celulares vendidos del tipo C: " + countC);
        System.out.println("");
        System.out.println("El tipo de celular más vendido fue el: " + masVendido(countA, countB, countC));
        System.out.println("El tipo que no fue el más vendido, pero tampoco el menos vendido fue el: " + intermedio(countA, countB, countC));
        System.out.println("");
        double costoA = -150000;
        double costoB = -400000;
        double costoC = -1200000;
        for (DetalleVenta detalle : ctrlDetallesVenta.list()) {
            if(detalle.getCelular().getTipo().equals(Tipo.A)){
                costoA += detalle.getTotal();
            }
            if(detalle.getCelular().getTipo().equals(Tipo.B)){
                costoB += detalle.getTotal();
            }
            if(detalle.getCelular().getTipo().equals(Tipo.C)){
                costoC += detalle.getTotal();
            }
        }
        System.out.println("Total venta de celulares de tipo A: " + costoA);
        System.out.println("Total venta de celulares de tipo B: " + costoB);
        System.out.println("Total venta de celulares de tipo C: " + costoC);
        System.out.println("");
        System.out.println(mayorVenta());
    }


    private static void realizarVenta() {
        int count = 0;
        data.put("consecutivo", ctrlVentas.size() + 1);
        data.put("fecha", Calendar.getInstance());
        System.out.println("Clientes: ");
        for (Object cliente : ctrlClientes.list()) {
            count++;
            System.out.printf("%d - %s %n", count, cliente);
        }
        System.out.println("Seleccione el índice del cliente al que le hará la venta: ");
        int indiceCliente = scan.nextInt();
        data.put("cliente", new JSONObject(ctrlClientes.get(indiceCliente)));
        ctrlVentas.add(data);
        boolean salida = false;
        int cantidad = 0;
        int indiceCelular = 0;
        do {
            System.out.println("Tipo de celular: (A | B | C)");
            String tipo = scan.next().toUpperCase();
            switch (tipo) {
                case "A":
                    indiceCelular = 0;
                    System.out.println("Ingrese la cantidad a comprar: ");
                    cantidad = scan.nextInt();
                    break;
                case "B":
                    indiceCelular = 1;
                    System.out.println("Ingrese la cantidad a comprar: ");
                    cantidad = scan.nextInt();
                    break;
                case "C":
                    indiceCelular = 2;
                    System.out.println("Ingrese la cantidad a comprar: ");
                    cantidad = scan.nextInt();
                    break;
                default:
                    salida = false;
            }
        } while (salida == true);

        agregarDetalleVenta(ctrlVentas.size() -1, indiceCelular, cantidad);
        
    }

    private static void agregarDevoluciones() {
        double totalBase = 0;
        double totalIVA = 0;
        double totalNeto = 0;
        int count = 0;
        System.out.println("Devolución");
        for (Venta venta : ctrlVentas.list()) {
            count++;
            System.out.printf("%d %d %s %s", count, venta.getConsecutivo(), venta.getFecha(), venta.getCliente());
        }
        System.out.print("Seleccione el índice de la venta: ");
        int indiceVenta = scan.nextInt();

        for (int i = 0; i < ctrlVentas.size(); i++) {
            if (ctrlVentas.get(i).equals(ctrlVentas.get(indiceVenta))) {
                System.out.println("-".repeat(34));
                System.out.println(ctrlVentas.get(i));
                System.out.println("-".repeat(34));
                System.out.println("INDICE CANT. TIPO Vr. UNIT. Vr. TOTAL");
                System.out.println("-".repeat(34));
                listarVenta(ctrlVentas.get(i), 1);
                totalBase = listarVenta(ctrlVentas.get(i), 0);
                totalIVA = totalBase * 0.19;
                totalNeto = totalBase * 1.19;
                System.out.println("-".repeat(34));
                System.out.printf("Total base venta: %s", totalBase);
                System.out.printf("%nTotal IVA: %s", totalIVA);
                System.out.printf("%nTotal neto %s %n", totalNeto);
                System.out.println("-".repeat(34));
                System.out.println();
                System.out.print("Indice del detalle afectado con la devolución: ");
                int indiceDetalle = scan.nextInt();
                System.out.printf("Cuántos celulares devuelve (Hasta %d): ",
                        ctrlDetallesVenta.get(indiceDetalle).getCantidad());
                int cantidad = scan.nextInt();
                if (cantidad <= ctrlDetallesVenta.get(indiceDetalle).getCantidad()) {
                    ctrlDetallesVenta.get(indiceDetalle)
                            .setCantidad(ctrlDetallesVenta.get(indiceDetalle).getCantidad() - cantidad);
                } else {
                    System.out.println("Error en la cantidad de celulares.");
                }
            }
        }
    }

    private static void agregarCliente() {
        System.out.println("ID del cliente: ");
        data.put("identificacion", scan.next());
        System.out.println("Nombre del cliente: ");
        data.put("nombre", scan.next());
        System.out.println("Teléfono del cliente: ");
        data.put("telefono", scan.next());
        ctrlClientes.add((data));
    }

    private static void agregarExistencias() {
        System.out.println("Tipo de celular: (A | B | C)");
        data.put("tipo", scan.next().toUpperCase());
        int indice = ctrlCelulares.indexOf(data);
        Celular celular = ctrlCelulares.get(indice);
        System.out.println("Cantidad de celulares: ");
        int cantidad = scan.nextInt();
        celular.setCantidad(celular.getCantidad() + cantidad);
        ctrlCelulares.set(indice, celular.getJSONObject());
    }

    private static void verDatosPrueba() {
        System.out.println("-".repeat(30));
        System.out.println("ID       NOMBRE      TELÉFONO");
        System.out.println("-".repeat(30));
        for (Object cliente : ctrlClientes.list()) {
            System.out.printf("%s %n", cliente);
        }
        System.out.println("");

        System.out.println("-".repeat(18));
        System.out.println("TIPO     CANTIDAD");
        System.out.println("-".repeat(18));
        for (Celular celular : ctrlCelulares.list()) {
            System.out.printf(" %s           %s %n", celular.getTipo(), celular.getCantidad());
        }
        System.out.println("-".repeat(18));

        System.out.println("");

        for (Venta venta : ctrlVentas.list()) {
            listarVenta(ctrlVentas.get(0), 2);
            break;
        }
    }

    private static String mayorVenta() {
        double temporal = 0;
        double mayor = 0;
        int contVenta = 0;
        int consecMayor = 0;
        for (Venta venta : ctrlVentas.list()) {
            contVenta++;
            temporal = listarVenta(venta, 0);
            if (temporal > mayor) {
                mayor = temporal;
                consecMayor = contVenta;
            }
        }
        return "La factura " + consecMayor + "es la de mayor valor hasta ahora con un total"
                + "de " + mayor;
    }

    private static Tipo masVendido(int a, int b, int c) {
        Tipo masVendido = Tipo.INDEFINIDO;
        if (a > b && a > c) {
            masVendido = Tipo.A;
        } else if (b > a && b > c) {
            masVendido = Tipo.B;
        } else if (c > a && c > b) {
            masVendido = Tipo.C;
        } else {
            System.out.println("debug");
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
            System.out.printf("Factura No: %d Fecha: %s %nCliente:  %s - %s %n", venta.getConsecutivo(), venta.getFecha(), venta.getCliente().getIdentificacion(), venta.getCliente().getNombre());
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
        double iva = 0;
        double neto = 0;
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
                        valorVenta += detalle.getTotal();
                        iva = valorVenta * 0.19;
                        neto = valorVenta + iva;
                    }
                }
                System.out.println("--------------------------------------");
                System.out.printf("     Total venta base: %.0f %n", valorVenta);
                System.out.printf("     Total de IVA:     %.0f %n", iva);
                System.out.printf("     Total Neto:       %.0f %n", neto);
                System.out.println("--------------------------------------");
                break;
            case 1:
                System.out.println("ÍNDICE CANT. TIPO Vr.UNIT   Vr.TOTAL");
                int count = 0;
                for (DetalleVenta detalle : ctrlDetallesVenta.list()) {
                    count++;
                    if (detalle.getVenta().getConsecutivo() == v.getConsecutivo()) {
                        System.out.printf("%d       %d       %s    %.0f    %.0f   %n", count, detalle.getCantidad(), detalle.getCelular().getTipo(), detalle.getPrecio(), detalle.getTotal());
                        valorVenta += detalle.getTotal();
                        iva = valorVenta * 0.19;
                        neto = valorVenta + iva;
                    }
                }
                System.out.println("--------------------------------------");
                System.out.printf("     Total venta base: %.0f %n", valorVenta);
                System.out.printf("     Total de IVA:     %.0f %n", iva);
                System.out.printf("     Total Neto:       %.0f %n", neto);
                System.out.println("--------------------------------------");
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
