package hibernate;

import model.Country;
import org.hibernate.Session;
import util.HibernateUtils;

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

    }
}
