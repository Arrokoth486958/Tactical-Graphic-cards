package mod.arrokoth.tacticalcards.block;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GraphicCardBlock extends DirectionalBlock
{
    public GraphicCardBlock()
    {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.CLAY));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return List.of(new ItemStack(this));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53105_)
    {
        p_53105_.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_53087_)
    {
        Direction direction = p_53087_.getClickedFace();
        BlockState blockstate = p_53087_.getLevel().getBlockState(p_53087_.getClickedPos().relative(direction.getOpposite()));
        return blockstate.is(this) && blockstate.getValue(FACING) == direction ? this.defaultBlockState().setValue(FACING, direction.getOpposite()) : this.defaultBlockState().setValue(FACING, direction);
    }

    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion)
    {
        return true;
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
}
