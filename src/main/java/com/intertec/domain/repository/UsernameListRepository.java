package com.intertec.domain.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsernameListRepository {

    private static final Log LOG = LogFactory.getLog(UsernameListRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllUsernameList(){
        String select = "SELECT USERNAME FROM USERNAME_LIST";
        List<String> result = jdbcTemplate.queryForList(select, String.class);

        return result;
    }

    public List<String> findByUsername(String username){
        String select = String.format("SELECT USERNAME FROM USERNAME_LIST WHERE USERNAME = ?");
        List<String> result = jdbcTemplate.queryForList(select, String.class, username);

        return result;
    }
}
