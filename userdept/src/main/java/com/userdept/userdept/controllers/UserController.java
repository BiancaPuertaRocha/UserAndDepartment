package com.userdept.userdept.controllers;

import com.userdept.userdept.entities.User;
import com.userdept.userdept.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//com essa anottation eu configuro para ser um controller e responder por requisições
@RestController
//caminho do controlador
@RequestMapping(value = "/users")
public class UserController {

    //usa injeção de dependencia para nao precisar instanciar por aqui toda hora
    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        List<User> userList = repository.findAll();
        return userList;
    }
}
