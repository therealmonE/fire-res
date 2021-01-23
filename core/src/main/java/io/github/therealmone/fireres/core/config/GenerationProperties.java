package io.github.therealmone.fireres.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationProperties {

    private GeneralProperties general;
    private List<SampleProperties> samples;

    public SampleProperties getSampleById(UUID id) {
        return samples.stream()
                .filter(sample -> sample.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
