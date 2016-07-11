package me.academeg.androidmvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.academeg.androidmvp.R;
import me.academeg.androidmvp.api.dataSet.Joke;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

    private List<Joke> dataSet;

    public JokesAdapter() {
    }

    public void addDataSet(List<Joke> dataSet) {
        if (this.dataSet == null) {
            this.dataSet = dataSet;
        } else {
            this.dataSet.addAll(dataSet);
        }
        notifyDataSetChanged();
    }

    public Joke getItem(int position) {
        return dataSet.get(position);
    }

    public List<Joke> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<Joke> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textTV.setText(dataSet.get(position).getJoke());
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textTV;

        public ViewHolder(View itemView) {
            super(itemView);

            textTV = (TextView) itemView.findViewById(R.id.textTV);
        }
    }
}
