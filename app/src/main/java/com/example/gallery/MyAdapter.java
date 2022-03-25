package com.example.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolder> {


    // создание переменной для нашего контекста и списка массивов.
    private final Context context;
    private final ArrayList<String> imagePathArrayList;


    // в строке ниже мы создали конструктор.
    public MyAdapter(Context context, ArrayList<String> imagePathArrayList) {
        this.context = context;
        this.imagePathArrayList = imagePathArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

// Раздуть Layout в этом методе, который мы создали.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {


// в строке ниже мы получаем файл из пути, который мы сохранили в нашем списке
        File imgFile = new File(imagePathArrayList.get(position));


// в нижней строке мы проверяем, существует ли файл или нет.
        if (imgFile.exists()) {


// если файл существует, мы отображаем этот файл в нашем представлении изображения, используя библиотеку picasso
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(holder.imageIV);


// в строке ниже мы добавляем прослушиватель кликов к нашему элементу представления ресайклера.
            holder.itemView.setOnClickListener(v -> {

                // внутри прослушивателя кликов мы создаем новый интент
                Intent i = new Intent(context, Image.class);

                // в строке ниже мы передаем путь изображения к нашей новой активности.
                i.putExtra("imgPath", imagePathArrayList.get(position));


// наконец мы начинаем нашу деятельность.
                context.startActivity(i);
            });
        }
    }

    @Override
    public int getItemCount() {

// этот метод возвращает
        // размер recyclerview
        return imagePathArrayList.size();
    }
    // Класс держателя представления для обработки представления Recycler.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        // создание переменных для наших представлений.
        private final ImageView imageIV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

// инициализируем наши представления их идентификаторами.
            imageIV = itemView.findViewById(R.id.idIVImage);
        }
    }
}