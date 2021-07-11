package com.example.spacexapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spacexapi.databinding.RecyclerViewBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrewRecyclerAdapter extends RecyclerView.Adapter<CrewRecyclerAdapter.CrewViewHolder> {

    private LayoutInflater layoutInflater;
    private List<CrewModal> crewModals;

    public CrewRecyclerAdapter (Context context , List<CrewModal> crewModals1)
    {
        layoutInflater  = LayoutInflater.from(context);
        this.crewModals = crewModals1;
    }


    @NonNull
    @NotNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerViewBinding binding1 = RecyclerViewBinding.inflate(layoutInflater,parent,false);
        return new CrewViewHolder(binding1);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        holder.binding.name.setText("Name : - " + crewModals.get(position).getName());
        holder.binding.status.setText("Status : - " + crewModals.get(position).getStatus());
        holder.binding.wiki.setText( crewModals.get(position).getWikipedia());
        holder.binding.agency.setText("Agency : - " + crewModals.get(position).getAgency());

        if ( crewModals.get(position).getImageUrl() != null )
        {
            Glide.with(holder.binding.image)
                    .asBitmap()
                    .load(crewModals.get(position).getImageUrl())
                    .into(holder.binding.image);
        }

    }

    @Override
    public int getItemCount() {
        return crewModals.size();
    }

    public class CrewViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewBinding binding;

        public CrewViewHolder(RecyclerViewBinding binding1) {
            super(binding1.getRoot());
            binding = binding1;
        }
    }
}
