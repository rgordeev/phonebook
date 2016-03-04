package commands.builders;

import com.google.inject.Inject;
import commands.Command;
import commands.DeleteCommand;
import commands.factories.ConsoleCommandsFactory;
import model.Params;
import services.StorageService;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by klik on 28.02.16.
 */
public class DeleteCommandBuilder extends AbstractCommandBuilder
{
    @Inject
    public DeleteCommandBuilder(StorageService storageService)
    {
        super(storageService);
    }
    @Override
    public Command createCommand(Params params)
    {
        String[] args = null;
        if(StringUtils.isNotEmpty(params.getCommandArgs()))
            args = StringUtils.split(params.getCommandArgs());
        if (args == null || args.length != 1)
            return ConsoleCommandsFactory.getInstance().createUnknownCommand(params);
        return new DeleteCommand(args[0], getStorage());
    }
}
