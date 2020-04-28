package com.example.mymobil.info;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymobil.R;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;
/**
 * Update by Jinyeob on 2020. 04. 28.
 */
public class InfoFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private JsoupAsyncTask JsoupAsyncTask;
    private List<NewsData> news = new ArrayList<>();

    private RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        queue = Volley.newRequestQueue(getActivity());

        JsoupAsyncTask = new JsoupAsyncTask();
        JsoupAsyncTask.execute();

        return root;
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect("https://seoul.childcare.go.kr/ccef/community/notice/NoticeSlPL.jsp?BBSGB=48")
                        .get();
                System.out.println(doc);

                Elements titles = doc.select("td.lef a");

                for (Element elem : titles) {
                    String Title_ = elem.attr("title");
                    String link_ = elem.attr("abs:href");

                    Title_=Title_.replace("[연합뉴스]","");

                    System.out.println("@@@@@@@@@@@@@타이틀::: @@@@@@@@@@@@" + Title_);
                    System.out.println("#############링크::: #############" + link_);

                    NewsData newsData = new NewsData();

                    newsData.setTitle(Title_);
                    newsData.setContent(link_);
                    newsData.setUrlToImage("https://t1.daumcdn.net/cfile/tistory/99B6E6405C00AB432D");

                    news.add(newsData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // specify an adapter (see also next example)
            mAdapter = new MyAdapter(news, getActivity(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object obj = v.getTag();
                    if(obj != null) {
                        int position = (int)obj;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(((MyAdapter)mAdapter).getNews(position).getContent()));

                        startActivity(intent);
                    }
                }
            });
            recyclerView.setAdapter(mAdapter);
        }
    }
}