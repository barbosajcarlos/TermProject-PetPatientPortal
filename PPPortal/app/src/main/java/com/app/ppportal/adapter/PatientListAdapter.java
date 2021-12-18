package com.app.ppportal.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ppportal.PatientDetailsActivity;
import com.app.ppportal.R;
import com.app.ppportal.model.PetData;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {

    List<PetData> petList;

    public PatientListAdapter(List<PetData> petList){
        this.petList = petList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_existing_patient,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindData(int position){
            ImageView imgPet = itemView.findViewById(R.id.imgPet);
            TextView tvName = itemView.findViewById(R.id.tvName);
            TextView tvCategory = itemView.findViewById(R.id.tvCategory);
            TextView tvDOB = itemView.findViewById(R.id.tvDob);

            tvName.setText(petList.get(position).getPetName());
            tvCategory.setText(petList.get(position).getPetCategory());
            tvDOB.setText(petList.get(position).getPetDOB());


            Glide.with(imgPet).load(petList.get(position).getPetImage()).placeholder(R.drawable.download).into(imgPet);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(itemView.getContext(), PatientDetailsActivity.class);
                    intent.putExtra("DATA", petList.get(position));
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

    public void updateList(List<PetData> list){
        this.petList =list;
        notifyDataSetChanged();
    }
}
