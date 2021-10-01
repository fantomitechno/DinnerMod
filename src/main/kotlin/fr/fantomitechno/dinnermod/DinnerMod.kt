package fr.fantomitechno.dinnermod
import net.fabricmc.api.ModInitializer


@Suppress("UNUSED")
object DinnerMod: ModInitializer {
    private const val MOD_ID = "dinnermod"
    override fun onInitialize() {
        println("DinnerMod succefuly loaded")
    }
}