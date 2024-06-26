public class Generation {
    public static double[][] identicalRows(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (double) i + 1;
            }
        }
        return matrix;
    }
    public static double[][] mainDiagonal(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++ ){
                if (i == j) {
                    matrix[i][j] = 2;
                }
                else {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }
}