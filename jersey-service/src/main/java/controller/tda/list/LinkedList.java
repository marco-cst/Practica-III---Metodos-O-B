package controller.tda.list;
import java.lang.reflect.Method;
import java.util.Arrays;

// import controller.tda.list.LinkedList;
// import models.Persona;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;   
    private Integer size;
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    public LinkedList() {
        this.header = null; 
        this.last = null;   
        this.size = 0;     
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.size++;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    public void addLast(E info) { 
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(info);
            header = help;
            last = help;
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
        }
        this.size++;
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return header.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    public E delete(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacía");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (post == 0) {
            return deleteFirst(); 
        } else if (post == (size - 1)) {
            return deleteLast(); 
        } else {
            Node<E> previous = getNode(post - 1); 
            Node<E> current = previous.getNext(); 
            E element = current.getInfo(); 
            Node<E> next = current.getNext();
    
            previous.setNext(next); 
            size--;
            return element; 
        }
    }
    
   public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacía");
        } else {
            E elemnt = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            } 
            size--;
            return elemnt;
            }
        }
        
    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacía");
        } else{
            E elemnt = last.getInfo();
            Node<E> aux = getNode(size -2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                }else {
                header = null;
                }
            }else{
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return elemnt;
        }
    }

    public boolean remove(E element) {
        if (isEmpty()) return false;
        
        if (header.getInfo().equals(element)) { 
            header = header.getNext();
            size--;
            return true;
        }
        Node<E> current = header;
        while (current.getNext() != null) {
            if (current.getNext().getInfo().equals(element)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false; 
    }
    
    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header;
    }

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }


    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }
  
// ===== METODOS ORDENACION ====

// ---- inserccion --- 
    public LinkedList<E> order(int orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            for (int i = 1; i < lista.length; i++) {
                E aux = lista[i];
                int j = i - 1;
                while (j >= 0 && comparar(lista[j], aux, orderType)) {
                    lista[j + 1] = lista[j];
                    j--;
                }
                lista[j + 1] = aux;
            }
            this.toList(lista);
        }
        return this;
    }
   
    public LinkedList<E> order(String atrib, Integer type) throws Exception{
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                for (int i = 1; i < lista.length; i++){
                    E aux = lista[i];
                    int j = i - 1;
                    while (j >= 0 && compararAtributo(atrib, lista[j], aux, type)) {
                        lista[j + 1] = lista[j--];
                    }
                    lista[j + 1] = aux;
                }
                this.toList(lista); 
            } 
        }
        return this;
    }


    // comparar dos objetos
    private Boolean comparar(Object a, Object b, Integer type) {
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof Number && b instanceof Number) {
            Number nroA = (Number) a;
            Number nroB = (Number) b;
            return (type == 1) ? nroA.doubleValue() > nroB.doubleValue() : nroA.doubleValue() < nroB.doubleValue();
        } else if (a instanceof String && b instanceof String) {
            String a1 = (String) a;
            String b2 = (String) b;
            return (type == 1) ? a1.compareTo(b2) > 0 : a1.compareTo(b2) < 0;
        }
    
        return false;
    }

    private Boolean compararAtributo(String atrib, E a, E b, Integer type) throws Exception {
        return (a != null && b != null) && comparar(atributoExistente(a, atrib), atributoExistente(b, atrib), type);
    }

    // obtener valor de atributo
    private Object atributoExistente(E a, String atrib) throws Exception {
        Method method = null;
        atrib = atrib.substring(0, 1).toUpperCase() + atrib.substring(1);
        atrib = "get" + atrib;

        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().equals(atrib)) {
                method = aux;
                break;
            }
        }
        // Si no se encuentra en los metodos de la clase, buscar en los metodos de la superclase
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {
                if (aux.getName().equals(atrib)) {
                    method = aux;
                    break;
                }
            }
        }
    
        if (method != null) {
            return method.invoke(a);
        }
    
        return null;
    }

