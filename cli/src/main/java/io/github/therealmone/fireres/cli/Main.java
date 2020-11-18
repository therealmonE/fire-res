package io.github.therealmone.fireres.cli;

import com.google.inject.Guice;
import lombok.val;

public class Main {

    public static void main(String[] args) {
        val application = Guice.createInjector(new CliModule(args))
                .getInstance(Application.class);

        application.run();
    }

}
