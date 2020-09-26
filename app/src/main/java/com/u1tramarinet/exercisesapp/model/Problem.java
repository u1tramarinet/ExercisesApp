package com.u1tramarinet.exercisesapp.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class Problem {
    public final long id;
    @NonNull
    public final String statement;
    @NonNull
    public final List<Choice> choices;
    @NonNull
    public final Choice answer;
    @NonNull
    public final String commentary;

    @Nullable
    private Record currentResult = null;
    @Nullable
    private Record previewResult = null;
    private boolean marked = false;
    @Nullable
    private String source = null;

    public Problem(long id, @NonNull String statement, @NonNull List<Choice> choices, @NonNull Choice answer, @NonNull String commentary) {
        this.id = id;
        this.statement = statement;
        this.choices = choices;
        this.answer = answer;
        this.commentary = commentary;
    }

    public void setCurrentResult(@NonNull Record currentResult) {
        this.currentResult = currentResult;
    }

    @Nullable
    public Record getCurrentResult() {
        return this.currentResult;
    }

    public void setPreviewRecord(@NonNull Record previewRecord) {
        this.previewResult = previewRecord;
    }

    @Nullable
    public Record getPreviewResult() {
        return this.previewResult;
    }

    public void updateRecord() {
        if (this.currentResult != null) {
            setPreviewRecord(this.currentResult);
        }
    }

    public void clearRecord() {
        this.currentResult = null;
        this.previewResult = null;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isMarked() {
        return this.marked;
    }

    public void setSource(@NonNull String source) {
        this.source = source;
    }

    @Nullable
    public String getSource() {
        return this.source;
    }

    public static class Choice {
        public final String sign;
        public final String statement;

        public Choice(@NonNull String sign, @NonNull String statement) {
            this.sign = sign;
            this.statement = statement;
        }
    }

    public enum Result {
        CORRECT,
        INCORRECT
    }

    public static class Record {
        @NonNull
        public final Result result;
        @NonNull
        public final Date date;

        public Record(@NonNull Result result, @NonNull Date date) {
            this.result = result;
            this.date = date;
        }
    }
}
