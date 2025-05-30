package com.gi199.randomod.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;


class RandoModCustomWidget extends ClickableWidget {
    public RandoModCustomWidget(int x, int y, int width, int height) {
            super(x, y, width, height, Text.empty());
        }

        @Override
        protected final void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            // We'll just draw a simple rectangle for now.
            // x1, y1, x2, y2, startColor, endColor
            int startColor = 0xFF00FF00; // Green
            int endColor = 0xFF0000FF; // Blue

            context.fillGradient(getX(), getY(), getX() + this.width, getY() + this.height, startColor, endColor);
                // This is in the "renderWidget" method, so we can check if the mouse is hovering over the widget.
            isHovered();// Red
// Cyan
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
            // For brevity, we'll just skip this for now - if you want to add narration to your widget, you can do so here.
        }


    }
