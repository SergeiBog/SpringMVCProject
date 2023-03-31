package ru.library.util;

import ru.library.dao.PeopleDAO;
import ru.library.models.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PeopleValidator implements Validator {
    private final PeopleDAO peopleDAO;

    @Autowired
    public PeopleValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return People.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        People people = (People) target;
        if (peopleDAO.show(people.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Такое Имя уже Зарегестрировано");
        }
    }
}
