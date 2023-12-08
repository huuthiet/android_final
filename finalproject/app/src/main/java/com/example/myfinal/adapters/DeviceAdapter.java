package com.example.myfinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal.MainActivity;
import com.example.myfinal.R;
import com.example.myfinal.databases.DatabaseHandler;
import com.example.myfinal.fragment_rooms.TabLayoutFragment;
import com.example.myfinal.fragments.RoomFragment;
import com.example.myfinal.models.DeviceItem;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private List<DeviceItem> mListDevice;
    private TabLayoutFragment mContext;
    private DeviceItemLongClickListener itemClickListener;
    private DatabaseHandler databaseHandler;

    public DeviceAdapter(TabLayoutFragment mContext, DeviceItemLongClickListener itemClickListener,
                         DatabaseHandler databaseHandler) {
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
        this.databaseHandler = databaseHandler;
    }

    public interface DeviceItemLongClickListener {
        void onItemLongClick(DeviceItem deviceItem);
    }

    public void setData(List<DeviceItem> list){
        this.mListDevice = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new DeviceAdapter.DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        DeviceItem deviceItem = mListDevice.get(position);
        if (deviceItem == null) {
            return; //-> không làm gì cả
        }

        holder.nameDevice.setText(deviceItem.getNameDevice());
        holder.switchDevice.setChecked(deviceItem.isStatusDevice());


        // click vào switch
        holder.switchDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    updateDeviceStatus(deviceItem.getDeviceId(), true);
                } else {
                    updateDeviceStatus(deviceItem.getDeviceId(), false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListDevice != null) {
            return mListDevice.size();
        }
        return 0;
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        private TextView nameDevice;
        private Switch switchDevice;

        private ImageView icDevice;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);

            nameDevice = itemView.findViewById(R.id.name_device);
            switchDevice = itemView.findViewById(R.id.switch_device);
            icDevice = itemView.findViewById(R.id.ic_device);

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                    itemClickListener.onItemLongClick(mListDevice.get(position));
                    return true;
                }
                return false;
            });

        }
    }
    private void updateDeviceStatus(int deviceId, boolean status) {
        databaseHandler.updateDeviceStatus(deviceId, status);
    }

}
