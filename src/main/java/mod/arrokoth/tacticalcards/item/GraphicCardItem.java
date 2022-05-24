package mod.arrokoth.tacticalcards.item;

import mod.arrokoth.tacticalcards.entity.GraphicCardEntity;
import mod.arrokoth.tacticalcards.utils.RegistryHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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
