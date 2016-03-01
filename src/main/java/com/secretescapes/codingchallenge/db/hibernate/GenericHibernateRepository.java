package com.secretescapes.codingchallenge.db.hibernate;

import com.secretescapes.codingchallenge.db.GenericRepository;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Nigel on 2016-02-29.
 */
public abstract class GenericHibernateRepository<T> implements GenericRepository<T> {

    private static Logger log = Logger.getLogger(GenericHibernateRepository.class);

    @Autowired
    SessionFactory sessionFactory;

    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericHibernateRepository() {
        persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public T findById(Integer id) {
        T t = (T) getCurrentSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        return t;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Criteria criteria = getClassCriteria();
        return (List<T>) criteria.list();
    }

    public T save(T transientInstance) {
        log.debug("saving " + getPersistentClass().getName() + " instance");

        try {
            getCurrentSession().saveOrUpdate(transientInstance);
        } catch (RuntimeException re) {
            log.error("saving failed", re);
            throw re;
        }

        return transientInstance;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    protected Criteria getClassCriteria() {
        return getCurrentSession().createCriteria(getPersistentClass(), "object");
    }

}
