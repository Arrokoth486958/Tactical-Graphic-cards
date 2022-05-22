package mod.arrokoth.tacticalcards.utils;

import mod.arrokoth.tacticalcards.TacticalCards;
import mod.arrokoth.tacticalcards.block.Gtx690Block;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

public class RegistryHandler
{
    public static final Map<String, RegistryObject<Item>> ITEM = new TreeMap<>();
    private static final DeferredRegister<Item> ITEMS_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, TacticalCards.MOD_ID);
    public static final Map<String, RegistryObject<Block>> BLOCKS = new TreeMap<>();
    private static final DeferredRegister<Block> BLOCKS_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, TacticalCards.MOD_ID);

    public static void register(IEventBus bus)

    {
        registerBlock("gtx_690", Gtx690Block::new);
        registerItem("gtx_690", () -> new BlockItem(BLOCKS.get("gtx_690").get(), new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
        ITEMS_REGISTER.register(bus);
        BLOCKS_REGISTER.register(bus);
    }

    private static void registerItem(String id, Supplier<? extends Item> sup)
    {
        ITEM.put(id, ITEMS_REGISTER.register(id, sup));
    }

    private static void registerBlock(String id, Supplier<? extends Block> sup)
    {
        BLOCKS.put(id, BLOCKS_REGISTER.register(id, sup));
    }
}
