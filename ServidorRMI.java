import java.rmi.Naming;

public class ServidorRMI{
    public static void main(String[] args) throws Exception{
        if(args.length!=1){
            System.out.println("Parámetros incorrectos");
            System.exit(1);
        }
        
        String url = "rmi://localhost/matrices";
        int N= Integer.parseInt(args[0]);
        ClaseRMI obj = new ClaseRMI(N);


        // registra la instancia en el rmiregistry
        Naming.rebind(url,obj);
        System.out.println("Servidor montado");
    }
}
