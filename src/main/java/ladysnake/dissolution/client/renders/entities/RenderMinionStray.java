package ladysnake.dissolution.client.renders.entities;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import ladysnake.dissolution.client.models.ModelMinionSkeleton;
import ladysnake.dissolution.client.models.ModelMinionStray;
import ladysnake.dissolution.common.entity.EntityWanderingSoul;
import ladysnake.dissolution.common.entity.minion.AbstractMinion;
import ladysnake.dissolution.common.entity.minion.EntityMinionSkeleton;
import ladysnake.dissolution.common.entity.minion.EntityMinionStray;
import ladysnake.dissolution.common.entity.minion.EntityMinionZombie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerStrayClothing;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMinionStray extends RenderBiped<EntityMinionStray> {

	private static final ResourceLocation STRAY_TEXTURES = new ResourceLocation("dissolution:textures/entity/minions/minion_stray.png");
	private static final DataParameter<Boolean> DEATH = EntityDataManager.<Boolean>createKey(EntityMinionStray.class, DataSerializers.BOOLEAN);
	private EntityDataManager dataManager;

    public static final Factory FACTORY = new Factory();

    public RenderMinionStray(RenderManager rendermanagerIn, boolean death) {
    		super(rendermanagerIn, new ModelMinionStray(), 0.5F);
    		this.addLayer(new LayerMinionStrayClothing(this));
    		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
            {
                protected void initArmor()
                {
                    this.modelLeggings = new ModelMinionStray(0.5F, true);
                    this.modelArmor = new ModelMinionStray(1.0F, true);
                }
            };
            this.addLayer(layerbipedarmor);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityMinionStray entity) {
    		return STRAY_TEXTURES;

    }

    public static class Factory implements IRenderFactory<EntityMinionStray> {

        @Override
        public Render<EntityMinionStray> createRenderFor(RenderManager manager) {
        	
        		return new RenderMinionStray(manager, true);	
 	
        }

    }
   
    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}
   
    @Override
    public void doRender(EntityMinionStray minionIn, double x, double y, double z, float entityYaw, float partialTicks) {
    	GL11.glPushMatrix();
    	if(minionIn.isCorpse() && minionIn.getRemainingTicks() > 0 && minionIn.getRemainingTicks() < minionIn.MAX_DEAD_TICKS) {
    		GL11.glColor4f(1.0f, 1.0f, 1.0f, ((float)minionIn.getRemainingTicks()) / ((float)minionIn.MAX_DEAD_TICKS));
    		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    		GL11.glEnable(GL11.GL_BLEND);
    	} else if (minionIn.getRemainingTicks() > 0 && minionIn.getRemainingTicks() < minionIn.MAX_RISEN_TICKS) {
        	float colorRatio = ((float)minionIn.getRemainingTicks()) / ((float)minionIn.MAX_RISEN_TICKS);
    		GL11.glColor4f(colorRatio, colorRatio, colorRatio, 1.0f);
    	}
    	super.doRender(minionIn, x, y, z, entityYaw, partialTicks);
        GL11.glPopMatrix();
    }
    
    @Override
    protected void preRenderCallback(EntityMinionStray minionIn, float partialTickTime)
    {
    	
        super.preRenderCallback(minionIn, partialTickTime);
    }
}