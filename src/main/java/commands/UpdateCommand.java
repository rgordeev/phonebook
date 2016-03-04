package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
 * Created by klik on 28.02.16.
 */
public class UpdateCommand extends AbstractCommand
{
    public final static String NAME = "update";
    public String nameOrPhone; //indicates what is supposed to be changed
    public String personName; //specifies a person to change
    public String newObject; //name or phone to set

    public UpdateCommand(String nameOrPhone, String personName, String newObject, StorageService storage)
    {
        super(storage);
        this.nameOrPhone = nameOrPhone;
        this.personName = personName;
        this.newObject = newObject;
    }
    @Override
    public void execute(ApplicationContext ap)
    {
        if(nameOrPhone.equalsIgnoreCase("name"))
        {
            getStorage().updateName(personName, newObject);
            System.out.println(personName + "name was changed to " + newObject);
        }
        else if(nameOrPhone.equalsIgnoreCase("phone"))
        {
            getStorage().updatePhone(personName, newObject);
            System.out.println("phone of " + personName + " was changed to " + newObject);
        }
        return;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
