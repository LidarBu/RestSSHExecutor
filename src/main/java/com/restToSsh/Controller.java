package com.restToSsh;


import com.restToSsh.utils.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/app")
public class Controller {

    @Autowired
    Executor executor;

    @GetMapping("/getData")
    public ResponseEntity<String> greet() {
        return new ResponseEntity<String>(executor.getPort("300"), HttpStatus.OK);

    }

}
