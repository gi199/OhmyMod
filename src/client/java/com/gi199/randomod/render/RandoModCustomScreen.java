package com.gi199.randomod.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;


class RandoModCustomScreen extends Screen {
    private RandoModCustomScreen(Text title) {
        super(title);
    }
    @Override
    public final void init() {
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Hello World"), (btn) -> {
            // When the button is clicked, we can display a toast to the screen.
            assert this.client != null;
            this.client.getToastManager().add(
                    SystemToast.create(this.client, SystemToast.Type.NARRATOR_TOGGLE, Text.of("Hello World!"), Text.of("This is a toast."))
            );
        }).dimensions(40, 40, 120, 20).build();
        // x, y, width, height
        // It's recommended to use the fixed height of 20 to prevent rendering issues with the button
        // textures.

        // Register the button widget.
        this.addDrawableChild(buttonWidget);
        // Add a custom widget to the screen.
// x, y, width, height
        RandoModCustomWidget customWidget = new RandoModCustomWidget(40, 80, 120, 20);
        this.addDrawableChild(customWidget);

    }

    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
        context.drawText(this.textRenderer, "Special Button", 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
        MinecraftClient.getInstance().setScreen(
                new RandoModCustomScreen(Text.empty())
        );
        MinecraftClient.getInstance().setScreen(null);


    }
    private Screen parent;
    private RandoModCustomScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        MinecraftClient.getInstance().setScreen(
                new RandoModCustomScreen(Text.empty(), currentScreen)
        );
    }

    @Override
    public final void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

}