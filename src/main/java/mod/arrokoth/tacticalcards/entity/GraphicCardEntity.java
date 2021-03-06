package mod.arrokoth.tacticalcards.entity;

import com.google.common.collect.Sets;
import mod.arrokoth.tacticalcards.utils.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import javax.print.DocFlavor;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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

//    @Override
//    public Vec3 getDeltaMovement()
//    {
//        siy
//        return super.getDeltaMovement();
//    }

    protected void onHitBlock(BlockHitResult p_37258_)
    {
        super.onHitBlock(p_37258_);
    }

    protected void onHit(HitResult result)
    {
        super.onHit(result);
        if (!this.level.isClientSide)
        {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
            this.level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), this.damage / 2, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
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

    protected void onHitEntity(EntityHitResult p_37216_) {
        super.onHitEntity(p_37216_);
        if (!this.level.isClientSide) {
            Entity entity = p_37216_.getEntity();
            Entity entity1 = this.getOwner();
            entity.hurt(DamageSource.fireball(this, entity1), 6.0F);
            if (entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }

        }
    }

    @Override
    protected boolean shouldBurn()
    {
        return false;
    }
}
