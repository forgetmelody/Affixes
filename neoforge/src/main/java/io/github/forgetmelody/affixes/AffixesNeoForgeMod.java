package io.github.forgetmelody.affixes;

import io.github.forgetmelody.affixes.api.AffixHolder;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import java.util.Optional;

@Mod(AffixesMod.MOD_ID)
public class AffixesNeoForgeMod extends AffixesMod {

    public AffixesNeoForgeMod(IEventBus modEventBus, ModContainer modContainer) {

    }

    @Override
    public Optional<AffixHolder> get(Entity entity) {
        return Optional.empty();
    }
}
