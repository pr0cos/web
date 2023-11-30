package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@org.springframework.stereotype.Controller
@RestController
public class Controller {
    private final List<Contact> contacts = new ArrayList<>();

    @PostMapping("contact") //curl -X POST localhost:8080/contact -H "Content-Type: application/json" -d "{\"name\": \"Fyodor\", \"number\": \"+79214914575\", \"mail\": \"fedorglazko@mail.ru\"}"
    public ResponseEntity<Void> addContact(@RequestBody Contact contact){
        contacts.add(contact);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("contact/{index}") //curl -X DELETE localhost:8080/contact/0
    public ResponseEntity<Void> deleteContact(@PathVariable("index") Integer index){
        contacts.remove((int) index);
        return ResponseEntity.accepted().build();
    }
    //curl -X GET localhost:8080/contact
    //curl localhost:8080/contact?string=sad
    //curl localhost:8080/contact?numbers=1234
    //curl "localhost:8080/contact?numbers=1234&string=sad"
    @GetMapping("contact")
    public ResponseEntity<List<Contact>> getContacts(@RequestParam(value = "string", defaultValue = "") String string, @RequestParam(value = "numbers", defaultValue = "") String numbers){
        List<Contact> to_return = new ArrayList<>();
        String[] strSplit = numbers.split("");
        ArrayList<String> numbers_array = new ArrayList<>(Arrays.asList(strSplit));
        ArrayList<String> nums = new ArrayList<>();
        for(String s : numbers_array){
            if(!nums.contains(s)){
                nums.add(s);
            }
        }
        for(Contact c : contacts) {
            boolean flag = true;
            if (!numbers.isEmpty()){
                for (String s : nums) {
                    if (!c.name.contains(s)) {
                        flag = false;
                        break;
                    }
                }
            }
            if(!string.isEmpty()) {
                if (!c.name.contains(string)) {
                    flag = false;
                }
            }
            if(flag){
                to_return.add(c);
            }
        }
        return ResponseEntity.ok(to_return);
    }

    @GetMapping("contact/{index}") //curl -X GET localhost:8080/contact/0
    public ResponseEntity<Contact> getContact(@PathVariable("index") Integer index){
        return ResponseEntity.ok(contacts.get((int) index));
    }

    @PutMapping("contact/{index}") //curl -X PUT localhost:8080/contact/0 -H "Content-Type: application/json" -d "{\"name\": \"Glazko\", \"number\": \"+79214914575\", \"mail\": \"fedorglazko@yandex.ru\"}"
    public ResponseEntity<Void> changeContact(@PathVariable("index") Integer index, @RequestBody Contact contact){
        contacts.set((int)index, contact);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("contact/sorted") //curl -X GET localhost:8080/contact/sorted
    public ResponseEntity<List<Contact>> sortedContact(){
        List<Contact> to_return = new ArrayList<>(contacts);
        Comparator<Contact> new_comparator = new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.name.compareTo(o2.name);
            }
        };
        to_return.sort(new_comparator);
        return ResponseEntity.ok(to_return);
    }
}
