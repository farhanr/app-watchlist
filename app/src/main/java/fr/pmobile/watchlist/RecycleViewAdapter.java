package fr.pmobile.watchlist;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class RecycleViewAdapter extends RecyclerSwipeAdapter<RecycleViewAdapter.ViewHolder> {

    public int swipemode=0;
    private Context context;
    private ArrayList<Film> listFilm;
    private ArrayList<Film> bookmarkedFilm = new ArrayList<>();
    private ArrayList<Film> tempAllData = new ArrayList<>();

    private int[] bookmarkStat;
    Gson gson = new Gson();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    
    //methods
    public RecycleViewAdapter(Context context) {
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

    public void setBookmarkStatSize(int size){
        this.bookmarkStat = new int[size];
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return getListFilm().size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends BaseViewHolder{
        @BindView(R.id.ll_wrapper)
        LinearLayout linearLayout;

        @BindView(R.id.tv_genre)
        TextView mGenre;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.iv_poster)
        ImageView ivCover;

        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

        @BindView(R.id.cv_1)
        CardView cv1;

        @BindView(R.id.cv_2)
        CardView cv2;

        @BindView(R.id.cv_3)
        CardView cv3;

        @BindView(R.id.iv_bookmark)
        ImageView ivbookmark;

        @BindView(R.id.circ_indicator)
        CircularProgressIndicator circularBar;

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

            sharedPreferences = getContext().getSharedPreferences("favData",Context.MODE_PRIVATE);
            editor  = sharedPreferences.edit();
            ivbookmark.bringToFront();

            String rel = getListFilm().get(position).getmReleaseDate();
            String releaseYear = StringUtils.right(rel, 4);

            mTitle.setText(" "+getListFilm().get(position).getmTitle().toUpperCase()+ " ("+releaseYear+") ");
            mGenre.setText(getListFilm().get(position).getmGenre().toUpperCase()+String.valueOf(bookmarkStat[position]));
            Glide.with(context).load(Integer.valueOf(getListFilm().get(position).getmImageFile())).into(ivCover);

            if(bookmarkStat[position]==0){
                Glide.with(getContext()).load(R.drawable.ic_bookmark_border_white_24dp).into(ivbookmark);
            } else{
                Glide.with(getContext()).load(R.drawable.ic_bookmark_white_24dp).into(ivbookmark);
            }

            final String url_trailer = getListFilm().get(position).getmTrailer();
            final String url_db = getListFilm().get(position).getmLink();
            String score = getListFilm().get(position).getmScore();
            final String searchQuery = getListFilm().get(position).getmTitle();


            circularBar.setProgress(Integer.valueOf(score),100);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            if(swipemode==1){
                //swipeLayout.setLeftSwipeEnabled(false);
                //swipeLayout.setLeftSwipeEnabled(false);
                //swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bot_wrap));
                swipeLayout.setRightSwipeEnabled(false);
                swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bot_wrap));
            }
            else {
                swipeLayout.setRightSwipeEnabled(false);
                swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bot_wrap));
            }

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swipeLayout.close();
                        }
                    });

                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                }
            });

            swipeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ivbookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bookmarkedFilm.isEmpty()){
                        bookmarkedFilm.add(getListFilm().get(position));
                        Glide.with(getContext()).load(R.drawable.ic_check_white_24dp).into(ivbookmark);
                        bookmarkStat[position]=1;
                        updateFav();
                        return;
                    }
                    if(bookmarkStat[position]==1){
                        bookmarkedFilm.remove(getListFilm().get(position));
                        Glide.with(getContext()).load(R.drawable.ic_bookmark_border_white_24dp).into(ivbookmark);
                        bookmarkStat[position]=0;
                        deleteOnFav(getListFilm().get(position));
                    }else {
                        bookmarkedFilm.add(getListFilm().get(position));
                        Glide.with(getContext()).load(R.drawable.ic_bookmark_white_24dp).into(ivbookmark);
                        bookmarkStat[position]=1;
                        updateFav();
                    }
                }
            });

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(swipeLayout.getOpenStatus() == SwipeLayout.Status.Close){
                        Intent godetail = new Intent(getContext(), DetailFilmActivity.class);
                        godetail.putExtra("Film",getListFilm().get(position));
                        godetail.putExtra("Size",bookmarkedFilm.size());
                        godetail.putParcelableArrayListExtra("listFilm",bookmarkedFilm);
                        getContext().startActivity(godetail);
                    }
                }
            });

            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });

            cv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        swipeLayout.close();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_trailer));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            cv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeLayout.close();
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, searchQuery);
                    getContext().startActivity(intent);
                }
            });

            cv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeLayout.close();
                    //Toasty.error(getContext(),"lalalala",Toast.LENGTH_SHORT,true).show();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, url_db);
                    intent.setType("text/plain");
                    getContext().startActivity(Intent.createChooser(intent, "Send to.."));
                }

            });
            mItemManger.bindView(itemView,position);
        }
        
        public void updateFav(){
            //get string tempdata from sharedpref
            String allTemp = sharedPreferences.getString("favData","");
            //mkae bigdata
            ArrayList<Film> allData= new ArrayList<>();

            //make string tempddata from sharedpref to tempdata arraylist
            tempAllData = gson.fromJson(allTemp, new TypeToken<ArrayList<Film>>(){
            }.getType());
            //add tempdata from sharedpref to big data
            if(tempAllData != null){
                for (Film a: tempAllData
                ) {
                    allData.add(a);
                }
            }

            //allData.addAll(tempAllData);
            if (bookmarkedFilm.isEmpty()){
                String arrayData = gson.toJson(allData);
                editor.putString("favData",arrayData);
                editor.apply();
                return;
            }

            allData.addAll(bookmarkedFilm);

            for (int i= 0; i<allData.size();i++) {
                for (int j = i+1; j < allData.size(); j++) {
                    if (allData.get(i).getmTitle().equals(allData.get(j).getmTitle())) {
                        allData.remove(j);
                    }
                }
            }

            String arrayData = gson.toJson(allData);
            editor.putString("favData",arrayData);
            editor.apply();
            }

        public void deleteOnFav(Film film){
            String allTemp = sharedPreferences.getString("favData","");
            ArrayList<Film> tempDeleted = gson.fromJson(allTemp, new TypeToken<ArrayList<Film>>(){
            }.getType());

            for (Film f: tempDeleted
                 ) {
                if (film.getmTitle().equals(f.getmTitle())){
                    tempDeleted.remove(f);
                }
            }

            String arrayData = gson.toJson(tempDeleted);
            editor.putString("favData",arrayData);
            editor.apply();
        }


        }
    }