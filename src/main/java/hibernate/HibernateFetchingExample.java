package hibernate;

import model.City;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtils;


public class HibernateFetchingExample {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();

        Query simpleQuery = session.createQuery("FROM City WHERE id=1");
        City simpleCity = (City) simpleQuery.getSingleResult();
        boolean initialized = Hibernate.isInitialized(simpleCity.getCountry());
        System.out.println("Initialized without fetch: " + initialized);

        Query query = session.createQuery("FROM City city JOIN FETCH city.country country WHERE city.id=1");
        City cityWithFetch = (City) query.getSingleResult();
        boolean initializdWithFetch = Hibernate.isInitialized(cityWithFetch.getCountry());
        System.out.println("Initialized with fetch: " + initializdWithFetch);

        session.close();
    }
}
