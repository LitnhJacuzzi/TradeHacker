package org.litnhjacuzzi.tradehacker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.Merchant;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	
	@Shadow
	public ServerPlayerEntity player;
	
	@Inject(method = {"onMerchantTradeSelect", "onSelectMerchantTrade"}, at = @At(value = "INVOKE", target = 
			"Lnet/minecraft/screen/MerchantScreenHandler;switchTo(I)V"))
	public void getItemIfCreative(SelectMerchantTradeC2SPacket packet, CallbackInfo ci) {
		if(player.isCreative()) {
			boolean isShiftPressed = InputUtil.isKeyPressed(
					MinecraftClient.getInstance().getWindow().getHandle(), 340);
			hackItem(player, packet.getTradeId(), isShiftPressed);
		}
	}
	
	private void hackItem(PlayerEntity player, int selectedIndex, boolean isShiftPressed) {
		Merchant merchant = (Merchant) ((MerchantScreenHandlerMixin) player.currentScreenHandler).getMerchant();
		ItemStack targetItem = merchant.getOffers().get(selectedIndex).copySellItem();
		if(isShiftPressed) {
			targetItem.setCount(targetItem.getMaxCount());
		}
		player.getInventory().insertStack(targetItem);
		player.currentScreenHandler.sendContentUpdates();
	}
}
