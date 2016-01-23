package org.example;

import griffon.core.injection.Module;
import griffon.core.mvc.MVCGroup;
import griffon.core.mvc.MVCGroupManager;
import griffon.core.test.GriffonUnitRule;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.codehaus.griffon.runtime.core.injection.AbstractModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class SampleIntegrationTest {
    static {
        // force initialization JavaFX Toolkit
        new javafx.embed.swing.JFXPanel();
    }

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule();

    @Inject private MVCGroupManager mvcGroupManager;

    private MVCGroup group;

    @Before
    public void setup() {
        group = mvcGroupManager.createMVCGroup("sample");
    }

    @After
    public void cleanup() {
        if (group != null) {
            group.destroy();
        }
    }

    @Test
    @Parameters({",Howdy stranger!",
        "Test, Hello Test"})
    public void sayHelloAction(String input, String output) {
        // given:
        SampleModel model = mvcGroupManager.findModel("sample", SampleModel.class);
        SampleController controller = mvcGroupManager.findController("sample", SampleController.class);

        // expect:
        assertThat(model.getOutput(), nullValue());
        // when:
        model.setInput(input);
        controller.sayHello();

        // then:
        assertThat(model.getOutput(), equalTo(output));
    }

    @Nonnull
    private List<Module> moduleOverrides() {
        return asList(new AbstractModule() {
            @Override
            protected void doConfigure() {
                bind(SampleView.class)
                    .toProvider(() -> mock(SampleView.class));
            }
        });
    }
}