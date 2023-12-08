package com.example.myfinal.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal.MainActivity;
import com.example.myfinal.R;
import com.example.myfinal.adapters.RoomAdapter;
import com.example.myfinal.databases.DatabaseHandler;
import com.example.myfinal.models.DeviceItem;
import com.example.myfinal.models.RoomItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment
        implements RoomAdapter.OnItemClickListener,
        RoomAdapter.OnItemLongClickListener{
    private View mView;
    private RecyclerView rcvRoom;
    private RoomAdapter roomAdapter;
    public List<RoomItem> listRoom;
    DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

//        db
        databaseHandler = ((MainActivity) requireActivity()).getDatabaseHandler();

        rcvRoom = mView.findViewById(R.id.rcv_room);

        roomAdapter = new RoomAdapter(requireContext(), this, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcvRoom.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        rcvRoom.addItemDecoration(decoration);


//        listRoom  = new ArrayList<>();
        listRoom = databaseHandler.getAllRoom();

        roomAdapter.setData(listRoom);

        rcvRoom.setAdapter(roomAdapter);

        return mView;
    }

    public void addRoom(String roomName) {
        RoomItem newRoom = new RoomItem(1, R.drawable.img_bed_room, roomName);
        databaseHandler.addRoom(newRoom);
        listRoom.add(newRoom);
        roomAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RoomItem roomItem) {

    }

    @Override
    public void onItemLongClick(RoomItem roomItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Thực hiện xóa phòng
                    deleteRoom(roomItem);

                    //Thực hiện thay đổi ở tablayout
                    updateRoomOnTablayout();

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Đóng dialog nếu người dùng chọn Cancel
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void deleteRoom(RoomItem roomItem) {
        // Xóa phòng từ cơ sở dữ liệu
        databaseHandler.deleteRoom(roomItem.getRoomId());

        // Xóa phòng từ danh sách và cập nhật RecyclerView
        listRoom.remove(roomItem);
        roomAdapter.notifyDataSetChanged();
    }

    // hàm thay đổi ở tablayout
    private void updateRoomOnTablayout() {
        Fragment roomFragment = requireActivity().getSupportFragmentManager().findFragmentByTag("f1");
        if (roomFragment != null && roomFragment instanceof RoomFragment) {
            ((RoomFragment) roomFragment).updateRoomOnTablayout();
        }
    }
}
