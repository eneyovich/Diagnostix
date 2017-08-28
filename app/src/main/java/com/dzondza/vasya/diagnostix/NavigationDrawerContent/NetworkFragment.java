package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;


/**
 * contains network information
 */

public class NetworkFragment extends BaseDetailedFragment {

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        // activates recyclerView
        initializeRecyclerView(view);


        TelephonyManager telephonyManager = (TelephonyManager)
                getActivity().getSystemService(Context.TELEPHONY_SERVICE);


        //returns constant that represents the current state of all phone calls
        String callStateDescript = getString(R.string.network_device_call_state);
        String callState;
        switch (telephonyManager.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING :
                callState = getString(R.string.network_new_call_arrived);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK :
                callState = getString(R.string.network_call_dialing_active_hold);
                break;
            case TelephonyManager.CALL_STATE_IDLE :
                callState = getString(R.string.network_no_activity);
                break;
            default:
                callState = getString(R.string.unknown);
        }
        recyclerViewLine.add(new RecyclerItemsData(callStateDescript, callState));


        //type of activity on a data connection (cellular)
        String connectActivityDescript = getString(R.string.network_data_connection_activity);
        String connectActivity;
        switch (telephonyManager.getDataActivity()) {
            case TelephonyManager.DATA_ACTIVITY_NONE:
                connectActivity = getString(R.string.network_no_traffic);
                break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                connectActivity = getString(R.string.network_active_physical_link_down);
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                connectActivity = getString(R.string.network_receiving_ip_ppp);
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                connectActivity = getString(R.string.network_sending_receiving_ip_ppp);
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                connectActivity = getString(R.string.network_sending_ip_ppp);
                break;
            default:
                connectActivity = getString(R.string.unknown);
        }
        recyclerViewLine.add(new RecyclerItemsData(connectActivityDescript, connectActivity));


        //data connection state (cellular).
        String connectStateDescript = getString(R.string.network_connection_state);
        String connectState;
        switch (telephonyManager.getDataState()) {
            case TelephonyManager.DATA_DISCONNECTED:
                connectState = getString(R.string.network_disconnected_traffic_not_available);
                break;
            case TelephonyManager.DATA_CONNECTING:
                connectState = getString(R.string.network_setting_up_data_connection);
                break;
            case TelephonyManager.DATA_CONNECTED:
                connectState = getString(R.string.network_connected_traffic_should_be_available);
                break;
            case TelephonyManager.DATA_SUSPENDED:
                connectState = getString(R.string.network_suspended_connection_up_traffic_temporarily_unavailable);
                break;
            default:
                connectState = getString(R.string.unknown);
        }
        recyclerViewLine.add(new RecyclerItemsData(connectStateDescript, connectState));


        //ISO country code
        String isoCodeDescript = getString(R.string.network_iso_country_code);
        String isoCode = telephonyManager.getNetworkCountryIso();
        view.setTag(isoCode);
        recyclerViewLine.add(new RecyclerItemsData(isoCodeDescript, isoCode));


        //alphabetic name of current registered operator.
        String operatorName = getString(R.string.network_operator_name);
        recyclerViewLine.add(new RecyclerItemsData(operatorName, telephonyManager.getNetworkOperatorName()));


        //numeric name (MCC+MNC) of current registered operator.
        String operatorCode = getString(R.string.network_operator_numeric_code);
        recyclerViewLine.add(new RecyclerItemsData(operatorCode, telephonyManager.getNetworkOperator()));


