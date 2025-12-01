package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;

public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinemaHall " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a CinemaHall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<CinemaHall> cinemaHalls = session.createQuery("FROM CinemaHall ",
                    CinemaHall.class).list();
            transaction.commit();
            return cinemaHalls;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't get all of the cinemaHalls ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
