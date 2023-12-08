package com.example.myfinal;

import static android.os.Build.VERSION_CODES.S;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinal.adapters.ViewPager2AdapterMain;
import com.example.myfinal.databases.DatabaseHandler;
import com.example.myfinal.fragments.HomeFragment;
import com.example.myfinal.fragments.RoomFragment;
import com.example.myfinal.models.DeviceItem;
import com.example.myfinal.models.RoomItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.example.myfinal.fragment_rooms.TabLayoutFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RoomFragment.OnTabClickListener{
    List<RoomItem> listRoom;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    ViewPager2AdapterMain viewPager2Adapter;

//    db
    public DatabaseHandler databaseHandler;

    public FloatingActionButton floatingActionButton;

    private String alertDialogTitle = "Thêm phòng";

    public int state = 0;

    public String tabNameRoom;
    public int idRoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        db
        databaseHandler = new DatabaseHandler(this);
//        set mặc định cho tabname là tab và id room đầu tiên được hiển thị từ db
        tabNameRoom = databaseHandler.getAllRoom().get(0).getRoomName();
        idRoom = databaseHandler.getIdRoomByName(tabNameRoom);

        floatingActionButton = findViewById(R.id.fl_action_button);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager2 = findViewById(R.id.view_pager_2_main);


//        Sử dụng ViewPager2
        viewPager2Adapter = new ViewPager2AdapterMain(this);
        viewPager2.setAdapter(viewPager2Adapter);

//        DeviceItem newDevice = new DeviceItem(1, "xy", 1,
//                0,false, 1, 6);
//        databaseHandler.addDevice(newDevice);

        System.out.println("Hiện tại :" + viewPager2.getCurrentItem());



//        Xử lý khi click vào icon
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.bottom_home){
                    state = 0;
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.bottom_rooms){
                    state = 1;
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.bottom_notifications){
                    state = 2;
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.bottom_profile){
                    state = 3;
                    viewPager2.setCurrentItem(3);
                }
                return true;
            }
        });

//        Xử lý khi vuốt chuyển tab thì icon cũng chuyển theo
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        state = 0;
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        state = 1;
                        bottomNavigationView.getMenu().findItem(R.id.bottom_rooms).setChecked(true);
                        break;
                    case 2:
                        state = 2;
                        bottomNavigationView.getMenu().findItem(R.id.bottom_notifications).setChecked(true);
                        break;
                    case 3:
                        state = 3;
                        bottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
                        break;
                }
            }
        });

//        floating button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "hihihih", Toast.LENGTH_SHORT).show();
                if(state == 0) {
                    Toast.makeText(MainActivity.this, "h1", Toast.LENGTH_SHORT).show();
                    showAddRoomDialogRoom();
                } else if (state == 1) {
                    Toast.makeText(MainActivity.this, "h2", Toast.LENGTH_SHORT).show();
                    showAddRoomDialogDevice();
                }
//                }
            }
        });

    }

    private void showAddRoomDialogRoom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm phòng");

        final EditText input = new EditText(this);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String roomName = input.getText().toString();
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f0");
//                "f" + viewPager2.getCurrentItem()
                ((HomeFragment) currentFragment).addRoom(roomName);
//                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + viewPager2.getCurrentItem());
//
//                // Kiểm tra xem currentFragment có phải là instance của HomeFragment hay không
//                if (currentFragment instanceof HomeFragment) {
//                    // Nếu là HomeFragment, thì mới thực hiện chuyển đổi kiểu và gọi phương thức addRoom
//                    ((HomeFragment) currentFragment).addRoom(roomName);
//                } else {
//                    // Xử lý trường hợp khi currentFragment không phải là HomeFragment
//                    // Điều này có thể làm thông báo hoặc xử lý tùy thuộc vào yêu cầu của bạn.
//                    // Ví dụ:
//                    Log.e("MainActivity", "currentFragment is not an instance of HomeFragment");
//                    // Hoặc hiển thị một thông báo Toast hoặc AlertDialog
//                }



                // Cập nhật lại các tabname ở roomfragment
                updateRoomOnTablayout();

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
    private void showAddRoomDialogDevice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tabNameRoom + " Thêm thiết bị " + idRoom );

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Toast.makeText(MainActivity.this, "thêm vài", Toast.LENGTH_SHORT).show();
                String deviceName = input.getText().toString();
                DeviceItem newDevice = new DeviceItem(1, deviceName, 1,
                        0,false, 1, idRoom);

                addDeviceToTab(tabNameRoom, newDevice);

//                databaseHandler.addDevice(newDevice);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    @Override
    public void onTabClick(String tabName) {
        tabNameRoom = tabName;
        idRoom = databaseHandler.getIdRoomByName(tabNameRoom);

        Toast.makeText(this, "Tab selected: " + idRoom + " " + tabName, Toast.LENGTH_SHORT).show();
    }


    private void updateRoomOnTablayout() {
        Fragment roomFragment = getSupportFragmentManager().findFragmentByTag("f1");
        if (roomFragment != null && roomFragment instanceof RoomFragment) {
            ((RoomFragment) roomFragment).updateRoomOnTablayout();
        }
    }


    private TabLayoutFragment findTabLayoutFragment(String tabName) {
        // Lấy danh sách các fragment hiện tại từ FragmentStateAdapter
        List<Fragment> fragments = new ArrayList<>();
        int allRoom = databaseHandler.getAllRoom().size();
//        viewPager2Adapter.getItemCount()
        for (int i = 0; i < allRoom; i++) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + i);
            fragments.add(fragment);
        }

        // Tìm fragment có tên là tabName
        for (Fragment fragment : fragments) {
            if (fragment instanceof TabLayoutFragment) {
                TabLayoutFragment tabFragment = (TabLayoutFragment) fragment;
                if (tabFragment.getNameRoom().equals(tabName)) {
                    return tabFragment;
                }
            }
        }
        return null;
    }

    private void addDeviceToTab(String tabName, DeviceItem newDevice) {
        TabLayoutFragment tabLayoutFragment = findTabLayoutFragment(tabName);
        if (tabLayoutFragment != null) {
            tabLayoutFragment.addDevice(newDevice);
        }
    }

}