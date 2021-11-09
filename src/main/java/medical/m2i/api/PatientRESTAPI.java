package medical.m2i.api;

import entities.PatientEntity;
import entities.VilleEntity;
import medical.m2i.dao.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/patient")
public class PatientRESTAPI {

    EntityManager em= DbConnection.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<PatientEntity> getAll(){
        List<PatientEntity> p = em.createNativeQuery("SELECT * from patient", PatientEntity.class).getResultList();
        return p;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PatientEntity getOne(@PathParam("id") int id){return em.find(PatientEntity.class, id);}

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPatient (PatientEntity p){
        // Récupération d’une transaction
        EntityTransaction tx = em.getTransaction();
        // Début des modifications
        try {
            tx.begin();
            em.persist(p);
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
        PatientEntity p = em.find(PatientEntity.class, id);
        EntityTransaction tx = em.getTransaction();
        // D�but des modifications
        try {
            tx.begin();
            em.remove(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // em.close();
            // emf.close();
        }
    }
}
