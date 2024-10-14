package org.litnhjacuzzi.tradehacker.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler>
{
	@Shadow
	protected T handler;
}
