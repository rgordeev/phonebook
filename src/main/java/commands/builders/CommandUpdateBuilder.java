package commands.builders;
import com.google.inject.Inject;
import commands.Command;
import commands.CommandAdd;
import commands.CommandUpdate;
import commands.UnknownCommand;
import commands.factories.ConsoleCommandsFactory;
import model.Params;
import org.apache.commons.lang3.StringUtils;
import services.StorageService;
/**
 * Created by igor on 29.02.16.
 */
public class CommandUpdateBuilder extends  AbstractCommandBuilder{
    public CommandUpdateBuilder(StorageService storageService)
    {
        super(storageService);
    }


    public Command createCommand(Params params)
    {
        String[] args = null;

        if (StringUtils.isNotEmpty(params.getCommandArgs()))
            args = StringUtils.split(params.getCommandArgs());

        if (args == null || args.length != 4)
            return ConsoleCommandsFactory.getInstance().createUnknownCommand(params);

        Command upd = new CommandUpdate(args[0], args[1], args[2], getStorage());
        return upd;

    }
}
