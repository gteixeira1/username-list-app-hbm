package com.intertec.domain.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@RunWith(SpringRunner.class)
public class RestrictedWordRepositoryTest {

    @InjectMocks
    private RestrictedWordRepository restrictedWordRepository;
    @Mock
    private H2SessionFactory h2SessionFactory;
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;

    private List<String> dbList;
    private final String validWord = "valid";
    private final String invalidWord = "invalid";
    private final String selectAll = "SELECT word FROM RestrictedWords";
    private final String selectByWord = "SELECT word FROM RestrictedWords WHERE WORD = ?";
    private final String insertRestrictedWord = "INSERT INTO RestrictedWords (WORD) VALUES (?)";;

    @Before
    public void setUp() throws Exception {
        when(h2SessionFactory.getFactory()).thenReturn(sessionFactory);
        when(h2SessionFactory.getFactory().openSession()).thenReturn(session);
        buildLists();
    }

    @Test
    public void test(){

    }

//    @Test
//    public void shouldGetUserNameList_WhenDatabaseHasData() throws Exception {
//        when(session.createQuery(selectAll).list()).thenReturn(dbList);
//        List<String> result = restrictedWordRepository.getAllRestrictedWords();
//        assertTrue(result.size() == 1);
//    }

//    @Test
//    public void shouldGetEmptyList_WhenDatabaseHasNoData() throws Exception {
//        when(session.createQuery(selectAll).list()).thenReturn(new ArrayList<String>());
//        List<String> result = restrictedWordRepository.getAllRestrictedWords();
//        assertTrue(result.size() == 0);
//    }
//
//    @Test
//    public void shouldReturnUserNameList_WhenUserExistOnDatabase() throws Exception {
//        when(session.createCriteria(RestrictedWords.class)
//                .setProjection(Projections.property("word")).list()).thenReturn(dbList);
//        List<String> result = restrictedWordRepository.findRestrictedWordByWord(validWord);
//        assertTrue(result.size() == 1);
//    }
//
//    @Test
//    public void shouldReturnUserNameList_WhenUserDoesNotExistOnDatabase() throws Exception {
//        when(session.createCriteria(RestrictedWords.class)
//                .setProjection(Projections.property("word")).list()).thenReturn(new ArrayList<String>());
//        List<String> result = restrictedWordRepository.findRestrictedWordByWord(invalidWord);
//        assertTrue(result.size() == 0);
//    }
//
//    @Test
//    public void shouldInsertRestrictedWordList(){
//        List<String> restrictedWordList = new ArrayList<String>();
//        restrictedWordList.add(validWord);
//        when(session.save(anyObject())).thenReturn(1);
//        int result = restrictedWordRepository.insertRestricedWord(restrictedWordList);
//        assertEquals(result, 1);
//    }

    private void buildLists(){
        dbList = new ArrayList<String>();
        dbList.add("RES1");
    }

}