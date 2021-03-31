package io.github.therealmone.fireres.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintainedFunctionsGenerationParams {

    private Integer temperature;
    private Integer tStart;
    private Integer tEnd;

    @Nullable
    private FunctionsGenerationBounds bounds;

    private Integer functionsCount;

}
