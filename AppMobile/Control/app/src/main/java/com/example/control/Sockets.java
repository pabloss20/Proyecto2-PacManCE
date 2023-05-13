package com.example.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sockets extends AppCompatActivity {

    // Atributtes
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String server_ip;
    private int server_port;

    // Components
    Button send_button;
    EditText entry_IP;

    // Go to activity_control
    Intent next_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sockets);

        String saludo = "Hola, estoy controlando el jugador desde mi Android";

        send_button = findViewById(R.id.send_button);
        entry_IP = findViewById(R.id.entryIP);
        next_activity = new Intent(getApplicationContext(), Control.class);
        send_button.setOnClickListener(view -> {

            server_ip = entry_IP.getText().toString();
            if (isValidIPv4(server_ip)){
                sendInfo(saludo);
                startActivity(next_activity);
            } else {
                noValidIP();
            }
        });
    }

    private void noValidIP(){
        System.out.println("No valid IP");
    }

    private static boolean isValidIPv4(String ip){
        Pattern pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public void sendInfo(String message){
        new Thread(() -> {
            String a = "";
            try {

                socket = new Socket(server_ip, server_port);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);

                System.out.println(message);
                output.printf(message);
                Log.d("ENVIADO", message);

                String response = input.readLine();
                Log.d("RESPUESTA", response);

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getNetworkIPs() {
        byte[] ip = new byte[4];
        final StringLinkedList list = new StringLinkedList();
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        ip = ipv4ToBytes(addr.getHostAddress());
                        Log.d("HOST IP ADDRESS", addr.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;     // exit method, otherwise "ip might not have been initialized"
        }
        final byte[] ipFinal = ip;

        try {
            String ipID = InetAddress.getByAddress(ipFinal).toString().substring(1);
            ipID = ipID.substring(0, ipID.lastIndexOf(".")+1) + 'X';
            entry_IP.setText(ipID);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] ipv4ToBytes(String ipv4) throws IllegalArgumentException {
        String[] octets = ipv4.split("\\.");
        if (octets.length != 4) {
            throw new IllegalArgumentException("Invalid IPv4 address format");
        }
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            int octet = Integer.parseInt(octets[i]);
            if (octet < 0 || octet > 255) {
                throw new IllegalArgumentException("Invalid octet value: " + octet);
            }
            bytes[i] = (byte) octet;
        }
        return bytes;
    }
}