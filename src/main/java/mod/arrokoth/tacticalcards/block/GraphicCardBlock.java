package mod.arrokoth.tacticalcards.block;

import committee.nova.firesafety.common.block.api.ISpecialRenderType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GraphicCardBlock extends DirectionalBlock implements ISpecialRenderType
{
    protected final float damage;
    
    public GraphicCardBlock(float damage)
    {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.CLAY));
        this.damage = damage;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction)
    {
        return true;
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion)
    {
        super.onBlockExploded(state, level, pos, explosion);
        explode(level, pos);
        level.removeBlock(pos, false);
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos1, boolean value)
    {
        if (level.hasNeighborSignal(pos))
        {
            explode(level, pos);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return List.of(new ItemStack(this));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        Direction direction = ctx.getClickedFace();
        BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos().relative(direction.getOpposite()));
        return blockstate.is(this) && blockstate.getValue(FACING) == direction ? this.defaultBlockState().setValue(FACING, direction.getOpposite()) : this.defaultBlockState().setValue(FACING, direction);
    }

    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    @Override
    public RenderType getRenderType()
    {
        return RenderType.cutout();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext)
    {
        return switch (state.getValue(FACING))
        {
            case DOWN -> Shapes.box(0, 0.84375, 0.25, 1, 1, 0.75);
            case UP -> Shapes.box(0, 0, 0.25, 1, 0.15625, 0.75);
            case NORTH -> Shapes.box(0, 0.25, 0.828125, 1, 0.75, 0.984375);
            case SOUTH -> Shapes.box(0, 0.25, 0, 1, 0.75, 0.15625);
            case WEST -> Shapes.box(0.84375, 0.25, 0, 1, 0.75, 1);
            case EAST -> Shapes.box(0, 0.25, 0, 0.15625, 0.75, 1);
        };
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion)
    {
        return false;
    }

    protected void explode(Level level, BlockPos pos)
    {
        if (!level.isClientSide)
        {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, null);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), this.damage / 2, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            for (int x = (int) (pos.getX() - (this.damage / 2)); x < (int) (pos.getX() + (this.damage / 2)); ++x)
            {
                for (int z = (int) (pos.getZ() - (this.damage / 2)); z < (int) (pos.getZ() + (this.damage / 2)); ++z)
                {
                    for (int y = (int) (pos.getY() - (this.damage / 2)); y < (int) (pos.getY() + (this.damage / 2)); ++y)
                    {
                        double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(z - pos.getZ(), 2) + Math.pow(y - pos.getY(), 2));
                        if (distance <= this.damage / 2)
                        {
                            BlockPos pos1 = new BlockPos(x, y, z);
                            if (level.getBlockState(pos1.below()).getMaterial().isSolid() && !level.getBlockState(pos1).getMaterial().isSolid() && (distance == 0 || level.random.nextInt((int) (this.damage + (distance * 2))) <= this.damage - distance))
                            {
                                level.setBlockAndUpdate(pos1, BaseFireBlock.getState(level, pos1));
                            }
                        }
                    }
                }
            }
        }
    }
}
