package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
 * Created by nightwolf on 05.03.16.
 */
public class CommandDelete extends AbstractCommand
{
    public final static String NAME = "delete";
    public String personName;

    public CommandDelete(String personName, StorageService storage)
    {
        super(storage);
        this.personName = personName;
    }
    @Override
    public void execute(ApplicationContext ap)
    {
        getStorage().delete(personName);
        System.out.println(personName + " successfully deleted from the phone book");
        return;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
