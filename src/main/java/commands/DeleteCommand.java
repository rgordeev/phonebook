package commands;

import controllers.ApplicationContext;
import services.StorageService;

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