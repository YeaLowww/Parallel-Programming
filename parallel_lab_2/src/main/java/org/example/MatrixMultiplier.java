package org.example;

import java.util.Random;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
//У цьому прикладі створюється внутрішній клас Result для зберігання результату множення матриць. Клас MatrixMultiplicationTask реалізує інтерфейс Callable, щоб обчислити частину результату множення матриць. Метод multiply створює пул потоків і розподіляє роботу між ними. Результат множення матриць записується у відповідні місця в результуючій матриці.
class MatrixMultiplier {
    private static class Result {
        private final int[][] matrix;

        public Result(int[][] matrix) {
            this.matrix = matrix;
        }

        public int[][] getMatrix() {
            return matrix;
        }
    }

    private static class MatrixMultiplicationTask implements Callable<Result> {
        private final int[][] matrixA;
        private final int[][] matrixB;
        private final int startRow;
        private final int endRow;

        public MatrixMultiplicationTask(int[][] matrixA, int[][] matrixB, int startRow, int endRow) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        public Result call() {
            int[][] result = new int[endRow - startRow][matrixB[0].length];
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < matrixB[0].length; j++) {
                    for (int k = 0; k < matrixA[0].length; k++) {
                        result[i - startRow][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
            return new Result(result);
        }
    }

    public static int[][] multiply(int[][] matrixA, int[][] matrixB, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int numRowsPerThread = matrixA.length / numThreads;
        int remainingRows = matrixA.length % numThreads;
        List<Future<Result>> futures = new ArrayList<>();
        int currentRow = 0;
        for (int i = 0; i < numThreads; i++) {
            int numRows = numRowsPerThread + (i < remainingRows ? 1 : 0);
            futures.add(executor.submit(new MatrixMultiplicationTask(matrixA, matrixB, currentRow, currentRow + numRows)));
            currentRow += numRows;
        }
        int[][] result = new int[matrixA.length][matrixB[0].length];
        try {
            for (int i = 0; i < numThreads; i++) {
                Result res = futures.get(i).get();
                for (int j = 0; j < res.getMatrix().length; j++) {
                    System.arraycopy(res.getMatrix()[j], 0, result[i * numRowsPerThread + j], 0, res.getMatrix()[j].length);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return result;
    }
    public static int[][] generateMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(5) + 1;
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        int SIZE = 1000;
        int[][] matrixA = generateMatrix(SIZE, SIZE);
        int[][] matrixB = generateMatrix(SIZE, SIZE);
        int[][] result = multiply(matrixA, matrixB, 2);
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}
