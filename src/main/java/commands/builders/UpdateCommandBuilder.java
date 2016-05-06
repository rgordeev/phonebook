package commands.builders;

import com.google.inject.Inject;
import commands.Command;
import commands.UpdateCommand;
import commands.factories.ConsoleCommandsFactory;
import model.Params;
import org.apache.commons.lang3.StringUtils;
import services.StorageService;

public class UpdateCommandBuilder extends AbstractCommandBuilder
{
    @Inject
    public UpdateCommandBuilder(StorageService storageService)
    {
        super(storageService);
    }
    @Override
    public Command createCommand(Params params)
    {
        String[] args = null;
        if(StringUtils.isNotEmpty(params.getCommandArgs()))
            args = StringUtils.split(params.getCommandArgs());
        if (args == null || args.length != 4)
            return ConsoleCommandsFactory.getInstance().createUnknownCommand(params);
        return  new UpdateCommand(args[0], args[1], args[2], getStorage());
    }
}
