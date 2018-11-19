package com.pierpaolopascarella.apod;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {


    private List<ListItem> listItemList;
    private Context context;


    public AdapterClass(List<ListItem> listItemList, Context context) {
        this.listItemList = listItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;

        if (MainActivity.media_type.equals("image")) {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
            return new ViewHolder(v);

        }else if (MainActivity.media_type.equals("video")) {
            // make video cardview layout
            // and return new ViewHolder(v);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListItem itemList = listItemList.get(position);

        holder.text.setText(itemList.getText());
        holder.date.setText(itemList.getDate());
        holder.title.setText(itemList.getTitle());

        Picasso.get()
                .load(itemList.getPictureURL())
                .into(holder.picture);

    }

    @Override
    public int getItemCount() {
        return listItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView date;
        public TextView text;
        public ImageView picture;


        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTV);
            date = itemView.findViewById(R.id.dateTV);
            text = itemView.findViewById(R.id.textTV);
            picture = itemView.findViewById(R.id.pictureIMG);
        }
    }

}
