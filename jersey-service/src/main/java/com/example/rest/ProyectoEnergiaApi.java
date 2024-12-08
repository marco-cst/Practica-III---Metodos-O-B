package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import controller.Dao.servicies.ProyectoEnergiaServicies;
import controller.tda.list.LinkedList;
import models.ProyectoEnergia;

@Path("ProyectoEnergia")
public class ProyectoEnergiaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyectoEnergias() {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyectoEnergia(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
            ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        try {
            ps.setProyectoEnergia(ps.get(id));
        } catch (Exception e) {

        }
        map.put("msg", "Ok");
        map.put("data", ps.getProyectoEnergia());
        if (ps.getProyectoEnergia() == null || ps.getProyectoEnergia().getIdProyectoEnergia() == 0) {
            map.put("msg", "No se encontró ProyectoEnergia con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, String> res = new HashMap<>();
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        System.out.println(" " + jsonString);
    
        try {
            ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
            ProyectoEnergia ProyectoEnergia = ps.getProyectoEnergia();
            ProyectoEnergia.setNombre(map.get("nombre").toString());
            ProyectoEnergia.setInversionistas(map.get("inversionistas").toString());
            ProyectoEnergia.setInversion(Double.parseDouble(map.get("inversion").toString()));
            ProyectoEnergia.setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
            ProyectoEnergia.setTiempoInicioConstruccion(map.get("tiempoInicioConstruccion").toString());
            ProyectoEnergia.setTiempoFinConstruccion(map.get("tiempoFinConstruccion").toString()); 
            ProyectoEnergia.setCapacidadGeneracionDiaria(Double.parseDouble(map.get("capacidadGeneracionDiaria").toString()));
            ProyectoEnergia.setMontoInversion(Double.parseDouble(map.get("montoInversion").toString()));
            ProyectoEnergia.setUbicacion(map.get("ubicacion").toString());
            ps.save();
    
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();
    
        } catch (NumberFormatException e) {
            res.put("msg", "Error");
            res.put("data", "Formato de número inválido: " + e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(res).build();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error");
            res.put("data", "Error en save data: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, String> res = new HashMap<>();
        try {
            ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
            int idProyectoEnergia = Integer.parseInt(map.get("idProyectoEnergia").toString());
            ProyectoEnergia ProyectoEnergia = ps.get(idProyectoEnergia);

            if (ProyectoEnergia != null) {
                ps.setProyectoEnergia(ProyectoEnergia);
                ps.getProyectoEnergia().setNombre(map.get("nombre").toString());
                ps.getProyectoEnergia().setInversion(Double.parseDouble(map.get("inversion").toString()));
                ps.getProyectoEnergia().setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
                ps.getProyectoEnergia().setTiempoInicioConstruccion(map.get("tiempoInicioConstruccion").toString());
                ps.getProyectoEnergia().setTiempoFinConstruccion(map.get("tiempoFinConstruccion").toString());
                ps.getProyectoEnergia().setInversionistas(map.get("inversionistas").toString());
                ps.getProyectoEnergia().setCapacidadGeneracionDiaria(Double.parseDouble(map.get("capacidadGeneracionDiaria").toString()));
                ps.getProyectoEnergia().setMontoInversion(Double.parseDouble(map.get("montoInversion").toString()));
                ps.getProyectoEnergia().setUbicacion(map.get("ubicacion").toString());
                if (ps.update()) {
                    res.put("msg", "Ok");
                    res.put("data", "Guardado correctamente");
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error");
                    res.put("data", "error al intentar actualizar.");
                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
                }
            } else {
                res.put("msg", "Error");
                res.put("data", "ProyectoEnergia no encontrado.");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error");
            res.put("data", "Error en save " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @DELETE 
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProyectoEnergia(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();
        
        try {
            ProyectoEnergiaServicies fs = new ProyectoEnergiaServicies();
                if (id <= 0) {
                res.put("message", "Error");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            boolean ProyectoEnergiaDelete = fs.delete(id - 1); 
            if (ProyectoEnergiaDelete) {
                res.put("message", " eliminado corectametne");
                return Response.ok(res).build();
            } else {
                res.put("message", "ProyectoEnergia no encontrada o no eliminada");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            res.put("message", "Error al intentar eliminar el ProyectoEnergia");
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
        
    @Path("/list/search/nombre/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyectoName(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busquedaNombreProyecto(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/list/search/inversionistas/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInverBusqueda(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busquedaInversionsita(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }


//ordenacion
    @Path("/list/merge/{atrib}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNombreProyecEnerg(@PathParam("atrib") String atrib, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        try {
            //revisar el order
            LinkedList lista = ps.ordenacionMergesort(type, atrib);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
        }

        return Response.ok(map).build();
    }

    @Path("/list/quick/{atrib}/{type}") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyectoName(@PathParam("atrib") String atrib, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        try {
            //revisar el order
            LinkedList lista = ps.ordenacionQuicksort(type, atrib);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
        }

        return Response.ok(map).build();
    }

    @Path("/list/shell/{atrib}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyectoEnergia(@PathParam("atrib") String atrib, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        try {
            LinkedList lista = ps.ordenacionMergesort(type, atrib);
            map.put("data", lista.toArray());
            if (lista.isEmpty()) {
                map.put("data", new Object[] {});
            }
        } catch (Exception e) {
        }

        return Response.ok(map).build();
    }
    
//Busqueda
    @Path("/list/search/lineal/inversionistas/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonLa(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busquedaLineaInversionistas(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/list/search/binario/inversionistas/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersodnLa(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busquedaBinariaInversionista(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/list/search/lineal/nombre/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsLa(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busquedaLinealNombre(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/list/search/binario/nombre/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersdssonsLa(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServicies ps = new ProyectoEnergiaServicies();
        map.put("msg", "Ok");
        LinkedList lista = ps.busquedaBinariaNombre(texto);
        map.put("data", lista.toArray());
        if (lista.isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }
}