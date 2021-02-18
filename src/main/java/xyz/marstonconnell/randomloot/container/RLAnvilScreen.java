package xyz.marstonconnell.randomloot.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.marstonconnell.randomloot.RandomLootMod;

@OnlyIn(Dist.CLIENT)
public class RLAnvilScreen extends ContainerScreen<RLRepairContainer> implements IContainerListener {
	private static final ResourceLocation TEXTURE = new ResourceLocation(RandomLootMod.MODID,
			"textures/gui/editor.png");

	public RLAnvilScreen(RLRepairContainer container, PlayerInventory pInv, ITextComponent textComp) {
		super(container, pInv, textComp);
	}

	protected void init() {
		super.init();
		this.container.addListener(this);
	}

	@Override
	public void onClose() {
		super.onClose();
		this.container.removeListener(this);

	}

	@Override
	public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		this.renderBackground(p_230430_1_);
		super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
		RenderSystem.disableBlend();
		this.renderHoveredTooltip(p_230430_1_, p_230430_2_, p_230430_3_);

		Button destroy = new Button(164, 72, 64, 18, new TranslationTextComponent("button.remove"), new IPressable() {

			@Override
			public void onPress(Button p_onPress_1_) {
//				getMinecraft().player.connection.send
				container.removeAllTraits();
			}
		});

//		this.buttons.add(destroy);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(this.TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;

		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
		this.blit(matrixStack, i + 59, j + 20, 0, this.ySize + (this.container.getSlot(0).getHasStack() ? 0 : 16), 110,
				16);
		if ((this.container.getSlot(0).getHasStack() || this.container.getSlot(1).getHasStack())
				&& !this.container.getSlot(3).getHasStack()) {
			this.blit(matrixStack, i + 99, j + 45, this.xSize, 0, 28, 21);
		}
	}

	@Override
	public void sendAllContents(Container arg0, NonNullList<ItemStack> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendSlotContents(Container arg0, int arg1, ItemStack arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendWindowProperty(Container arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}