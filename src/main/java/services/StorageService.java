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
    void updateName(String oldName, String newName);
    void updatePhone(String personName, String newPhone);
    void delete(String personName);

    List<Person> list();

    Book defaultBook();

    void close();
}
