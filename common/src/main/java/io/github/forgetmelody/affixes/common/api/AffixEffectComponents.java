package io.github.forgetmelody.affixes.common.api;

import com.mojang.serialization.Codec;
import io.github.forgetmelody.affixes.common.AffixesMod;
import io.github.forgetmelody.affixes.common.loot.parameters.LootContextParamSets;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Unit;

import java.util.List;
import java.util.function.UnaryOperator;

public final class AffixEffectComponents {
    public static final Codec<DataComponentMap> CODEC = DataComponentMap.makeCodec(Codec.lazyInitialized(() -> AffixesMod.getInstance().getAffixEffectComponentCodec()));
    public static final DataComponentType<List<ConditionalEffect<Unit>>> IMMUNE_DAMAGE = create(listBuilder -> listBuilder.persistent(ConditionalEffect.codec(Unit.CODEC, LootContextParamSets.AFFIX_DAMAGE).listOf()));

    private AffixEffectComponents() {
    }

    private static <T> DataComponentType<T> create(UnaryOperator<DataComponentType.Builder<T>> operator) {
        return operator.apply(DataComponentType.builder()).build();
    }
}
