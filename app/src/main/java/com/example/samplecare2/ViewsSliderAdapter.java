package com.example.samplecare2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewsSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    int[] layoutScreens;

    public ViewsSliderAdapter(int[] layouts)
    {
        layoutScreens=layouts;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemViewType(int position)
    {
        return layoutScreens[position];
    }

   @Override
    public int getItemCount()
   {
        return layoutScreens.length;
   }

    public class SliderViewHolder extends RecyclerView.ViewHolder
    {
        public SliderViewHolder(View view)
        {
            super(view);
        }
    }
}







