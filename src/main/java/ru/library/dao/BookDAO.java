package ru.library.dao;

import ru.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.models.People;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        Book book = jdbcTemplate.query("SELECT * FROM book WHERE book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny().orElse(null);
        return book;
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title,author,publishYear) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getPublishYear());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, publishYear = ? WHERE book_id = ?",
                updateBook.getTitle(), updateBook.getAuthor(), updateBook.getPublishYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book Where book_id = ?", id);
    }

    public Optional<People> getBookOwner(int id) {
        return jdbcTemplate.query("select people.* from people JOIN book on people.people_id = book.people_id WHERE book.book_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(People.class)).stream().findAny();
    }

    public void assign(int id, People people) {
        jdbcTemplate.update("UPDATE book SET book.people_id =? WHERE book_id = ?", people.getPeople_id(), id);
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET people_id =null WHERE book_id = ?", id);
    }
}
