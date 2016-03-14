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

    @Override
    public void delete(String personName)
    {
        Set<Person> personList = defaultBook().getPersons();
        for (Person person: personList)
        {
            personList.remove(person);
            break;
        }
    }

    @Override
    public void updatePerson(String personName, String newName)
    {
        Set<Person> persons = defaultBook().getPersons();
        for(Person person: persons)
        {
            if(person.getName().equals(personName))
            {
                person.setName(newName);
                break;
            }
        }
    }

    @Override
    public  void updatePhone(String personName, String newName)
    {
        Set<Person> persons = defaultBook().getPersons();
        for(Person person: persons)
        {
            if(person.getName().equals(personName))
            {
                person.getPhones().clear();
                person.getPhones().add(new Phone(person,newName));
            }
        }
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
