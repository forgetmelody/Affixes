package io.github.forgetmelody.affixes.api;

import com.mojang.serialization.Codec;
import io.github.forgetmelody.affixes.AffixesMod;
import net.minecraft.core.component.DataComponentMap;

public final class AffixEffectComponents {
    public static final Codec<DataComponentMap> CODEC = DataComponentMap.makeCodec(Codec.lazyInitialized(() -> AffixesMod.getInstance().getAffixEffectComponentCodec()));
    private AffixEffectComponents() {
    }
}
