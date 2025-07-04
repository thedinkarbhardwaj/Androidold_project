package com.example.p2p.Sender_Receiver.Sender;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.p2p.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SenderAct extends AppCompatActivity {

    ServerSocket serverSocket;
    Thread Thread1 = null;
    TextView tvIP, tvPort, tvMessages;
    EditText etMessage;
    Button btnSendText, btnSendImage;
    public static String SERVER_IP = "";
    public static final int SERVER_PORT = 8080;
    String message;

    int PICK_IMAGE_REQUEST = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        tvIP = findViewById(R.id.tvIP);
        tvPort = findViewById(R.id.tvPort);
        tvMessages = findViewById(R.id.tvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSendText = findViewById(R.id.btnSend);
        btnSendImage = findViewById(R.id.btnSendImage);

        try {
            SERVER_IP = getLocalIpAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Thread1 = new Thread(new Thread1());
        Thread1.start();

        btnSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "text:" + etMessage.getText().toString().trim();
                if (!message.isEmpty()) {
                  //  new Thread(new Thread3(message)).start();
                }
            }
        });

        btnSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the gallery to select an image
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()).getHostAddress();
    }

    private PrintWriter output;
    private BufferedReader input;

    private class Thread1 implements Runnable {
        @Override
        public void run() {
            Socket socket;
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMessages.setText("Not connected");
                        tvIP.setText("IP: " + SERVER_IP);
                        tvPort.setText("Port: " + String.valueOf(SERVER_PORT));
                    }
                });
                try {
                    socket = serverSocket.accept();
                    output = new PrintWriter(socket.getOutputStream());
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMessages.setText("Connected ");
                        }
                    });
                    new Thread(new Thread2()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Print the stack trace for debugging
                System.out.println("Exception in Thread1: " + e.getMessage());
            }
        }
    }


    private class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.append("client:" + message + " ");
                            }
                        });
                    } else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private class Thread3 implements Runnable {
//        private Bitmap imageBitmap;
//
//        Thread3(Bitmap imageBitmap) {
//            this.imageBitmap = imageBitmap;
//        }
//
//        @Override
//        public void run() {
//            if (imageBitmap != null) {
//                // Convert the bitmap to a byte array
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] byteArray = baos.toByteArray();
//
//                // Send the image byte array using Base64 encoding
//                String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                output.println("image:" + encodedImage);
//                output.flush();
//
//                displayImage(encodedImage);
//
//
//                runOnUiThread(() -> {
//                    tvMessages.append("server: Image sent ");
//                    // Clear the input field or handle as needed
//                    etMessage.setText("");
//                });
//            }
//        }
//    }
//



    private class Thread3 implements Runnable {
        private Bitmap bitmap;

        Thread3(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            // Send the bitmap as an image
            sendImage(bitmap);
        }

        private void sendImage(Bitmap bitmap) {
            try {
                // Convert the bitmap to a byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] byteArray = baos.toByteArray();

                // Send the entire image data using Base64 encoding
                String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("encodedddd", encodedImage);

                // Send the image data with a newline character at the end
                output.print(encodedImage);
               // output.print("image:" + encodedImage + "\n");
                output.flush();

                displayImage(encodedImage);

                runOnUiThread(() -> {
                    tvMessages.append("server: Image sent " + encodedImage);
                    etMessage.setText("");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private void displayImage(String imageString) {
        try {

           // String imageString = "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";
            // Convert Base64 encoded string to byte array
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Log.d("ImagesOfBitmap",imageString);
            // Convert byte array to Bitmap
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            // Set Bitmap to ImageView or handle the image as needed
            ImageView img = findViewById(R.id.senderImageView);
            img.setImageBitmap(decodedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image path from the selected image in the gallery
            Uri selectedImageUri = data.getData();

            try {
                // Load the selected image into a Bitmap
                Bitmap selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                // Create the message and send it
                new Thread(new Thread3(selectedBitmap)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Method to get the real path from the URI
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

}
