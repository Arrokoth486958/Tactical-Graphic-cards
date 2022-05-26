package mod.arrokoth.tacticalcards.utils;

import mod.arrokoth.tacticalcards.entity.render.GraphicCardRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unchecked")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderRegistry
{
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) RegistryHandler.ENTITIES.get("card").get(), ThrownItemRenderer::new);
        event.registerEntityRenderer((EntityType<? extends ThrowableItemProjectile>) RegistryHandler.ENTITIES.get("card").get(), GraphicCardRenderer::new);
    }
}
