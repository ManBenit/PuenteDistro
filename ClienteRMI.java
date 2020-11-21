import java.rmi.Naming;
import java.rmi.RemoteException;

public class ClienteRMI{
    private static int A[][];
    private static int B[][];
    private static int C[][];
    private static int N;
    private static String nodo1, nodo2, nodo3;
    
    public static void main(String args[]) throws Exception{
        // obtiene una referencia que "apunta" al objeto remoto asociado a la URL
        InterfaceRMI r;


        if(args.length!=4){
            System.err.println("Uso:\njava ClienteRMI <IPnodo1> <IPnodo2> <IPnodo3> <tamMatriz>");
            System.exit(1);
        }
        else{
            nodo1= args[0];
            nodo2= args[1];
            nodo3= args[2];
            N=Integer.parseInt(args[3]);
            iniciarMatrices();
            
            if(N==4){
                imprimirMatriz(A, "A");
                imprimirMatriz(B, "B");
            }
            traspuestaB();
            if(N==4)
                imprimirMatriz(B, "Traspuesta de B");
            int[][] A1= parteMatriz(A, 0);
            int[][] A2= parteMatriz(A, N/2);
            int[][] B1= parteMatriz(B, 0);
            int[][] B2= parteMatriz(B, N/2);
            
            r= (InterfaceRMI)Naming.lookup(obtUrl(nodo1));
            int[][] C1= r.multiplicaMatrices(A1, B1);
            
            r= (InterfaceRMI)Naming.lookup(obtUrl(nodo2));
            int[][] C2= r.multiplicaMatrices(A1, B2);
            
            r= (InterfaceRMI)Naming.lookup(obtUrl(nodo2));
            int[][] C3= r.multiplicaMatrices(A2, B1);
            
            r= (InterfaceRMI)Naming.lookup(obtUrl(nodo3));
            int[][] C4= r.multiplicaMatrices(A2, B2);
            
            acomodaMatriz(C, C1, 0, 0);
            acomodaMatriz(C, C2, 0, N/2);
            acomodaMatriz(C, C3, N/2, 0);
            acomodaMatriz(C, C4, N/2, N/2);
            
            if(N==4)
                imprimirMatriz(C, "C");
            
            System.out.println("Checksum de C: "+checksum(C));
            
        }

    }
    
    private static String obtUrl(String ip){
        return "rmi://"+ip+"/matrices";
    }
    
    private static long checksum(int[][] M){
        long ret=0;
        for(int i=0; i<M.length; i++)
            for(int j=0; j<M.length; j++)
                ret+=M[i][j];
            
        return ret;
    }

    private static void iniciarMatrices(){
        A= new int[N][N];
        B= new int[N][N];
        C= new int[N][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++){
                A[i][j] = 2 * i - j;
                B[i][j] = 2 * i + j;
            }
    }
    
    private static void traspuestaB(){
        //Calcular traspuesta de la matriz B
        for (int i = 0; i < N; i++)
            for (int j = 0; j < i; j++){
                int x = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = x;
            }
    }
    private static int[][] parteMatriz(int[][] A,int inicio){
        int[][] M = new int[N/2][N];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    }
    
    private static void acomodaMatriz(int[][] C,int[][] A,int renglon,int columna){
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                C[i + renglon][j + columna] = A[i][j];
    }
    
    private static void imprimirMatriz(int[][] M, String nom){
        System.out.println("Matriz "+nom+":");
        for(int i=0; i<M.length; i++){
            for(int j=0; j<M[i].length; j++)
                System.out.print(M[i][j]+"  ");
            System.out.println();
        }
        System.out.println("");
    }//printMatrix
}
