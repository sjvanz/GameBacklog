package com.example.user.gamebacklog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class BacklogAdapter extends RecyclerView.Adapter<BacklogViewHolder> {
    final private BacklogClickListener mBacklogClickListener;
    public interface BacklogClickListener {
        void backlogGameOnClick (int i);
    }

    public List<Backlog> listBacklog;

    public BacklogAdapter(List<Backlog> listBacklog, BacklogClickListener mBacklogClickListener) {
        this.listBacklog = listBacklog;
        this.mBacklogClickListener = mBacklogClickListener;
    }

    public void swapList (List<Backlog> newList) {
        listBacklog = newList;
        if (newList != null) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public BacklogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.backloggame_viewholder, parent, false);
        return new BacklogViewHolder(view, mBacklogClickListener);
    }

    @Override
    public void onBindViewHolder(final BacklogViewHolder holder, final int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Backlog backlogGame = listBacklog.get(position);
        holder.title.setText(backlogGame.getTitle());
        holder.platform.setText(backlogGame.getPlatform());
        holder.status.setText(backlogGame.getStatus());
        holder.backlogDate.setText(sdf.format(backlogGame.getSaveDate()));
    }

    @Override
    public int getItemCount() {
        return listBacklog.size();
    }
}

