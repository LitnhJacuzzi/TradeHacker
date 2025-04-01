package org.litnhjacuzzi.tradehacker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

@Mixin(ScreenHandler.class)
public interface ScreenHandlerMixin {
	
	@Invoker("insertItem")
	boolean invokeInsertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast);
	
	@Invoker("sendContentUpdates")
	void invokeSendContentUpdates();
}
