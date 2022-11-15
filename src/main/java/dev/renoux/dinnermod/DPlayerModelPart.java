package dev.renoux.dinnermod;

import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

public enum DPlayerModelPart {

    DINNERBONE(PlayerModelPart.values().length, "Dinnerbone");

    private final int id;
    private final int bitFlag;
    private final String name;
    private final Text optionName;
    private byte enabled;

    DPlayerModelPart(int id, String name) {
        this.id = id;
        this.bitFlag = 1 << id;
        this.name = name;
        this.optionName = Text.of(name);
        this.enabled = 1;
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

    public byte getEnabled() {
        return enabled;
    }

    public void addEnabled() {
        if (enabled == 3)
            enabled = 0;
        else
            enabled++;
    }

    public String getRotation() {
        return switch (getEnabled()) {
            case 0 -> "Disabled";
            case 1 -> "Down";
            case 2 -> "Left";
            case 3 -> "Right";
            default -> throw new IllegalStateException("Unexpected value: " + enabled);
        };
    }

    public MutableText getText() {
        return Text.translatable("options.dinnermod.modelpart", this.getRotation());
    }
}
