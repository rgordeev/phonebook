package services;

import com.google.inject.Singleton;
import model.Book;
import model.Person;
import model.Phone;

import java.util.*;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
@Singleton
public class InMemoryStorage implements StorageService
{
    @Override
    public void add(String personName, String phone)
    {
        Person person = new Person(personName);

        person.getPhones().add(new Phone(person, phone));
        book.getPersons().add(person);
    }

    @Override
    public void updateName(String oldName, String newName)
    {
        Set<Person> persons = defaultBook().getPersons();
        for (Person p: persons) // persons with unique names?
        {
            if(p.getName().equalsIgnoreCase(oldName))
            {
                p.setName(newName);
                break;
            }
        }
    }

    @Override
    public void updatePhone(String personName, String newPhone)
    {
        Set<Person> persons = defaultBook().getPersons();
        for (Person p: persons)
        {
            if(p.getName().equalsIgnoreCase(personName))
            {
                p.getPhones().clear();
                p.getPhones().add(new Phone(p, newPhone)); //one person has one phone?
            }
        }
    }

    @Override
    public void delete(String personName)
    {
        Set<Person> persons = defaultBook().getPersons();
        for (Person p: persons)
        {
            persons.remove(p);
            break;
        }
    }
    @Override
    public List<Person> list()
    {
        List<Person> copy = new ArrayList<Person>(book.getPersons());
        Collections.sort(copy, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        return copy;
    }

    public Book defaultBook()
    {
        return book == null ? book = new Book() : book;
    }

    @Override
    public void close()
    {
        book = null;
    }

    private Book book;
}
