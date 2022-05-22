package mod.arrokoth.tacticalcards;

import mod.arrokoth.tacticalcards.utils.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TacticalCards.MOD_ID)
public class TacticalCards
{
    public static final String MOD_ID = "tactical_cards";

    public TacticalCards()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryHandler.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
