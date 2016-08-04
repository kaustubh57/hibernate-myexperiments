package com.hibernate.myexperiments.dao;

import com.hibernate.myexperiments.HibernateUtil;
import com.hibernate.myexperiments.models.Storyboard;
import com.hibernate.myexperiments.utl.CriteriaOptions;
import com.hibernate.myexperiments.utl.CriteriaUtil;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.hibernate.type.MaterializedClobType;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println(" =======STORYBOARD DATA =======");
        System.out.println("Found " + storyboard + " Storyboards");
        return storyboard;

    }

    public static List<Storyboard> readStoryboardByCriteria() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Storyboard.class, "storyboard");

        // create filter options
        CriteriaOptions options = new CriteriaOptions();
        options.setFirstResult(1);
        options.setOrder("asc");
        options.setOrderBy("name");
        options.setSearchTxt("");
        options.setSearchColName("name");
        List<String> columns = CriteriaUtil.getColumns("id,name,creator,updateTs,createTs,isPublic");
        options.setColumns(columns);

        // set search / order / set max result
        criteria.add(Restrictions.ilike(options.getSearchColName(), options.getSearchTxt(), MatchMode.ANYWHERE));
        criteria.addOrder(Order.desc("startDate"));// criteria.addOrder(Order.asc(options.getOrderBy()).ignoreCase());
        criteria.setFirstResult(options.getFirstResult()).setMaxResults(8);

        // get properties of data
        final ClassMetadata metaData = session.getSessionFactory().getClassMetadata(Storyboard.class);
        final List<String> properties = new ArrayList<>(Arrays.asList(metaData.getPropertyNames()));
        properties.add(metaData.getIdentifierPropertyName());
        columns.retainAll(properties);

        // set options to criteria
        criteria.createAlias("interactions", "i", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("creator", "creator");
        criteria.createAlias("comments", "comments", JoinType.LEFT_OUTER_JOIN);

        // set projections
        final ProjectionList projections = Projections.projectionList();
        for (final String column : columns) {
            projections.add(Projections.property(column), column);
        }
        projections.add(Projections.min("i.inMarketDate"), "startDate");
        projections.add(Projections.property("id"));

        // set sub query projection to get storyboard data
        final String schemaName = ((SessionFactoryImplementor)session.getSessionFactory()).getSettings().getDefaultSchemaName();
        projections.add(Projections.sqlProjection("(select sbGraph.storyboard_data from " + schemaName
                + ".storyboard sbGraph where sbGraph.id = this_.id) as storyboardData",
                new String[] { "storyboardData"}, new Type[] {new MaterializedClobType()}), "storyboardData");

        // add group by
        projections.add(Projections.groupProperty("id"));
        projections.add(Projections.groupProperty("creator.firstName"));
        projections.add(Projections.countDistinct("comments.id"), "commentCount");

        for (final String column : columns) {
            projections.add(Projections.groupProperty(column), column);
        }

        criteria.setProjection(projections);
        criteria.setResultTransformer(Transformers.aliasToBean(Storyboard.class));

        @SuppressWarnings("unchecked")
        List<Storyboard> storyboards = criteria.list();
        System.out.println("Found " + storyboards.size() + " Storyboards");
        return storyboards;

    }

    // Hibernate - advance query options
    // http://what-when-how.com/hibernate/advanced-query-options-hibernate/
    public static List<Storyboard> readStoryboardByCriteriaFormula() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Storyboard.class);

        // create filter options
        CriteriaOptions options = new CriteriaOptions();
        options.setFirstResult(1);
        options.setOrder("asc");
        options.setOrderBy("name");
        options.setSearchTxt("");
        options.setSearchColName("name");
        List<String> columns = CriteriaUtil.getColumns("id,name,creator,updateTs,createTs,isPublic,storyboardData");
        options.setColumns(columns);

        // set search / order / set max result
        criteria.add(Restrictions.ilike(options.getSearchColName(), options.getSearchTxt(), MatchMode.ANYWHERE));
        criteria.addOrder(Order.asc(options.getOrderBy()).ignoreCase());
        criteria.setFirstResult(options.getFirstResult()).setMaxResults(8);

        // get properties of data
        final ClassMetadata metaData = session.getSessionFactory().getClassMetadata(Storyboard.class);
        final List<String> properties = new ArrayList<>(Arrays.asList(metaData.getPropertyNames()));
        properties.add(metaData.getIdentifierPropertyName());
        columns.retainAll(properties);

        // set options to criteria
        criteria.createAlias("creator", "creator");

        // set projections
        final ProjectionList projections = Projections.projectionList();
        for (final String column : columns) {
            projections.add(Projections.property(column), column);
        }
        projections.add(Projections.property("id"));
        projections.add(Projections.property("startDate"), "startDate");
        projections.add(Projections.property("commentCount"), "commentCount");

        criteria.setProjection(projections);
        criteria.setResultTransformer(Transformers.aliasToBean(Storyboard.class));

        @SuppressWarnings("unchecked")
        List<Storyboard> storyboards = criteria.list();
        System.out.println("Found " + storyboards.size() + " Storyboards");
        return storyboards;

    }
}
