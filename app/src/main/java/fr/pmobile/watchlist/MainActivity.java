package fr.pmobile.watchlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_main) RecyclerView rView;

    private ArrayList<Film> listFilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        addAdapterItem();
        showRecycleListSec();
    }

    private void addAdapterItem(){
        FilmData filmData = new FilmData(this);
        listFilm = filmData.getListFilm("1");
    }

    private void showRecycleList(){
        rView.setLayoutManager(new LinearLayoutManager(this));
        FilmAdapter filmAdapter = new FilmAdapter(this);
        filmAdapter.setListFilm(listFilm);
        rView.setAdapter(filmAdapter);
    }

    private void showRecycleListSec(){
        rView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this);
        ((RecycleViewAdapter) recycleViewAdapter).setMode(Attributes.Mode.Single);
        recycleViewAdapter.setListFilm(listFilm);
        rView.setAdapter(recycleViewAdapter);
    }
}
