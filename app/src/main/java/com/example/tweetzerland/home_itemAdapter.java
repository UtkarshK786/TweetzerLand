package com.example.tweetzerland;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class home_itemAdapter extends RecyclerView.Adapter<home_itemAdapter.home_itemAdapterViewHolder>{
  private ArrayList<String> arrayList;
    private ArrayList<String> followList;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private OnItemClickListener listener;
  public interface OnItemClickListener{
         void onItemClick(int position);
  }

  public void setOnItemClickListener(OnItemClickListener listener){
      this.listener=listener;
  }
    @NonNull
    @Override
    public home_itemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items,parent,false);
       home_itemAdapterViewHolder evh =new home_itemAdapterViewHolder(view,listener);
       return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull home_itemAdapterViewHolder holder, int position) {
       String currentItem=arrayList.get(position);
       holder.textView.setText(currentItem);
//       if(currentItem.equals("a@gmail.com");lo
//        Log.i("follow list",followList.toString());
     if(followList.contains(currentItem))
        holder.imageView.setImageResource(R.drawable.markedtick);
     else
         holder.imageView.setImageResource(R.drawable.unmarkedtick);

    }
public home_itemAdapter(ArrayList<String> lists, ArrayList<String> following){

      arrayList=lists;
      followList=following;
//      setHasStableIds(true);
}
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class home_itemAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;
        public home_itemAdapterViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
              textView=itemView.findViewById(R.id.textView);
              imageView=itemView.findViewById(R.id.imageView);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                      if(listener!=null){
                          int position=getAdapterPosition();
                          if(position!=RecyclerView.NO_POSITION){
                              listener.onItemClick((position));
                          }
                      }
               }
           });
        }
    }
}
