package app.DAO;


import app.config.HibernateConfig;
import app.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RoomDAO implements IDAO<Room, Integer> {

    //TODO: add correct exception handling
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public RoomDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Room> getAll() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("SELECT r FROM Room r").getResultList();
    }

    @Override
    public Room getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            em.getTransaction().commit();
            return room;
        }
    }

    @Override
    public Room create(Room room) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(room);
            em.getTransaction().commit();
            return room;
        }
    }

    @Override
    public Room update(Room room) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Room updatedRoom = em.merge(room);
            em.getTransaction().commit();
            return updatedRoom;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            em.remove(room);
            em.getTransaction().commit();
            if(room != null){
                return true;
            } else {
                return false;
            }
        }
    }
}
