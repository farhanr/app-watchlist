package fr.pmobile.watchlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFilmActivity extends AppCompatActivity {

    @BindView(R.id.iv_posterdetail)
    ImageView iv_poster;

    @BindView(R.id.tv_titledetail)
    TextView tv_title;

    @BindView(R.id.tv_dateDetail)
    TextView tb_date;

    @BindView(R.id.tv_genreDetail)
    TextView tv_genre;

    @BindView(R.id.tv_descDetail)
    TextView tv_desc;

    @BindView(R.id.tv_status)
    TextView tv_stat;

    @BindView(R.id.tv_duration)
    TextView tv_duration;

    String extras="";

    Gson gson = new Gson();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("favData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Film film = (Film) getIntent().getParcelableExtra("Film");
        int size = (int) getIntent().getIntExtra("Size",0);
        String rel = "RELEASED";
        ArrayList<Film> fav = new ArrayList<>(getIntent().<Film>getParcelableArrayListExtra("listFilm"));
        ArrayList<Film> favSec = new ArrayList<>();

        String allDataTemp = sharedPreferences.getString("favData","");
        favSec = gson.fromJson(allDataTemp, new TypeToken<ArrayList<Film>>(){
        }.getType());

        if (film.getmStatus().equals("2")){
            rel = "UNRELEASED";
        }

        String releaseYear = "released in "+StringUtils.right(film.getmReleaseDate(), 4);

        if(film.getmReleaseDate().length() > 4){
            releaseYear = "coming in "+film.getmReleaseDate();
        }


        for (Film f:favSec
             ) {
            extras+="\n-"+f.getmTitle();
        }

        Glide.with(this).load(Integer.valueOf(film.getmImageFile())).into(iv_poster);
        tv_title.setText(film.getmTitle());
        tb_date.setText(releaseYear);
        tv_genre.setText(film.getmGenre());
        tv_desc.setText(film.getmInfo()+extras);
        tv_stat.setText("Status: "+rel + String.valueOf(size));
        tv_duration.setText("Duration: "+film.getmDuration()+" min");

    }
}
