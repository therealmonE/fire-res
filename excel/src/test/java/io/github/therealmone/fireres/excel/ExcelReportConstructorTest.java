package io.github.therealmone.fireres.excel;

import com.google.inject.Inject;
import lombok.val;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(GuiceRunner.class)
public class ExcelReportConstructorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Inject
    private ReportConstructor reportConstructor;

    @Test
    public void construct() throws IOException {
        val file = temporaryFolder.newFile("test.xls");

        reportConstructor.construct(file);
    }

}