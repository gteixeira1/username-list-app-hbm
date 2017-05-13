package com.intertec.domain.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestrictedWordRepository {

    private static final Log LOG = LogFactory.getLog(RestrictedWordRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllRestrictedWords(){
        String select = "SELECT WORD FROM RESTRICTED_WORDS";
        LOG.info(String.format("Executing query: %s", select));
        List<String> result = jdbcTemplate.queryForList(select, String.class);
        return result;
    }

    public List<String> findRestrictedWordByWord(String word){
        String select = "SELECT WORD FROM RESTRICTED_WORDS WHERE WORD = ?";
        LOG.info(String.format("Executing query: %s", select));
        List<String> result = jdbcTemplate.queryForList(select, String.class, word);
        return result;
    }

    public int insertRestricedWordList(List<String> restrictedWordList){
        int result = 0;
        for(String word: restrictedWordList){
            result += insertRestricedWordList(word);
        }
        return result;
    }

    public int insertRestricedWordList(String word){
        String insert = "INSERT INTO RESTRICTED_WORDS (WORD) VALUES (?)";
        LOG.info(String.format("Executing query: %s", insert));
        int result = jdbcTemplate.update(insert, word);
        return result;
    }
}
