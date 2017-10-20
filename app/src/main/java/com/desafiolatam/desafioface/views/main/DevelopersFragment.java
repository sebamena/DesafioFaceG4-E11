package com.desafiolatam.desafioface.views.main;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.adapters.DevelopersAdapter;
import com.desafiolatam.desafioface.networks.GetUsers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private DevelopersAdapter adapter;
    private boolean pendingRequest = false;

    public DevelopersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.reloadSrl);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.developersRv);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DevelopersAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int position = linearLayoutManager.findLastVisibleItemPosition();
                int total = linearLayoutManager.getItemCount();

                if (total - 10 < position){

                    if (!pendingRequest){
                        Map<String,String> map = new HashMap<String, String>();
                        String currentPage = String.valueOf((total/10)+1);
                        map.put("page",currentPage);
                        new ScrollRequest(4).execute(map);
                    }

                }



            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pendingRequest = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       refreshLayout.setRefreshing(false);
                       adapter.update();
                    }
                },800);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }

    public void update(String name) {
        pendingRequest = true;
        adapter.find(name);
    }

    private class ScrollRequest extends GetUsers{

        public ScrollRequest(int additionalPages) {
            super(additionalPages);
        }

        @Override
        protected void onPreExecute() {
            pendingRequest = true;
            refreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            pendingRequest = false;
            adapter.update();
            refreshLayout.setRefreshing(false);
        }
    }

}
