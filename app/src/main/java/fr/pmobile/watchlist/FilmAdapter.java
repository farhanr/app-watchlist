package fr.pmobile.watchlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import org.apache.commons.lang3.StringUtils;

public class FilmAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private ArrayList<Film> listFilm;

    public FilmAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Film> getListFilm() {
        return listFilm;
    }

    public void setListFilm(ArrayList<Film> listFilm) {
        this.listFilm = listFilm;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return getListFilm().size();
    }

    public class ViewHolder extends BaseViewHolder{
        @BindView(R.id.tv_genre)
        TextView mGenre;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.iv_poster)
        ImageView ivCover;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        protected void clear() {
            ivCover.setImageDrawable(null);
            mGenre.setText("");
            mTitle.setText("");
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBind(final int position) {
            super.onBind(position);
            String rel = getListFilm().get(position).getmReleaseDate();

            String releaseYear = StringUtils.right(rel, 4);

            mTitle.setText(" "+getListFilm().get(position).getmTitle().toUpperCase()+ " ("+releaseYear+") ");
            mGenre.setText(getListFilm().get(position).getmGenre().toUpperCase());
            Glide.with(context).load(Integer.valueOf(getListFilm().get(position).getmImageFile())).into(ivCover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent godetail = new Intent(getContext(), DetailFilmActivity.class);
                godetail.putExtra("Film",getListFilm().get(position));
                //Log.e("Title", getListFilm().get(position).getmTitle());
                getContext().startActivity(godetail);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });

        }
    }
}
