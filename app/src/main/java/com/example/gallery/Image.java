package com.example.gallery;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.File;

public class Image extends AppCompatActivity {

    // создание строковой переменной, переменной представления изображения
    // и переменная для нашего класса детектора жестов.
    String imgPath;

    String[] pathsArr;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    Button button;

    // в строке ниже мы определяем коэффициент масштабирования.
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

// в строке ниже получаем данные, которые мы передали из нашего класса адаптера.
        imgPath = getIntent().getStringExtra("imgPath");





// инициализируем наше изображение
        imageView = findViewById(R.id.idIVImage);

        // в строке ниже мы инициализируем наш детектор жестов масштаба для увеличения и уменьшения масштаба нашего изображения.
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // в строке ниже мы получаем наш файл изображения по его пути.
        File imgFile = new File(imgPath);

        // если файл существует, мы загружаем это изображение в наше представление изображений.
        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // внутри метода события касания, который мы вызываем
        // сенсорный метод события и передача ему нашего события движения.
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // в строке ниже мы создаем класс для нашей шкалы
        // слушатель и расширение его с помощью прослушивателя жестов.
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {


// внутри метода on scale мы устанавливаем масштаб
            // для нашего изображения в нашем представлении изображения.
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // в нижней строке мы устанавливаем
            // масштабируем x и масштабируем y в соответствии с нашим представлением изображения.
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}