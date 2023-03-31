package ru.library.dao;

import ru.library.models.Book;
import ru.library.models.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PeopleDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<People> index(){
            return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(People.class));
    }

    public People show(int id){
        return jdbcTemplate.query("SELECT * FROM people WHERE people_id = ?",new Object[]{id},new BeanPropertyRowMapper<>(People.class)).
                stream().findAny().orElse(null);
    }

    public Optional<People> show(String fullName){
        return jdbcTemplate.query("SELECT * FROM people WHERE fullName = ?", new Object[]{fullName},new BeanPropertyRowMapper<>(People.class)).
                stream().findAny();

    }
    public void save(People people){
        jdbcTemplate.update("INSERT INTO people(fullName,yearOfBirth) VALUES (?,?)",
                people.getFullName(),people.getYearOfBirth());
    }

    public void update(int id,People updatePeople){
        jdbcTemplate.update("UPDATE people SET fullName = ?, yearOfBirth = ? WHERE people_id = ?",
                updatePeople.getFullName(),updatePeople.getYearOfBirth(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM people Where people_id = ?",id);
    }


    public List<Book> showBook(int id){
        return jdbcTemplate.query("SELECT title,author,publishYear FROM book WHERE people_id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }
}
