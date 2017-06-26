package com.g.deals.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.g.deals.R;
import com.g.deals.adapters.DealsListAdapter;
import com.g.deals.database.DealsInfoDAO;
import com.g.deals.database.DealsInfoModel;
import com.g.deals.models.DealsItemModel;
import com.g.deals.util.NetworkDetails;
import com.g.deals.util.VolleyRequest;
import com.paginate.Paginate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ganesh on 23-06-2017.
 */

public class FragmentTop extends Fragment {

    public final static String TAG = "FragmentTop";
    private VolleyRequest volleyRequest;
    private ProgressDialog pDialog;
    RecyclerView recyclerView;
    DealsListAdapter recyclerAdapter;
    private boolean loading = false;
    private int page = 1;
    private int totalpages = 10;
    DealsInfoDAO dealsInfoDAO;
    LinearLayout noDataLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(
                R.layout.fragment_fragment_tab, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        noDataLL = (LinearLayout)view.findViewById(R.id.no_data_ll);
        volleyRequest = new VolleyRequest(getContext());
        pDialog = new ProgressDialog(getContext());
        dealsInfoDAO = new DealsInfoDAO(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(NetworkDetails.isNetworkAvailable(getContext()))
        {
            setUpRequest(page);
        }
        else
        {
            if(dealsInfoDAO.getDealDataByName("Top").size()>0)
            {
                try
                {
                    JSONArray data = new JSONArray(dealsInfoDAO.getDealDataByName("Top").get(0).getDealResponse());
                    if(data.length()>0)
                    {
                        dataOK();
                        recyclerAdapter = new DealsListAdapter(getContext(),createItemList(data));
                        recyclerView.setAdapter(recyclerAdapter);
                    }
                    else
                    {
                        dataError();
                    }
                }
                catch (Exception e)
                {
                    dataError();
                    e.printStackTrace();
                }
            }
            else
            {
                dataError();
            }
        }

        return view;
    }

    private void setUpRequest(final int page)
    {
        Log.e("for page top",":"+page);
        final String url = "https://stagingapi.desidime.com/v3/deals.json?type=top&deal_view=true&per_page=10&page="+page;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject js = new JSONObject(response.toString());
                            JSONObject deals = js.getJSONObject("deals");
                            JSONArray data  = deals.getJSONArray("data");
                            if(page == 1)
                            {
                                pDialog.hide();
                                if(data.length()>0)
                                {
                                    dataOK();
                                    dealsInfoDAO.insertOrUpdateDeal(new DealsInfoModel("Top",data.toString()));
                                    setupRecyclerView(recyclerView,true,createItemList(data));
                                }
                                else
                                {
                                    dataError();
                                }
                            }
                            else
                            {
                                setupRecyclerView(recyclerView,false,createItemList(data));
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        if(page == 1)
                        {
                            pDialog.hide();
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Accept", "text/javascript");
                params.put("X-Desidime-Client", "0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0");
                return params;
            }
        };
        //loading = false;
        if(page == 1)
        {
            pDialog.setCancelable(false);
            pDialog.show();
        }
        volleyRequest.addToRequestQueue(postRequest, "top");
    }

    private void setupRecyclerView(RecyclerView recyclerView,boolean first,List<DealsItemModel> items)
    {
        if(first)
        {
            recyclerAdapter = new DealsListAdapter(getContext(),items);
            recyclerView.setAdapter(recyclerAdapter);
            Paginate.with(recyclerView, callbacks)
                    .setLoadingTriggerThreshold(2)
                    .addLoadingListItem(true)
                    .build();
        }
        else
        {
            recyclerAdapter.add(items);
            loading = false;
        }
    }

    private List<DealsItemModel> createItemList(JSONArray data)
    {
        List<DealsItemModel> itemList = new ArrayList<>();
        try
        {
            for(int i=0;i<data.length();i++)
            {
                JSONObject deal = data.getJSONObject(i);
                itemList.add(new DealsItemModel( deal.optString("title","title") , deal.optString("description","description"), deal.optString("image","image") ));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return itemList;
    }

    Paginate.Callbacks callbacks = new Paginate.Callbacks() {
        @Override
        public void onLoadMore()
        {
            loading = true;
            page++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setUpRequest(page);
                }
            },1000);
        }

        @Override
        public boolean isLoading() {
            return loading;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return page == (totalpages-1);
        }
    };

    private void dataError()
    {
        recyclerView.setVisibility(View.GONE);
        noDataLL.setVisibility(View.VISIBLE);
    }

    private void dataOK()
    {
        recyclerView.setVisibility(View.VISIBLE);
        noDataLL.setVisibility(View.GONE);
    }
}
