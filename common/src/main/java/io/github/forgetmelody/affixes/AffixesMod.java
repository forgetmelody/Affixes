package io.github.forgetmelody.affixes;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import io.github.forgetmelody.affixes.api.Affix;
import io.github.forgetmelody.affixes.api.AffixHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.Optional;

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

    public abstract Optional<AffixHolder> get(Entity entity);

    public abstract Codec<DataComponentType>
}
