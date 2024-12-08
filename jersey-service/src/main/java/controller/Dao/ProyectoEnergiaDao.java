package controller.Dao;

/*
 * En los Metodos de ordenacion
 * 1 Ascentente
 * 0 Descendente
 */


import java.util.Arrays;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.ProyectoEnergia;

public class ProyectoEnergiaDao<E> extends AdapterDao<ProyectoEnergia> {
    private ProyectoEnergia ProyectoEnergia; 
    // private LinkedList<ProyectoEnergia> proyectos = new LinkedList<>();
    private LinkedList<ProyectoEnergia> listAll;

    public ProyectoEnergiaDao() {
        super(ProyectoEnergia.class);
        this.listAll = new LinkedList<>();
    }

    public ProyectoEnergia getProyectoEnergia() {
        if (ProyectoEnergia == null) {
            ProyectoEnergia = new ProyectoEnergia();
        }
        return this.ProyectoEnergia;
    }

    public void setProyectoEnergia(ProyectoEnergia ProyectoEnergia) {
        this.ProyectoEnergia = ProyectoEnergia;
    }

    public LinkedList<ProyectoEnergia> getFullList() {
        if (listAll == null || listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }
//========ordenacion========

    private Boolean verify(ProyectoEnergia a, ProyectoEnergia b, Integer type_order, String atributo) {
        //verifica si los proyectos son nulos
        if (a == null || b == null)
        return false;

            if (type_order == 1) {
            switch(atributo.toLowerCase()) {
                case "nombre":
                    return a.getNombre() != null && b.getNombre() != null && 
                            a.getNombre().compareToIgnoreCase(b.getNombre()) > 0;
                case "inversionistas":
                    return a.getInversionistas() != null && b.getInversionistas() != null && 
                            a.getInversionistas().compareToIgnoreCase(b.getInversionistas()) > 0;            
                default:
                    return false;
            }
        } else {
            switch(atributo.toLowerCase()) {
                case "nombre":
                    return a.getNombre() != null && b.getNombre() != null && 
                            a.getNombre().compareToIgnoreCase(b.getNombre()) < 0;
                case "inversionistas":
                    return a.getInversionistas() != null && b.getInversionistas() != null && 
                            a.getInversionistas().compareToIgnoreCase(b.getInversionistas()) < 0;
                default:
                    return false;
            }
        }
    }

//save, update, delete
    public Boolean save() throws Exception {
        if (ProyectoEnergia == null) {
            throw new IllegalStateException("ATENCION! ProyectoEnergia no inicializado");
        }
        Integer id = getFullList().getSize() + 1; //id y tamaño de la lista
        ProyectoEnergia.setIdProyectoEnergia(id);
        this.persist(ProyectoEnergia); //Guarda el Pr.Enrg
        this.listAll = getFullList(); // Actualiza lista de Pr.Enrg
        return true;
    }

    public Boolean update() throws Exception {
        try {
            if (ProyectoEnergia == null) {
                throw new IllegalStateException("ATENCION! Proyecto Energia no inicializado");
            }
            //actualiza el Pr.Enrg
            this.merge(getProyectoEnergia(), getProyectoEnergia().getIdProyectoEnergia() - 1);
            this.listAll = getFullList();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar el Proyecto Energia.");
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<ProyectoEnergia> list = getFullList();
        ProyectoEnergia ProyectoEnergia = get(id);
        if (ProyectoEnergia != null) {
            list.remove(ProyectoEnergia);
            String info = g.toJson(list.toArray());
            guardarDatos(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Error, No se encontro el proyecto");
            return false;
        }
    }

//========ordenacion========p 4

//--- Quiksort ---
    public LinkedList<ProyectoEnergia> ordenacionQuicksort(Integer type_order, String atributo) {
        LinkedList<ProyectoEnergia> listita = new LinkedList<>();
        for (int i = 0; i < listAll().getSize(); i++) {  // nueva lista con datos actuales
            try {
                listita.add(listAll().get(i));
            } catch (Exception e) {
                System.out.println("Error al agregar elemento");
                e.printStackTrace();
            }
        }
        
        if (!listita.isEmpty()) {
            ProyectoEnergia[] lista = (ProyectoEnergia[]) listita.toArray();
            ordQuickSort(lista, 0, lista.length - 1, type_order, atributo); //rango de incio y indice final [].
            listita = new LinkedList<>();
            listita.toList(lista);
        }
        return listita;
    }

    // quickSort para arreglo proyectos
    private void ordQuickSort(ProyectoEnergia[] lista, int start, int end, Integer type_order, String atributo) {
        if (start < end) {
            int pivoteIndice = particionar(lista, start, end, type_order, atributo); // Particionar el arreglo
            ordQuickSort(lista, start, pivoteIndice - 1, type_order, atributo); 
            ordQuickSort(lista, pivoteIndice + 1, end, type_order, atributo); 
        }
    }

    // particion QuickSort
    private int particionar(ProyectoEnergia[] lista, int start, int end, Integer type_order, String atributo) {
        ProyectoEnergia pivot = lista[end];
        int i = start - 1; 

        for (int j = start; j < end; j++) {    // Recorrer arreglo y comparar con el pivote
            if (verify(lista[j], pivot, type_order, atributo)) {
                i++;
                ProyectoEnergia temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }
        ProyectoEnergia temp = lista[i + 1]; //ubicar pivote en su lugar
        lista[i + 1] = lista[end];
        lista[end] = temp;

        return i + 1;
    }

//---  mergeSort --- 
    public LinkedList<ProyectoEnergia> ordenacionMergesort(Integer type_order, String atributo) {
        LinkedList<ProyectoEnergia> listita = new LinkedList<>();

        for (int i = 0; i < listAll().getSize(); i++) { // nueva lista con datos actuales
            try {
                listita.add(listAll().get(i));
            } catch (Exception e) {
                System.out.println("Error al agregar elemento");
                e.printStackTrace();
            }
        }

        if (!listita.isEmpty()) { // Convertir a arreglo y ordenar
            ProyectoEnergia[] lista = (ProyectoEnergia[]) listita.toArray();
            lista = ordMergeSort(lista, atributo, type_order); // mergeSort
            listita = new LinkedList<>();
            listita.toList(lista);
        }
        return listita;
    }

    // mergeSort para arreglo
    private ProyectoEnergia[] ordMergeSort(ProyectoEnergia[] lista, String atributo, Integer type_order) {
        if (lista.length <= 1) {
            return lista;
        }
        int midIndex = lista.length / 2;  //Dividir el arreglo en dos mitades
        ProyectoEnergia[] leftHalf = Arrays.copyOfRange(lista, 0, midIndex); 
        ProyectoEnergia[] rightHalf = Arrays.copyOfRange(lista, midIndex, lista.length); 

        leftHalf = ordMergeSort(leftHalf, atributo, type_order); 
        rightHalf = ordMergeSort(rightHalf, atributo, type_order);

        return merge(leftHalf, rightHalf, atributo, type_order); //unir las partes ordenadas (izq, der)
    }

//--- ShellSort ---
    public LinkedList<ProyectoEnergia> orderShell(Integer type_order, String atributo) {
        LinkedList<ProyectoEnergia> listita = new LinkedList<>();
    
        for (int i = 0; i < listAll().getSize(); i++) {  // nueva lista con datos actuales
            try {
                listita.add(listAll().get(i));
            } catch (Exception e) {
                System.out.println("Error al agregar elemento");
                e.printStackTrace();
            }
        }
    
        if (!listita.isEmpty()) { // Si la lista no esta vacia se ordenara
            ProyectoEnergia[] lista = (ProyectoEnergia[]) listita.toArray(); 
            lista = shellSort(lista, atributo, type_order); 
            listita = new LinkedList<>(); 
            listita.toList(lista);
        }
    
        return listita;
    }
    
    // shellSort para ordenar arreglo
    private ProyectoEnergia[] shellSort(ProyectoEnergia[] lista, String atributo, Integer type_order) {
        int n = lista.length;
    
        for (int intervalo = n / 2; intervalo > 0; intervalo /= 2) {  // iniciar intervalo de shellSort
            for (int i = intervalo; i < n; i++) {
                ProyectoEnergia temp = lista[i];
                int j;
    
                for (j = i; j >= intervalo && verify(lista[j - intervalo], temp, type_order, atributo); j -= intervalo) {
                    lista[j] = lista[j - intervalo];
                }
                lista[j] = temp;
            }
        }
        return lista;
    }

//========busqueda========
    public LinkedList<ProyectoEnergia> busquedaNombreProyecto(String texto) {
        LinkedList<ProyectoEnergia> lista = new LinkedList<>();
        LinkedList<ProyectoEnergia> listita = listAll();
        
        if (!listita.isEmpty() && texto != null) { // Si la lista no esta vacia y el texto no es nulo se buscara
            ProyectoEnergia[] aux = listita.toArray();
            for (ProyectoEnergia ProyectoEnergia : aux) {
                if (ProyectoEnergia != null && ProyectoEnergia.getNombre() != null && 
                    ProyectoEnergia.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(ProyectoEnergia);
                }
            }
        }
        return lista;
    }

    public LinkedList<ProyectoEnergia> busquedaInversionsita(String texto) {
        LinkedList<ProyectoEnergia> lista = new LinkedList<>();
        LinkedList<ProyectoEnergia> listita = listAll();
        
        if (!listita.isEmpty() && texto != null) {  // Si la lista no esta vacia y el texto no es nulo se buscara
            ProyectoEnergia[] aux = listita.toArray();
            for (ProyectoEnergia ProyectoEnergia : aux) {
                if (ProyectoEnergia != null && ProyectoEnergia.getInversionistas() != null && 
                    ProyectoEnergia.getInversionistas().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(ProyectoEnergia);
                }
            }
        }
        return lista;
    }

    // para combinar 2 subarreglos ordenados
    private ProyectoEnergia[] merge(ProyectoEnergia[] leftHalf, ProyectoEnergia[] rightHalf, String atributo, Integer type_order) {
        ProyectoEnergia[] result = new ProyectoEnergia[leftHalf.length + rightHalf.length];
        int i = 0, j = 0, k = 0;
    
        while (i < leftHalf.length && j < rightHalf.length) {
            if (verify(leftHalf[i], rightHalf[j], type_order, atributo)) {
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

// --- busqueda lineal --- Nommbre & Inversionista
    public LinkedList<ProyectoEnergia> busquedaLinealNombre(String texto) {
        LinkedList<ProyectoEnergia> lista = new LinkedList<>();
        LinkedList<ProyectoEnergia> listita = listAll(); 
    
        if (!listita.isEmpty() && texto != null) {
            for (ProyectoEnergia ProyectoEnergia : listita.toArray()) {
                if (ProyectoEnergia != null && ProyectoEnergia.getNombre() != null &&
                    ProyectoEnergia.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(ProyectoEnergia);
                }
            }
        }
        return lista;
    }

    public LinkedList<ProyectoEnergia> busquedaLineaInversionistas(String texto) {
        LinkedList<ProyectoEnergia> lista = new LinkedList<>();
        LinkedList<ProyectoEnergia> listita = listAll();
    
        if (!listita.isEmpty() && texto != null) {
            for (ProyectoEnergia ProyectoEnergia : listita.toArray()) {
                if (ProyectoEnergia != null && ProyectoEnergia.getInversionistas() != null &&
                    ProyectoEnergia.getInversionistas().toLowerCase().contains(texto.toLowerCase())) {
                    lista.add(ProyectoEnergia);
                }
            }
        }
        return lista;
    }

// --- busqueda binaria ---
    public LinkedList<ProyectoEnergia> busquedaBinariaNombre(String texto) {
        LinkedList<ProyectoEnergia> lista = new LinkedList<>();
        LinkedList<ProyectoEnergia> listita = listAll();
        if (!listita.isEmpty() && texto != null) {
            ProyectoEnergia[] aux = new ProyectoEnergia[listita.getSize()]; //de lista enlazada a array.
            int index = 0;
            for (ProyectoEnergia ProyectoEnergia : listita.toArray()) {
                aux[index++] = ProyectoEnergia;
            }
            int izq = 0;
            int der = aux.length - 1;
            while (izq <= der) {
                int mitad = izq + (der - izq) / 2;
                ProyectoEnergia ProyectoEnergiaMitad = aux[mitad];
    
                if (ProyectoEnergiaMitad != null && ProyectoEnergiaMitad.getNombre() != null) {
                    String nombreMitad = ProyectoEnergiaMitad.getNombre().toLowerCase();
                    //comparar si el nombre contiene el texto
                    if (nombreMitad.contains(texto.toLowerCase())) {
                        lista.add(ProyectoEnergiaMitad);
                        int i = mitad - 1;
                        while (i >= 0 && aux[i].getNombre().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[i]);
                            i--;
                        }
                        int j = mitad + 1;
                        while (j < aux.length && aux[j].getNombre().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[j]);
                            j++;
                        }
                        break;
                    } else if (nombreMitad.compareTo(texto.toLowerCase()) < 0) {
                        izq = mitad + 1;
                    } else {
                        der = mitad - 1;
                    }
                } else {
                    break;
                }
            }
        }
        return lista;
    }

    public LinkedList<ProyectoEnergia> busquedaBinariaInversionista(String texto) {
        LinkedList<ProyectoEnergia> lista = new LinkedList<>();
        LinkedList<ProyectoEnergia> listita = listAll();
    
        if (!listita.isEmpty() && texto != null) {
            ProyectoEnergia[] aux = new ProyectoEnergia[listita.getSize()]; // de lsita a array
            int index = 0;
            for (ProyectoEnergia ProyectoEnergia : listita.toArray()) {
                aux[index++] = ProyectoEnergia;
            }
            int izq = 0;
            int der = aux.length - 1;

            while (izq <= der) { // realizar la busqueda en el array
                int mitad = izq + (der - izq) / 2;
                ProyectoEnergia ProyectoEnergiaMitad = aux[mitad];
    
                if (ProyectoEnergiaMitad != null && ProyectoEnergiaMitad.getInversionistas() != null) {
                    String nombreMitad = ProyectoEnergiaMitad.getInversionistas().toLowerCase();
                    if (nombreMitad.contains(texto.toLowerCase())) {
                        lista.add(ProyectoEnergiaMitad);
                        int i = mitad - 1; // en caso de existir coindencias expandir hacia ambos lados
                        while (i >= 0 && aux[i].getInversionistas().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[i]);
                            i--;
                        }
                        int j = mitad + 1; 
                        while (j < aux.length && aux[j].getInversionistas().toLowerCase().contains(texto.toLowerCase())) {
                            lista.add(aux[j]);
                            j++;
                        }
                        break;
                    } else if (nombreMitad.compareTo(texto.toLowerCase()) < 0) {
                        izq = mitad + 1;
                    } else {
                        der = mitad - 1;
                    }
                } else {
                    break;
                }
            }
        }
        return lista;
    }



    
//---- para comprobar con datos numericos
    // busqueda secuencial
    public boolean busquedaLinealAr(int[] array, int target) {
        for (int value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }

    // busqueda binaria
    public boolean busquedaBinaria(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return true;
            }
            if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}