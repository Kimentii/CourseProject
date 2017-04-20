package com.example.courseproject;

import android.os.AsyncTask;
import android.widget.Button;

import java.util.concurrent.Semaphore;

/**
 * Created by Семен on 4/20/2017.
 */

public class WaitOtherPlayer extends AsyncTask<Void, Void, Void> {

    Semaphore semaphore;
    Button[][] buttons;
    int x,y;
    WaitOtherPlayer(Button[][] buttons, Semaphore s)
    {
        semaphore = s;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            semaphore.acquire();
            x = Client.getX();
            y = Client.getY();
            semaphore.release();
        }
        catch(Exception e)
        {
            System.out.println("Error with semaphore");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        buttons[x][y].setText("X");
    }
}
