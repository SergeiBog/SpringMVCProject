package ru.library.controllers;

import ru.library.dao.BookDAO;
import ru.library.dao.PeopleDAO;
import ru.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.library.models.People;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BookControllers {
    private final BookDAO bookDAO;
    private final PeopleDAO peopleDAO;

    @Autowired
    public BookControllers(BookDAO bookDAO, PeopleDAO peopleDAO) {
        this.bookDAO = bookDAO;
        this.peopleDAO = peopleDAO;
    }

    //Получаем список всеъ книг
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    //Получаем книгу по ее id
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("people") People people) {
        model.addAttribute("books", bookDAO.show(id));
        Optional<People> bookOwner = bookDAO.getBookOwner(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("person", peopleDAO.index());
        }
        return "books/show";
    }

    //Создаем новую книгу
    @GetMapping("/new")
    public String newBook(@ModelAttribute("books") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("books") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    //Редактируем данные о книги
    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("books", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("books") @Valid Book book,
                             BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    //Удаляем книгу
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("people") People people) {
        bookDAO.assign(id, people);
        return "redirect:/books/" + id;
    }
}
