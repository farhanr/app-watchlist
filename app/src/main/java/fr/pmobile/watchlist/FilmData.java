package fr.pmobile.watchlist;

import android.content.Context;
import android.content.ContextWrapper;

import java.util.ArrayList;

public class FilmData extends ContextWrapper {
    private Context context;
    ArrayList<Film> listFilm = new ArrayList<>();

    String[] filmDrawable;
    String[] filmList;
    String[] filmInfo;
    String[] filmRelease;
    String[] filmGenre;
    String[] filmStatus;
    String[] filmScore;
    String[] filmDuration;
    String[] filmTrailer;
    String[] filmLink;

    public FilmData(Context context) {
        super(context);
        filmDrawable = getResources().getStringArray(R.array.film_drawable);
        filmList = getResources().getStringArray(R.array.film_title);
        filmInfo = getResources().getStringArray(R.array.film_info);
        filmRelease = getResources().getStringArray(R.array.film_release);
        filmGenre = getResources().getStringArray(R.array.film_genre);
        filmStatus = getResources().getStringArray(R.array.film_stat);
        filmScore = getResources().getStringArray(R.array.film_score);
        filmDuration = getResources().getStringArray(R.array.film_duration);
        filmTrailer = getResources().getStringArray(R.array.film_trailer);
        filmLink= getResources().getStringArray(R.array.film_moviedb);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Film> getListFilm(String showType){
        for (int i = 0; i < filmList.length; i++) {
            int resourceId = this.getResources().getIdentifier(filmDrawable[i],"drawable",this.getPackageName());
            String value = String.valueOf(resourceId);
            if(filmStatus[i].equals(showType)){
                listFilm.add(new Film(value, filmList[i], filmInfo[i], filmRelease[i], filmGenre[i], filmStatus[i], filmScore[i], filmDuration[i], filmTrailer[i], filmLink[i]));
            }
        }
        return listFilm;
    }
}
