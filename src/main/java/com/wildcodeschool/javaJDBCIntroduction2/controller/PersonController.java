package com.wildcodeschool.javaJDBCIntroduction2.controller;


import com.wildcodeschool.javaJDBCIntroduction2.entity.Person;
import com.wildcodeschool.javaJDBCIntroduction2.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonRepository repository = new PersonRepository();

    @GetMapping("/persons")
    public String getAll(Model model) {
        model.addAttribute("personList", repository.findAll());

        return "persons";
    }

    @GetMapping("/person")
    public String getPerson(Model model, @RequestParam(required = false) Long id) {
        Person person = new Person();
        if (id != null) {
            person = repository.findById(id);
        }
        model.addAttribute("person", person);

        return "person";
    }

    @PostMapping("/person")
    public String postPerson(@ModelAttribute Person person) {
        System.out.println(person.toString());
        if(person.getId() != null) {
            repository.update(person);
        } else {
            repository.save(person);
        }
        return "redirect:/persons";
    }

    @GetMapping("/person/delete")
    public String deletePerson(@RequestParam Long id) {

        repository.deleteById(id);

        return "redirect:/persons";
    }
}
