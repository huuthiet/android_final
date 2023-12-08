package com.example.myfinal.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myfinal.models.DeviceItem;
import com.example.myfinal.models.RoomItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "smarthome";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_ROOM_NAME = "rooms";

    private static String KEY_ROOM_ID = "id";
    private static String KEY_ROOM_NAME = "name";

    private static String TABLE_DEVICE_NAME = "devices";
    private static String KEY_DEVICE_ID = "id";
    private static String KEY_DEVICE_NAME = "name";
    private static String KEY_STATUS_DEVICE = "status";
    private static String KEY_VALUE_DEVICE = "value";
    private static String KEY_TYPE_DEVICE = "type";
    private static String KEY_ROOM_ID_IN_DEVICES = "id_room";

    String create_rooms_table;
    String create_devices_table;
    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //    Nơi viết câu lệnh tạo bảng
//    Được gi khi database đã được tạo
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        create_rooms_table = "CREATE TABLE " + TABLE_ROOM_NAME + " ("
                + KEY_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ROOM_NAME + " TEXT )";

        create_devices_table = "CREATE TABLE " + TABLE_DEVICE_NAME + " ("
                + KEY_DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_DEVICE_NAME + " TEXT, "
                + KEY_STATUS_DEVICE + " INTEGER (0,1), "
                + KEY_TYPE_DEVICE + " INTEGER, "
                + KEY_VALUE_DEVICE + " INTEGER, "
                + KEY_ROOM_ID_IN_DEVICES + " INTEGER)";

//                + "FOREIGN KEY (" + KEY_ROOM_ID + ") REFERENCES " + TABLE_DEVICE_NAME + "(" + KEY_DEVICE_ID + ")

        sqLiteDatabase.execSQL(create_rooms_table);
        sqLiteDatabase.execSQL(create_devices_table);
    }

    //   Được gọi khi database được nâng cấp
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(sqLiteDatabase);
    }


//    -------------------------------------------------------- //
//    ----------------- ROOM METHODS--------------------------//
//    --------------------------------------------------------//

    //    Phương thức thêm 1 dòng mới
    public void addRoom(RoomItem roomItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ROOM_NAME, roomItem.getRoomName());

        db.insert(TABLE_ROOM_NAME, null, values);
        db.close();
    }

    //    Phương thức lấy 1 dòng theo ID
    public RoomItem getRoom(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();

//        Truy vấn và lưu vào cursor
        Cursor cursor = db.query(TABLE_ROOM_NAME, null, KEY_ROOM_ID + " = ?", new String[] {
                String.valueOf(roomId)
        }, null, null, null);

//        db.query trả về một Cursor, lúc này cursor chưa trỏ tới dòng nào cả nên dùng
//        moveToFirst để trỏ tới dòng đầu tiên
        if(cursor != null) {
            cursor.moveToFirst();
        }

//        Lấy dữ liệu từ cursor
        RoomItem roomItem = new RoomItem(cursor.getInt(0), 0, cursor.getString(1));

        return roomItem;
    }

    //    Phương thức lấy toàn bộ bảng
    public List<RoomItem> getAllRoom() {
        List<RoomItem> roomList = new ArrayList<>();

//        Câu lệnh truy vấn
        String query = "SELECT * FROM " + TABLE_ROOM_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

//        Di chuyển cursor cho tới dòng cuối cùng
        while (cursor.isAfterLast() == false) {
            RoomItem roomItem = new RoomItem(cursor.getInt(0), 0, cursor.getString(1));

            roomList.add(roomItem);
            // Di chuyển con trỏ tới dòng tiếp theo
            cursor.moveToNext();
        }
        return roomList;
    }

    public int getIdRoomByName(String nameRoom) {
        SQLiteDatabase db = this.getReadableDatabase();
        int roomId = -1; // Giả sử không tìm thấy phòng

        // Truy vấn và lưu vào cursor
        Cursor cursor = db.query(TABLE_ROOM_NAME, new String[]{KEY_ROOM_ID}, KEY_ROOM_NAME + " = ?",
                new String[]{nameRoom}, null, null, null);

        // Kiểm tra xem có dữ liệu không
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy ID từ cursor
            roomId = cursor.getInt(0);
        }

        // Đóng cursor để tránh rò rỉ bộ nhớ
        if (cursor != null) {
            cursor.close();
        }

        return roomId;
    }

    //    Phương thức cập nhật dữ liệu 1 dòng
