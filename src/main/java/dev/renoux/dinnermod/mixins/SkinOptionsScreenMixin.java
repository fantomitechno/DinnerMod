package dev.renoux.dinnermod.mixins;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import dev.renoux.dinnermod.Config;

import java.util.List;

@Mixin(SkinOptionsScreen.class)
public class SkinOptionsScreenMixin extends Screen {

  protected SkinOptionsScreenMixin(Text title) {
    super(title);
  }

  private ButtonWidget dinnermodButton;

  @Inject(method = "addOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addAll(Ljava/util/List;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
  private void addDinnerButton(CallbackInfo ci, List<ClickableWidget> list) {
      Config Configuration = Config.getConfig();
      dinnermodButton = ButtonWidget.builder(Configuration.getText(),
                      button -> {
                          Configuration.bumpRotation();
                          button.setMessage(Configuration.getText());
                          Configuration.save();
                      })
              .size(150, 20)
              .build();
    list.add(dinnermodButton);
  }
}
