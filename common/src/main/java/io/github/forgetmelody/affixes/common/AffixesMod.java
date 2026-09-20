package io.github.forgetmelody.affixes.common;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import io.github.forgetmelody.affixes.common.api.AffixHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;

import java.util.Objects;

public abstract class AffixesMod {
    public static final String MOD_ID = "affixes";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static AffixesMod instance;

    public AffixesMod() {
        instance = this;
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static AffixesMod getInstance() {
        return Objects.requireNonNull(instance);
    }

    public abstract AffixHolder get(Entity entity);

    public abstract Codec<DataComponentType<?>> getAffixEffectComponentCodec();
}
