package it.unisa.razzolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unisa.razzolo.model.Point;
import it.unisa.razzolo.model.Trie;
import it.unisa.razzolo.model.Word;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        final var trie = Trie.getInstance();    // Build Trie

        foundWordSize_text = findViewById(R.id.foundWordSize_text);
        elapsedTime_text = findViewById(R.id.elapsedTime_text);

        final GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i=0; i<16; i++){
            final MyEditText e = new MyEditText(this);
            boxes[i] = e;
            gridLayout.addView(e);
        }

        final ListView listView = findViewById(R.id.listView);
        wordAdapter = new WordAdapter(this, R.layout.activity_listview, R.id.listView);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            final Word w = wordAdapter.getItem(position);
            assert w != null;
            onWordClicked(w.coordinates());
        });

    }

    @SuppressLint("DefaultLocale")
    public void onClickRunBtn(View view) {
        int i=0,j=0;
        for(EditText e : boxes){
            char c;
            if(e.getText().length() == 1){
                c = e.getText().charAt(0);
                if(Character.isLetter(c)){
                    matrix[i][j] = c;
                    if((j%4) == 3){
                        i++;
                        j=0;
                    }
                    else j++;
                }
                else{
                    Toast.makeText(this, "Compila correttamente tutte le caselle!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else{
                Toast.makeText(this, "Compila correttamente tutte le caselle!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            final var background = new Background(matrix);
            background.run();
            handler.post(() -> {
                _reset();
                background.getFoundWords().forEach((k,v) -> {
                    wordAdapter.add(new Word(k, k.length(), v));
                });
                foundWordSize_text.setText(String.valueOf(background.getFoundWords().size()));
                elapsedTime_text.setText(String.format("%g s", background.getElapsedTime()));
            });
        });
    }

    public void onWordClicked(final ArrayList<Point> coordinates) {
        _cleanHighlightOnGrid();
        for(final Point p : coordinates)
            boxes[p.getI()*4 + p.getJ()].setBackground(getDrawable(R.drawable.box_corners_orange));
    }

    private void _cleanHighlightOnGrid(){
        for(final EditText e : boxes)
            e.setBackground(getDrawable(R.drawable.box_corners_white));
    }

    public void onClickResetBtn(View view) {
        _reset();
        for(final EditText e : boxes)
            e.setText("");
    }

    public void onClickRandomBtn(View view) {
        _reset();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for(EditText e : boxes)
            e.setText(String.valueOf(alphabet[(int)(Math.random()*26)]));
    }

    private void _reset(){
        wordAdapter.clear();
        foundWordSize_text.setText("");
        elapsedTime_text.setText("");
        _cleanHighlightOnGrid();
    }

    private final char[][] matrix = new char[4][4];
    private final EditText[] boxes = new EditText[16];
    private WordAdapter wordAdapter;
    private TextView foundWordSize_text;
    private TextView elapsedTime_text;

    private static Context context;

    public static Context getContext() {
        return context;
    }
}