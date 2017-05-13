package com.intertec.controller;

import com.intertec.domain.entity.UsernameResponseEntity;
import com.intertec.exception.InvalidInputDataException;
import com.intertec.service.RestrictedWordService;
import com.intertec.service.UsernameListService;
import com.intertec.util.UsernameUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${url.root.path}")
@RestController
public class UsernameListController {

    private static final Log LOG = LogFactory.getLog(UsernameListController.class);

    @Autowired
    private UsernameListService usernameListService;

    @RequestMapping(value = "/validate-username", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUsername(@RequestParam(value = "username", required = true) String username){
        LOG.info(String.format("Processing username: %s", username));
        UsernameResponseEntity response = usernameListService.searchUsername(username);
        return ResponseEntity.ok(response);
    }
}
