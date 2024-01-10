package it.unisa.razzolo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import it.unisa.razzolo.model.Word;

public class WordAdapter extends ArrayAdapter<Word> {
    private final LayoutInflater inflater;

    public WordAdapter(Context context, int layout, int textViewResourceId) {
        super(context, layout, textViewResourceId);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if(view == null)
            view = inflater.inflate(R.layout.activity_listview, null);

        final Word w = getItem(position);
        TextView text = (TextView) view.findViewById(R.id.word);
        TextView score = (TextView) view.findViewById(R.id.score);

        assert w != null;
        text.setText(w.text());
        score.setText(String.valueOf(w.score()));

        text.setTag(position);
        score.setTag(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().onWordClicked(w.coordinates());
            }
        });

        return view;
    }


}
