package com.app.ppportal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ppportal.R;
import com.app.ppportal.model.VisitHistoryData;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VisitHistoryAdapter extends RecyclerView.Adapter<VisitHistoryAdapter.ViewHolder> {

    List<VisitHistoryData> list;

    public VisitHistoryAdapter(List<VisitHistoryData> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visit_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(int position){
            TextView tvReason = itemView.findViewById(R.id.tvReason);
            TextView tvTime = itemView.findViewById(R.id.tvTime);

            tvReason.setText(list.get(position).getReason());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(list.get(position).getTime());

            tvTime.setText(calendar.getTime().toString());
        }
    }
}
