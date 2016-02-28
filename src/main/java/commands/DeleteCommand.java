package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
 * Created by klik on 28.02.16.
 */
public class DeleteCommand extends AbstractCommand
{
    public final static String NAME = "delete";
    public String personName;

    public DeleteCommand(String personName, StorageService storage)
    {
        super(storage);
        this.personName = personName;
    }
    @Override
    public void execute(ApplicationContext ap)
    {
        getStorage().delete(personName);
        System.out.println(personName + " is no longer in the phonebook");
        return;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
