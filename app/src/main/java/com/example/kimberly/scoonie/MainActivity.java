package com.example.kimberly.scoonie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // select each image by id - 7 total
        ImageView img1 = (ImageView) findViewById(R.id.image_melon);
        ImageView img2 = (ImageView) findViewById(R.id.image_mhacks);
        ImageView img3 = (ImageView) findViewById(R.id.image_cherry);
        ImageView img4 = (ImageView) findViewById(R.id.image_blue_rasp);
        
        // to do
        // img5 - cherrypi
        // img6 - chelon
        // img7 - rasleberry,

        // set the listener for the first image - watermelon
        img1.setOnClickListener(new View.OnClickListener() {
            // add onClick action
            public void onClick(View v) {
                // try send remote command to pi
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            // send command to the pi
                            executeRemoteCommand("pi", "raspberry", "0.0.0.0", 22, "watermelon");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        // set the listener for the second image - mhacks special
        img1.setOnClickListener(new View.OnClickListener() {
            // add onClick action
            public void onClick(View v) {
                // try send remote command to pi
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            // send command to the pi
                            executeRemoteCommand("pi", "raspberry", "0.0.0.0", 22, "mhacks");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        // set the listener for the third image - cherry
        img1.setOnClickListener(new View.OnClickListener() {
            // add onClick action
            public void onClick(View v) {
                // try send remote command to pi
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            // send command to the pi
                            executeRemoteCommand("pi", "raspberry", "0.0.0.0", 22, "cherry");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        // set the listener for the fourth image - raspberry
        img1.setOnClickListener(new View.OnClickListener() {
            // add onClick action
            public void onClick(View v) {
                // try send remote command to pi
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            // send command to the pi
                            executeRemoteCommand("pi", "raspberry", "0.0.0.0", 22, "raspberry");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });

        //  set the listener for the fifth image - cherrypi
        // set the listener for the sixth - chelon
        // set the listener for the seventh - rasleberry
    }

    public static String executeRemoteCommand(String username, String password, String hostname, int port, String type)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        channelssh.setCommand("sudo python /root/stepper.py " + type);
        channelssh.connect();
        channelssh.disconnect();

        return baos.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}