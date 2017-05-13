package com.intertec.controller;

import com.intertec.service.RestrictedWordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${url.root.path}")
@RestController
public class RestrictedWordController {

    private static final Log LOG = LogFactory.getLog(RestrictedWordController.class);

    @Autowired
    private RestrictedWordService restrictedWordService;

    @RequestMapping(value = "/restricted-word", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> saveRestrictedWord(@RequestBody final List<String> restrictedWordList){
        LOG.info(String.format("Processing username: %s", restrictedWordList));
        int result = restrictedWordService.saveRestrictedWord(restrictedWordList);
        return ResponseEntity.ok(String.format("Restricted words inserted: %s", result));
    }
}
