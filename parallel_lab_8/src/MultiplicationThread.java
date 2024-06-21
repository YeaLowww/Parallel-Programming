class MultiplicationThread implements Runnable {
    private final double[][] matrixA;
    private final double[][] matrixB;
    private final double[][] result;
    private final int firstRow;
    private final int lastRow;

    public MultiplicationThread(double[][] matrixA, double[][] matrixB, double[][] result, int firstRow, int lastRow) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
        this.firstRow = firstRow;
        this.lastRow = lastRow;
    }

    @Override
    public void run() {
        int cols = matrixB[0].length;
        int commonDim = matrixA[0].length;
        for (int i = firstRow; i < lastRow; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = 0;
                for (int k = 0; k < commonDim; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
}