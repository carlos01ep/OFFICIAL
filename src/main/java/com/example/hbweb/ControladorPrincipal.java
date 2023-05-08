package com.example.hbweb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorPrincipal {
    @
GetMapping("/inicio")
public String iniPg(){
    return "/index";
}
}
