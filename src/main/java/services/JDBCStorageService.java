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
    public void updateName(String oldName, String newName)
    {
        TransactionScript.getInstance().updatePersonName(oldName, newName);
    }

    @Override
    public void updatePhone(String personName, String newPhone)
    {
        TransactionScript.getInstance().updatePersonPhone(personName, newPhone);
    }

    @Override
    public void delete(String personName)
    {
        TransactionScript.getInstance().deletePerson(personName);
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

    @Override
    public void close()
    {
        try
        {
            TransactionScript.getInstance().close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
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
            }
        }

        public List<Person> listPersons()
        {
            List<Person> result = new ArrayList<Person>(10);

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
        public void updatePersonName(String oldName, String newName)
        {
            try
            {
                PreparedStatement updateName = connection.prepareStatement(
                        "update person set name = ? where name = ?");
                updateName.setString(1, newName);
                updateName.setString(2, oldName);
                updateName.execute();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        public void updatePersonPhone(String personName, String newPhone)
        {
            try
            {
                PreparedStatement getPersonId = connection.prepareStatement(
                        "select id from person where name = ?");
                getPersonId.setString(1, personName);
                ResultSet rs = getPersonId.executeQuery();
                long id = rs.getLong(1);
                PreparedStatement updatePhone = connection.prepareStatement(
                        "update phone set phone = ? where person_id = ?");
                updatePhone.setString(1, newPhone);
                updatePhone.setLong(2, id);
                updatePhone.execute();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        public void deletePerson(String personName)
        {
            try
            {
                PreparedStatement deletePerson = connection.prepareStatement(
                    "delete from person where name = ?");
                deletePerson.setString(1, personName);
                deletePerson.execute();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
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

                addPerson.setLong(1, book.getId());
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

        public void close() throws SQLException
        {
            if (connection != null && !connection.isClosed())
                connection.close();
        }

        private Connection connection;
    }
}
