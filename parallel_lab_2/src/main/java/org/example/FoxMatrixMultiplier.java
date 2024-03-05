package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

class FoxMatrixMultiplier {
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
        private final int blockSize;
        private final int rowOffset;
        private final int colOffset;

        public MatrixMultiplicationTask(int[][] matrixA, int[][] matrixB, int blockSize, int rowOffset, int colOffset) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.blockSize = blockSize;
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
        }

        @Override
        public Result call() {
            int[][] result = new int[blockSize][blockSize];
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    for (int k = 0; k < blockSize; k++) {
                        result[i][j] += matrixA[rowOffset + i][k + colOffset] * matrixB[k + rowOffset][colOffset + j];
                    }
                }
            }
            return new Result(result);
        }
    }

    public static int[][] multiply(int[][] matrixA, int[][] matrixB, int blockSize, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int[][] result = new int[matrixA.length][matrixB[0].length];
        try {
            for (int k = 0; k < matrixA.length; k += blockSize) {
                for (int i = 0; i < matrixA.length; i += blockSize) {
                    List<Future<Result>> futures = new ArrayList<>();
                    for (int j = 0; j < matrixB[0].length; j += blockSize) {
                        futures.add(executor.submit(new MatrixMultiplicationTask(matrixA, matrixB, blockSize, i, j)));
                    }
                    for (int j = 0; j < futures.size(); j++) {
                        Result res = futures.get(j).get();
                        for (int x = 0; x < blockSize; x++) {
                            System.arraycopy(res.getMatrix()[x], 0, result[i + x], j * blockSize, blockSize);
                        }
                    }
                }
                matrixB = rotateMatrix(matrixB, blockSize);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        return result;
    }

    private static int[][] rotateMatrix(int[][] matrix, int blockSize) {
        int[][] rotated = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i += blockSize) {
            for (int j = 0; j < matrix[0].length; j += blockSize) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        rotated[i + y][j + blockSize - 1 - x] = matrix[i + x][j + y];
                    }
                }
            }
        }
        return rotated;
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
        int[][] result = multiply(matrixA, matrixB, 100, 2);
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}