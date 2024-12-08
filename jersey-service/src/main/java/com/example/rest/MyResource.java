package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import controller.Dao.servicies.InversionistaServices;
import controller.Dao.servicies.ProyectoEnergiaServicies; 
import controller.tda.list.LinkedList;
import models.ProyectoEnergia;  

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {

        HashMap<String, Object> mapa = new HashMap<>();
        ProyectoEnergiaServicies pd = new ProyectoEnergiaServicies();
        String aux = "";

        try {
            aux = "La lista de ProyectoEnergias está vacía: " + pd.listAll().isEmpty();
            System.out.println("Verificación de lista vacía: " + aux); 
            // Obtener lista de Proyectos
            LinkedList<ProyectoEnergia> ProyectoEnergias = pd.listAll();
            pd.getProyectoEnergia().setNombre("Molinos eolicos del sur");
            pd.getProyectoEnergia().setInversion(222332);
            pd.getProyectoEnergia().setTiempoVida(15);
            pd.getProyectoEnergia().setTiempoInicioConstruccion("23-08-2019");
            pd.getProyectoEnergia().setTiempoFinConstruccion("23-08-2028");
            pd.getProyectoEnergia().setInversionistas("Alexander");
            pd.getProyectoEnergia().setMontoInversion(22342);
            pd.getProyectoEnergia().setUbicacion("Loja");
            pd.save();

            aux = "La lista contiene " + ProyectoEnergias.getSize() + " ProyectoEnergias.";
        } catch (Exception e) {
            e.printStackTrace();
            mapa.put("msg", "Error");
            mapa.put("error", e.getMessage() != null ? e.getMessage() : "Error desconocido");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        mapa.put("msg", "Ok");
        mapa.put("data", aux);

        return Response.ok(mapa).build();
    }

    @GET
    @Path("/saveInversionista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveInversionista() {
        HashMap<String, String> mapa = new HashMap<>();
        InversionistaServices pd = new InversionistaServices();
        String aux = "";

        try {
            pd.getInversionista().setNombre("Heydi");
            pd.getInversionista().setApellido("Flores");
            pd.getInversionista().setDNI("12355438");
            pd.getInversionista().setTipoInversionista("Publico");
            pd.getInversionista().setMontoInvertido(5000.56f);
            pd.getInversionista().setTipoInversionista("privado");
            pd.save();

            aux = "Persona inversionista guardada: ";

        } catch (Exception e) {
            System.out.println("Error al guardar: " + e);
            mapa.put("msg", "error");
            mapa.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        mapa.put("msg", "ok");
        mapa.put("data", aux);
        return Response.ok(mapa).build();
    }
}