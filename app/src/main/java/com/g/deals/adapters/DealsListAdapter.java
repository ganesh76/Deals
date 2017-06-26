package com.g.deals.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.g.deals.R;
import com.g.deals.models.DealsItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ganesh on 23-06-2017.
 */

public class DealsListAdapter extends RecyclerView.Adapter<DealsListAdapter.DealsItemViewHolder>
{
    private Context context;
    private List<DealsItemModel> list;
   public DealsListAdapter(Context context, List<DealsItemModel> list)
    {
        this.context = context;
        this.list = list;
    }
    @Override
    public DealsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal_item_view, parent, false);

        return new DealsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DealsItemViewHolder holder, int position)
    {
        try
        {
            holder.description.setText(Html.fromHtml(list.get(position).getDealTitle()));
            holder.title.setText(Html.fromHtml(list.get(position).getDealDesc()));
            Picasso.with(context).load(list.get(position).getDealImage()).placeholder(R.drawable.map).error(R.drawable.map).into(holder.dealimg);
        }
        catch (Exception e)
        {
          //  e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void add(List<DealsItemModel> items) {
        int previousDataSize = this.list.size();
        this.list.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    class DealsItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title,description;
        public ImageView dealimg;
        public DealsItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleText);
            description = (TextView) itemView.findViewById(R.id.descText);
            dealimg = (ImageView) itemView.findViewById(R.id.dealIcon);
        }
    }
}
