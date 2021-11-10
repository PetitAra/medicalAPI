package medical.m2i.api;

import entities.PatientEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ville")
public class VilleRESTAPI {

    EntityManager em = DbConnection.getInstance();

    public VilleEntity getVille(int id){
        VilleEntity v=em.find(VilleEntity.class, id);
        if (v == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return v;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<VilleEntity> getAll(@QueryParam("nom") String vnom) {
        //List<VilleEntity> v = em.createNativeQuery("SELECT * from ville", VilleEntity.class).getResultList();

        List<VilleEntity> v = null;

        System.out.println("le nom passé en param est " + vnom);

        if (vnom ==null || vnom.length() == 0) {
            v = em.createNamedQuery("ville.findAll").getResultList();
        }else{
            v = em.createNamedQuery("ville.findAllByNom").setParameter("nom",'%'+ vnom+'%').getResultList();
        }
        return v;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VilleEntity getOne(@PathParam("id") int id) {
        return getVille(id);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addVille(VilleEntity v) {
        // Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(v);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // em.close();
            // emf.close();
        }

    }

    @DELETE
    @Path("/{id}")
    public void deleteOne(@PathParam("id") int id) {

        EntityTransaction tx = em.getTransaction();
        // D�but des modifications
        try {
            tx.begin();
            em.remove(getVille(id));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Exception"+e.getMessage());
            throw e;
        }
        }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateVille(@PathParam("id") int id, VilleEntity vparam) {

        VilleEntity v=getVille(id);

        v.setNom(vparam.getNom());
        v.setCodePostal(vparam.getCodePostal());
        v.setPays(vparam.getPays());


        // Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(v);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // em.close();
            // emf.close();
        }

    }
    }

