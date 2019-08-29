package Transmitter;


import org.testng.annotations.Test;
import qolsys.powerGRadioController.PowergDeviceHandler;

import java.util.Arrays;
import java.util.List;

public class DeviceMessages {

    PowergDeviceHandler powergDevice = PowergDeviceHandler.getPowergDeviceHandler();

    List<String> door_window = Arrays.asList("1003434", "1011234", "1042222", "1072121", "1085445", "1093456", "1105432");
    List<String> motion = Arrays.asList("1201111", "1222323", "1234567", "1267676", "1275511", "1282345", "1298888", "1304343", "1408998", "1427654", "1501212");

    public void setDevice(String Id){
        int ret = powergDevice.set_sensor_info(0, Id, (byte)0x0A);
        System.out.println("Set Sensor Info to " + Id + " Return Val: " + ret);
    }


    public int getNetworkState(int index){
      int ret = powergDevice.get_network_state(index);
      if(ret == 0){
          System.out.println("0: Not Connected to a PowerG Network");
      } else if(ret == 1){
          System.out.println("1: Syncing with PowerG Network");
      } else if (ret == 2){
          System.out.println("Joining PowerG Network");
      } else if (ret == 3){
          System.out.println("3: Fully Optimized PowerG Network Connection");
      } else if (ret == 4){
          System.out.println("4:Failure to Check Device Connection");
      } else if (ret == 200) {
          System.out.println("200: Radio Communication Failure");
      } else if (ret == 300){
          System.out.println("300: Device Index Does No Exist");
      }
        return  ret;
    }

    public byte event(String state) {
        byte value =0;
        if (state == "open" | state == "activated") {
            value = 1;
        } else if (state == "close" | state == "idle") {
            value = 0;
        } else if (state == "auxiliary open") {
            value = 111;
        } else if (state == "auxiliary close") {
            value = 110;
        }
        return value;
    }

    public byte tamperEvent(String state) {
        byte value;
        if (state == "tamper") {
            value = 1;
        } else {
            value = 0;
        }
        return value;
    }

    public void dw(int index, String state) {
        byte[] Mess = {17, 1, event(state)};
        powergDevice.send_report(index, Mess);
    }
    public void motion(int index, String state) {
        byte[] Mess = {1, 1, event(state)};
        powergDevice.send_report(index, Mess);
    }

    public void tamper(int index, String state) {
        byte[] Mess = {39, 1, tamperEvent(state)};
        powergDevice.send_report(index, Mess);
    }

    @Test
    public void test_dw() throws InterruptedException {
        dw(0, "open");
        Thread.sleep(1000);
        dw(0, "close");
        Thread.sleep(1000);
        tamper(0, "tamper");
        Thread.sleep(1000);
        tamper(0, "restore");
    }

    @Test
    public void dwTest() throws InterruptedException {

        for (int i = 0; i < door_window.size(); i++) {
            setDevice(door_window.get(i));
            Thread.sleep(5000);

            int index = powergDevice.get_device_index_using_id(door_window.get(i));
            System.out.println("Index for " + door_window.get(i).toString() +"  Return Val: " + index);
            Thread.sleep(2000);

            powergDevice.send_registration(index);
            Thread.sleep(5000);

            while (getNetworkState(index) != 3) {
                powergDevice.send_registration(index);
                System.out.println("\n***Current state: " + powergDevice.send_registration(index));
                Thread.sleep(60000);
            }

            dw(index, "open");
            Thread.sleep(1000);
            dw(index, "close");
            Thread.sleep(1000);
            tamper(index, "tamper");
            Thread.sleep(1000);
            tamper(index, "restore");
            Thread.sleep(1000);

            powergDevice.stop_all_threads();
            Thread.sleep(5000);
        }
    }

    @Test
    public void motionTest() throws InterruptedException {
        for (int i = 0; i < motion.size(); i++) {
            setDevice(motion.get(i));
            Thread.sleep(5000);

            int index = powergDevice.get_device_index_using_id(motion.get(i));
            System.out.println("\n*** Index for " + motion.get(i).toString() + "  Return Val: " + index);
            Thread.sleep(2000);

            powergDevice.send_registration(index);
            Thread.sleep(5000);

            while (getNetworkState(index) != 3) {
                powergDevice.send_registration(index);
                System.out.println("\n*** Current state: " + powergDevice.send_registration(index));
                Thread.sleep(60000);
            }

            motion(index, "activated");
            Thread.sleep(1000);
            motion(index, "idle");
            Thread.sleep(1000);
            tamper(index, "tamper");
            Thread.sleep(1000);
            tamper(index, "restore");
            Thread.sleep(1000);

            powergDevice.stop_all_threads();
            Thread.sleep(5000);
        }
    }


}
