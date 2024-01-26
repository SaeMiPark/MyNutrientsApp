package com.cosoco.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cosoco.myapplication.databinding.FragmentIngredientBinding;
import com.google.firebase.auth.FirebaseAuth;


public class IngredientFragment extends Fragment {
    
    private FragmentIngredientBinding ingredientBinding;
    public IngredientFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
        }else{
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ingredientBinding = FragmentIngredientBinding.inflate(inflater,container,false);
        View view =  ingredientBinding.getRoot();
        
        ingredientBinding.test1.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ingredientBinding = null;
    }
}