package com.intertec.domain.repository;

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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RestrictedWordRepositoryTest {

    @InjectMocks
    private RestrictedWordRepository restrictedWordRepository;
    @Mock
    private JdbcTemplate jdbcTemplate;

    private List<String> dbList;
    private final String validWord = "valid";
    private final String invalidWord = "invalid";
    private final String selectAll = "SELECT WORD FROM RESTRICTED_WORDS";
    private final String selectByWord = "SELECT WORD FROM RESTRICTED_WORDS WHERE WORD = ?";
    private final String insertRestrictedWord = "INSERT INTO RESTRICTED_WORDS (WORD) VALUES (?)";;

    @Before
    public void setUp() throws Exception {
        buildLists();
    }

    @Test
    public void shouldGetUserNameList_WhenDatabaseHasData() throws Exception {
        when(jdbcTemplate.queryForList(selectAll, String.class)).thenReturn(dbList);
        List<String> result = restrictedWordRepository.getAllRestrictedWords();
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldGetEmptyList_WhenDatabaseHasNoData() throws Exception {
        when(jdbcTemplate.queryForList(selectAll, String.class)).thenReturn(new ArrayList<String>());
        List<String> result = restrictedWordRepository.getAllRestrictedWords();
        assertTrue(result.size() == 0);
    }

    @Test
    public void shouldReturnUserNameList_WhenUserExistOnDatabase() throws Exception {
        when(jdbcTemplate.queryForList(selectByWord, String.class, validWord)).thenReturn(dbList);
        List<String> result = restrictedWordRepository.findRestrictedWordByWord(validWord);
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldReturnUserNameList_WhenUserDoesNotExistOnDatabase() throws Exception {
        when(jdbcTemplate.queryForList(selectByWord, String.class, invalidWord)).thenReturn(new ArrayList<String>());
        List<String> result = restrictedWordRepository.findRestrictedWordByWord(invalidWord);
        assertTrue(result.size() == 0);
    }

    @Test
    public void shouldInsertRestrictedWordList(){
        List<String> restrictedWordList = new ArrayList<String>();
        restrictedWordList.add(validWord);
        when(jdbcTemplate.update(insertRestrictedWord, validWord)).thenReturn(1);
        int result = restrictedWordRepository.insertRestricedWordList(restrictedWordList);
        assertEquals(result, 1);
    }

    private void buildLists(){
        dbList = new ArrayList<String>();
        dbList.add("RES1");
    }

}