package com.example.acdajsonclase.ui.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acdajsonclase.R;
import com.example.acdajsonclase.ui.retrofit.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 6/02/18.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private List<Repo> mRepos;
    private Context mContext;

    public ReposAdapter(Context context, List<Repo> contacts) {
        mRepos = contacts;
        mContext = context;
    }

    public ReposAdapter(){
        this.mRepos = new ArrayList<>();
    }

    public void setRepos(ArrayList<Repo> repos){
        mRepos = repos;
        notifyDataSetChanged();
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo = mRepos.get(position);
        holder.txvUser.setText(repo.getName());
        holder.txvDate.setText(repo.getCreatedAt());
        holder.txvDescription.setText(repo.getDescription().toString());
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvUser;
        TextView txvDate;
        TextView txvDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            txvUser = itemView.findViewById(R.id.txvUser);
            txvDate = itemView.findViewById(R.id.txvDate);
            txvDescription = itemView.findViewById(R.id.txvDescription);
        }
    }
}