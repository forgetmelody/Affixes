package io.github.forgetmelody.affixes.api;

import net.minecraft.core.Holder;

public record AffixInstance(Holder<Affix> affix, int level) {
}
