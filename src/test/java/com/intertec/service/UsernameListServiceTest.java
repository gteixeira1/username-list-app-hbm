package com.intertec.service;

import com.intertec.domain.entity.UsernameResponseEntity;
import com.intertec.domain.repository.RestrictedWordRepository;
import com.intertec.domain.repository.UsernameListRepository;
import com.intertec.exception.InvalidInputDataException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsernameListServiceTest {

    @InjectMocks
    private UsernameListService usernameListService;
    @Mock
    private UsernameListRepository usernameListRepository;
    @Mock
    private RestrictedWordRepository restrictedWordRepository;

    private UsernameResponseEntity usernameResponseEntity;

    private List<String> userList;
    private List<String> restrictedWords;

    private final String validUsername = "MrJohn99";
    private final String repeatedUsername = "MrJohn";
    private final String invalidUsername = "Johngrass";
    private final String shortUsername = "John";

    @Before
    public void setUp() throws Exception {
        builLists();
        when(usernameListRepository.findByUsername(validUsername)).thenReturn(new ArrayList<String>());
        when(usernameListRepository.findByUsername(repeatedUsername)).thenReturn(userList);
        when(usernameListRepository.findByUsername(invalidUsername)).thenReturn(new ArrayList<String>());
        when(usernameListRepository.getAllUsernameList()).thenReturn(userList);
        when(restrictedWordRepository.getAllRestrictedWords()).thenReturn(restrictedWords);
        usernameResponseEntity = new UsernameResponseEntity(false, new ArrayList<String>());
    }

    @Test
    public void shouldReturnTrue_WhenUsernameIsValid() throws Exception {
        UsernameResponseEntity result = usernameListService.searchUsername(validUsername);
        assertEquals(result.getValidUsername(),true);
        assertTrue(result.getUsernameList().size() == 0);
    }

    @Test
    public void shouldReturnFalse_WhenUsernameIsInvalid() throws Exception {
        UsernameResponseEntity result = usernameListService.searchUsername(repeatedUsername);
        assertEquals(result.getValidUsername(),false);
        assertTrue(result.getUsernameList().size() > 0);
    }

    @Test
    public void shouldReturnFalse_WhenUsernameHasRestrictedWord() throws Exception {
        UsernameResponseEntity result = usernameListService.searchUsername(invalidUsername);
        assertEquals(result.getValidUsername(),false);
        assertTrue(result.getUsernameList().size() > 0);
    }

    @Test(expected = InvalidInputDataException.class)
    public void shouldThrowExceptionFalse_WhenUsernameHasRestrictedWord() throws Exception {
        UsernameResponseEntity result = usernameListService.searchUsername(shortUsername);
    }

    private void builLists(){
        userList = new ArrayList<String>();
        restrictedWords = new ArrayList<String>();

        userList.add("MrJohn");
        userList.add("MrJohn1");
        userList.add("MrJohn2");
        userList.add("MrJohn3");
        userList.add("MrJohn4");
        userList.add("MrJohn5");
        userList.add("MrJohn6");
        userList.add("MrJohn7");
        userList.add("MrJohn8");
        userList.add("MrJohn9");
        userList.add("MrJohn10");
        userList.add("MrJohn11");
        userList.add("MrJohn12");
        userList.add("MrJohn13");
        userList.add("MrJohn14");
        userList.add("MrJohn15");
        userList.add("MrJohn16");
        userList.add("MrJohn17");

        restrictedWords.add("cannabis");
        restrictedWords.add("abuse");
        restrictedWords.add("crack");
        restrictedWords.add("damn");
        restrictedWords.add("drunk");
        restrictedWords.add("grass");
    }

}