package hibernate;

import model.City;
import model.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtils;

import java.util.List;

public class HibernateObjectsExample {

    public static void main(String[] args) {

        Country country = new Country();
        country.setName("US");


        System.out.println("country is now in transient state");

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(country);


        System.out.println("country is now in persistent state");

        country.setName("DE");

        session.getTransaction().commit();
        session.close();

        System.out.println("country is now in detached state");

        country.setName("IT");

        Session session1 = HibernateUtils.getSessionFactory().openSession();
        session1.beginTransaction();
        session1.update(country);
        session1.getTransaction().commit();
        session1.close();

        Session session2 = HibernateUtils.getSessionFactory().openSession();
        Query simpleQuery = session2.createQuery("FROM Country WHERE name='IT'");
        Object result = simpleQuery.getSingleResult();
        Country simpleCountry = result != null ? (Country) result : null;

        if (simpleCountry != null)
            System.out.println("After update in detached state:  countryId = " + simpleCountry.getId() + " name: " + simpleCountry.getName());
    }
}
