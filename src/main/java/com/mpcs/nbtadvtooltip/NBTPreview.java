package com.mpcs.nbtadvtooltip;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod("nbtadvtooltip")
public class NBTPreview {

    public static KeyMapping showNbtKeybinding;
    public static boolean isKeybindPressed;

    public NBTPreview() {

        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent itemTooltipEvent) {
        if (itemTooltipEvent.getFlags().isAdvanced()) {
            ItemStack stack = itemTooltipEvent.getItemStack();
            if (stack.hasTag()) {
                if (isKeybindPressed) {
                    assert stack.getTag() != null;
                    String tags = stack.getTag().toString();
                    itemTooltipEvent.getToolTip().add(Component.literal(tags));
                } else {
                    itemTooltipEvent.getToolTip().add(Component.translatable("tooltip.nbtadvtooltip.hold_key", showNbtKeybinding.getTranslatedKeyMessage()));
                }

            } else {
                itemTooltipEvent.getToolTip().add(Component.translatable("tooltip.nbtadvtooltip.no_nbt_found"));
            }
        }
    }

    @SubscribeEvent
    public void onGuiKeyboardPressEvent(ScreenEvent.KeyPressed.Post event) {
        InputConstants.Key input = InputConstants.getKey(event.getKeyCode(), event.getScanCode());
        if (showNbtKeybinding.isActiveAndMatches(input)) {
            isKeybindPressed = true;
        }
    }

    @SubscribeEvent
    public void onGuiKeyboardReleaseEvent(ScreenEvent.KeyReleased.Post event) {
        InputConstants.Key input = InputConstants.getKey(event.getKeyCode(), event.getScanCode());
        if (showNbtKeybinding.isActiveAndMatches(input)) {
            isKeybindPressed = false;
        }
    }

    private void clientSetup(RegisterKeyMappingsEvent event) {
        showNbtKeybinding = new KeyMapping("key.nbtadvtooltip.show_nbt.desc", GLFW.GLFW_KEY_LEFT_SHIFT, "key.nbtadvtooltip.category");
        event.register(showNbtKeybinding);
    }

}


/*
    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent itemTooltipEvent) {
        System.out.println("Start");
        if (itemTooltipEvent.getFlags().isAdvanced()) {
            System.out.println("Advanced");
            ItemStack stack = itemTooltipEvent.getItemStack();
            if (stack.hasTag()) {
                System.out.print("Has Tag");
                Keyboard
                if (showNbtKeybinding.isKeyDown()) {
                    String tags = stack.getTag().toString();
                    itemTooltipEvent.getToolTip().add(new StringTextComponent(tags));
                } else {
                    itemTooltipEvent.getToolTip().add(new TranslationTextComponent("tooltip.nbtadvtooltip.hold_key", I18n.format(showNbtKeybinding.getKey().getTranslationKey())));
                }

            } else {
                itemTooltipEvent.getToolTip().add(new TranslationTextComponent("tooltip.nbtadvtooltip.no_nbt_found"));
            }
        }
    }
 */