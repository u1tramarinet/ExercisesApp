package com.u1tramarinet.exercisesapp.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ProblemRepository {
    private static final ProblemRepository INSTANCE = new ProblemRepository();

    private ProblemRepository() {
    }

    public static ProblemRepository getInstance() {
        return INSTANCE;
    }

    public List<Problem> getProblems(int count) {
        List<Problem> problems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Problem problem = new Problem(0, makeWord(String.valueOf(i), 50), makeChoices("あああああ", "いいいいい", "ううううう", "えええええ！？"), new Problem.Choice("ア", "あああああ"), "解説です");
            problems.add(problem);
        }
        return problems;
    }

    private String makeWord(@NonNull String s, int repeat) {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < repeat; i++) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    private List<Problem.Choice> makeChoices(@NonNull String a, @NonNull String b, @NonNull String c, @NonNull String d) {
        List<Problem.Choice> choices = new ArrayList<>();
        choices.add(new Problem.Choice("ア", a));
        choices.add(new Problem.Choice("イ", b));
        choices.add(new Problem.Choice("ウ", c));
        choices.add(new Problem.Choice("エ", d));
        return choices;
    }
}
