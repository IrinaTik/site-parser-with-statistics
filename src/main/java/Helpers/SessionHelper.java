package Helpers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionHelper {

    private SessionFactory sessionFactory;
    private Session session;

    public SessionHelper() {
        init();
    }

    // инициализация соединения с БД
    private void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        session = sessionFactory.openSession();
    }

    // останов
    public void stop() {
        session.close();
        sessionFactory.close();
    }

    public Session getSession() {
        return session;
    }
}