        //NETWORK_TYPE_xxxx for current data connection.
        String networkTypeDescript = getString(R.string.network_type);
        String networkTypeSolution;

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_IDEN:
            case TelephonyManager.NETWORK_TYPE_CDMA:
                networkTypeSolution = getString(R.string.network_2g);
                break;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPA:
                networkTypeSolution = getString(R.string.network_3g);
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                networkTypeSolution = getString(R.string.network_4g);
                break;
            default:
                networkTypeSolution = getString(R.string.unknown);
        }
        recyclerViewLine.add(new RecyclerItemsData(networkTypeDescript, networkTypeSolution));


        //Returns the number of available phones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String simSlotsDescript = getString(R.string.network_sim_slots);
            String simSlots = String.valueOf(telephonyManager.getPhoneCount());
            recyclerViewLine.add(new RecyclerItemsData(simSlotsDescript, simSlots));
        }


        //device's radio type
        String radioDescript = getString(R.string.network_type_radio);
        String radio;
        switch (telephonyManager.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_NONE:
                radio = getString(R.string.network_no_phone_radio);
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                radio = getString(R.string.network_gsm);
                break;
            case TelephonyManager.PHONE_TYPE_CDMA:
                radio = getString(R.string.network_cdma);
                break;
            case TelephonyManager.PHONE_TYPE_SIP:
                radio = getString(R.string.network_via_sip);
                break;
            default:
                radio = getString(R.string.unknown);
        }
        recyclerViewLine.add(new RecyclerItemsData(radioDescript, radio));


        //ISO country code equivalent for the SIM provider's country code.
        String providersCode = getString(R.string.network_sim_providers_country_code);
        recyclerViewLine.add(new RecyclerItemsData(providersCode, telephonyManager.getSimCountryIso()));


        //default SIM card's state
        String simStateDescript = getString(R.string.network_sim_state);
        String simState;
        switch (telephonyManager.getSimState()) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simState = getString(R.string.network_no_sim_card_available);
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = getString(R.string.network_locked_requires_sim_pin_unlock);
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = getString(R.string.network_locked_requires_sim_puk_unlock);
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState = getString(R.string.network_requires_network_pin_unlock);
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState = getString(R.string.network_sim_card_ready);
                break;
            case TelephonyManager.SIM_STATE_NOT_READY:
                simState = getString(R.string.network_sim_card_not_ready);
                break;
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                simState = getString(R.string.network_sim_card_error_permanently_disabled);
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                simState = getString(R.string.network_sim_card_error_present_faulty);
                break;
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                simState = getString(R.string.network_sim_card_restricted_present_not_usable);
                break;
            default:
                simState = getString(R.string.unknown);
        }
        recyclerViewLine.add(new RecyclerItemsData(simStateDescript, simState));


        //true if device is considered roaming on current network
        String roamingDescript = getString(R.string.network_roaming);
        String roaming;
        if (telephonyManager.isNetworkRoaming()) {
            roaming = getString(R.string.yes);
        } else {
            roaming = getString(R.string.no);
        }
        recyclerViewLine.add(new RecyclerItemsData(roamingDescript, roaming));



        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        //wifi type of network
        String networkType = getString(R.string.wifi_network_type);
        recyclerViewLine.add(new RecyclerItemsData(networkType, networkInfo.getTypeName()));


        //wifi state information
        String networkStateInform = getString(R.string.wifi_network_state_information);
        recyclerViewLine.add(new RecyclerItemsData(networkStateInform, networkInfo.getExtraInfo()));


        //Indicates whether network connectivity is possible
        String connectivityDescript = getString(R.string.wifi_connectivity);
        String connectivity;
        if (networkInfo.isAvailable()) {
            connectivity = getString(R.string.available);
        } else {
            connectivity = getString(R.string.unavailable);
        }
        recyclerViewLine.add(new RecyclerItemsData(connectivityDescript, connectivity));


        //Indicates whether network connectivity exists
        String connectStatusDescript = getString(R.string.wifi_connection_status);
        String connectStatus;
        if (networkInfo.isConnected()) {
            connectStatus = getString(R.string.wifi_connected);
        } else {
            connectStatus = getString(R.string.wifi_not_connected);
        }
        recyclerViewLine.add(new RecyclerItemsData(connectStatusDescript, connectStatus));



        WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        String ssid = getString(R.string.wifi_SSID);
        recyclerViewLine.add(new RecyclerItemsData(ssid, wifiInfo.getSSID()));


        String bssid = getString(R.string.wifi_BSSID);
        recyclerViewLine.add(new RecyclerItemsData(bssid, wifiInfo.getBSSID()));


        String ipAddress  = getString(R.string.wifi_ip_address);
        recyclerViewLine.add(new RecyclerItemsData(ipAddress, String.valueOf(wifiInfo.getIpAddress())));


        String linkSpeed  = getString(R.string.wifi_link_speed);
        String linkSpeedSolution = String.valueOf(wifiInfo.getLinkSpeed()).concat(" Mbps");
        recyclerViewLine.add(new RecyclerItemsData(linkSpeed, linkSpeedSolution));


        String macAddress = getString(R.string.wifi_mac_address);
        recyclerViewLine.add(new RecyclerItemsData(macAddress, wifiInfo.getMacAddress()));


        String networkId = getString(R.string.wifi_network_id);
        recyclerViewLine.add(new RecyclerItemsData(networkId, String.valueOf(wifiInfo.getNetworkId())));


        String signalStrength = getString(R.string.wifi_signal_strength);
        String signalStrengthSolution = String.valueOf(wifiInfo.getRssi()).concat(" dBm");
        recyclerViewLine.add(new RecyclerItemsData(signalStrength, signalStrengthSolution));


        String dns1 = getString(R.string.wifi_dns1);
        String dns1Solution = String.valueOf(wifiManager.getDhcpInfo().dns1);
        recyclerViewLine.add(new RecyclerItemsData(dns1, dns1Solution));


        String dns2 = getString(R.string.wifi_dns2);
        String dns2Solution = String.valueOf(wifiManager.getDhcpInfo().dns2);
        recyclerViewLine.add(new RecyclerItemsData(dns2, dns2Solution));


        String netmaskDescript = getString(R.string.wifi_netmask);
        String netmask = String.valueOf(wifiManager.getDhcpInfo().netmask);
        recyclerViewLine.add(new RecyclerItemsData(netmaskDescript, netmask));


        String wifiStateDescript = getString(R.string.wifi_state);
        String wifiState;
        switch (wifiManager.getWifiState()) {
            case WifiManager.WIFI_STATE_DISABLED:
                wifiState = getString(R.string.wifi_disabled);
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                wifiState = getString(R.string.wifi_enabled);
                break;
            default:
                wifiState = getString(R.string.unknown);
        }
        recyclerViewLine.add(new RecyclerItemsData(wifiStateDescript, wifiState));



        getActivity().setTitle(R.string.drawer_network);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        try {
            if (position < 12) {
                startActivity(new Intent(Settings.ACTION_APN_SETTINGS));
            } else if (position >= 12 && position <= 13) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }else  {
                startActivity(new Intent(Settings.ACTION_WIFI_IP_SETTINGS));
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.option_unavailable), Toast.LENGTH_SHORT).show();
        }

        super.onItemClick(adapterView, view, position, l);
    }
}