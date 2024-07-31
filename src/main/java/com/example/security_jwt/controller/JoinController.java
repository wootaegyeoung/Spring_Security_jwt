package com.example.security_jwt.controller;

import com.example.security_jwt.dto.joinDTO;
import com.example.security_jwt.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;
    public JoinController(JoinService joinService){
        this.joinService=joinService;

    }

    @PostMapping("/join")
    public ResponseData joinProcess(@RequestBody joinDTO joinDTO){
        boolean result=joinService.joinProcess(joinDTO);
        ResponseData data;
        if(result){
            data = new ResponseData("ok");
        }else{
            data = new ResponseData("fail");
        }


        return data;
    }

    class ResponseData {
        private String result;

        public ResponseData(String result){
            this.result=result;
        }

        // Getters and Setters
        public String getOutput() {
            return result;
        }

        public void setOutput(String result) {
            this.result=result;
        }
    }
}
