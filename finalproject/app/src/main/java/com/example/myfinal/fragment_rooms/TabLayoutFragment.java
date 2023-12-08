package com.example.myfinal.fragment_rooms;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal.MainActivity;
import com.example.myfinal.R;
import com.example.myfinal.adapters.DeviceAdapter;
import com.example.myfinal.databases.DatabaseHandler;
import com.example.myfinal.fragments.RoomFragment;
import com.example.myfinal.interfaces.OnDataNumberDeviceInRoomChangeListener;
import com.example.myfinal.models.DeviceItem;
import com.example.myfinal.models.RoomItem;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutFragment extends Fragment
        implements DeviceAdapter.DeviceItemLongClickListener{
    RecyclerView rcvDevices;
    DeviceAdapter deviceAdapter;
    private List<DeviceItem> listDevice;

    int idRoom;
    String nameRoom;
    DatabaseHandler databaseHandler;

    public TabLayoutFragment(int idRoom, String nameRoom) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
    }



    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frament_tablayout, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvDevices = view.findViewById(R.id.rcv_devices);
        //db
        databaseHandler = ((MainActivity) requireActivity()).getDatabaseHandler();

        // ADAPTER
        deviceAdapter = new DeviceAdapter(this, this, databaseHandler);

        int numberOfColumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), numberOfColumns);
        rcvDevices.setLayoutManager(gridLayoutManager);

        listDevice = new ArrayList<>();


        listDevice = databaseHandler.getAllDeviceByRoom(idRoom);
        System.out.println(listDevice.size());
        System.out.println(listDevice);

        deviceAdapter.setData(listDevice);
        rcvDevices.setAdapter(deviceAdapter);

    }
    public void addDevice(DeviceItem deviceItem) {
        Toast.makeText(requireContext(), "Đã gọi thêm thiết bị", Toast.LENGTH_SHORT).show();
        databaseHandler.addDevice(deviceItem);
        listDevice.add(deviceItem);
        deviceAdapter.notifyDataSetChanged();
    }

    public int getIdRoom() {
        return idRoom;
    }
    public String getNameRoom() {
        return nameRoom;
    }


    @Override
    public void onItemLongClick(DeviceItem deviceItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Bạn có chắc chắn muốn xóa thiết bị này?")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Thực hiện xóa thiết bị
                    deleteDevice(deviceItem);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void deleteDevice(DeviceItem deviceItem) {
        // Xóa thiết bị từ cơ sở dữ liệu
        databaseHandler.deleteDevice(deviceItem.getDeviceId());

        // Xóa thiết bị từ danh sách và cập nhật RecyclerView
        listDevice.remove(deviceItem);
        deviceAdapter.notifyDataSetChanged();
    }

}
