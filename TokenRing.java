
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TokenRing{
    private static DataInputStream entrada;
    private static DataOutputStream salida;
    private static boolean primera_vez = true;
    private static String ip;
    private static long token = 0;
    private static int nodo;

    private static class Worker extends Thread{
        public void run(){
            //Algoritmo 1
            try{
                ServerSocket servidor= new ServerSocket(50000);
                Socket conexion= servidor.accept();
                entrada= new DataInputStream(conexion.getInputStream());
            }catch(IOException ex){
                System.out.println("Error al conectar");
            }
        }
    }

    public static void main(String[] args) throws Exception{
        if (args.length != 2){
            System.err.println("Se debe pasar como parametros el numero de nodo y la IP del siguiente nodo");
            System.exit(1);
        }

        nodo = Integer.valueOf(args[0]);  // el primer parametro es el numero de nodo
        ip = args[1];  // el segundo parametro es la IP del siguiente nodo en el anillo

        //Algoritmo 2
        Worker w= new Worker();
        w.start();
        Socket conexion=null;
        
        while(true){
            try{
                conexion= new Socket(ip, nodo);
                break;
            }catch(IOException ex){
                System.out.println("Reconectando...");
                Thread.sleep(500);
            }
        }
        
        salida= new DataOutputStream(conexion.getOutputStream());
        w.join();
        
        while(true){
            if(nodo==0)
                if(primera_vez)
                    primera_vez= false;
                else
                    token=entrada.readLong();
            else
                token=entrada.readLong();
            
            token+=1;
            System.out.println("Token: "+token);
            salida.writeLong(token);
        }
    }
}