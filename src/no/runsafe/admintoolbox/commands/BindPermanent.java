package no.runsafe.admintoolbox.commands;

import net.minecraft.server.v1_7_R1.NBTTagCompound;
import no.runsafe.admintoolbox.binding.BindingHandler;
import no.runsafe.framework.api.command.IBranchingExecution;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.StaticArgument;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class BindPermanent extends PlayerCommand implements IBranchingExecution
{
	public BindPermanent()
	{
		super("bind", "Bind a command to an item permanently.", "runsafe.admintoolbox.bind.permanent",
			new StaticArgument("-p"), new TrailingArgument("commands"));
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeMeta handItem = executor.getItemInHand();

		if (handItem == null)
			return "&cYou need to bind to an item";

		String commandString = parameters.get("commands");

		NBTTagCompound tag = handItem.getTagCompound();
		if (tag == null)
			tag = new NBTTagCompound();
		tag.setString("runsafe.bound-commands", commandString);
		handItem.setTagCompound(tag);
		return "&aBound!";
	}
}
