package fr.fantomitechno.dinnermod.mixins;

import fr.fantomitechno.dinnermod.DPlayerModelPart;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SkinOptionsScreen.class)
public class SkinOptionsScreenMixin extends GameOptionsScreen {

    public SkinOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    //@Inject(at = @At("TAIL"), method = "init")
    /**
     * @author minemobs
     * @reason I'm bad at mixin stuff
     */
    @Overwrite
    public void init() {
        int i = 0;
        PlayerModelPart[] var2 = PlayerModelPart.values();

        for (PlayerModelPart playerModelPart : var2) {
            this.addDrawableChild(CyclingButtonWidget.onOffBuilder(this.gameOptions.isPlayerModelPartEnabled(playerModelPart))
                    .build(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20,
                            playerModelPart.getOptionName(), (button, enabled) -> this.gameOptions.togglePlayerModelPart(playerModelPart, enabled)));
            ++i;
        }

        this.addDrawableChild(Option.MAIN_HAND.createButton(this.gameOptions, this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150));
        ++i;
        if (i % 2 == 1) ++i;

        DPlayerModelPart playerModelPart = DPlayerModelPart.DINNERBONE;
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(playerModelPart.isEnabled()).build(this.width / 2 - 155 + i % 2 * 160,
                this.height / 6 + 24 * (i >> 1), 150, 20, playerModelPart.getOptionName(),
                (button, enabled) -> playerModelPart.setEnabled(enabled)));

        ++i;

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 30 * (i >> 1), 200, 20, ScreenTexts.DONE,
                (button) -> this.client.setScreen(this.parent)));
    }
}
