package com.intertec.domain.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsernameListRepositoryTest {

    @InjectMocks
    private UsernameListRepository usernameListRepository;
    @Mock
    private H2SessionFactory h2SessionFactory;
    @Mock
    private Configuration configuration;
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;

    private List<String> dbList;
    private final String validUser = "valid";
    private final String invalidUser = "invalid";
    private final String selectAll = "SELECT username FROM Username";
    private final String selectByUsername = "SELECT username FROM Username WHERE Username = ?";

    @Before
    public void setUp() throws Exception {
        buildLists();
        when(h2SessionFactory.getFactory()).thenReturn(sessionFactory);
        when(h2SessionFactory.getFactory().openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString())).thenReturn(query);
    }

    @Test
    public void shouldGetUserNameList_WhenDatabaseHasData() throws Exception {
        when(session.createQuery(selectAll).list()).thenReturn(dbList);
        List<String> result = usernameListRepository.getAllUsernameList();
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldGetEmptyList_WhenDatabaseHasNoData() throws Exception {
        when(session.createQuery(selectAll).list()).thenReturn(new ArrayList<String>());
        List<String> result = usernameListRepository.getAllUsernameList();
        assertTrue(result.size() == 0);
    }

    @Test
    public void shoutReturnUserNameList_WhenUserExistOnDatabase() throws Exception {
        when(session.createQuery(String.format(selectByUsername, validUser)).list()).thenReturn(dbList);
        List<String> result = usernameListRepository.findByUsername(validUser);
        assertTrue(result.size() == 1);
    }

    @Test
    public void shoutReturnUserNameList_WhenUserDoesNotExistOnDatabase() throws Exception {
        when(session.createQuery(String.format(selectByUsername, invalidUser)).list()).thenReturn(new ArrayList<String>());
        List<String> result = usernameListRepository.findByUsername(invalidUser);
        assertTrue(result.size() == 0);
    }

    private void buildLists(){
        dbList = new ArrayList<String>();
        dbList.add("RES1");
    }

}