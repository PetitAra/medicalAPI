package medical.m2i.api;

import entities.PatientEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ville")
public class VilleRESTAPI {

    EntityManager em= DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<VilleEntity> getAll(){
        List<VilleEntity> v = em.createNativeQuery("SELECT * from ville", VilleEntity.class).getResultList();
        return v;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VilleEntity getOne(@PathParam("id") int id){return em.find(VilleEntity.class, id);}

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addVille (VilleEntity v){
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
