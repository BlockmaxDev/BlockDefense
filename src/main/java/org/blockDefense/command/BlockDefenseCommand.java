package org.blockDefense.command;

import cn.jason31416.planetlib.command.ICommandContext;
import cn.jason31416.planetlib.command.RootCommand;
import cn.jason31416.planetlib.message.Message;
import org.jetbrains.annotations.Nullable;

public class BlockDefenseCommand extends RootCommand {
    public BlockDefenseCommand(String name){
        super(name);
        new JoinCommand("create", this);
    }
    public @Nullable Message execute(ICommandContext context){
        return null;
    }
}
