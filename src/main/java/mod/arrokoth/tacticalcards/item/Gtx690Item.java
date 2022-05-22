package mod.arrokoth.tacticalcards.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Gtx690Item extends Item
{
    public Gtx690Item()
    {
        super(new Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
    {
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
    }
}
