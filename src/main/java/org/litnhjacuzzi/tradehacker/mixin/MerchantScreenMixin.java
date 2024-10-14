package org.litnhjacuzzi.tradehacker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.village.Merchant;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends HandledScreenMixin<MerchantScreenHandler>
{
	@Shadow
	private int selectedIndex;
	
	@Inject(method = "syncRecipeIndex", at = @At("TAIL"))
	private void getItemIfCreative(CallbackInfo ci) {
		IntegratedServer server =  MinecraftClient.getInstance().getServer();
		if(server != null) {
			PlayerEntity player = server.getPlayerManager().getPlayerList().get(0);
			if(player.isCreative()) {
				boolean isShiftPressed = InputUtil.isKeyPressed(
						MinecraftClient.getInstance().getWindow().getHandle(), 340);
				hackItem(player, isShiftPressed);
			}
		}
	}
	
	private void hackItem(PlayerEntity player, boolean isShiftPressed) {
		Merchant merchant = (Merchant) ((MerchantScreenHandlerMixin) handler).getMerchant();
		ItemStack targetItem = merchant.getOffers().get(selectedIndex).getSellItem().copy();
		if(isShiftPressed) {
			targetItem.setCount(targetItem.getMaxCount());
		}
		player.getInventory().insertStack(targetItem);
		player.currentScreenHandler.sendContentUpdates();
	}
}