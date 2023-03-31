package ru.library.controllers;


import ru.library.dao.PeopleDAO;
import ru.library.models.People;
import ru.library.util.PeopleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleDAO peopleDAO;
    private final PeopleValidator peopleValidator;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO, PeopleValidator peopleValidator) {
        this.peopleDAO = peopleDAO;
        this.peopleValidator = peopleValidator;
    }

    // Получаем список всех людей, зарегестрированных в библиотеке
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",peopleDAO.index());
        return "people/index";
    }

    //Получаем одного человека по его id
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("people", peopleDAO.show(id));
        model.addAttribute("books",peopleDAO.showBook(id));
        return "people/show";
    }

    //Добавляем нового человека
    @GetMapping("/new")
    public String newPeople(@ModelAttribute("people") People people){
        return "people/new";
    }

    @PostMapping
    public String createPeople(@ModelAttribute("people") @Valid People people,
                         BindingResult bindingResult){
        peopleValidator.validate(people,bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleDAO.save(people);
        return "redirect:/people";
    }

    //Редактируем Данные о человеке
    @GetMapping("/{id}/edit")
    public String editPeople(Model model,@PathVariable("id") int id){
        model.addAttribute("people", peopleDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePeople(@ModelAttribute("people") @Valid People people,
                         BindingResult bindingResult,@PathVariable("id") int id){
        peopleValidator.validate(people,bindingResult);
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleDAO.update(id,people);
        return "redirect:/people";
    }

    //Удаляем Человека
    @DeleteMapping("/{id}")
    public String deletePeople(@PathVariable("id") int id){
        peopleDAO.delete(id);
        return "redirect:/people";
    }
}
