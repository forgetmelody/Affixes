package io.github.forgetmelody.affixes;

import com.mojang.serialization.Codec;
import io.github.forgetmelody.affixes.api.AffixHolder;
import io.github.forgetmelody.affixes.world.entity.EntityAffixHolder;
import io.github.forgetmelody.affixes.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(AffixesMod.MOD_ID)
public class AffixesNeoForgeMod extends AffixesMod {

    public AffixesNeoForgeMod(IEventBus modEventBus, ModContainer modContainer) {
        BuiltInRegistries.register(modEventBus);
    }

    @Override
    public AffixHolder get(Entity entity) {
        return new EntityAffixHolder(entity);
    }

    @Override
    public Codec<DataComponentType<?>> getAffixEffectComponentCodec() {
        return BuiltInRegistries.AFFIX_EFFECT_COMPONENT_TYPE.byNameCodec();
    }
}
