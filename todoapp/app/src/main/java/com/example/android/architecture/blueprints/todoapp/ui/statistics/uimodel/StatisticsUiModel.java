package com.example.android.architecture.blueprints.todoapp.ui.statistics.uimodel;

/**
 * Model for the Statistics View.
 */
public class StatisticsUiModel {

    private String mText;

    public StatisticsUiModel(String text) {
        mText = text;
    }

    /**
     * @return text that should be displayed in the Statistics screen.
     */
    public String getText() {
        return mText;
    }
}
