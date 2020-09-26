package com.u1tramarinet.exercisesapp.ui.quiz;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.u1tramarinet.exercisesapp.model.Problem;
import com.u1tramarinet.exercisesapp.model.ProblemRepository;

import java.util.List;

public class QuizViewModel extends ViewModel {
    @NonNull
    private final ProblemRepository repository = ProblemRepository.getInstance();
    @NonNull
    private final MutableLiveData<List<Problem>> problemListData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Problem> problemData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> positionData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> totalCountData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> previewEnabled = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> nextEnabled = new MutableLiveData<>();

    public QuizViewModel() {
        super();
        initializeState(repository.getProblems(10));
    }

    public void setProblemList(@NonNull List<Problem> problemList) {
        initializeState(problemList);
    }

    @NonNull
    public LiveData<Problem> getProblem() {
        return problemData;
    }

    @NonNull
    public LiveData<Integer> getPosition() {
        return positionData;
    }

    @NonNull
    public LiveData<Integer> getTotalCount() {
        return totalCountData;
    }

    public void preview() {
        int position = positionData.getValue();
        updatePosition(position - 1);
    }

    public LiveData<Boolean> previewEnabled() {
        return previewEnabled;
    }

    public void next() {
        int position = positionData.getValue();
        updatePosition(position + 1);
    }

    public LiveData<Boolean> nextEnabled() {
        return nextEnabled;
    }

    private void updatePosition(int position) {
        int size = problemListData.getValue().size();
        if ((position < 0) || (position >= size)) {
            return;
        }
        Problem problem = problemListData.getValue().get(position);
        positionData.setValue(position);
        problemData.setValue(problem);
        previewEnabled.setValue((position != 0));
        nextEnabled.setValue((position != (size - 1)));
    }

    private void initializeState(@NonNull List<Problem> problemList) {
        problemListData.setValue(problemList);
        positionData.setValue(0);
        totalCountData.setValue(problemList.size());
        problemData.setValue(problemList.get(0));
        previewEnabled.setValue(false);
        nextEnabled.setValue((problemList.size() > 0));
    }


}