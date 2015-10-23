package facade;

import entity.Car;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class CarController {

    private EntityManagerFactory emf;

    public CarController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Car> getCars() throws Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Car> query = em.createNamedQuery("Car.findAll", Car.class);
            List<Car> cars = query.getResultList();
            if (!cars.isEmpty()) {
                return cars;
            } else {
                throw new Exception("Something went wrong");
            }
        } finally {
            em.close();
        }
    }

    public Car createCar(Car car) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
            return car;
        } finally {
            em.close();
        }
    }

    public Car editCar(Car car) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Car editedCar = new Car();
            TypedQuery<Car> query = em.createNamedQuery("Car.findById", Car.class).setParameter("id", car.getId());
            List<Car> cars = query.getResultList();
            if (!cars.isEmpty()) {
                editedCar = cars.get(0);
            } else {
                throw new Exception("Something went wrong");
            }
            editedCar.setModelYear(car.getModelYear());
            editedCar.setRegistered(car.getRegistered());
            editedCar.setMake(car.getMake());
            editedCar.setModel(car.getModel());
            editedCar.setDescription(car.getDescription());
            editedCar.setPrice(car.getPrice());
            em.getTransaction().commit();
            return editedCar;
        } finally {
            em.close();
        }
    }
}
