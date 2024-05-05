package org.example.task2;

import java.util.Random;

public class MatrixMultiplicationExperiment {

    private static double[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int randomInt = random.nextInt(5) + 1; // Генеруємо випадкове ціле число від 1 до 5
                matrix[i][j] = randomInt; // Присвоюємо це ціле число як частину дійсного числа
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
    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        //            int[][] matrix1 = {
//                    {23, 2, 3},
//                    {4, 5, 6},
//                    {7, 8, 9}
//            };
//            int[][] matrix2 = {
//                    {2, 2, 2},
//                    {4, 5, 6},
//                    {2, 2, 29}
//            };
//
//            int[][] result = MatrixMultiplier.multiply(matrix1, matrix2, 4);
//            printMatrix(result);
//            int[][] result2 = FoxMatrixMultiplier.multiply(matrix2, matrix1, 1, 4);
        final int[] dimensions = {50,500,1000,1500};
        final int numThreads = Runtime.getRuntime().availableProcessors();

        for (int dim : dimensions) {
            double[][] matrixA = generateRandomMatrix(dim, dim);
            double[][] matrixC = matrixA;
//            System.out.println("---------------------------------------------------");
//            printMatrix(matrixA);
//            System.out.println("---------------------------------------------------");
            double[][] matrixB = generateRandomMatrix(dim, dim);
            double[][] matrixD = matrixB;
//            System.out.println("****************************************************");
//            printMatrix(matrixB);
//            System.out.println("****************************************************");
            long ForkTime = measureExecutionTime(() -> {
                double[][] result1 = ForkFoxMatrixMultiplier.multiply2(matrixA, matrixB, 5,4);
                //printMatrix(result1);
                //System.out.println("Fox>>");
            });
            long foxTime = measureExecutionTime(() -> {
                double[][] result = FoxMatrixMultiplier.multiply2(matrixC, matrixD, 5, 4);
                //printMatrix(result);
            });

            System.out.println("Matrix Dimension: " + dim);
            System.out.println("Fork Fox Algorithm Time: " + ForkTime / 1_000_000 + "ms");
            System.out.println("Fox Algorithm Time: " + foxTime / 1_000_000 + "ms");
            System.out.println();
        }

    }
}
