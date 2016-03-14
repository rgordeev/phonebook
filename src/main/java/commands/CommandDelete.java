package commands;

import controllers.ApplicationContext;
import services.StorageService;


/**
 * Created by igor on 29.02.16.
 */
public class CommandDelete extends  AbstractCommand
{

    public static final String COMMNAME = "delete";
    public String Person;

    public CommandDelete(String person,StorageService storage)
    {
        super(storage);
        this.Person = person;
    }

    @Override
    public void execute(ApplicationContext app)
    {
        getStorage().delete(Person);
        System.out.println(Person + "  deleted from this phonebook");
        return;
    }

    public  String getName(){
        return COMMNAME;
    }
}
