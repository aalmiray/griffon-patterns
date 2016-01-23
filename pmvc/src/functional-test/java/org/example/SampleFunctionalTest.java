package org.example;

import griffon.javafx.test.GriffonTestFXClassRule;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.not;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleFunctionalTest {
    @ClassRule
    public static GriffonTestFXClassRule testfx = new GriffonTestFXClassRule("mainWindow");

    @Test
    @Parameters({",Howdy stranger!",
        "Test, Hello Test"})
    public void clickSayHelloButton(String input, String output) throws Exception {
        // given:
        testfx.clickOn("#input").write(input);

        // when:
        testfx.clickOn("#sayHelloActionTarget");

        // then:
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return NodeMatchers.hasText(not("")).matches(testfx.lookup("#output").queryFirst());
        });
        verifyThat("#output", hasText(output));
    }
}