package repository;

import domain.Arbitru;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class ArbitruRepositoryHibernate implements IRepository<String, Arbitru>  {
    private SessionFactory sessionFactory;
    public ArbitruRepositoryHibernate() {
        try{
            initialize();
            System.out.println(sessionFactory);
        } catch (Exception e){
            close();
        }
    }

    private void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    private void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public int size() {
        return 0;
        //no use
    }

    @Override
    public Arbitru save(Arbitru entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(entity);
            System.out.println("Arbitru adaugat");
            tx.commit();
            return entity;
        } catch (RuntimeException ex){
            if(tx!=null)
                tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void delete(String s) {
        //no use
    }

    @Override
    public Arbitru update(String s, Arbitru entity) {
        //no use
        return null;
    }

    @Override
    public Arbitru findOne(String s)  {
        Session session = sessionFactory.openSession();

        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Arbitru arbitru = session.get(Arbitru.class, s);
            System.out.println("findOne ArbitruRepositoryHibernate " + arbitru);
            tx.commit();
            return arbitru;
        }catch (RuntimeException ex){
            if(tx!=null)
                tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Iterable<Arbitru> findAll()  {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("from Arbitru ");
            return (List<Arbitru>) query.list();

        } catch (RuntimeException ex){
            if(tx!=null)
                tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }
}
