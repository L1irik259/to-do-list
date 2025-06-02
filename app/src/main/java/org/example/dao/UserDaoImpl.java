package org.example.dao;

import java.util.List;
import javax.swing.JOptionPane;
import org.example.interfaces.DefaultDao;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository 
public class UserDaoImpl implements DefaultDao<User> {

    @Override
    public User save(User user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Insertion error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public void deleteById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            tx.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Delete by ID error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void deleteByEntity(User user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.remove(user);
            tx.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Delete entity error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public User update(User user) {
        Session session = null;
        User updated = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            updated = (User) session.merge(user);
            tx.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Update error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return updated;
    }

    @Override
    public User getById(int id) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = session.get(User.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Get by ID error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        List<User> users = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Get all error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
    }
}