package org.example;

import java.util.Random;

public class MatrixMultiplicationExperiment {

    private static int[][] generateRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }

    private static long measureExecutionTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        final int[] dimensions = {1000, 1500, 2000, 2500,3000};
        final int numThreads = Runtime.getRuntime().availableProcessors();

        for (int dim : dimensions) {
            int[][] matrixA = generateRandomMatrix(dim, dim);
            int[][] matrixB = generateRandomMatrix(dim, dim);

            long sequentialTime = measureExecutionTime(() -> {
                MatrixMultiplier.multiply(matrixA, matrixB, 9);
            });

            long foxTime = measureExecutionTime(() -> {
                FoxMatrixMultiplier.multiply(matrixA, matrixB, 50, 25);
            });

            System.out.println("Matrix Dimension: " + dim);
            System.out.println("Sequential Algorithm Time: " + sequentialTime / 1_000_000 + "ms");
            System.out.println("Fox Algorithm Time: " + foxTime / 1_000_000 + "ms");
            System.out.println();
        }

    }
}
