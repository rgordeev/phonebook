package services;

import model.Book;
import model.Person;

import java.util.List;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
public interface StorageService
{
    void add(String personName, String phone);

    void delete(String personName);

    void updatePhone(String personName, String newInfo);

    void updatePerson(String personName, String newInfo);

    List<Person> list();

    Book defaultBook();

    void close();
}
