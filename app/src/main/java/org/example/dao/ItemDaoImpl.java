package org.example.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.example.interfaces.DefaultDao;
import org.example.model.Item;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository 
public class ItemDaoImpl implements DefaultDao<Item> {
    @Override
    public Item save(Item item) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Insertion error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return item;
    }

    @Override
    public void deleteById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Item item = session.get(Item.class, id);
            if (item != null) {
                session.remove(item);
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
    public void deleteByEntity(Item item) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.remove(item);
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
    public Item update(Item item) {
        Session session = null;
        Item updated = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            updated = (Item) session.merge(item);
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
    public Item getById(int id) {
        Session session = null;
        Item item = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            item = session.get(Item.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Get by ID error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return item;
    }

    @Override
    public List<Item> getAll() {
        Session session = null;
        List<Item> items = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            items = session.createQuery("FROM Cat", Item.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Get all error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return items;
    }
}
