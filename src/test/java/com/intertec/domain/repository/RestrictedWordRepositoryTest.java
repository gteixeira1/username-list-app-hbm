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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RestrictedWordRepositoryTest {

    @InjectMocks
    private RestrictedWordRepository restrictedWordRepository;
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
    private final String validWord = "valid";
    private final String invalidWord = "invalid";
    private final String selectAll = "SELECT word FROM RestrictedWords";
    private final String selectByWord = "SELECT word FROM RestrictedWords WHERE WORD = ?";

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
        List<String> result = restrictedWordRepository.getAllRestrictedWords();
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldGetEmptyList_WhenDatabaseHasNoData() throws Exception {
        when(session.createQuery(selectAll).list()).thenReturn(new ArrayList<String>());
        List<String> result = restrictedWordRepository.getAllRestrictedWords();
        assertTrue(result.size() == 0);
    }

    @Test
    public void shouldReturnUserNameList_WhenUserExistOnDatabase() throws Exception {
        when(session.createQuery(String.format(selectByWord, validWord)).list()).thenReturn(dbList);
        List<String> result = restrictedWordRepository.findRestrictedWordByWord(validWord);
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldReturnUserNameList_WhenUserDoesNotExistOnDatabase() throws Exception {
        when(session.createQuery(String.format(selectByWord, invalidWord)).list()).thenReturn(new ArrayList<String>());
        List<String> result = restrictedWordRepository.findRestrictedWordByWord(invalidWord);
        assertTrue(result.size() == 0);
    }

    @Test
    public void shouldInsertRestrictedWordList(){
        List<String> restrictedWordList = new ArrayList<String>();
        restrictedWordList.add(validWord);
        when(session.save(anyObject())).thenReturn(1);
        int result = restrictedWordRepository.insertRestricedWord(restrictedWordList);
        assertEquals(result, 1);
    }

    private void buildLists(){
        dbList = new ArrayList<String>();
        dbList.add("RES1");
    }

}