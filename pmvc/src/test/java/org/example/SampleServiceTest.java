package org.example;

import griffon.core.test.GriffonUnitRule;
import griffon.core.test.TestFor;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnitParamsRunner.class)
@TestFor(SampleService.class)
public class SampleServiceTest {
    static {
        // force initialization JavaFX Toolkit
        new javafx.embed.swing.JFXPanel();
    }

    private SampleService service;

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule();

    @Test
    @Parameters({",Howdy stranger!",
        "Test, Hello Test"})
    public void sayHello(String input, String output) {
        assertThat(service.sayHello(input), equalTo(output));
    }
}