package org.example;

import griffon.core.artifact.GriffonController;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Map;

@ArtifactProviderFor(GriffonController.class)
public class SamplePresenter extends AbstractGriffonController {
    private SampleModel model;
    private SampleView view;

    @Inject
    private SampleService sampleService;

    public void setModel(SampleModel model) {
        this.model = model;
    }

    public void setView(SampleView view) {
        this.view = view;
    }

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {
        model.outputProperty().addListener((observable, oldValue, newValue) -> {
            runInsideUIAsync(() -> view.getOutput().setText(newValue));        //<4>
        });
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void sayHello() {
        String input = view.getInput().getText();                              //<1>
        String output = sampleService.sayHello(input);                         //<2>
        model.setOutput(output);                                               //<3>
    }
}