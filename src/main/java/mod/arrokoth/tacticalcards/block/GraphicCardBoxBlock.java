package mod.arrokoth.tacticalcards.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

public class GraphicCardBoxBlock extends FaceAttachedHorizontalDirectionalBlock
{
    protected final GraphicCardBlock card;
//    TODO: VoxelShape and Texture&Models
    //Block.box(1.0D, 5.0D, 14.0D, 15.0D, 11.0D, 16.0D)
    protected final VoxelShape NORTH_AABB = Block.box(1.0D, 4.0D, 12.5D, 15.0D, 12.0D, 16.0D);
    protected final VoxelShape SOUTH_AABB = Block.box(1.0D, 4.0D, 0.0D, 15.0D, 12.0D, 3.5D);
    protected final VoxelShape WEST_AABB = Block.box(12.5D, 4.0D, 1.0D, 16.0D, 12.0D, 15.0D);
    protected final VoxelShape EAST_AABB = Block.box(0.0D, 4.0D, 1.0D, 3.5D, 12.0D, 15.0D);
    protected final VoxelShape UP_AABB_Z = Block.box(1.0D, 0.0D, 4.0D, 15.0D, 3.5D, 12.0D);
    protected final VoxelShape UP_AABB_X = Block.box(4.0D, 0.0D, 1.0D, 12.0D, 3.5D, 15.0D);
    protected final VoxelShape DOWN_AABB_Z = Block.box(1.0D, 12.5D, 4.0D, 15.0D, 16.0D, 12.0D);
    protected final VoxelShape DOWN_AABB_X = Block.box(4.0D, 12.5D, 1.0D, 12.0D, 16.0D, 15.0D);

    public GraphicCardBoxBlock(GraphicCardBlock card)
    {
        super(Properties.of(Material.CLOTH_DECORATION, MaterialColor.CLAY).sound(SoundType.WOOL));
        this.card = card;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_)
    {
        p_54663_.add(FACE, FACING);
    }

//    TODO: VoxelShape and Texture&Models
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext)
    {
        switch((AttachFace) state.getValue(FACE))
        {
            case FLOOR:
                switch(state.getValue(FACING).getAxis())
                {
                    case X:
                        return UP_AABB_X;
                    case Z:
                    default:
                        return UP_AABB_Z;
                }
            case WALL:
                switch((Direction) state.getValue(FACING))
                {
                    case EAST:
                        return EAST_AABB;
                    case WEST:
                        return WEST_AABB;
                    case SOUTH:
                        return SOUTH_AABB;
                    case NORTH:
                    default:
                        return NORTH_AABB;
                }
            case CEILING:
            default:
                switch(state.getValue(FACING).getAxis())
                {
                    case X:
                        return DOWN_AABB_X;
                    case Z:
                    default:
                        return DOWN_AABB_Z;
                }
        }
    }

    @Nonnull
    @Override
    public List<ItemStack> getDrops(@NotNull BlockState p_60537_, LootContext.@NotNull Builder p_60538_)
    {
        return List.of(new ItemStack(this.asItem()));
    }

    @Nonnull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result)
    {
        level.playSound(player, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1f, 1f);
        level.setBlockAndUpdate(pos, card.defaultBlockState().setValue(FACE, state.getValue(FACE)).setValue(FACING, state.getValue(FACING)));
        return InteractionResult.SUCCESS;
    }
}
