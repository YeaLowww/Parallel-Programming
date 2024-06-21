import mpi.MPI;

public class Multiplicator {

    final static int MATRIX_SIZE = 1000;
    final static int MASTER = 0;

    public static void main(String[] args) {

        MPI.Init(args);

        final int rank = MPI.COMM_WORLD.Rank();
        final int size = MPI.COMM_WORLD.Size();

        if (MATRIX_SIZE % size != 0) {
            System.out.println("Number of rows and number of processors " +
                    "must be equally distributed!!!");
            MPI.COMM_WORLD.Abort(-1);
            return;
        }

        final int rowsPerProcess = MATRIX_SIZE / size;

        int[][] matrixA = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] matrixB = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] matrixAPart = new int[rowsPerProcess][MATRIX_SIZE];
        int[][] resultPart = new int[rowsPerProcess][MATRIX_SIZE];

        final long start;

        if (rank == MASTER) {
            matrixA = Utils.generateRandomMatrix(MATRIX_SIZE,1000);
            matrixB = Utils.generateRandomMatrix(MATRIX_SIZE,1000);

            start = System.currentTimeMillis();
        } else {
            start = 0;
        }

        MPI.COMM_WORLD.Bcast(matrixB, 0, matrixB.length, MPI.OBJECT, MASTER);
        MPI.COMM_WORLD.Scatter(matrixA, 0, rowsPerProcess, MPI.OBJECT,
                matrixAPart, 0, rowsPerProcess, MPI.OBJECT, MASTER);

        for (int i = 0; i < rowsPerProcess; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                int sum = 0;
                for (int k = 0; k < MATRIX_SIZE; k++) {
                    sum += matrixAPart[i][k] * matrixB[k][j];
                }
                resultPart[i][j] = sum;
            }
        }

        MPI.COMM_WORLD.Gather(resultPart, 0, rowsPerProcess, MPI.OBJECT,
                result, 0, rowsPerProcess, MPI.OBJECT, MASTER);

        if (rank == MASTER) {
            final long elapsed = System.currentTimeMillis() - start;
            System.out.println(String.format(
                    "----------------------------------------------- \n"+
                            "Matrix size: %dx%d \n" +
                            "Process count: %d \n" +
                            "Time elapsed: %f ms!",
                    MATRIX_SIZE, MATRIX_SIZE, size, (1.0 * elapsed )));
        }

        MPI.Finalize();
    }
}