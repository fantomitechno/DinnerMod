package fr.fantomitechno.dinnermod;

import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public enum DPlayerModelPart {

    DINNERBONE(PlayerModelPart.values().length, "dinnerbone");

    private final int id;
    private final int bitFlag;
    private final String name;
    private final Text optionName;
    private boolean enabled;

    DPlayerModelPart(int id, String name) {
        this.id = id;
        this.bitFlag = 1 << id;
        this.name = name;
        this.optionName = new LiteralText(name);
        this.enabled = true;
    }

    public int getId() {
        return id;
    }

    public int getBitFlag() {
        return bitFlag;
    }

    public String getName() {
        return name;
    }

    public Text getOptionName() {
        return optionName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
