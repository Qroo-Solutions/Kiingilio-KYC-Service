package com.qroo.kyc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DefaultController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Unauthorised access";
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "Check";
    }

}
