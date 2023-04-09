/*
 * MIT License
 *
 * Copyright (c) 2022 Simon - fantomitechno
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.renoux.dinnermod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import static dev.renoux.dinnermod.Dinnermod.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class Config {

    public static final int DEFAULT_ROTATION = 0;
    private static Config SINGLE_INSTANCE = null;
    private final File configFile;

    private int rotation;

    private String fileName = MODID + ".json";

    public Config() {
        this.configFile = FabricLoader
                .getInstance()
                .getConfigDir()
                .resolve(fileName)
                .toFile();
        this.rotation = DEFAULT_ROTATION;
    }

    public static Config getConfig() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new Config();
        }

        return SINGLE_INSTANCE;
    }

    public void load() {
        try {
            String jsonStr = new String(Files.readAllBytes(this.configFile.toPath()));
            if (!jsonStr.equals("")) {
                // Monstrosity of a json object
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(jsonStr);
                this.rotation = jsonObject.has("rotation") ? jsonObject.getAsJsonPrimitive("rotation").getAsInt()
                        : DEFAULT_ROTATION;
            }
            LOGGER.info("Loaded config");
        } catch (IOException e_) {
            LOGGER.info("No file... uh panic");
            try {
                this.configFile.createNewFile();
                this.save(); // Save the default config
            } catch (IOException e) {
                LOGGER.info("An error occurred.");
                e.printStackTrace();
            }
            // Do nothing, we have no file and thus we have to keep everything as default
        }
    }

    public void save() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("rotation", this.rotation);
        try (PrintWriter out = new PrintWriter(configFile)) {
            out.println(jsonObject.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getRotation() {
        return rotation;
    }

    public void bumpRotation() {
        if (rotation == 3)
            rotation = 0;
        else
            rotation++;
    }

    public String getRotationText() {
        return switch (getRotation()) {
            case 0 -> "Disabled";
            case 1 -> "Down";
            case 2 -> "Left";
            case 3 -> "Right";
            default -> {
                LOGGER.info("Invalid rotation value");
                rotation = 0;
                yield "Disabled";
            }
        };
    }

    public MutableText getText() {
        return Text.translatable("options.dinnermod.modelpart", this.getRotationText());
    }
}
