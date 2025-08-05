package org.blockDefense.command;

import cn.jason31416.planetlib.command.ChildCommand;
import cn.jason31416.planetlib.command.ICommandContext;
import cn.jason31416.planetlib.command.IParentCommand;
import cn.jason31416.planetlib.message.Message;
import org.blockDefense.team.Game;
import org.blockDefense.util.Lang;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JoinCommand extends ChildCommand {
    public JoinCommand(String name, IParentCommand parent) {
        super(name, parent);
    }

    @Override
    public @Nullable Message execute(ICommandContext context) {
        if(context.getPlayer()==null) return null;
        if(Game.getPlayerGame(context.getPlayer())!=null) return Lang.getMessage("command.failed.already-in-game");
        if(Game.ongoingGame == null) {
            Game.ongoingGame = new Game();
            Game.ongoingGame.setup();
        }
        Game.ongoingGame.addPlayer(context.getPlayer());

        return Lang.getMessage("command.success.joined");
    }

    @Override
    public List<String> tabComplete(ICommandContext context) {
        return List.of();
    }
}
