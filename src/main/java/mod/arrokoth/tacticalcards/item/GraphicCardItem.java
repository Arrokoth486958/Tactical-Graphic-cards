package mod.arrokoth.tacticalcards.item;

import mod.arrokoth.tacticalcards.entity.GraphicCardEntity;
import mod.arrokoth.tacticalcards.utils.RegistryHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GraphicCardItem extends BlockItem
{
    protected final float damage;

    public GraphicCardItem(Block block, float damage)
    {
        super(block, new Properties().tab(RegistryHandler.TAB).stacksTo(1).rarity(Rarity.RARE));
        this.damage = damage;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
    {
        if (!world.isClientSide)
        {
            GraphicCardEntity card = new GraphicCardEntity(world, player, player.getLookAngle().x(), player.getLookAngle().y(), player.getLookAngle().z(), new ItemStack(this), this.damage);
            card.setPos(player.getEyePosition());
            world.addFreshEntity(card);
            player.getCooldowns().addCooldown(this, 20);
            player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
    }
}
