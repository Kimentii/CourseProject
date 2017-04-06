package com.example.courseproject;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


public class MainActivity extends Activity {
    private Game game;
    private Button[][] buttons = new Button[3][3];                      //Поле из кнопок 3 на 3
    private TableLayout layout;                                         //layout в виде таблицы

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
            Button button = (Button) view;
            Game g = game;
            Player player = g.getCurrentActivePlayer();
            if (game.makeTurn(x, y)) {
                button.setText(player.getName());
            }
            Player winner = g.checkWinner();
            if (winner != null) {
                gameOver(winner);
            }
            if (g.isFieldFilled()) {  // в случае, если поле заполнено
                gameOver();
            }
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
    }
}
