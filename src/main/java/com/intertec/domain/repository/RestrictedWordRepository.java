package com.intertec.domain.repository;

import com.intertec.domain.entity.RestrictedWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestrictedWordRepository {

    private static final Log LOG = LogFactory.getLog(RestrictedWordRepository.class);

    @Autowired
    private H2SessionFactory h2SessionFactory;
    private Session session;

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    public List<String> getAllRestrictedWords(){
        session = h2SessionFactory.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        String select = "SELECT word FROM RestrictedWords";
        LOG.info(String.format("Executing query: %s", select));
        List<String> result = session.createQuery(select).list();
        tx.commit();
        session.close();
        return result;
    }

    public List<String> findRestrictedWordByWord(String word){
        session = h2SessionFactory.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        LOG.info(String.format("Trying to find word: %s", word));
        List<String> result = session.createCriteria(RestrictedWords.class)
                .setProjection(Projections.property("word")).list();
        tx.commit();
        session.close();
        return result;
    }

    public int insertRestricedWord(List<String> restrictedWordList){
        int result = 0;
        for(String word: restrictedWordList){
            result += insertRestricedWord(word);
        }
        return result;
    }

    public int insertRestricedWord(String word){
        RestrictedWords restrictedWords = new RestrictedWords();
        restrictedWords.setWord(word);
        session = h2SessionFactory.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        LOG.info(String.format("Inserting restricted word: %s", restrictedWords.toString()));
        int result = (Integer) session.save(restrictedWords);
        tx.commit();
        session.close();
        return result;
    }
}
