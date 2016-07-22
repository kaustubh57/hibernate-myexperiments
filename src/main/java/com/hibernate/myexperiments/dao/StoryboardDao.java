package com.hibernate.myexperiments.dao;

import com.hibernate.myexperiments.HibernateUtil;
import com.hibernate.myexperiments.models.Storyboard;
import org.hibernate.Session;
import java.util.List;

/**
 * Created by kaustubh on 7/22/16.
 */
public class StoryboardDao {

    public static List<Storyboard> read() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Storyboard> storyboards = session.createQuery("FROM Storyboard ").list();
        session.close();
        System.out.println("Found " + storyboards.size() + " Storyboards");
        return storyboards;

    }

    public static Storyboard readStoryboardById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        Storyboard storyboard = (Storyboard)session.createQuery("FROM Storyboard where id = :id").setParameter("id", id).list().get(0);
        session.close();
        System.out.println("Found " + storyboard + " Storyboards");
        return storyboard;

    }
}
