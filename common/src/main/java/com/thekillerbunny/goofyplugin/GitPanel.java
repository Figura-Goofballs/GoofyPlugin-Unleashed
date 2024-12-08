package com.thekillerbunny.goofyplugin;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.figuramc.figura.gui.screens.AbstractPanelScreen;
import org.figuramc.figura.gui.widgets.lists.AbstractList;

public class GitPanel extends AbstractPanelScreen {
    public GitPanel(Screen parent) {
        super(parent, Component.translatable("goofyplugin.gui.panels.title.history"));
    }

    public static class Commits extends AbstractList {
        public Commits(int x, int y, int width, int height) {
            super(x, y, width, height);
        }
    }
}
