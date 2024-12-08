package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Inversionista;
import models.Persona;
import models.ProyectoEnergia;

public class InversionistaDao extends AdapterDao<Inversionista> {

    private ProyectoEnergia proyecto;
    private LinkedList<Inversionista> listAll;
    private Inversionista inversionista;
    private Persona persona;
    // private int size = 0;

    // public InversionistaDao() {
    //     this.inversionista = new Inversionista();
    // }

    public InversionistaDao(){
        super(Inversionista.class);
        this.listAll = new LinkedList<>();
    }

    public LinkedList<Inversionista> getFullList(){
        if (listAll.isEmpty()) { 
            this.listAll = listAll(); 
        }
        return listAll;
    }

    public Inversionista getInversionista() {
        if (inversionista == null) {
            inversionista = new Inversionista();
        }
        return this.inversionista;
    }

    public void setInversionista(Inversionista inversionista){
        this.inversionista = inversionista;
    }


    // V - PS
    // public Boolean save() throws Exception{
    //     Integer inversionistaId = getInversionista().getSize() + 1;
    //     persona.setIdPersona(inversionistaId);
    //     return true;
    // }

    
    public Boolean save() throws Exception {
        Integer id = getFullList().getSize() + 1;
        inversionista.setIdInversionista(id);
        this.persist(this.inversionista);
        this.listAll = getFullList(); 
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getInversionista(), getInversionista().getIdInversionista() - 1);
            this.listAll = getFullList(); 
            System.out.println("Inversionista actualizado correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar inversionista: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Inversionista> list = getFullList(); 
        Inversionista inversionista= get(id); 
        if (inversionista != null) {
            list.remove(inversionista); 
            String info = g.toJson(list.toArray());
            guardarDatos(info); 
            this.listAll = list;
            return true;
        } else {
            System.out.println("Inversionista con id " + id + " no encontrada.");
            return false;
        }
    }

}
