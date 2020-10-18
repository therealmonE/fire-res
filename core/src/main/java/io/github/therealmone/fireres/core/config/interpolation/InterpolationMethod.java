package io.github.therealmone.fireres.core.config.interpolation;

import lombok.Getter;
import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math3.analysis.interpolation.NevilleInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;

@Getter
public enum InterpolationMethod {

    //todo: Непрерывно растущую функцию дает только LINEAR, что не позволяет использовать другие методы
    AKIMA_SPLINE(new AkimaSplineInterpolator()),
    DIVIDED_DIFFERENCE(new DividedDifferenceInterpolator()),
    LINEAR(new LinearInterpolator()),
    LOESS(new LoessInterpolator()),
    NEVILLE(new NevilleInterpolator()),
    SPLINE(new SplineInterpolator());

    private final UnivariateInterpolator interpolator;

    InterpolationMethod(UnivariateInterpolator interpolator) {
        this.interpolator = interpolator;
    }
}
