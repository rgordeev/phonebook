package services;

import com.google.inject.Singleton;
import configs.DBConnection;
import model.Book;
import model.Person;
import model.Phone;
import org.apache.commons.lang3.StringUtils;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
@Singleton
public class JDBCStorageService implements StorageService
{
    @Override
    public void add(String personName, String phone)
    {
        TransactionScript.getInstance().addPerson(personName, phone, defaultBook());
    }

    @Override
    public List<Person> list()
    {
        return TransactionScript.getInstance().listPersons();
    }

    @Override
    public Book defaultBook()
    {
        return TransactionScript.getInstance().defaultBook();
    }

    public static final class TransactionScript
    {
        private static final TransactionScript instance = new TransactionScript();

        public static TransactionScript getInstance() {
            return instance;
        }

        public TransactionScript()
        {
            String url      = DBConnection.JDBC.url();
            String login    = DBConnection.JDBC.username();
            String password = DBConnection.JDBC.password();

            try
            {
                connection = DriverManager.getConnection(url, login,
                        password);
            } catch (Exception e)
            {
                e.printStackTrace();
            };
        }

        public List<Person> listPersons()
        {
            List<Person> result = new ArrayList<>(10);

            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "select name, phone from book b \n" +
                        "inner join person p on b.id = p.book_id \n" +
                        "inner join phone ph on p.id = ph.person_id\n");

                ResultSet r_set = statement.executeQuery();

                while (r_set.next())
                {
                    Person p = new Person(r_set.getString("name"));
                    Phone ph = new Phone(p, r_set.getString("phone"));
                    p.getPhones().add(ph);
                    result.add(p);
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }

        public void addPerson(String person, String phone, Book book)
        {
            try
            {
                if (book.getId() == null)
                {
                    PreparedStatement addBook = connection.prepareStatement("insert into book (id) values (DEFAULT)", Statement.RETURN_GENERATED_KEYS);
                    addBook.execute();
                    ResultSet generated_book_id = addBook.getGeneratedKeys();

                    if (generated_book_id.next())
                        book.setId(generated_book_id.getLong("id"));
                }

                PreparedStatement addPerson = connection.prepareStatement("insert into person (book_id, name) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement addPhone  = connection.prepareStatement("insert into phone (person_id, phone) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

                addPerson.setLong   (1, book.getId());
                addPerson.setString (2, person);

                addPerson.execute();

                ResultSet auto_pk = addPerson.getGeneratedKeys();
                while (auto_pk.next())
                {
                    int id = auto_pk.getInt("id");
                    addPhone.setInt(1, id);
                    addPhone.setString(2, phone);
                    addPhone.execute();
                }



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public Book defaultBook()
        {
            // создаем новый экземпляр, который в дальнейшем и сохраним,
            // если не найдем для него записи в БД
            Book book = new Book();

            try
            {
                Statement statement = connection.createStatement();
                // выбираем из таблицы book единственную запись
                ResultSet books = statement.executeQuery("select id from book limit 1");
                // если хоть одна зепись в таблице нашлась, инициализируем наш объект полученными значениями
                if (books.next())
                {
                    book.setId(books.getLong("id"));
                }

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            // возвращаем проинициализированный или пустой объект книги
            return book;
        }

        private Connection connection;
    }
}
