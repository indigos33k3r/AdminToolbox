package no.runsafe.admintoolbox.binding;

import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.HashMap;
import java.util.Map;

public class BindingHandler implements IPlayerRightClick
{
	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		player.sendColouredMessage("I see you right clicking!");
		if (usingItem != null)
		{
			player.sendColouredMessage("With an item!");
			CommandBinding binding = getBinding(player, usingItem.getItemType());
			if (binding != null)
				binding.execute(player);
			else
				player.sendColouredMessage("Item was null.");
		}
		return true;
	}

	public void removeBinding(IPlayer player, Item item)
	{
		String playerName = player.getName();
		if (bindings.containsKey(playerName))
			bindings.get(playerName).remove(item);
	}

	public void addBinding(IPlayer player, Item item, String... commands)
	{
		String playerName = player.getName();
		if (!bindings.containsKey(playerName))
			bindings.put(playerName, new HashMap<Item, CommandBinding>(1));

		bindings.get(playerName).put(item, new CommandBinding(commands));
	}

	public CommandBinding getBinding(IPlayer player, Item item)
	{
		String playerName = player.getName();

		if (bindings.containsKey(playerName))
		{
			HashMap<Item, CommandBinding> playerBindings = bindings.get(playerName);
			for (Map.Entry<Item, CommandBinding> node : playerBindings.entrySet())
				if (node.getKey().equals(item))
					return node.getValue();
		}

		return null;
	}

	private final HashMap<String, HashMap<Item, CommandBinding>> bindings = new HashMap<String, HashMap<Item, CommandBinding>>(0);
}
