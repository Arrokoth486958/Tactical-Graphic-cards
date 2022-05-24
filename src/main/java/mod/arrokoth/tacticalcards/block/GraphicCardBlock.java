package mod.arrokoth.tacticalcards.block;

import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GraphicCardBlock extends DirectionalBlock
{
    public GraphicCardBlock()
    {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.CLAY));
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootContext.Builder p_60538_)
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

    public PushReaction getPistonPushReaction(BlockState p_53112_)
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
        Direction direction = state.getValue(FACING);
        VoxelShape shape = Shapes.empty();
        switch (direction)
        {
            case DOWN ->
            {
                shape = Shapes.join(shape, Shapes.box(0, 0.84375, 0.25, 1, 1, 0.75), BooleanOp.OR);
            }
            case UP ->
            {
                shape = Shapes.join(shape, Shapes.box(0, 0, 0.25, 1, 0.15625, 0.75), BooleanOp.OR);
            }
            case NORTH ->
            {
                shape = Shapes.join(shape, Shapes.box(0, 0.25, 0.828125, 1, 0.75, 0.984375), BooleanOp.OR);
            }
            case SOUTH ->
            {
                shape = Shapes.join(shape, Shapes.box(0, 0.25, 0, 1, 0.75, 0.15625), BooleanOp.OR);
            }
            case WEST ->
            {
                shape = Shapes.join(shape, Shapes.box(0.84375, 0.25, 0, 1, 0.75, 1), BooleanOp.OR);
            }
            case EAST ->
            {
                shape = Shapes.join(shape, Shapes.box(0, 0.25, 0, 0.15625, 0.75, 1), BooleanOp.OR);
            }
        }
        return shape;
    }
}
