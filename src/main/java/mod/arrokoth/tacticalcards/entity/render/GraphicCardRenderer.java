package mod.arrokoth.tacticalcards.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GraphicCardRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T>
{
    private final ItemRenderer itemRenderer;

    protected GraphicCardRenderer(EntityRendererProvider.Context p_174008_)
    {
        super(p_174008_);
        this.itemRenderer = p_174008_.getItemRenderer();
    }

    protected int getBlockLightLevel(T p_116092_, BlockPos p_116093_)
    {
        return super.getBlockLightLevel(p_116092_, p_116093_);
    }

    public void render(T p_116085_, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_, int p_116090_)
    {
        if (p_116085_.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(p_116085_) < 12.25D))
        {
            p_116088_.pushPose();
            p_116088_.scale(1, 1, 1);
            p_116088_.mulPose(this.entityRenderDispatcher.cameraOrientation());
            p_116088_.mulPose(Vector3f.YP.rotationDegrees(90.0F));
            this.itemRenderer.renderStatic(p_116085_.getItem(), ItemTransforms.TransformType.GROUND, p_116090_, OverlayTexture.NO_OVERLAY, p_116088_, p_116089_, p_116085_.getId());
//            this.itemRenderer.renderGuiItem(p_116085_.getItem(), 2, 2);
            p_116088_.popPose();
            super.render(p_116085_, p_116086_, p_116087_, p_116088_, p_116089_, p_116090_);
        }
    }

    public ResourceLocation getTextureLocation(Entity p_116083_)
    {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
