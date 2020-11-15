package io.github.therealmone.fireres.cli;

import lombok.val;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ApplicationTest  {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void defaultConfigValuesTest() {
        //noinspection ConstantConditions
        val config = this.getClass().getClassLoader().getResource("minimum.conf").getPath();

        Application.main(new String[] {"-c", config, "-p", temporaryFolder.getRoot().getAbsolutePath()});
    }
}