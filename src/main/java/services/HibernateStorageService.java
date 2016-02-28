package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.Book;
import model.Person;
import model.Phone;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 25
 */
@Singleton
public class HibernateStorageService implements StorageService
{
    @Inject
    public HibernateStorageService(EntityManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void add(String personName, String phone)
    {
        Book   book   = defaultBook();
        Person person = new Person(personName);
        Phone  ph     = new Phone(person, phone);
        person.getPhones().add(ph);

        book.getPersons().add(person);

        manager.getTransaction().begin();
        manager.persist(ph);
        manager.persist(person);
        manager.persist(book);

        manager.getTransaction().commit();

    }

    @Override
    public void updateName(String oldName, String newName)
    {
        long id = getPersonId(oldName);
        if(id != -1)
        {
            manager.getTransaction().begin();
            Person personToChange = manager.find(Person.class, id);
            personToChange.setName(newName); //changed inside transaction
            manager.getTransaction().commit();
        }
    }

    @Override
    public void updatePhone(String personName, String newPhone)
    {
        long id = getPersonId(personName);
        if(id != -1)
        {
            manager.getTransaction().begin();
            Person personToChange = manager.find(Person.class, id);
            Phone phoneToChange = personToChange.getPhones().iterator().next();
            phoneToChange.setPhone(newPhone);
            manager.getTransaction().commit();
        }
    }

    @Override
    public void delete(String personName)
    {
        long id = getPersonId(personName);
        if(id != -1)
        {
            manager.getTransaction().begin();
            Person personToDelete = manager.find(Person.class, id);
            manager.remove(personToDelete); //remove operation is cascaded to phones
            manager.getTransaction().commit();
        }
    }
    private long getPersonId(String personName)
    {
        Book book = defaultBook();
        Set<Person> persons = book.getPersons();
        for (Person p: persons)
        {
            if(p.getName().equalsIgnoreCase(personName))
            {
                return  p.getId();
            }
        }
        return -1;
    }
    @Override
    public List<Person> list() {
        return manager.createQuery("select p from model.Person p").getResultList();
    }

    public Book defaultBook()
    {
        List<Book> books = manager.createQuery("from model.Book").setMaxResults(1).getResultList();
        if (books.isEmpty())
            return new Book();
        return books.get(0);
    }

    @Override
    public void close()
    {
        manager.getEntityManagerFactory().close();
    }

    private EntityManager manager;
}
