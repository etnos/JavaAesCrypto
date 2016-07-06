package com.example.javaaescryptotest.javaaescryptotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView txtSmall;
    private TextView txtBig;
    private TextView txtHuge;
    AesCbcWithIntegrity.SecretKeys keys = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSmall = (TextView) findViewById(R.id.txtSmall);
        txtBig = (TextView) findViewById(R.id.txtBig);
        txtHuge = (TextView) findViewById(R.id.txtHuge);

        try {
            keys = AesCbcWithIntegrity.generateKey();
        } catch (GeneralSecurityException e) {
            Log.e(TAG, "no encryption keys", e);
        }
    }


    public void onSmallEncrypt(View view) {
        Log.i(TAG, "onSmallEncrypt");
        if (keys == null) {
            Toast.makeText(this, "No Crypto generated", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long l = System.currentTimeMillis();
                    AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = AesCbcWithIntegrity.encrypt("some test", keys);
                    //store or send to server
                    String ciphertextString = cipherTextIvMac.toString();
                    Log.i(TAG, "ciphertext " + ciphertextString);

                    cipherTextIvMac = new AesCbcWithIntegrity.CipherTextIvMac(ciphertextString);
                    String plainText = AesCbcWithIntegrity.decryptString(cipherTextIvMac, keys);

                    final long l1 = System.currentTimeMillis() - l;
                    Log.i(TAG, l1 + " plaintext " + plainText);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtSmall.setText("Duration (ms) " + l1);
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "no encryption", e);
                }
            }
        }).start();


    }


    /**
     * encript/decrypt 12 Mb
     *
     * @param view
     */
    public void onBigEncrypt(View view) {
        Log.i(TAG, "onBigEncrypt");
        if (keys == null) {
            Toast.makeText(this, "No Crypto generated", Toast.LENGTH_LONG).show();
            return;
        }
        int size = 10 * 1024 * 1024;

        final byte[] data = new byte[size];

        for (int i = 0; i < size; i++) {
            data[i] = (byte) i;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long l = System.currentTimeMillis();
                    AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = AesCbcWithIntegrity.encrypt(data, keys);
                    //store or send to server
                    String ciphertextString = cipherTextIvMac.toString();

                    cipherTextIvMac = new AesCbcWithIntegrity.CipherTextIvMac(ciphertextString);
                    AesCbcWithIntegrity.decryptString(cipherTextIvMac, keys);

                    final long l1 = System.currentTimeMillis() - l;

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtBig.setText("Duration (ms) " + l1);
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "no encryption", e);
                }
            }
        }).start();

    }

    /**
     * encript/decrypt 50 Mb
     *
     * @param view
     */
    public void onHugeEncrypt(View view) {
        Log.i(TAG, "onHugeEncrypt");

        if (keys == null) {
            Toast.makeText(this, "No Crypto generated", Toast.LENGTH_LONG).show();
            return;
        }

        int size = 50 * 1024 * 1024;

        final byte[] data = new byte[size];

        for (int i = 0; i < size; i++) {
            data[i] = (byte) i;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long l = System.currentTimeMillis();
                    AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = AesCbcWithIntegrity.encrypt(data, keys);
                    //store or send to server
                    String ciphertextString = cipherTextIvMac.toString();

                    cipherTextIvMac = new AesCbcWithIntegrity.CipherTextIvMac(ciphertextString);
                    AesCbcWithIntegrity.decryptString(cipherTextIvMac, keys);

                    final long l1 = System.currentTimeMillis() - l;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtHuge.setText("Duration (ms) " + l1);
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "no encryption", e);
                }
            }
        }).start();
    }
}
