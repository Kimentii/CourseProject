package com.example.courseproject;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class MainActivity extends Activity {
    private Game game;
    private Button[][] buttons = new Button[3][3];
    private TableLayout layout;

    public MainActivity() {
        game = new Game();
        game.start(); // будет реализован позже
    }

    public class Listener implements View.OnClickListener {
        private int x = 0;
        private int y = 0;

        public Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            Button button = (Button) view;
        }
    }
    private void buildGameField() {
        Square[][] field = game.getField();
        for (int i = 0, lenI = field.length; i < lenI; i++ ) {
            TableRow row = new TableRow(this); // создание строки таблицы
            for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
                Button button = new Button(this);
                buttons[i][j] = button;
                button.setOnClickListener(new Listener(i, j)); // установка слушателя, реагирующего на клик по кнопке
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)); // добавление кнопки в строку таблицы
                button.setWidth(107);
                button.setHeight(107);
            }
            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT)); // добавление строки в таблицу
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        game.start(); // будет реализован позже
        layout = (TableLayout) findViewById(R.id.main_l);
        buildGameField();
    }
}
