package org.example;

import griffon.core.injection.Module;
import griffon.core.test.GriffonUnitRule;
import griffon.core.test.TestFor;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.codehaus.griffon.runtime.core.injection.AbstractModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
@TestFor(SampleViewModel.class)
public class SampleViewModelTest {
    static {
        // force initialization JavaFX Toolkit
        new javafx.embed.swing.JFXPanel();
    }

    private SampleViewModel controller;

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule();

    @Inject private SampleService sampleService;

    @Test
    @Parameters({",Howdy stranger!",
        "Test, Hello Test"})
    public void sayHelloAction(String input, String output) {
        // expect:
        assertThat(controller.getOutput(), nullValue());

        // expectations
        when(sampleService.sayHello(input)).thenReturn(output);

        // when:
        controller.setInput(input);
        controller.sayHello();

        // then:
        await().until(() -> controller.getOutput(), notNullValue());
        assertThat(controller.getOutput(), equalTo(output));
        verify(sampleService, only()).sayHello(input);
    }

    @Nonnull
    private List<Module> moduleOverrides() {
        return asList(new AbstractModule() {
            @Override
            protected void doConfigure() {
                bind(SampleService.class)
                    .toProvider(() -> mock(SampleService.class))
                    .asSingleton();
            }
        });
    }
}