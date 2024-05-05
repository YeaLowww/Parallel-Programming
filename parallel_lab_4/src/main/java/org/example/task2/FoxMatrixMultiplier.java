package org.example.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class FoxMatrixMultiplier {
    private static class Result {
        private final double[][] matrix;
        private final int rowOffset;
        private final int colOffset;

        public Result(double[][] matrix, int rowOffset, int colOffset) {
            this.matrix = matrix;
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
        }

        public double[][] getMatrix() {
            return matrix;
        }

        public int getRowOffset() {
            return rowOffset;
        }

        public int getColOffset() {
            return colOffset;
        }
    }

    private static class MatrixMultiplicationTask implements Callable<Result> {
        private final double[][] matrixA;
        private final double[][] matrixB;
        private final int rowOffset;
        private final int colOffset;
        private final int k;
        private final int blockSize;

        public MatrixMultiplicationTask(double[][] matrixA, double[][] matrixB, int rowOffset, int colOffset, int k, int blockSize) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
            this.k = k;
            this.blockSize = blockSize;
        }

        @Override
        public Result call() {
            double[][] result = new double[blockSize][blockSize];
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    for (int x = 0; x < blockSize; x++) {
                        result[i][j] += matrixA[rowOffset + i][k + x] * matrixB[k + x][colOffset + j];
                    }
                }
            }
            return new Result(result, rowOffset, colOffset);
        }
    }

    public static double[][] multiply2(double[][] matrixA, double[][] matrixB, int blockSize, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        double[][] result = new double[matrixA.length][matrixB[0].length];
        try {
            for (int i = 0; i < matrixA.length; i += blockSize) {
                for (int j = 0; j < matrixB[0].length; j += blockSize) {
                    List<Future<Result>> futures = new ArrayList<>();
                    for (int k = 0; k < matrixA[0].length; k += blockSize) {
                        futures.add(executor.submit(new MatrixMultiplicationTask(matrixA, matrixB, i, j, k, blockSize)));
                    }
                    for (Future<Result> future : futures) {
                        Result res = future.get();
                        for (int x = 0; x < blockSize; x++) {
                            for (int y = 0; y < blockSize; y++) {
                                result[res.getRowOffset() + x][res.getColOffset() + y] += res.getMatrix()[x][y];
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return result;
    }
    private static double[][] generateMatrix(int rows, int cols) {
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
    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double[][] matrix1 = {
                {23, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] matrix2 = {
                {2, 25, 2},
                {4, 25, 6},
                {2, 25, 29}
        };

        double[][] result = FoxMatrixMultiplier.multiply2(matrix1, matrix2, 1, 4);
        printMatrix(result);
//        int SIZE = 100;
//        double[][] matrixA = generateMatrix(SIZE, SIZE);
//        //printMatrix(matrixA);
//        double[][] matrixB = generateMatrix(SIZE, SIZE);
//        //printMatrix(matrixB);
//        long startTime = System.nanoTime();
//        double[][] result = multiply2(matrixA, matrixB, 50, 9);
//        long endTime = System.nanoTime();
//        System.out.println("Fox Algorithm Time: " + (endTime-startTime) / 1_000_000 + "ms");
//        //printMatrix(result);
    }
}