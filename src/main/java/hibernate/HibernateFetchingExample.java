package hibernate;

import model.City;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtils;



public class HibernateFetchingExample {

    public static void main(String[] args) {
        Session simpleSession = HibernateUtils.getSessionFactory().openSession();
        Query simpleQuery = simpleSession.createQuery("FROM City WHERE id=1");
        City simpleCity = (City) simpleQuery.getSingleResult();
        simpleSession.close();

        try {
            simpleCity.getCountry().getName();
            System.out.println("No exception - simple query");
        } catch(LazyInitializationException e) {
            System.out.println("Catched LazyInitializationException for simple query");
        }

        Session fetchSession = HibernateUtils.getSessionFactory().openSession();
        Query fetchQuery = fetchSession.createQuery("FROM City city JOIN FETCH city.country country WHERE city.id=1");
        City cityWithFetch = (City) fetchQuery.getSingleResult();
        fetchSession.close();

        try {
            cityWithFetch.getCountry().getName();
            System.out.println("No exception - fetch query");
        } catch(LazyInitializationException e) {
            System.out.println("Catched LazyInitializationException for FETCH query");
        }


    }
}
