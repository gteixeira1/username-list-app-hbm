package com.intertec.service;

import com.intertec.domain.repository.RestrictedWordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RestrictedWordServiceTest {

    @InjectMocks
    private RestrictedWordService restrictedWordService;
    @Mock
    private RestrictedWordRepository restrictedWordRepository;
    private List<String> restrictedWordList;

    @Before
    public void setUp() throws Exception {
        when(restrictedWordRepository.insertRestricedWord(anyList())).thenReturn(1);
        restrictedWordList = new ArrayList<String>();
        restrictedWordList.add("word");
    }

    @Test
    public void shouldInsertRestrictedWord() throws Exception {
        int result = restrictedWordService.saveRestrictedWord(restrictedWordList);
        assertEquals(result, 1);
    }

}