// --- shellSort ---
    public LinkedList<E> ordShellSort(String atrib, Integer orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            int n = lista.length;
            for (int intervalo = n / 2; intervalo > 0; intervalo /= 2) {
                for (int i = intervalo; i < n; i++) {
                    E temp = lista[i];
                    int j;
                    for (j = i; j >= intervalo && comparar(lista[j - intervalo], temp, orderType); j -= intervalo) {
                        lista[j] = lista[j - intervalo];
                    }
                    lista[j] = temp;
                }
            }
            this.toList(lista);
        }
        return this;
    }

    public LinkedList<E> shellSort(String atrib, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            int n = lista.length;
            // iniciar con un intervalo grande, luego reducir el intervalo
            for (int intervalo = n / 2; intervalo > 0; intervalo /= 2) {
                for (int i = intervalo; i < n; i++) {
                    E temp = lista[i];
                    int j;
                    //comparar elementos en intervalos
                    for (j = i; j >= intervalo && compararAtributo(atrib, lista[j - intervalo], temp, type); j -= intervalo) {
                        lista[j] = lista[j - intervalo];
                    }
                    lista[j] = temp;
                }
            }
            this.toList(lista);
        }
        return this;
    }
    
//--- quickSort ---
    public LinkedList<E> ordQuickSort(int orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            ordQuickSortHelper(lista, 0, lista.length - 1, orderType);
            this.toList(lista);
        }
        return this;
    }
    
    private void ordQuickSortHelper(E[] lista, int start, int end, int orderType) throws Exception {
        if (start < end) {
            int pi = particionar(lista, start, end, orderType);
            ordQuickSortHelper(lista, start, pi - 1, orderType);
            ordQuickSortHelper(lista, pi + 1, end, orderType);
        }
    }
    
    private int particionar(E[] lista, int start, int end, int orderType) throws Exception {
        E pivot = lista[end];
        int i = (start - 1);
        for (int j = start; j < end; j++) {
            if (comparar(lista[j], pivot, orderType)) {
                i++;
                E temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }
        E temp = lista[i + 1];
        lista[i + 1] = lista[end];
        lista[end] = temp;
        
        return i + 1;
    }
    
    public LinkedList<E> ordQuickSort(String atrib, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            ordQuickSortHelperAtrib(lista, 0, lista.length - 1, atrib, type);
            this.toList(lista);
        }
        return this;
    }
    
    private void ordQuickSortHelperAtrib(E[] lista, int start, int end, String atrib, Integer type) throws Exception {
        if (start < end) {
            int pi = particionarAtrib(lista, start, end, atrib, type);
            ordQuickSortHelperAtrib(lista, start, pi - 1, atrib, type);
            ordQuickSortHelperAtrib(lista, pi + 1, end, atrib, type);
        }
    }
    
    private int particionarAtrib(E[] lista, int start, int end, String atrib, Integer type) throws Exception {
        E pivot = lista[end];
        int i = (start - 1);
        
        for (int j = start; j < end; j++) {
            if (compararAtributo(atrib, lista[j], pivot, type)) {
                i++;
                E temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }
        E temp = lista[i + 1];
        lista[i + 1] = lista[end];
        lista[end] = temp;
        
        return i + 1;
    }
    
//--- mergeSort ---
    public LinkedList<E> ordMergeSort(int orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            lista = ordMergeSortHelper(lista, orderType);
            this.toList(lista);
        }
        return this;
    }

    private E[] ordMergeSortHelper(E[] lista, int orderType) throws Exception {
        if (lista.length <= 1) {
            return lista;
        }

        int midIndex = lista.length / 2;
        E[] leftHalf = Arrays.copyOfRange(lista, 0, midIndex);
        E[] rightHalf = Arrays.copyOfRange(lista, midIndex, lista.length);

        leftHalf = ordMergeSortHelper(leftHalf, orderType);
        rightHalf = ordMergeSortHelper(rightHalf, orderType);

        return merge(leftHalf, rightHalf, orderType);
    }

    private E[] merge(E[] leftHalf, E[] rightHalf, int orderType) throws Exception {
        E[] result = (E[]) new Object[leftHalf.length + rightHalf.length];
        int i = 0, j = 0, k = 0;

        while (i < leftHalf.length && j < rightHalf.length) {
            if (comparar(leftHalf[i], rightHalf[j], orderType)) {
                result[k++] = leftHalf[i++];
            } else {
                result[k++] = rightHalf[j++];
            }
        }
        while (i < leftHalf.length) {
            result[k++] = leftHalf[i++];
        }
        while (j < rightHalf.length) {
            result[k++] = rightHalf[j++];
        }
        return result;
    }

    public LinkedList<E> ordMergeSort(String atrib, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            lista = ordMergeSortHelperAtrib(lista, atrib, type);
            this.toList(lista);
        }
        return this;
    }

    private E[] ordMergeSortHelperAtrib(E[] lista, String atrib, Integer type) throws Exception {
        if (lista.length <= 1) {
            return lista;
        }
        int midIndex = lista.length / 2;
        E[] leftHalf = Arrays.copyOfRange(lista, 0, midIndex);
        E[] rightHalf = Arrays.copyOfRange(lista, midIndex, lista.length);
        leftHalf = ordMergeSortHelperAtrib(leftHalf, atrib, type);
        rightHalf = ordMergeSortHelperAtrib(rightHalf, atrib, type);

        return mergeAtrib(leftHalf, rightHalf, atrib, type);
    }

    private E[] mergeAtrib(E[] leftHalf, E[] rightHalf, String atrib, Integer type) throws Exception {
        E[] result = (E[]) new Object[leftHalf.length + rightHalf.length];
        int i = 0, j = 0, k = 0;

        while (i < leftHalf.length && j < rightHalf.length) {
            if (compararAtributo(atrib, leftHalf[i], rightHalf[j], type)) {
                result[k++] = leftHalf[i++];
            } else {
                result[k++] = rightHalf[j++];
            }
        }
        while (i < leftHalf.length) {
            result[k++] = leftHalf[i++];
        }
        while (j < rightHalf.length) {
            result[k++] = rightHalf[j++];
        }
        return result;
    }

