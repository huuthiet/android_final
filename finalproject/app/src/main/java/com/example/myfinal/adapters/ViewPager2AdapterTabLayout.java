package com.example.myfinal.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myfinal.fragment_rooms.TabLayoutFragment;
import com.example.myfinal.models.RoomItem;

import java.util.List;

public class ViewPager2AdapterTabLayout extends FragmentStateAdapter {
    List<RoomItem> listRoom;

    public ViewPager2AdapterTabLayout(@NonNull FragmentActivity fragmentActivity,
                                      List<RoomItem> listRoom) {
        super(fragmentActivity);
        this.listRoom = listRoom;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int mountRoom = listRoom.size();
        System.out.println(listRoom);
        for(int i = 0; i < mountRoom; i++) {
            if(i == position) {
                int idRoom = listRoom.get(i).getRoomId();
                String nameRoom = listRoom.get(i).getRoomName();
                return new TabLayoutFragment(idRoom, nameRoom);
            }
        }
        //tạm thời để default là phòng đầu tiên
        return new TabLayoutFragment(listRoom.get(0).getRoomId(), listRoom.get(0).getRoomName());
    }



//    Cần phải xử lý để tăng lên tự động khi thêm phòng
    @Override
    public int getItemCount() {
        if (listRoom != null) {
            return listRoom.size();
        }
        return 0;
    }


    public void updateRooms(List<RoomItem> updatedRooms) {
        listRoom.clear();
        listRoom.addAll(updatedRooms);
        notifyDataSetChanged();
    }
}
