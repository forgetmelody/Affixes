package io.github.forgetmelody.affixes.common.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public record ConditionalEffect<T>(
        T effect,
        Optional<LootItemCondition> requirement
) {
    public static <T> Codec<ConditionalEffect<T>> codec(Codec<T> effectCodec, LootContextParamSet paramSet) {
        return RecordCodecBuilder.create(instance -> instance.group(
                effectCodec.fieldOf("effect").forGetter(ConditionalEffect::effect),
                conditionCodec(paramSet).optionalFieldOf("requirement").forGetter(ConditionalEffect::requirement)
        ).apply(instance, ConditionalEffect::new));
    }

    private static Codec<LootItemCondition> conditionCodec(LootContextParamSet params) {
        return LootItemCondition.DIRECT_CODEC
                .validate(
                        condition -> {
                            ProblemReporter.Collector collector = new ProblemReporter.Collector();
                            ValidationContext context = new ValidationContext(collector, params);
                            condition.validate(context);
                            return collector.getReport()
                                    .map(msg -> DataResult.<LootItemCondition>error(() -> "Validation error in affix effect condition: " + msg))
                                    .orElseGet(() -> DataResult.success(condition));
                        }
                );
    }

    public boolean matches(LootContext context) {
        return this.requirement.map(condition -> condition.test(context)).orElse(true);
    }
}
