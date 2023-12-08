package com.example.myfinal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myfinal.MainActivity;
import com.example.myfinal.R;
import com.example.myfinal.adapters.ViewPager2AdapterTabLayout;
import com.example.myfinal.databases.DatabaseHandler;
import com.example.myfinal.fragment_rooms.TabLayoutFragment;
import com.example.myfinal.models.RoomItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment {
    private View mView;
    TabLayout tabLayout;
    ViewPager2 viewPager2Room;
    ViewPager2AdapterTabLayout viewPager2AdapterTabLayout;
    List<RoomItem> listRoom = new ArrayList<>();

    DatabaseHandler databaseHandler;

//    interface của click tabname
    public interface OnTabClickListener {
        void onTabClick(String tabName);
    }

    private OnTabClickListener tabClickListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_room, container, false);

        //db
        databaseHandler = ((MainActivity) requireActivity()).getDatabaseHandler();

        listRoom = databaseHandler.getAllRoom();
        System.out.println(listRoom);

        tabLayout = mView.findViewById(R.id.tab_layout);
        viewPager2Room = mView.findViewById(R.id.view_pager_2_room);

        // truyền cho adapter list phòng lấy được
        viewPager2AdapterTabLayout = new ViewPager2AdapterTabLayout(requireActivity(), listRoom);

        viewPager2Room.setAdapter(viewPager2AdapterTabLayout);

        onAttach(requireContext());


        new TabLayoutMediator(tabLayout, viewPager2Room, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //Truy xuất từ db

                int mountRoom = listRoom.size();
                int k = 6;
                for(int i = 0; i < mountRoom; i++) {
                    if(i == position) {
                        String nameRoom = listRoom.get(i).getRoomName();
                        tab.setText(nameRoom);
                        break;
                    }
                }
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                int currentTabPosition = tabLayout.getSelectedTabPosition();
                String tabName = tab.getText().toString();
                if (tabClickListener != null) {
                    tabClickListener.onTabClick(tabName);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return mView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            tabClickListener = (OnTabClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTabClickListener");
        }
    }

    public void updateRoomOnTablayout() {
        // Cập nhật danh sách các phòng từ cơ sở dữ liệu
        listRoom = databaseHandler.getAllRoom();

        // Thông báo cho adapter về sự thay đổi trong dữ liệu
        if (viewPager2AdapterTabLayout != null) {
            viewPager2AdapterTabLayout.updateRooms(listRoom);
        }

        // Thông báo cho TabLayoutMediator về sự thay đổi trong dữ liệu
        if (tabLayout != null && viewPager2Room != null) {
            new TabLayoutMediator(tabLayout, viewPager2Room, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    // Truy xuất từ db
                    int mountRoom = listRoom.size();
                    int k = 6;
                    for (int i = 0; i < mountRoom; i++) {
                        if (i == position) {
                            String nameRoom = listRoom.get(i).getRoomName();
                            tab.setText(nameRoom);
                            break;
                        }
                    }
                }
            }).attach();
        }
    }
}
