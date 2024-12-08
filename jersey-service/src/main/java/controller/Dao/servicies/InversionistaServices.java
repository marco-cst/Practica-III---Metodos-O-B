package controller.Dao.servicies;

import controller.Dao.InversionistaDao;
import controller.tda.list.LinkedList;
import models.Inversionista;
// import models.Persona;

public class InversionistaServices {

    private InversionistaDao obj;

    public InversionistaServices() {
        obj = new InversionistaDao();
    }
    
    public LinkedList<Inversionista> listAll() {
        return obj.getFullList();
    }

    public Inversionista getInversionista() {
        return obj.getInversionista();
    }

    public void setInversionista(Inversionista inversionista) {
        obj.setInversionista(inversionista);
    }

    public Inversionista get(Integer id) throws Exception {
        return obj.get(id);
    }

    //V - PD

    // public Boolean save() throws Exception{
    //     return this.personaDao.save();
    // }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id);
    }

}