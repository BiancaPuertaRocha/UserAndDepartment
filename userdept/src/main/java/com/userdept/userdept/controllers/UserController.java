package com.userdept.userdept.controllers;

import com.userdept.userdept.entities.User;
import com.userdept.userdept.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;

//com essa anottation eu configuro para ser um controller e responder por requisições
@RestController
//caminho do controlador
@RequestMapping(value = "/users")
public class UserController extends BaseController{

    //usa injeção de dependencia para nao precisar instanciar por aqui toda hora
    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> findAll(){
        //ja devolve serializado com os objetos relacionados inteiros tambem (nao somente a fk)
        List<User> userList = repository.findAll();
        return userList;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return repository.findById(id)
                .map(user ->ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody User user){
        User new_user = repository.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(new_user.getId()).toUri()).body(new_user);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity delete(@PathVariable  Long id){
        return repository.findById(id)
            .map(user -> {
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value="/{id}")
    public ResponseEntity upsert(@PathVariable Long id, @Valid @RequestBody User newUser){
      return repository.findById(id)
            .map(record -> {
                record.setName(newUser.getName());
                record.setDepartment(newUser.getDepartment());
                User updated = repository.save(record);
                return ResponseEntity.ok().body(updated);
            }).orElseGet(()->{
                newUser.setId(id);
                User created = repository.save(newUser);
                return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()).body(created);
            });
    }


}
