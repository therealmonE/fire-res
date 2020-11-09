package io.github.therealmone.fireres.cli;

import lombok.val;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

public class ApplicationTest  {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Test
    public void defaultConfigValuesTest() throws IOException {
        //noinspection ConstantConditions
        val config = this.getClass().getClassLoader().getResource("minimum.conf").getPath();
        val output = temporaryFolder.newFile("output.xlsx");

        Application.main(new String[] {"-c", config, "-o", output.getAbsolutePath()});
    }
}