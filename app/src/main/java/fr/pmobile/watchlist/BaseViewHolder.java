package fr.pmobile.watchlist;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private int mCurrentPos;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position){
        this.mCurrentPos = position;
        clear();
    }

    public int getmCurrentPos() {
        return mCurrentPos;
    }
}