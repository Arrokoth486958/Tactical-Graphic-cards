package mod.arrokoth.tacticalcards.utils;

import committee.nova.firesafety.common.block.api.ISpecialRenderType;
import mod.arrokoth.tacticalcards.TacticalCards;
import mod.arrokoth.tacticalcards.block.GraphicCardBlock;
import mod.arrokoth.tacticalcards.entity.GraphicCardEntity;
import mod.arrokoth.tacticalcards.item.GraphicCardItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.TreeMap;

@MethodsReturnNonnullByDefault
public class RegistryHandler
{
    public static final Map<String, RegistryObject<Item>> ITEMS = new TreeMap<>();
    private static final DeferredRegister<Item> ITEMS_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, TacticalCards.MOD_ID);
    public static final Map<String, RegistryObject<Block>> BLOCKS = new TreeMap<>();
    private static final DeferredRegister<Block> BLOCKS_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, TacticalCards.MOD_ID);
    public static final Map<String, RegistryObject<EntityType<?>>> ENTITIES = new TreeMap<>();
    private static final DeferredRegister<EntityType<?>> ENTITIES_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, TacticalCards.MOD_ID);

    public static final CreativeModeTab TAB = new CreativeModeTab("tactical_cards")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ITEMS.get("gtx_690").get());
        }
    };

    public static void register(IEventBus bus)
    {
        // NVIDIA
        registerCard("gt_610", 15,
                Block.box(4.0D, 5.0D, 14.0D, 12.0D, 11.0D, 16.0D),
                Block.box(4.0D, 5.0D, 0.0D, 12.0D, 11.0D, 2.0D),
                Block.box(14.0D, 5.0D, 4.0D, 16.0D, 11.0D, 12.0D),
                Block.box(0.0D, 5.0D, 4.0D, 2.0D, 11.0D, 12.0D),
                Block.box(4.0D, 0.0D, 5.0D, 12.0D, 2.0D, 11.0D),
                Block.box(5.0D, 0.0D, 4.0D, 11.0D, 2.0D, 12.0D),
                Block.box(4.0D, 14.0D, 5.0D, 12.0D, 16.0D, 11.0D),
                Block.box(5.0D, 14.0D, 4.0D, 11.0D, 16.0D, 12.0D));
        registerCard("gtx_590", 20);
        registerCard("gtx_690", 25);
        registerCard("titan_z", 30);
        // AMD
        registerCard("r9_295_x2", 20);
        registerCard("hd_3870", 25);
        ENTITIES.put("card", ENTITIES_REGISTER.register("card", () -> EntityType.Builder.<GraphicCardEntity>of(GraphicCardEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build(TacticalCards.MOD_ID + ".card")));
        ITEMS_REGISTER.register(bus);
        BLOCKS_REGISTER.register(bus);
        ENTITIES_REGISTER.register(bus);
    }

    public static void registerCard(String id, float damage)
    {
        BLOCKS.put(id, BLOCKS_REGISTER.register(id, () -> new GraphicCardBlock(damage)));
        ITEMS.put(id, ITEMS_REGISTER.register(id, () -> new GraphicCardItem(BLOCKS.get(id).get(), damage)));
    }

    public static void registerCard(String id, float damage, VoxelShape NORTH_AABB, VoxelShape SOUTH_AABB, VoxelShape WEST_AABB, VoxelShape EAST_AABB, VoxelShape UP_AABB_Z, VoxelShape UP_AABB_X, VoxelShape DOWN_AABB_Z, VoxelShape DOWN_AABB_X)
    {
        BLOCKS.put(id, BLOCKS_REGISTER.register(id, () -> new GraphicCardBlock(damage, NORTH_AABB, SOUTH_AABB, WEST_AABB, EAST_AABB, UP_AABB_Z, UP_AABB_X, DOWN_AABB_Z, DOWN_AABB_X)));
        ITEMS.put(id, ITEMS_REGISTER.register(id, () -> new GraphicCardItem(BLOCKS.get(id).get(), damage)));
    }

    public static Item getCard(String id)
    {
        return ITEMS.get(id).get();
    }
}
