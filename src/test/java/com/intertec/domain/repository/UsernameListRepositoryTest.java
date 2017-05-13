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
public class UsernameListRepositoryTest {

    @InjectMocks
    private UsernameListRepository usernameListRepository;
    @Mock
    private JdbcTemplate jdbcTemplate;

    private List<String> dbList;
    private final String validUser = "valid";
    private final String invalidUser = "invalid";
    private final String selectAll = "SELECT USERNAME FROM USERNAME_LIST";
    private final String selectByUsername = "SELECT USERNAME FROM USERNAME_LIST WHERE USERNAME = ?";

    @Before
    public void setUp() throws Exception {
        buildLists();
    }

    @Test
    public void shouldGetUserNameList_WhenDatabaseHasData() throws Exception {
        when(jdbcTemplate.queryForList(selectAll, String.class)).thenReturn(dbList);
        List<String> result = usernameListRepository.getAllUsernameList();
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldGetEmptyList_WhenDatabaseHasNoData() throws Exception {
        when(jdbcTemplate.queryForList(selectAll, String.class)).thenReturn(new ArrayList<String>());
        List<String> result = usernameListRepository.getAllUsernameList();
        assertTrue(result.size() == 0);
    }

    @Test
    public void shoutReturnUserNameList_WhenUserExistOnDatabase() throws Exception {
        when(jdbcTemplate.queryForList(selectByUsername, String.class, validUser)).thenReturn(dbList);
        List<String> result = usernameListRepository.findByUsername(validUser);
        assertTrue(result.size() == 1);
    }

    @Test
    public void shoutReturnUserNameList_WhenUserDoesNotExistOnDatabase() throws Exception {
        when(jdbcTemplate.queryForList(selectByUsername, String.class, invalidUser)).thenReturn(new ArrayList<String>());
        List<String> result = usernameListRepository.findByUsername(invalidUser);
        assertTrue(result.size() == 0);
    }

    private void buildLists(){
        dbList = new ArrayList<String>();
        dbList.add("RES1");
    }

}