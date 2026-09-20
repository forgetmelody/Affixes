package io.github.forgetmelody.affixes.capabilities;

import io.github.forgetmelody.affixes.AffixesMod;
import io.github.forgetmelody.affixes.api.AffixHolder;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.jetbrains.annotations.Nullable;

public final class Capabilities {
    private Capabilities() {
    }

    public static final class Affix {
        public static final EntityCapability<AffixHolder, @Nullable Void> ENTITY = EntityCapability.createVoid(AffixesMod.id("affix_holder"), AffixHolder.class);

        private Affix() {
        }
    }
}
