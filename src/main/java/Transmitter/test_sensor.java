package Transmitter;


import qolsys.powerGRadioController.PowergDeviceHandler;

import java.io.IOException;


public class test_sensor {

    public Runtime rt = Runtime.getRuntime();
    PowergDeviceHandler powergDevice = PowergDeviceHandler.getPowergDeviceHandler();

    public test_sensor() throws Exception {
    }


    @org.testng.annotations.Test
    public void test() throws InterruptedException, IOException {
        String Id = "1011234";

//        int number_of_radios = powergDevice.get_number_of_devices();
//        System.out.println("Number of Radios: " + number_of_radios);
//
//        int ret = powergDevice.set_sensor_info(0, Id, (byte)0x0A);
//        System.out.println("Set Sensor Info to 1011234 Return Val: " + ret);


        int index = powergDevice.get_device_index_using_id(Id);
        System.out.println("Index for 1011234 Return Val: " + index);
        Thread.sleep(1000);

//        powergDevice.send_registration(index);
//        System.out.println(powergDevice.send_registration(index));

        byte[]openMess = {17,1,1};

        int ret = powergDevice.send_report(index, openMess);
        System.out.println("Send Report: " + ret);

        if(ret !=0){
            System.out.println("*** Command failed! ***");
        }
        powergDevice.stop_all_threads();
    }



}
