package io.github.forgetmelody.affixes.api;

import io.github.forgetmelody.affixes.AffixesMod;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface AffixHolder {
    static AffixHolder get(Entity entity) {
        return AffixesMod.getInstance().get(entity);
    }

    void runIteration(AffixVisitor visitor);

    void update(Consumer<AffixHolder.Mutable> updater);

    interface Mutable {

        void upgrade(Holder<Affix> affix, int level);

        void set(Holder<Affix> affix, int level);

        void remove(Holder<Affix> affix);

        void removeIf(Predicate<Holder<Affix>> predicate);
    }

    interface AffixVisitor {
        void accept(Holder<Affix> affix, int level);
    }
}
