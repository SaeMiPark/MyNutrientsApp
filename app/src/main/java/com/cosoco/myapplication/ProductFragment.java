package com.cosoco.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cosoco.myapplication.databinding.FragmentIngredientBinding;
import com.cosoco.myapplication.databinding.FragmentProductBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProductFragment extends Fragment {
    private FragmentProductBinding productBinding;
    public ProductFragment() {
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
        productBinding = FragmentProductBinding.inflate(inflater,container,false);
        View view =  productBinding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productBinding.test2.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        productBinding = null;
    }
}