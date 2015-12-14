package org.example;

import griffon.core.artifact.GriffonController;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.inject.Inject;

@ArtifactProviderFor(GriffonController.class)
public class SampleController extends AbstractGriffonController {
    private SampleModel model;

    @Inject
    private SampleService sampleService;

    public void setModel(SampleModel model) {
        this.model = model;
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void sayHello() {
        model.setOutput(sampleService.sayHello(model.getInput()));
    }
}