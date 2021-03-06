package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.generator.strategy.ChildFunctionGeneratorStrategy;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeanWithChildFunctionGenerationParameters {

    private FunctionForm<?> meanFunctionForm;

    private Integer childFunctionsCount;
    private ChildFunctionGeneratorStrategy strategy;

    private IntegerPointSequence meanLowerBound;
    private IntegerPointSequence meanUpperBound;
    private IntegerPointSequence childLowerBound;
    private IntegerPointSequence childUpperBound;



}
