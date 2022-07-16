package mod.arrokoth.tacticalcards;

import mod.arrokoth.tacticalcards.compatibilities.firesafety.FireSafetyCompatibility;
import mod.arrokoth.tacticalcards.utils.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TacticalCards.MOD_ID)
public class TacticalCards
{
    public static final String MOD_ID = "tactical_cards";
    public static final String MOD_NAME = "Tactical Cards";
    public static final boolean BOX = true;

    public TacticalCards()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryHandler.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        if (ModList.get().isLoaded("firesafety")) MinecraftForge.EVENT_BUS.addListener(FireSafetyCompatibility::onExtension);
    }
}
