package ru.library.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Book {
    private int book_id;

    @NotNull(message = "название книги не может быть пустым")
    private String title;

    @NotNull(message = "Имя Автора не может быть пустым")
    @Pattern(regexp = "[А-Я][а-я]* [А-Я][а-я]*", message = "Автор должен быть записан в форме \"Фамилия Имя\"")
    private String author;

    @NotNull(message = "Год не может быть пустым")
    private int publishYear;

    public Book(){

    }

    public Book(int book_id,String title, String author, int publishYear) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
}
