package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
public class ApiController {
    private final List<String> messages = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    @GetMapping("messages") //curl --get localhost:8080/messages
    public ResponseEntity<List<String>> getMessages(@RequestBody(required = false) String text) {
        List<String> to_return = new ArrayList<>();
        if(text != null) {
            for (String message : messages) {
                if (message.startsWith(text)) {
                    to_return.add(message);
                }
            }
            return ResponseEntity.ok(to_return);
        }
        return ResponseEntity.ok(messages);
    }
    @PostMapping("messages") //curl -H "Content-Type: text/plain" -d hello localhost:8080/messages
    public ResponseEntity<Void> addMessage(@RequestBody String text) {
        messages.add(text);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("messages/{index}") //curl --get localhost:8080/messages/1
    public ResponseEntity<String> getMessage(@PathVariable("index") Integer
                                                     index) {
        return ResponseEntity.ok(messages.get(index));
    }
    @DeleteMapping("messages/{index}") //curl -X DELETE localhost:8080/messages/0
    public ResponseEntity<Void> deleteText(@PathVariable("index") Integer
                                                   index) {
        messages.remove((int) index);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("messages/{index}") //curl -X PUT localhost:8080/messages/0 -H "Content-Type: text/plain" -d "hello"
    public ResponseEntity<Void> updateMessage(
            @PathVariable("index") Integer i,
            @RequestBody String message) {
        messages.remove((int) i);
        messages.add(i, message);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("messages/search/{text}") //curl --get localhost:8080/messages/search/hello
    public ResponseEntity<Integer> searchMessage(@PathVariable String text){
        for(int i = 0; i < messages.size(); i++){
            if(messages.get(i).contains(text)){
                return ResponseEntity.ok(i);
            }
        }
        return ResponseEntity.ok(-1);
    }

    @GetMapping("messages/count") //curl --get localhost:8080/messages/count
    public ResponseEntity<Integer> countMessages(){
        return ResponseEntity.ok(messages.size());
    }

    @PostMapping("messages/{index}/create") //curl -H "Content-Type: text/plain" -d hello localhost:8080/messages/1/create
    public ResponseEntity<Void> createMessage(@RequestBody String message, @PathVariable("index") Integer i){
        messages.add(i, message);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("messages/search/{text}") //curl -X DELETE localhost:8080/messages/search/hello
    public ResponseEntity<Void> deleteMessage(@PathVariable String text){
        for(int i = messages.size() - 1; i >= 0; i--){
            if(messages.get(i).contains(text)){
                messages.remove(i);
            }
        }
        return ResponseEntity.accepted().build();
    }

    @PostMapping("users") //curl -X POST localhost:8080/users -H "Content-Type: application/json" -d "{\"name\": \"Fedor\", \"age\": \"23\"}"
    public ResponseEntity<Void> addUser(@RequestBody User user){
        users.add(user);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("users/{index}") //curl -X DELETE localhost:8080/users/1
    public ResponseEntity<Void> deleteUser(@PathVariable("index") Integer index){
        users.remove((int) index);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("users/{index}") //curl localhost:8080/users/1
    public ResponseEntity<User> getUser(@PathVariable("index") Integer index){
        return ResponseEntity.ok(users.get(index));
    }

    @GetMapping("users") //curl localhost:8080/users
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(users);
    }

    @PutMapping("users/{index}")//curl -X PUT localhost:8080/users/0 -H "Content-Type: application/json" -d 12
    public ResponseEntity<Void> changeAge(@PathVariable("index") Integer index, @RequestBody Integer age){
        users.get(index).setAge(age);
        return ResponseEntity.accepted().build();
    }
}
