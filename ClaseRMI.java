import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI{
    private static int N;
    
    public ClaseRMI(int N) throws RemoteException{
        super();
        this.N=N;
    }
    
    @Override
    public int[][] multiplicaMatrices(int[][] A,int[][] B) throws RemoteException{
        int[][] C = new int[N/2][N/2];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                for (int k = 0; k < N; k++)
                    C[i][j] += A[i][k] * B[j][k];
        return C;
    }

}
