package com.bvu.assistant.ui.main.news.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentNewsCommonBinding;
import com.bvu.assistant.data.repository.article.Article;
import com.bvu.assistant.system.services.NewsListenerService;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.main.MainActivity;
import com.bvu.assistant.ui.main.MainActivityViewModel;
import com.bvu.assistant.data.model.interfaces.CommonNewsSearchCallback;
import com.bvu.assistant.utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;


public class NewsCommonFragment
        extends BaseFragment<FragmentNewsCommonBinding, NewsCommonFragmentViewModel>
        implements CommonNewsSearchCallback {

    private static final String TAG = "NewsCommonFragment";
    private static final String ARGUMENT_TAB_POSITION = "POSITION";
    private static final String[] FIRESTORE_DOC_PATH = Constants.NEWS_CATEGORY_TITLES;

    private FirebaseFirestore db;   //  getInstance onAttach()
    private NewsRecyclerAdapter adapter;
    private ArrayList<Article> dataList;
    private int tabPosition;



    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_common;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }



    //  return A new instance of fragment NewsCommonFragment.
    public static NewsCommonFragment newInstance(int tabPosition) {
        NewsCommonFragment fragment = new NewsCommonFragment();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_TAB_POSITION, tabPosition);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {   // from: newInstance() --> fragment.setArgs(Bundle);
            tabPosition = getArguments().getInt(ARGUMENT_TAB_POSITION);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.onNewsFragmentAttached(B);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();

        B.refresher.setColorSchemeColors(
            Color.rgb(0, 138, 230),
            Color.rgb(25, 189, 0),
            Color.rgb(255, 204, 0),
            Color.rgb(245, 0, 53)
        );



        //  Register receiver && start the Service --> listen for FireStore data changes
        activity.registerReceiver(new NewsReceiver(), new IntentFilter(NewsReceiver.NEW_ARTICLE_ACTION));
        activity.startService(new Intent(activity, NewsListenerService.class));


        dataList = new ArrayList<>();
        adapter = new NewsRecyclerAdapter(dataList, activity, getActivity(), getFragmentManager());
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);

        B.recycler.setAdapter(adapter);
        B.recycler.setItemAnimator(new DefaultItemAnimator());
        B.recycler.setLayoutManager(layoutManager);

        B.refresher.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), "Đang cập nhật Tin tức...", Toast.LENGTH_SHORT).show();
            getCloudData();
        });
        getCloudData();
    }


    private void notifyDataChanged() {
        B.recycler.scheduleLayoutAnimation();
        adapter.notifyDataSetChanged();
        adapter.updateBaseDataList();
        B.refresher.setRefreshing(false);
    }


    private void getCloudData() {
        B.refresher.setRefreshing(true);
        String docPath = "news/details/" + FIRESTORE_DOC_PATH[this.tabPosition];

        db.collection(docPath)
                .limit(20)
                .orderBy("Id", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        dataList.clear();
                        int isNewsArticleCounter = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> record = document.getData();

                            Article item = new Article(
                                FIRESTORE_DOC_PATH[this.tabPosition],
                                record.get("Title").toString(),
                                record.get("Date").toString(),
                                record.get("Link").toString(),
                                (Boolean)record.get("IsNew"),
                                false
                            );

                            dataList.add(item);
                            if (item.isNew()) {
                                isNewsArticleCounter++;
                            }
                        }


                        notifyDataChanged();
                        if(isNewsArticleCounter > 0)
                            if (activity instanceof MainActivity) {
                                MainActivityViewModel mVM = ViewModelProviders.of(activity).get(MainActivityViewModel.class);

                                ArrayList<Integer> newList = mVM.bottomNavBadges.getValue();
                                newList.set(3, newList.get(3) + isNewsArticleCounter);
                                mVM.bottomNavBadges.setValue(newList);
                            }
                    } else {
                        Toast.makeText(getContext(), "Failed to get data from Firebase CloudFireStore", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onTypeComplete(String searchKeyWords) {
        /*if (searchKeyWords.isEmpty())
            return;

        List<Article> lastData = new ArrayList<>(dataList);*/
        adapter.getFilter().filter(searchKeyWords);
    }




    public class NewsReceiver extends BroadcastReceiver {
        public static final String NEW_ARTICLE_ACTION = "NEW_ARTICLE_ACTION";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NEW_ARTICLE_ACTION)) {
                Article a = (Article) intent.getSerializableExtra("article");
                Toast.makeText(context, a.getTitle(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onReceive: " + a.getTitle());
            }
        }
    }


}