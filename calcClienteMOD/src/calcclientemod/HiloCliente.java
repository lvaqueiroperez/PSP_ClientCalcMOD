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

//EN ESTA CLASE MANEJAREMOS LOS HILOS DEL CLIENTE
public class HiloCliente extends Thread {

    //PARA IDENTIFICAR EL TIPO DE OPERACIÓN QUE SE VA A HACER (USANDO UI)
    public static int op = 0;

    //constructor para usar el getName()
    public HiloCliente(String nombre) {

        super(nombre);

    }

    @Override
    public synchronized void run() {

        //CADA HILO TENDRÁ SU FUNCIÓN
        if (getName().equals("ui")) {

            UIcalc1 obj = new UIcalc1();
            obj.setVisible(true);
            //COMENZAMOS EL OTRO HILO A TRAVÉS DE LA UI

        } else if (getName().equals("cliente")) {
            //EL MÉTODO RUN SE PUEDE PONER COMO SYNC ???

            try {

                System.out.println("***** Creando socket cliente *****");
                Socket clienteSocket = new Socket();
                System.out.println("***** Estableciendo la conexión *****");

                InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
                clienteSocket.connect(addr);

                InputStream is = clienteSocket.getInputStream();
                OutputStream os = clienteSocket.getOutputStream();

                //OJO, MIENTRAS SE ESTÉ EJECUTANTO LA VENTANA DE LA UI, EL PROCESO DEL 
                //CLIENTE NO PUEDE SEGUIR AVANZANDO !!!
                //OPERACIÓN (A TRAVÉS DE UI)
                System.out.println("***** Enviando operación *****");

                int operacion = op;
                //(podríamos comprobar que se ha introducido el dato correcto con un bucle etc etc etc)

                //MANDAMOS EL MENSAJE AL SERVIDOR A TRAVÉS DE "write()",  SE MANDARÁ EN FORMATO DE BYTES 
                //"write()" PUEDE ENVIAR MENSAJES TANTO EN UN ARRAY BYTES COMO EN UN SIMPLE INT
                os.write(operacion);

                System.out.println("***** Mensaje enviado *****");

                try {
                    //OJO!!! DEJAMOS UN MARGEN DE SEGUNDOS PARA QUE SE ENVÍE EL MENSAJE Y NO HAYA ERRORES
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                }

                //ENVIAMOS LOS OPERADORES TAMBIÉN COMO INTEGERS
                //OPERADOR 1
                System.out.println("***** Enviando operador 1 *****");

                int operador1 = Integer.parseInt(JOptionPane.showInputDialog("ESCRIBE EL OPERADOR 1: (INT)"));
                //COMO EN ESTE CASO ESTAMOS ENVIANDO UN STRING, TENEMOS QUE CONVERTIRLO A BYTES
                os.write(operador1);

                System.out.println("***** Operador 1 enviado *****");

                try {
                    //OPERADOR 2
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("***** Enviando operador 2 *****");
                int operador2 = Integer.parseInt(JOptionPane.showInputDialog("ESCRIBE EL OPERADOR 2: (INT)"));

                os.write(operador2);

                System.out.println("***** Operador 2 enviado *****");

                //ESPERAMOS AL RESULTADO DEL SERVIDOR: (ESTARÁ EN BYTES PERO PODEMOS PASARLO DIRECTAMENTE A INT CON EL MÉTODO DE INPUT STREAM)
                int resultado = is.read();
                //PARSEAMOS EL STRING DE BYTES A STRING
                System.out.println("***** ESPERANDO RESULTADO *****");
                System.out.println("Mensaje recibido: EL RESULTADO ES = " + resultado);

                //CERRAMOS EL SOCKET DEL CLIENTE
                System.out.println("***** Cerrando el socket cliente *****");

                clienteSocket.close();

                System.out.println("Terminado");

                //NO SE CIERRA ???
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
