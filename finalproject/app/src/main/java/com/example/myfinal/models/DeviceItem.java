package com.example.myfinal.models;

public class DeviceItem {
    int deviceId;
    private String nameDevice;
    private int valueDevice;
    private boolean statusDevice;
    private int resourceImgDeviceId;
    private int typeDevice;
    private int roomId;


    public DeviceItem(){}
    public DeviceItem(int deviceId, String nameDevice, int typeDevice, int valueDevice,
                      boolean statusDevice, int resourceImgDeviceId, int roomId) {
        this.nameDevice = nameDevice;
        this.valueDevice = valueDevice;
        this.statusDevice = statusDevice;
        this.deviceId = deviceId;
        this.typeDevice = typeDevice;
        this.resourceImgDeviceId = resourceImgDeviceId;
        this.roomId = roomId;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public int getValueDevice() {
        return valueDevice;
    }

    public boolean isStatusDevice() {
        return statusDevice;
    }
    public int getKeyDevice() {
        return deviceId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public int getResourceImgDeviceId() {
        return resourceImgDeviceId;
    }

    public int getTypeDevice() {
        return typeDevice;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public void setValueDevice(int valueDevice) {
        this.valueDevice = valueDevice;
    }

    public void setStatusDevice(boolean statusDevice) {
        this.statusDevice = statusDevice;
    }

    public void setTypeDevice(int typeDevice) {
        this.typeDevice = typeDevice;
    }

    public void setResourceImgDeviceId(int resourceImgDeviceId) {
        this.resourceImgDeviceId = resourceImgDeviceId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
