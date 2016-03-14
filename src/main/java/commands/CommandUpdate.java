package commands;

import controllers.ApplicationContext;
import services.StorageService;


/**
 * Created by igor on 29.02.16.
 */
public class CommandUpdate extends AbstractCommand {
    public final static String COMMNAME = "update";

    public String oldInfo;
    public String personName;
    public String newInfo;

    public CommandUpdate(String oldInfo, String personName, String newInfo, StorageService storage)
    {
        super(storage);
        this.oldInfo = oldInfo;
        this.personName = personName;
        this.newInfo = newInfo;
    }
    @Override
    public void execute(ApplicationContext ap)
    {
        if(oldInfo.equals("name"))
        {
            getStorage().updatePerson(personName, newInfo);
            System.out.println(personName + " name was changed to " + newInfo);
        }
        else if(oldInfo.equalsIgnoreCase("phone"))
        {
            getStorage().updatePhone(personName, newInfo);
            System.out.println(personName + " phone was changed to " + newInfo);
        }
        return;
    }

    @Override
    public String getName() {
        return COMMNAME;
    }
}
