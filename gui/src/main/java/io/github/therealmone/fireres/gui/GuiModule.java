package io.github.therealmone.fireres.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excel.ExcelModule;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureModule;
import io.github.therealmone.fireres.excess.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.firemode.FireModeModule;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.gui.annotation.processor.AnnotationProcessor;
import io.github.therealmone.fireres.gui.annotation.processor.ModalWindowAnnotationProcessor;
import io.github.therealmone.fireres.gui.configurer.GeneralParametersConfigurer;
import io.github.therealmone.fireres.gui.configurer.PrimaryStageConfigurer;
import io.github.therealmone.fireres.gui.configurer.SamplesConfigurer;
import io.github.therealmone.fireres.gui.configurer.report.ExcessPressureParametersConfigurer;
import io.github.therealmone.fireres.gui.configurer.report.FireModeParametersConfigurer;
import io.github.therealmone.fireres.gui.configurer.report.HeatFlowParametersConfigurer;
import io.github.therealmone.fireres.gui.configurer.report.UnheatedSurfaceFirstGroupConfigurer;
import io.github.therealmone.fireres.gui.configurer.report.UnheatedSurfaceParametersConfigurer;
import io.github.therealmone.fireres.gui.configurer.report.UnheatedSurfaceSecondGroupConfigurer;
import io.github.therealmone.fireres.gui.configurer.report.UnheatedSurfaceThirdGroupConfigurer;
import io.github.therealmone.fireres.gui.controller.common.GeneralParams;
import io.github.therealmone.fireres.gui.model.Logos;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.AlertService;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ExportService;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.PresetService;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import io.github.therealmone.fireres.gui.service.SampleService;
import io.github.therealmone.fireres.gui.service.impl.AlertServiceImpl;
import io.github.therealmone.fireres.gui.service.impl.ChartsSynchronizationServiceImpl;
import io.github.therealmone.fireres.gui.service.impl.ExportServiceImpl;
import io.github.therealmone.fireres.gui.service.impl.FxmlLoadServiceImpl;
import io.github.therealmone.fireres.gui.service.impl.PresetServiceImpl;
import io.github.therealmone.fireres.gui.service.impl.ReportExecutorServiceImpl;
import io.github.therealmone.fireres.gui.service.impl.SampleServiceImpl;
import io.github.therealmone.fireres.heatflow.HeatFlowModule;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceModule;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import javafx.application.HostServices;
import javafx.scene.image.Image;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GuiModule extends AbstractModule {

    public static final Class[] REPORT_PROPERTIES_CLASSES = new Class[] {
            FireModeProperties.class,
            ExcessPressureProperties.class,
            HeatFlowProperties.class,
            UnheatedSurfaceProperties.class
    };

    private final GenerationProperties generationProperties = new GenerationProperties();

    private final Application application;
    private final ApplicationConfig applicationConfig;

    @Override
    protected void configure() {
        install(new CoreModule(generationProperties));
        install(new FireModeModule());
        install(new ExcessPressureModule());
        install(new UnheatedSurfaceModule());
        install(new HeatFlowModule());
        install(new ExcelModule());

        bind(GeneralParams.class).toInstance(new GeneralParams());

        bind(GraphicalInterface.class).in(Singleton.class);
        bind(SampleService.class).to(SampleServiceImpl.class).in(Singleton.class);
        bind(FxmlLoadService.class).to(FxmlLoadServiceImpl.class).in(Singleton.class);
        bind(ChartsSynchronizationService.class).to(ChartsSynchronizationServiceImpl.class).in(Singleton.class);
        bind(ExportService.class).to(ExportServiceImpl.class).in(Singleton.class);
        bind(AlertService.class).to(AlertServiceImpl.class).in(Singleton.class);
        bind(HostServices.class).toInstance(application.getHostServices());
        bind(Logos.class).toInstance(loadLogos());
        bind(ExecutorService.class).toInstance(configureExecutorService());
        bind(ReportExecutorService.class).to(ReportExecutorServiceImpl.class).in(Singleton.class);
        bind(PresetService.class).to(PresetServiceImpl.class).in(Singleton.class);

        bind(GeneralParametersConfigurer.class).in(Singleton.class);
        bind(PrimaryStageConfigurer.class).in(Singleton.class);
        bind(SamplesConfigurer.class).in(Singleton.class);
        bind(ExcessPressureParametersConfigurer.class).in(Singleton.class);
        bind(FireModeParametersConfigurer.class).in(Singleton.class);
        bind(HeatFlowParametersConfigurer.class).in(Singleton.class);
        bind(UnheatedSurfaceParametersConfigurer.class).in(Singleton.class);
        bind(UnheatedSurfaceFirstGroupConfigurer.class).in(Singleton.class);
        bind(UnheatedSurfaceSecondGroupConfigurer.class).in(Singleton.class);
        bind(UnheatedSurfaceThirdGroupConfigurer.class).in(Singleton.class);
    }

    private ExecutorService configureExecutorService() {
        return Executors.newFixedThreadPool(4);
    }

    @SneakyThrows
    private Logos loadLogos() {
        return new Logos(
                new Image(getClass().getResource("/image/logo-512.png").openStream()),
                new Image(getClass().getResource("/image/logo-50.png").openStream())
        );
    }

    @Provides
    @Singleton
    public List<AnnotationProcessor> annotationProcessors(
            ModalWindowAnnotationProcessor modalWindowAnnotationProcessor
    ) {
        return List.of(modalWindowAnnotationProcessor);
    }

    @Provides
    @Singleton
    public List<Preset> loadAvailablePresets(
            ObjectMapper mapper
    ) {
        initializeMapper(mapper);
        val availablePresets = new ArrayList<Preset>();

        availablePresets.addAll(loadDefaultPresets(mapper));
        availablePresets.addAll(loadCustomPresets(mapper));

        return availablePresets;
    }

    private void initializeMapper(ObjectMapper mapper) {
        val module = new SimpleModule();

        module.registerSubtypes(REPORT_PROPERTIES_CLASSES);

        mapper.registerModule(module);
    }

    private List<Preset> loadDefaultPresets(ObjectMapper mapper) {
        return loadPresets(applicationConfig.getDefaultPresetsPath(), mapper);
    }

    private List<Preset> loadCustomPresets(ObjectMapper mapper) {
        return loadPresets(applicationConfig.getCustomPresetsPath(), mapper);
    }

    @SuppressWarnings("ConstantConditions")
    private List<Preset> loadPresets(String path, ObjectMapper mapper) {
        val presetsDir = new File(path);

        if (!presetsDir.exists()) {
            throw new IllegalArgumentException("Can't load presets, path does not exist: " + path);
        }

        if (!presetsDir.isDirectory()) {
            throw new IllegalArgumentException("Can't load presets, path is not a directory: " + path);
        }

        return Arrays.stream(presetsDir.listFiles())
                .map(presetFile -> {
                    try {
                        return mapper.readValue(presetFile, Preset.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

}
