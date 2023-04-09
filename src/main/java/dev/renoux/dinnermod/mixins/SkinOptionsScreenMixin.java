package dev.renoux.dinnermod.mixins;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import dev.renoux.dinnermod.Config;

@Mixin(SkinOptionsScreen.class)
public class SkinOptionsScreenMixin extends Screen {

  protected SkinOptionsScreenMixin(Text title) {
    super(title);
  }

  private ButtonWidget dinnermodButton;

  @Inject(method = "init", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
  private void onInit(CallbackInfo ci, int i) {
    Config Configuration = Config.getConfig();
    dinnermodButton = ButtonWidget.builder(Configuration.getText(),
        button -> {
          Configuration.bumpRotation();
          button.setMessage(Configuration.getText());
          Configuration.save();
        })
        .size(150, 20)
        .position(this.width / 2 - 75, this.height / 6 + 24 * ((i + 2) >> 1))
        .build();
    this.addDrawableChild(dinnermodButton);
  }
}

// MTA5NDMwNTYyNzYzMjgzMju4MgU4Mg.G5M09e.ra9C9vDtqel4FIigy__bKFEaId015UfAwu0qcY
/*
 * Hi, Kiro you made a little misstake, showing your token online is a really bad idea. I've reset it for you so you don't have to worry for this one, get a new one with the discord developper portal, this time try to not post in on the internet (a stream count as posting) - anonymous developper
 */