package io.github.forgetmelody.affixes.common.api;

import io.github.forgetmelody.affixes.common.AffixesMod;import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 包含Entity上下文的Affix访问接口，为不同mod loader不同的数据存储方式提供统一访问入口
 * Mutable保证数据操作的原子性
 * AffixVisitor是包含Affix与其level上下文信息的访问接口
 */
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

    @FunctionalInterface
    interface AffixVisitor {
        void accept(Holder<Affix> affix, int level);
    }
}
