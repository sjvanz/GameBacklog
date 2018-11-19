package com.example.user.gamebacklog;


import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.TextView;

public class BacklogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final private BacklogAdapter.BacklogClickListener mBacklogClickListener;
    public TextView title;
    public TextView platform;
    public TextView status;
    public TextView backlogDate;
    public View view;

    public BacklogViewHolder(View itemView, BacklogAdapter.BacklogClickListener mBacklogClickListener) {

        super(itemView);
        title =  itemView.findViewById(R.id.gameTitle);
        platform = itemView.findViewById(R.id.gamePlatform);
        status = itemView.findViewById(R.id.gameStatus);
        backlogDate = itemView.findViewById(R.id.backlogDate);

        view = itemView;
        itemView.setOnClickListener(this);
        this.mBacklogClickListener = mBacklogClickListener;
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        mBacklogClickListener.backlogGameOnClick(clickedPosition);
    }
}