//    public void updateStaff(Staff staff) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, staff.getName());
//        values.put(KEY_GENDER, staff.getGender());
//        values.put(KEY_PHONE_NUMBER, staff.getPhoneNumber());
//        values.put(KEY_NAME_LOGIN, staff.getNameLogin());
//        values.put(KEY_PASSWORD, staff.getPassword());
//
//        db.update(TABLE_NAME, values, KEY_ID + " = ?",
//                new String[] {
//                        String.valueOf(staff.getId())
//                });
//
//        db.close();
//    }

    //    Phương thức xóa 1 dòng
    public void deleteRoom(int roomId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa phòng
        db.delete(TABLE_ROOM_NAME,KEY_ROOM_ID + " = ?",
                new String[] {
                        String.valueOf(roomId)
                });
        // Xóa thiết bị của phòng đó
        db.delete(TABLE_DEVICE_NAME,KEY_ROOM_ID_IN_DEVICES + " = ?",
                new String[] {
                        String.valueOf(roomId)
                });
    }

    //Xóa toàn bộ
    public void deleteAllRoom() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ROOM_NAME, null, null);
        db.close();
    }

//    -------------------------------------------------------- //
//    ----------------- DEVICE METHODS------------------------//
//    --------------------------------------------------------//

    public void addDevice(DeviceItem deviceItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ROOM_NAME, deviceItem.getNameDevice());
        values.put(KEY_STATUS_DEVICE, deviceItem.isStatusDevice());
        values.put(KEY_VALUE_DEVICE, deviceItem.getValueDevice());
        values.put(KEY_ROOM_ID_IN_DEVICES, deviceItem.getRoomId());
        values.put(KEY_TYPE_DEVICE, deviceItem.getTypeDevice());


        db.insert(TABLE_DEVICE_NAME, null, values);
        db.close();
    }

    public List<DeviceItem> getAllDeviceByRoom(int roomId) {
        List<DeviceItem> deviceList = new ArrayList<>();

//        Câu lệnh truy vấn
        String query = "SELECT * FROM " + TABLE_DEVICE_NAME + " WHERE " + KEY_ROOM_ID_IN_DEVICES + " = " + roomId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

//        Di chuyển cursor cho tới dòng cuối cùng
        while (cursor.isAfterLast() == false) {
            DeviceItem deviceItem = new DeviceItem(cursor.getInt(0), cursor.getString(1), cursor.getInt(3),
                    cursor.getInt(4), (((cursor.getInt(2)) != 0) ? true : false) , 0, roomId);

            deviceList.add(deviceItem);
            // Di chuyển con trỏ tới dòng tiếp theo
            cursor.moveToNext();
        }
        return deviceList;
    }

    public void updateDeviceStatus(int deviceId, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUS_DEVICE, status ? 1 : 0); // Chuyển đổi giá trị boolean sang integer (0 hoặc 1)

        // Cập nhật dữ liệu
        db.update(TABLE_DEVICE_NAME, values, KEY_DEVICE_ID + " = ?",
                new String[]{String.valueOf(deviceId)});

        db.close();
    }
    public void deleteDevice(int deviceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVICE_NAME,KEY_DEVICE_ID + " = ?",
                new String[] {
                        String.valueOf(deviceId)
                });
    }

    //Xóa toàn bộ thiết bị
    public void deleteAllDevice() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVICE_NAME, null, null);
        db.close();
    }
}
