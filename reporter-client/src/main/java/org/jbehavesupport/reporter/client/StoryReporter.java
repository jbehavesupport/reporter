package org.jbehavesupport.reporter.client;

import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;

import javax.jms.MessageProducer;
import java.util.Objects;
import java.util.Stack;
import java.util.UUID;

/**
 * @author Michal Bocek
 * @since 26/09/2017
 */
public class StoryReporter extends NullStoryReporter {

    private UUID uuid;
    private Stack<Story> stories = new Stack<>();
    private MessageProducer producer;

    public StoryReporter(MessageProducer producer) {
        this.producer = producer;
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        populateUuidIfEmpty();
        stories.push(story);
    }

    @Override
    public void afterStory(boolean givenStory) {
        stories.pop();
        removeUuidIfPossible();
    }

    private void removeUuidIfPossible() {
        if (stories.empty()) {
            uuid = null;
        }
    }

    private void populateUuidIfEmpty() {
        if (Objects.isNull(uuid)) {
            uuid = (UUID.randomUUID());
        }
    }


}
