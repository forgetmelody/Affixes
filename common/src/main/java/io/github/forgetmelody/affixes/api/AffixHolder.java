package io.github.forgetmelody.affixes.api;

import io.github.forgetmelody.affixes.AffixesMod;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface AffixHolder {
    static Optional<AffixHolder> get(Entity entity) {
        return AffixesMod.getInstance().get(entity);
    }

    void forEach(Consumer<Holder<Affix>> action);

    void update(Consumer<AffixHolder.Mutable> updater);

    interface Mutable {
        void add(Holder<Affix> affix);

        void remove(Holder<Affix> affix);

        void removeIf(Predicate<Holder<Affix>> predicate);
    }
}
