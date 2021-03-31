package io.github.therealmone.fireres.core.service;

import io.github.therealmone.fireres.core.model.FunctionsGenerationParams;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.MaintainedFunctionsGenerationParams;
import org.apache.commons.math3.util.Pair;

import java.util.List;

public interface FunctionsGenerationService {

    Pair<IntegerPointSequence, List<IntegerPointSequence>> meanWithChildFunctions(
            FunctionsGenerationParams generationParameters
    );

    List<IntegerPointSequence> maintainedFunctions(
            MaintainedFunctionsGenerationParams generationParameters
    );

}
