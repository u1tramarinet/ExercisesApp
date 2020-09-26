package com.u1tramarinet.exercisesapp.ui.quiz;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.u1tramarinet.exercisesapp.R;
import com.u1tramarinet.exercisesapp.model.Problem;
import com.u1tramarinet.exercisesapp.ui.quiz.commentary.CommentaryFragment;
import com.u1tramarinet.exercisesapp.ui.quiz.problem.ProblemFragment;

import java.util.Locale;

public class QuizFragment extends Fragment {

    private QuizViewModel viewModel;
    private ViewPager2 pager;
    private TabLayout tab;
    private Button previewButton;
    private Button nextButton;
    private TextView position;
    private int currentPosition = 0;
    private int totalCount = 0;

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        pager = root.findViewById(R.id.pager);
        tab = root.findViewById(R.id.tab);
        previewButton = root.findViewById(R.id.preview);
        nextButton = root.findViewById(R.id.next);
        position = root.findViewById(R.id.position);
        pager.setAdapter(new QuizFragmentStateAdapter(this));
        new TabLayoutMediator(tab, pager, (tab, position) -> tab.setText((position == 0) ? "問題" : "解説")).attach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        viewModel.getProblem().observe(getViewLifecycleOwner(), new Observer<Problem>() {
            @Override
            public void onChanged(Problem problem) {

            }
        });
        viewModel.getPosition().observe(getViewLifecycleOwner(), position -> {
            pager.setCurrentItem(0, true);
            currentPosition = position + 1;
            updatePosition();
        });
        viewModel.getTotalCount().observe(getViewLifecycleOwner(), count -> {
            totalCount = count;
            updatePosition();
        });
        viewModel.previewEnabled().observe(getViewLifecycleOwner(), enabled -> previewButton.setEnabled((enabled == null) ? false : enabled));
        viewModel.nextEnabled().observe(getViewLifecycleOwner(), enabled -> nextButton.setEnabled((enabled == null) ? false : enabled));
        previewButton.setOnClickListener(v -> viewModel.preview());
        nextButton.setOnClickListener(v -> viewModel.next());
    }

    private void updatePosition() {
        position.setText(String.format(Locale.JAPAN, "%d/%d", currentPosition, totalCount));
    }

    private static class QuizFragmentStateAdapter extends FragmentStateAdapter {
        private static final int PAGE_COUNT = 2;

        public QuizFragmentStateAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return (position == 0) ? ProblemFragment.newInstance() : CommentaryFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}