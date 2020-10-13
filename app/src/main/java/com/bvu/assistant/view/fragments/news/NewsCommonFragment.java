package com.bvu.assistant.view.fragments.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentNewsCommonBinding;
import com.bvu.assistant.model.Article.Article;
import com.bvu.assistant.services.NewsListenerService;
import com.bvu.assistant.viewmodel.interfaces.CommonNewsSearchCallback;
import com.bvu.assistant.viewmodel.interfaces.MainActivityBadger;
import com.bvu.assistant.viewmodel.interfaces.MainActivityChildFragmentGainer;
import com.bvu.assistant.viewmodel.interfaces.NewsFragmentBadger;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;



public class NewsCommonFragment extends Fragment implements CommonNewsSearchCallback {
    private static final String TAG = "NewsCommonFragment";

    private static final String[] FIRESTORE_DOC_PATH = new String[] {
            "",
            "Trang Chủ ",
            "Tin tức Sinh viên - Học viên",
            "",
            "Học bổng - Khen thưởng",
            "Hoạt động SV",
            "Sau Đại học",

            "Quy Định Đào tạo",
            "Biểu mẫu SV",
            "Quy định Công tác Sinh viên",
            "Các Hướng Dẫn cho sinh viên"
    };

    Context context;
    FirebaseFirestore db;   //  getInstance onAttach()
    FragmentNewsCommonBinding B;
    NewsRecyclerAdapter adapter;
    ArrayList<Article> dataList;


    MainActivityBadger mMainActBadgerCallback;
    MainActivityChildFragmentGainer mMainActivityChildFragmentGainer;
    NewsFragmentBadger mNewsFrmBadgerCallback;



    private static final String ARGUMENT_TAB_POSITION = "POSITION";
    private int tabPosition;


    public NewsCommonFragment() {
        // Required empty public constructor
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
    public void onAttach(@NonNull Context context) {    //  Context always is the Activity that hold this Fragment
        super.onAttach(context);
//        Log.i(TAG, "onAttach: [Activity] " + context.getClass().getName());


        try {
            this.context = context;
            mMainActBadgerCallback = (MainActivityBadger) context;
//            mMainActivityChildFragmentGainer = (MainActivityChildFragmentGainer) context;
            //  Log.i(TAG, "onAttach: [Activity] " + context.getClass());

            mNewsFrmBadgerCallback = (NewsFragmentBadger) getParentFragment();
            //  Log.i(TAG, "onAttach: [Fragment]" + mNewsFrmBadgerCallback.getClass());


            //  Notify to the MainActivity
//            Log.i(TAG, "onAttach: " + tabPosition + " - " + this.getActivity());
//            mMainActivityChildFragmentGainer.onNewFragmentAttached(this);
        }
        catch (ClassCastException e) {
//              Toast.makeText(getContext(), "The MainActivity not yet implemented the Badger Interface", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mMainActBadgerCallback = null;
        mNewsFrmBadgerCallback = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {   // from: newInstance() --> fragment.setArgs(Bundle);
            tabPosition = getArguments().getInt(ARGUMENT_TAB_POSITION);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        B = DataBindingUtil.inflate(inflater, R.layout.fragment_news_common, container, false);

        B.refresher.setColorSchemeColors(
            Color.rgb(0, 138, 230),
            Color.rgb(25, 189, 0),
            Color.rgb(255, 204, 0),
            Color.rgb(245, 0, 53)
        );

        db = FirebaseFirestore.getInstance();

        return B.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //  Register receiver && start the Service --> listen for FireStore data changes
        this.context.registerReceiver(new NewsReceiver(), new IntentFilter(NewsReceiver.NEW_ARTICLE_ACTION));
        this.context.startService(new Intent(this.context, NewsListenerService.class));


        dataList = new ArrayList<>();
        adapter = new NewsRecyclerAdapter(dataList, this.context, getActivity(), getFragmentManager());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

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
//        Log.i(TAG, "getting CloudData..." + docPath);


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

//                            Log.i(TAG, "got CloudData: " + document.getId());

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
                    } else {
                        Toast.makeText(getContext(), "Failed to get data from Firebase CloudFireStore", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onTypeComplete(String searchKeyWords) {
//        if (searchKeyWords.isEmpty())
//            return;

//        List<Article> lastData = new ArrayList<>(dataList);
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

//                dataList.add(0, a);
//                adapter.notifyItemInserted(0);
            }
        }
    }
}