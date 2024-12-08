package com.example.rest;

import java.util.Formatter;
import java.util.Random;

import controller.Dao.ProyectoEnergiaDao;
import controller.tda.list.LinkedList;

public class pruebasTiempos {

    public static void main(String[] args) {
        ProyectoEnergiaDao dao = new ProyectoEnergiaDao();
        int[] sizes = {10000, 20000, 25000};

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        String headerFormat = "| %-15s | %-25s | %-25s | %-25s | %-25s | %-25s |\n";
        String rowFormat = "| %-15d | %-25d | %-25d | %-25d | %-25d | %-25d |\n";
        String separator = "+-----------------+---------------------------+---------------------------+---------------------------+---------------------------+---------------------------+\n";

        sb.append(separator);
        formatter.format(headerFormat, "Tamaño", "Búsqueda Lineal (ns)", "Búsqueda Binaria (ns)", "Ordenamiento Quicksort (ns)", "Ordenamiento Mergesort (ns)", "Ordenamiento Shellsort (ns)");
        sb.append(separator);

        for (int size : sizes) {
            int[] array = generateRandomArray(size);
            int target = array[new Random().nextInt(size)];
//tiempo Busqueda

            // lineal
            long startTime = System.nanoTime();
            boolean foundSequential = dao.busquedaLinealAr(array, target);
            long endTime = System.nanoTime();
            long sequentialTime = endTime - startTime;

            // binaria
            startTime = System.nanoTime();
            boolean foundBinary = dao.busquedaBinaria(array, target);
            endTime = System.nanoTime();
            long binaryTime = endTime - startTime;

//tiempo Ordenamiento
            // QuickSort
            int[] arrayCopy = array.clone();
            startTime = System.nanoTime();
            LinkedList.ordQuickSortNum(arrayCopy, 0, arrayCopy.length - 1);
            endTime = System.nanoTime();
            long ordQuickSortTime = endTime - startTime;

            // MergeSort
            arrayCopy = array.clone();
            startTime = System.nanoTime();
            LinkedList.ordMergeSortNum(arrayCopy);
            endTime = System.nanoTime();
            long ordMergeSortTime = endTime - startTime;

            // ShellSort
            arrayCopy = array.clone();
            startTime = System.nanoTime();
            LinkedList.ordShellSortNum(arrayCopy);
            endTime = System.nanoTime();
            long ordShellSortTime = endTime - startTime;

            // Agregar los resultados a la tabla
            formatter.format(rowFormat, size, sequentialTime, binaryTime, ordQuickSortTime, ordMergeSortTime, ordShellSortTime);
            sb.append(separator);
        }

        System.out.println(sb.toString());
        formatter.close();
    }

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size * 10);
        }
        return array;
    }
}