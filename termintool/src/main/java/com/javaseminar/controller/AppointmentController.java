package com.javaseminar.controller;

import com.javaseminar.model.Appointment;
import com.javaseminar.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AppointmentController {

    public Appointment getAppointment(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Appointment.class, id);
        }
    }

    public Appointment createAppointment(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(appointment);
            transaction.commit();
            return appointment;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void updateAppointment(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(appointment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteAppointment(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(appointment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Appointment> getAllAppointments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Appointment", Appointment.class).list();
        }
    }
}