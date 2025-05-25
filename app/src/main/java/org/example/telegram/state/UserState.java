package org.example.telegram.state;

import java.util.HashMap;
import java.util.Map;

public class UserState {
    public enum Step {
        NAME, DAY_DEAD_LINE, STATUS, COMMENT, PRIORITY, DONE
    }

    private Step currentStep = Step.NAME;
    private final Map<String, String> answers = new HashMap<>();

    public Step getCurrentStep() {
        return currentStep;
    }

    public void nextStep() {
        switch (currentStep) {
            case NAME -> currentStep = Step.DAY_DEAD_LINE;
            case DAY_DEAD_LINE -> currentStep = Step.STATUS;
            case STATUS -> currentStep = Step.COMMENT;
            case COMMENT -> currentStep = Step.PRIORITY;
            case PRIORITY -> currentStep = Step.DONE;
        }
    }

    public void saveAnswer(String key, String value) {
        answers.put(key, value);
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public boolean isComplete() {
        return currentStep == Step.DONE;
    }
}
