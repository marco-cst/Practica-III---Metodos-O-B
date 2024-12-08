

package controller.tda.array;

import controller.tda.array.ArrayPositionException;
import controller.tda.list.ListEmptyException;

import java.util.ArrayList;


import java.util.Arrays;



public class CustomArrayList<T> {
    private Object[] elements;
    private int size;

    public CustomArrayList() {
        elements = new Object[10]; // Tamaño inicial
        size = 0;
    }

    public void addLast(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    public T get(int index) {
        return (T) elements[index];
    }

    public void edit(int index, T element) {
        if (index >= 0 && index < size) {
            elements[index] = element;
        }
    }

    public void delete(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size - 1; i++) {
                elements[i] = elements[i + 1];
            }
            elements[--size] = null; // Eliminar la referencia al último elemento
        }
    }

    public int size() {
        return size;
    }

    // Método para convertir CustomArrayList a un array
    public T[] toArray() {
        return Arrays.copyOf(elements, size, (Class<T[]>) elements.getClass()); // Devuelve un array del tipo correcto
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, size * 2); // Duplicar el tamaño del array
        }
    }
}
