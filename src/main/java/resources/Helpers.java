package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Carlos Cuesta Iglesias
 */
public class Helpers {

    public static final String mensaje = "\nMétodo sin implementar.\n" 
        + "Por favor revise la traza de este error para saber qué y en qué\n" 
        + "parte se le está solicitando implementar la funcionalidad requerida:";

    public static String strFecha(Calendar fecha) {
        return (
            new SimpleDateFormat("yyyy-MM-dd")
        ).format(fecha.getTime());
    }

    public static Calendar getFecha(String strFecha) throws ParseException {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(
            new SimpleDateFormat("yyyy-MM-dd").parse(strFecha)
        );
        return fecha;
    }

    public static String hoy() {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    public static String strFechaHora(Calendar fechaHora) {
        return (
            new SimpleDateFormat("yyyy-MM-dd hh:mm a")
        ).format(fechaHora.getTime());
    }

    public static Calendar getFechaHora(String strFechaHora) throws ParseException {
        Calendar fechaHora = Calendar.getInstance();
        fechaHora.setTime(
            new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(strFechaHora)
        );//                        
        return fechaHora;
    }

    

    public static long horasIntervalo(Calendar fechaHora1, Calendar fechaHora2) {
        long milisegundos = fechaHora1.getTimeInMillis() - fechaHora2.getTimeInMillis();
        return Math.round(milisegundos / 3600000);
    }

    public static boolean hayCruce(Calendar fechaHora1Inicio, Calendar fechaHora1Fin, 
                                    Calendar fechaHora2Inicio, Calendar fechaHora2Fin) {
        return fechaHora2Inicio.before(fechaHora1Fin) && fechaHora2Fin.after(fechaHora1Fin); 
    }


}
