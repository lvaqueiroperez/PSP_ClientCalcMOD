package calcclientemod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//USAREMOS HILOS PARA IMPEDIR QUE EL PROGRAMA AVANCE SIN QUE SE HAYA ESCOGIDO LA OPERACIÓN
//yield??? join???
//HAY UNA MANERA MÁS CORTA??
public class CalcClienteMOD {

    public static void main(String[] args) throws InterruptedException {

        HiloCliente hU = new HiloCliente("ui");

        hU.start();

        //COMENZAMOS EL HILO QUE USARÁ EL CLIENTE PARA CONECTARSE AL SERVIDOR
        //EN LA CLASE DE LA UI, SEGÚN LA OPCIÓN QUE ESCOJAMOS
    }

}
