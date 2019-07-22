package APICalls;

import java.util.HashMap;
import java.util.Map;


public class APIEnvironment {

    public static final Map<String, String> getEnvironment() {
        Map<String, String> result = new HashMap<>();
        result.put("username", "dealer1");
        result.put("password", "qolsys123");
        result.put("account_number", "0012824798520045");
        result.put("imei", "001107000079030");
        result.put("domain_url", "https://dev-api.qolsys.com/v1");
        result.put("access_token", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGSjg2R2NGM2pUYk5MT2NvNE52WmtVQ0lVbWZZQ3FvcXRPUWVNZmJoTmxFIn0.eyJqdGkiOiI4ODViYTllNy0zMWRmLTQ0ZDktOWUzYi0wZDA0ZjViMmFjOWYiLCJleHAiOjE1Njc4MDg0NjYsIm5iZiI6MCwiaWF0IjoxNTYyNjI0NDY2LCJpc3MiOiJodHRwczovL2Rldi1hdXRoLnFvbHN5cy5jb20vYXV0aC9yZWFsbXMvcW9sc3lzLXNlY3VyaXR5IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjAyZTg1NWVmLTVmOTAtNDQ1Mi1iZTZhLWZkNTYwMjdjNjU5ZiIsInR5cCI6IkJlYXJlciIsImF6cCI6InJlc3QtYXBpLXNlcnZlciIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjA4NTM2OWJlLTc2ODMtNDEwNS1hMmFjLWUyZTkyMjY2OTlhYSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlc3QtYXBpLXNlcnZlciI6eyJyb2xlcyI6WyJkZWFsZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF9kZWFsZXJpZCI6ImRlYWxlcjEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJkZWFsZXIxIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.SoftmZ-sqLk7eUZO1sZfqTtX_qI5TyfhKlPxXHj0eL9IjBDd9r5wUBiyksPqhej3mfux-w_bnen6m4QAip1WhatXHDsKF_hKt8Te3PM-ILpisjcrxNWAqpJU2RxTpZzjJiG5gNzpn80n5bahZtfdwS58xLVdBDixf5lDip1mlbs");
        result.put("refresh_token", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkODJkYzQ2Ni0wNjVjLTRlZDItOTgyMS01MTQxMDEyNzk1YTUifQ.eyJqdGkiOiJjNmQwOWI1NC1hMWI5LTRjZDAtOWE1Yy0xMzZkYWQ2OTRlMWIiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTYyNjI0NDY2LCJpc3MiOiJodHRwczovL2Rldi1hdXRoLnFvbHN5cy5jb20vYXV0aC9yZWFsbXMvcW9sc3lzLXNlY3VyaXR5IiwiYXVkIjoiaHR0cHM6Ly9kZXYtYXV0aC5xb2xzeXMuY29tL2F1dGgvcmVhbG1zL3FvbHN5cy1zZWN1cml0eSIsInN1YiI6IjAyZTg1NWVmLTVmOTAtNDQ1Mi1iZTZhLWZkNTYwMjdjNjU5ZiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJyZXN0LWFwaS1zZXJ2ZXIiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiIwODUzNjliZS03NjgzLTQxMDUtYTJhYy1lMmU5MjI2Njk5YWEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlc3QtYXBpLXNlcnZlciI6eyJyb2xlcyI6WyJkZWFsZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwgb2ZmbGluZV9hY2Nlc3MifQ.kCxlkVp9DOep5XaHyQRhZ28Mc7itI1D8A8goaXBYn4k");
        result.put("partition_id", "0");
        result.put("zone_id", "1");
        result.put("device_id_lock", "2");
        result.put("user_id", "1");
        result.put("endpoint_id", "1");
        result.put("bluetooth_toggle", "");
        result.put("all_chimes", "");
        result.put("wifi_ap_enabled", "");
        result.put("wifi_ap_password", "");
        result.put("wifi_ap_ssid", "");
        result.put("wifi_name", "");
        result.put("created_user_id", "239");
        result.put("device_id_thermo", "3");
        result.put("srf_sensor_id", "69");
        result.put("time_zone", "147");

        return result;
    }
}
