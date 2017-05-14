package com.intertec.domain.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsernameListRepository {

    private static final Log LOG = LogFactory.getLog(UsernameListRepository.class);

    @Autowired
    private H2SessionFactory h2SessionFactory;
    private Session session;

    public List<String> getAllUsernameList(){
        session = h2SessionFactory.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        String select = "SELECT username FROM Username";
        LOG.info(String.format("Executing query: %s", select));
        List<String> result = session.createQuery(select).list();
        tx.commit();
        session.close();
        return result;
    }

    public List<String> findByUsername(String username){
        session = h2SessionFactory.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        String select = String.format("SELECT username FROM Username WHERE Username = ?", username);
        LOG.info(String.format("Trying to find username: %s", username));
        List<String> result = session.createQuery(select).list();
        tx.commit();
        session.close();
        return result;
    }
}
