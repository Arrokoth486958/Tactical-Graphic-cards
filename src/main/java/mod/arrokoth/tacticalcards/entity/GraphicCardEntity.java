package mod.arrokoth.tacticalcards.entity;

import mod.arrokoth.tacticalcards.utils.RegistryHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("unchecked")
public class GraphicCardEntity extends Fireball
{
    protected final float damage;

    public GraphicCardEntity(EntityType<? extends GraphicCardEntity> p_37364_, Level p_37365_)
    {
        super(p_37364_, p_37365_);
        this.setItem(ItemStack.EMPTY);
        this.damage = 0;
    }

    @Override
    public boolean isNoGravity()
    {
        return false;
    }

    public GraphicCardEntity(Level p_37375_, LivingEntity p_37376_, double p_37377_, double p_37378_, double p_37379_, ItemStack item, float damage)
    {
        super((EntityType<? extends Fireball>) RegistryHandler.ENTITIES.get("card").get(), p_37376_, p_37377_, p_37378_, p_37379_, p_37375_);
        this.setItem(item);
        this.setNoGravity(false);
        this.xPower *= 0.5;
        this.yPower *= 0.5;
        this.zPower *= 0.5;
        this.damage = damage;
    }

    protected void onHitBlock(BlockHitResult hit)
    {
        super.onHitBlock(hit);
    }

    protected void onHit(HitResult result)
    {
        super.onHit(result);
        this.explode(result);
    }

    protected void onHitEntity(EntityHitResult result)
    {
        super.onHitEntity(result);
        this.explode(result);
    }

    @Override
    protected boolean shouldBurn()
    {
        return false;
    }

    protected void explode(HitResult result)
    {
        if (!this.level.isClientSide)
        {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
            this.level.explode(null, this.getX(), this.getY(), this.getZ(), this.damage / 2, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            for (int x = (int) (result.getLocation().x - (this.damage / 2)); x < (int) (result.getLocation().x + (this.damage / 2)); ++x)
            {
                for (int z = (int) (result.getLocation().z - (this.damage / 2)); z < (int) (result.getLocation().z + (this.damage / 2)); ++z)
                {
                    for (int y = (int) (result.getLocation().y - (this.damage / 2)); y < (int) (result.getLocation().y + (this.damage / 2)); ++y)
                    {
                        double distance = Math.sqrt(Math.pow(x - result.getLocation().x, 2) + Math.pow(z - result.getLocation().z, 2) + Math.pow(y - result.getLocation().y, 2));
                        if (distance <= this.damage / 2)
                        {
                            BlockPos pos = new BlockPos(x, y, z);
                            if (level.getBlockState(pos.below()).getMaterial().isSolid() && !level.getBlockState(pos).getMaterial().isSolid() && (distance == 0 || this.random.nextInt((int) (this.damage + (distance * 2))) <= this.damage - distance))
                            {
                                level.setBlockAndUpdate(pos, BaseFireBlock.getState(this.level, pos));
                            }
                        }
                    }
                }
            }
            this.discard();
        }
    }
}
