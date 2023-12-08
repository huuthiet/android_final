package com.example.myfinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal.R;
import com.example.myfinal.fragments.HomeFragment;
import com.example.myfinal.models.RoomItem;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{
    private Context context;
    private HomeFragment mContext;
    private List<RoomItem> mListRoom;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    //    Interface xử lý click item
    public interface OnItemClickListener {
        void onItemClick(RoomItem roomItem);
    }
    //    Interface xử lý longclick item
    public interface OnItemLongClickListener {
        void onItemLongClick(RoomItem roomItem);
    }

    public RoomAdapter(Context context, HomeFragment mContext,
                       OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.context = context;
        this.mContext = mContext;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    //    Phương thức set giá trị cho adapter
    public void setData(List<RoomItem> list){
        this.mListRoom = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomItem roomItem = mListRoom.get(position);
        if (roomItem == null) {
            return;
        }
        holder.nameRoom.setText(roomItem.getRoomName());
        holder.imgRoom.setImageResource(roomItem.getResourceId());

        holder.itemView.setOnLongClickListener(view -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(roomItem);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {

        if (mListRoom != null) {
            return mListRoom.size();
        }
        return 0;
    }


    public class RoomViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgRoom;
        private TextView nameRoom;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.img_room);
            nameRoom = itemView.findViewById(R.id.name_room);
        }
    }
}
