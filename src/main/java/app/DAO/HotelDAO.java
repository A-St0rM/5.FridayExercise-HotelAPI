package app.DAO;

import app.config.HibernateConfig;
import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HotelDAO implements IDAO<Hotel, Integer>{

    //TODO: add correct exception handling

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public HotelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Hotel> getAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT h FROM Hotel h").getResultList();
    }

    @Override
    public Hotel getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, id);
            em.getTransaction().commit();
            return hotel;
        }
    }

    @Override
    public Hotel create(Hotel hotel) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
            return hotel;
        }
    }

    @Override
    public Hotel update(Hotel hotel) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel updatedHotel = em.merge(hotel);
            em.getTransaction().commit();
            return updatedHotel;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, id);
            em.remove(hotel);
            em.getTransaction().commit();
            if(hotel != null){
                return true;
            } else {
                return false;
            }
        }
    }

    public void addRoom(Hotel hotel, Room room){

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel managedHotel = em.find(Hotel.class, hotel.getId());

            room.setHotel(managedHotel);
            managedHotel.getRooms().add(room);

            em.persist(room);
            em.getTransaction().commit();
        }
    }

    public void removeRoom(Hotel hotel, Room room){

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();

            Hotel managedHotel = em.find(Hotel.class, hotel.getId());
            Room managedRoom = em.find(Room.class, room.getId());

            if(managedHotel != null && managedRoom != null){
                em.remove(managedHotel.getRooms().remove(managedRoom));
                em.merge(managedHotel);
            }
            em.getTransaction().commit();
        }
    }

    public List<Room> getRoomsForHotel(Hotel hotel){
        try(EntityManager em = emf.createEntityManager()){
            Hotel managedHotel = em.find(Hotel.class, hotel.getId());

            return managedHotel.getRooms();

        }
    }
}
