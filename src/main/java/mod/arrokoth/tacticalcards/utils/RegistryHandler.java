package mod.arrokoth.tacticalcards.utils;

import mod.arrokoth.tacticalcards.TacticalCards;
import mod.arrokoth.tacticalcards.block.GraphicCardBlock;
import mod.arrokoth.tacticalcards.block.GraphicCardBoxBlock;
import mod.arrokoth.tacticalcards.block.GraphicCardDecoBlock;
import mod.arrokoth.tacticalcards.entity.GraphicCardEntity;
import mod.arrokoth.tacticalcards.item.GraphicCardItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
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

    public static final DeferredRegister<Motive> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, TacticalCards.MOD_ID);

    public static final CreativeModeTab TAB = new CreativeModeTab("tactical_cards")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ITEMS.get("gtx_690").get());
        }
    };

//    public static final CreativeModeTab DECO_TAB = new CreativeModeTab("tactical_cards_deco")
//    {
//        @Override
//        public ItemStack makeIcon()
//        {
//            return new ItemStack(ITEMS.get("gtx_690_box").get());
//        }
//    };

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
        registerDeco("gt_610");
        registerCard("gtx_590", 20);
        registerDeco("gtx_590");
        registerCard("gtx_690", 25);
        registerDeco("gtx_690");
        registerCard("titan_z", 30);
        registerDeco("titan_z");
        registerCard("rtx_4099", 60);
        registerDeco("rtx_4099");
        // AMD
        registerCard("r9_295_x2", 20);
        registerDeco("r9_295_x2");
        // What about RX 580?
        registerCard("hd_3870", 25);
        registerDeco("hd_3870");
        // Comming soon: ATi Voodoo5 6000
//        registerCard("ati_voodoo5_6000", 20);
//        registerDeco("ati_voodoo5_6000");
        // Paintings
        PAINTINGS.register("nvidia_1", () -> new Motive(16, 32));
        PAINTINGS.register("amd_1", () -> new Motive(16, 32));
        // Entity
        ENTITIES.put("card", ENTITIES_REGISTER.register("card", () -> EntityType.Builder.<GraphicCardEntity>of(GraphicCardEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build(TacticalCards.MOD_ID + ".card")));
        // Final registry
        ITEMS_REGISTER.register(bus);
        BLOCKS_REGISTER.register(bus);
        ENTITIES_REGISTER.register(bus);
        PAINTINGS.register(bus);
    }

    public static void registerCard(String id, float damage)
    {
        BLOCKS.put(id, BLOCKS_REGISTER.register(id, () -> new GraphicCardBlock(damage)));
        ITEMS.put(id, ITEMS_REGISTER.register(id, () -> new GraphicCardItem(BLOCKS.get(id).get(), damage)));
        if (TacticalCards.BOX)
        {
            BLOCKS.put(id + "_box", BLOCKS_REGISTER.register(id + "_box", () -> new GraphicCardBoxBlock((GraphicCardBlock) BLOCKS.get(id).get())));
            ITEMS.put(id + "_box", ITEMS_REGISTER.register(id + "_box", () -> new BlockItem(BLOCKS.get(id + "_box").get(), new Item.Properties().tab(TAB).rarity(Rarity.RARE))));
        }
    }

    public static void registerCard(String id, float damage, VoxelShape NORTH_AABB, VoxelShape SOUTH_AABB, VoxelShape WEST_AABB, VoxelShape EAST_AABB, VoxelShape UP_AABB_Z, VoxelShape UP_AABB_X, VoxelShape DOWN_AABB_Z, VoxelShape DOWN_AABB_X)
    {
        BLOCKS.put(id, BLOCKS_REGISTER.register(id, () -> new GraphicCardBlock(damage, NORTH_AABB, SOUTH_AABB, WEST_AABB, EAST_AABB, UP_AABB_Z, UP_AABB_X, DOWN_AABB_Z, DOWN_AABB_X)));
        ITEMS.put(id, ITEMS_REGISTER.register(id, () -> new GraphicCardItem(BLOCKS.get(id).get(), damage)));
        if (TacticalCards.BOX)
        {
            BLOCKS.put(id + "_box", BLOCKS_REGISTER.register(id + "_box", () -> new GraphicCardBoxBlock((GraphicCardBlock) BLOCKS.get(id).get())));
            ITEMS.put(id + "_box", ITEMS_REGISTER.register(id + "_box", () -> new BlockItem(BLOCKS.get(id + "_box").get(), new Item.Properties().tab(TAB).rarity(Rarity.RARE))));
        }
    }

    public static void registerDeco(String cardId)
    {
        BLOCKS.put(cardId + "_deco", BLOCKS_REGISTER.register(cardId + "_deco", () -> new GraphicCardDecoBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.CLAY))));
//        ITEMS.put(cardId + "_deco", ITEMS_REGISTER.register(cardId + "_deco", () -> new BlockItem(BLOCKS.get(cardId + "_deco").get(), new Item.Properties().tab(DECO_TAB))));
        ITEMS.put(cardId + "_deco", ITEMS_REGISTER.register(cardId + "_deco", () -> new BlockItem(BLOCKS.get(cardId + "_deco").get(), new Item.Properties())));
    }

    public static Item getCard(String id)
    {
        return ITEMS.get(id).get();
    }
}
