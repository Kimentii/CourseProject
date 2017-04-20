package com.example.courseproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;


public class MainActivity extends Activity {

    private Game game;
    private Button[][] buttons = new Button[3][3];                      //Поле из кнопок 3 на 3
    private TableLayout layout;                                         //layout в виде таблицы
   // Server server;
    Client client;
    Semaphore semaphore = new Semaphore(1);

    public MainActivity() {
        game = new Game();
        game.start();
    }

    /**
     * Создаем собственного слушателя
     */
    public class Listener implements View.OnClickListener {
        /**
         * Координаты слушателя
         */
        private int x = 0;
        private int y = 0;

        /**
         * Конструктор нашего слушателя с задание координат
         */
        public Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Реакция слушателя на нажатие кнопки
         */
        public void onClick(View view) {
            if(client!=null)
            {
                Button button = (Button) view;
                if(button.getText() == null)
                {
                    button.setText("X");
                    client.write(Integer.toString(x),Integer.toString(y));
                }
                WaitOtherPlayer w = new WaitOtherPlayer(buttons, semaphore);
                w.execute();

            }
        }
    }

    public class ClientListener implements View.OnClickListener {

        public ClientListener() {
        }
        /**
         * Реакция слушателя на нажатие кнопки
         */
        public void onClick(View view) {
            client = new Client(6666, semaphore, game, buttons);
            client.start();
        }
    }

    private void buildGameField() {
        Square[][] field = game.getField();
        for (int i = 0, lenI = field.length; i < lenI; i++) {
            TableRow row = new TableRow(this);                          //Cоздание строки таблицы
            for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
                Button button = new Button(this);
                buttons[i][j] = button;                                         //Добавляем кнопку в поле
                button.setOnClickListener(new Listener(i, j));                  //Установка слушателя, реагирующего на клик по кнопке
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));                   //Добавление кнопки в строку таблицы
                button.setWidth(107);
                button.setHeight(107);
            }
            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));                    //Добавление строки в таблицу
        }
    }

    private void buildServerClient() {
        TableRow row = new TableRow(this);

        Button clientButton = new Button(this);
        clientButton.setText("connect to server");
        clientButton.setOnClickListener(new ClientListener());
        row.addView(clientButton, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        clientButton.setWidth(100);
        clientButton.setHeight(100);

        layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    private void gameOver(Player player) {
        CharSequence text = "Player \"" + player.getName() + "\" won!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        game.reset();
        refresh();
    }

    private void gameOver() {
        CharSequence text = "Draw";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        game.reset();
        refresh();
    }
    /**
     * Обновление поле
     */
    private void refresh() {
        Square[][] field = game.getField();

        for (int i = 0, len = field.length; i < len; i++) {
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                if (field[i][j].getPlayer() == null) {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(field[i][j].getPlayer().getName());
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);                          //Установка layout
        game.start();
        layout = (TableLayout) findViewById(R.id.main_l);       //Поиск layout по id
        buildGameField();
        buildServerClient();
    }
}
