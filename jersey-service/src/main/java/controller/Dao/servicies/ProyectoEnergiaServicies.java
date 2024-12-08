package controller.Dao.servicies;

import controller.Dao.ProyectoEnergiaDao;
import controller.tda.list.LinkedList;
import models.ProyectoEnergia;

public class ProyectoEnergiaServicies {
    private ProyectoEnergiaDao obj;

    public ProyectoEnergiaServicies() {
        obj = new ProyectoEnergiaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id);
    }

    public LinkedList<ProyectoEnergia> listAll() {
        return obj.getFullList();
    }

    public ProyectoEnergia getProyectoEnergia() {
        return obj.getProyectoEnergia();
    }

    public void setProyectoEnergia(ProyectoEnergia ProyectoEnergia) {
        obj.setProyectoEnergia(ProyectoEnergia);
    }

    public ProyectoEnergia get(Integer id) throws Exception {
        return (ProyectoEnergia) obj.get(id);
    }

    public LinkedList<ProyectoEnergia> busquedaNombreProyecto(String texto) {
        return obj.busquedaNombreProyecto(texto);
    }

    public LinkedList<ProyectoEnergia> busquedaInversionsita(String texto) {
        return obj.busquedaInversionsita(texto);
    }

    public LinkedList<ProyectoEnergia>busquedaLinealNombre (String texto) {
        return obj.busquedaLinealNombre(texto);
    }

    public LinkedList<ProyectoEnergia>busquedaBinariaNombre (String texto) {
        return obj.busquedaBinariaNombre(texto);
    }
    public LinkedList<ProyectoEnergia> busquedaLineaInversionistas(String texto) {
        return obj.busquedaLineaInversionistas(texto);
    }

    public LinkedList<ProyectoEnergia> busquedaBinariaInversionista(String texto) {
        return obj.busquedaBinariaInversionista(texto);
    }

    public LinkedList ordenacionQuicksort(Integer type_order, String atributo) {
        return obj.ordenacionQuicksort(type_order, atributo);
    }

    public LinkedList ordenacionMergesort(Integer type_order, String atributo) {
        return obj.ordenacionMergesort(type_order, atributo);
    }

    public LinkedList orderShell(Integer type_order, String atributo) {
        return obj.orderShell(type_order, atributo);
    }
    
}
