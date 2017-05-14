package com.intertec.service;

import com.intertec.domain.repository.RestrictedWordRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictedWordService {

    private static final Log LOG = LogFactory.getLog(RestrictedWordService.class);

    @Autowired
    private RestrictedWordRepository restrictedWordRepository;

    public int saveRestrictedWord(List<String> restrictedWordList){
        LOG.info("Starting restricted word insertion");
        int result = restrictedWordRepository.insertRestricedWord(restrictedWordList);
        return result;
    }
}