//--

    
//metodos ordenacion para comprobar --- TIMEPO DE EJECUCION ---con datos numericos desde consola
    // Implementación de ordQuickSort para datos numéricos
    public static void ordQuickSortNum(int[] array, int start, int end) {
        if (start < end) {
            int pi = partitionNum(array, start, end);
            ordQuickSortNum(array, start, pi - 1);
            ordQuickSortNum(array, pi + 1, end);
        }
    }
    
    private static int partitionNum(int[] array, int start, int end) {
        int pivot = array[end];
        int i = (start - 1);
    
        for (int j = start; j < end; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
    
        int temp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = temp;
    
        return i + 1;
    }
    
    // Implementación de ordMergeSort para datos numéricos
    public static int[] ordMergeSortNum(int[] array) {
        if (array.length <= 1) {
            return array;
        }
    
        int midIndex = array.length / 2;
        int[] leftHalf = new int[midIndex];
        int[] rightHalf = new int[array.length - midIndex];
    
        System.arraycopy(array, 0, leftHalf, 0, midIndex);
        System.arraycopy(array, midIndex, rightHalf, 0, array.length - midIndex);
    
        leftHalf = ordMergeSortNum(leftHalf);
        rightHalf = ordMergeSortNum(rightHalf);
    
        return mergeNum(leftHalf, rightHalf);
    }
    
    private static int[] mergeNum(int[] leftHalf, int[] rightHalf) {
        int[] result = new int[leftHalf.length + rightHalf.length];
        int i = 0, j = 0, k = 0;
    
        while (i < leftHalf.length && j < rightHalf.length) {
            if (leftHalf[i] <= rightHalf[j]) {
                result[k++] = leftHalf[i++];
            } else {
                result[k++] = rightHalf[j++];
            }
        }
        while (i < leftHalf.length) {
            result[k++] = leftHalf[i++];
        }
        while (j < rightHalf.length) {
            result[k++] = rightHalf[j++];
        }
        return result;
    }
    
    // Implementación de ordShellSort para datos numéricos
    public static void ordShellSortNum(int[] array) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                int j;
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }

    }  
}