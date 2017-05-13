package com.intertec.service;

import com.intertec.domain.entity.UsernameResponseEntity;
import com.intertec.domain.repository.RestrictedWordRepository;
import com.intertec.domain.repository.UsernameListRepository;
import com.intertec.exception.InvalidInputDataException;
import com.intertec.util.UsernameUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UsernameListService {

    private static final Log LOG = LogFactory.getLog(UsernameListService.class);

    @Autowired
    private UsernameListRepository usernameListRepository;
    @Autowired
    private RestrictedWordRepository restrictedWordRepository;

    private UsernameResponseEntity response;

    private List<String> userList;
    private List<String> restrictedWords;

    public UsernameResponseEntity searchUsername(String username) {
        username = StringUtils.trim(username);
        response = new UsernameResponseEntity(false, new ArrayList<String>());

        LOG.info(String.format("Searching username '%s' into the repository", username));
        userList = usernameListRepository.findByUsername(username);

        LOG.info("Retrieving restricted words from repository");
        restrictedWords = restrictedWordRepository.getAllRestrictedWords();

        if (username.length() < UsernameUtil.USERNAME_MIN_LENGTH) {
            LOG.error(String.format("Username '%s' length is less than accepted", username));
            throw new InvalidInputDataException(
                    String.format("Username '%s' provided has less than the required length (%s)",
                            username, UsernameUtil.USERNAME_MIN_LENGTH
                    ));
        } else if(hasRestrictedWord(username)){
            LOG.info(String.format("Username '%s'contains a not allowed word/expression", username));
            List<String> validUsernames = generateRandomUsernameList();
            response.setUsernameList(validUsernames);
        }  else if (userList.size() != 0) {
            LOG.info(String.format("Invalid username: %s", username));
            List<String> validUsernames = generateValidUsernameList(username);
            response.setUsernameList(validUsernames);
        } else {
            response.setValidUsername(true);
        }

        return response;
    }

    private List<String> generateValidUsernameList(String username){
        List<String> validUsernames = new ArrayList<String>();
        List<String> existentUsernames = usernameListRepository.getAllUsernameList();
        LOG.info(String.format("Generating username list based on user: %s",username));
        for (int i = 0; i < UsernameUtil.USERNAME_GENERATION_RETRY
                && validUsernames.size() < UsernameUtil.USERNAME_MIN_SUGGESTION; i++){
            for (int n = 0; n < UsernameUtil.USERNAME_MIN_SUGGESTION; n++){
                String newUsername = generateUsername(username);
                if(!existentUsernames.contains(newUsername) && !validUsernames.contains(newUsername)){
                    validUsernames.add(newUsername);
                }
            }
        }
        java.util.Collections.sort(validUsernames);
        return validUsernames;
    }

    private List<String> generateRandomUsernameList(){
        List<String> validUsernames = new ArrayList<String>();
        List<String> existentUsernames = usernameListRepository.getAllUsernameList();
        LOG.info("Generating random username list");
        for (int i = 0; i < UsernameUtil.USERNAME_GENERATION_RETRY
                && validUsernames.size() < UsernameUtil.USERNAME_MIN_SUGGESTION; i++){
            for (int n = 0; n < UsernameUtil.USERNAME_MIN_SUGGESTION; n++){
                String newUsername = generateUsername();
                if(!existentUsernames.contains(newUsername)){
                    validUsernames.add(newUsername);
                }
            }
        }
        java.util.Collections.sort(validUsernames);
        return validUsernames;
    }

    private String generateUsername(String username){
        Random rand = new Random();
        StringBuilder generatedUsername = new StringBuilder();
        generatedUsername.append(username);
        generatedUsername.append(UsernameUtil.End[rand.nextInt(UsernameUtil.End.length)]);
        generatedUsername.append(rand.nextInt(UsernameUtil.USERNAME_RANDOM_RANGE));

        while(generatedUsername.length() < UsernameUtil.USERNAME_MIN_LENGTH) {
            generatedUsername.append(rand.nextInt(UsernameUtil.USERNAME_RANDOM_RANGE));
        }
        LOG.info(String.format("Username generated: %s", generatedUsername));
        return generatedUsername.toString();
    }

    private String generateUsername(){
        Random rand = new Random();
        StringBuilder generatedUsername = new StringBuilder();
        generatedUsername.append(UsernameUtil.Beginning[rand.nextInt(UsernameUtil.Beginning.length)]);
        generatedUsername.append(UsernameUtil.Middle[rand.nextInt(UsernameUtil.Middle.length)]);
        generatedUsername.append(UsernameUtil.End[rand.nextInt(UsernameUtil.End.length)]);
        LOG.info(String.format("Username generated: %s", generatedUsername));
        return generatedUsername.toString();
    }

    private boolean hasRestrictedWord(String username){
        for(String word: restrictedWords){
            if(username.contains(word)){
                return true;
            }
        }
        return false;
    }


}
