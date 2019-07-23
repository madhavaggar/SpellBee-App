package com.example.game.dictionaryapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Word> dataName;
    private Context mContext;

    public RecyclerAdapter(Context mContext , List<Word> dataName) {
        this.dataName = dataName;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView serialno,names;
        RelativeLayout parentLayout;;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serialno = itemView.findViewById(R.id.serialno);
            names = itemView.findViewById(R.id.names);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_adapter, viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return dataName.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d("RecyclerViewAdapter", "onBind Called.");
        viewHolder.serialno.setText("" + (i+1));
        viewHolder.names.setText(dataName.get(i).getWord()+"\n"+dataName.get(i).getResults().get(0).getLexicalEntries().get(0).getEntries().get(0).getEtymologies().get(0));
    }

}