package com.example.gallery;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // в строке ниже мы создаем переменные для
    // наш список массивов, представление ресайклера и класс адаптера.
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ArrayList<String> imagePaths;
    private RecyclerView imagesRV;
    private MyAdapter imageRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // мы вызываем метод для запроса
        // права на чтение внешнего хранилища.
        requestPermissions();

        // создание нового списка массивов и
        // инициализируем наше представление переработчика.
        imagePaths = new ArrayList<>();
        imagesRV = findViewById(R.id.idRVImages);

        // вызов метода для
        // подготавливаем наше представление переработчика.
        prepareRecyclerView();
    }

    private boolean checkPermission() {
        // в этом методе мы проверяем, предоставлены разрешения или нет, и возвращаем результат.
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (checkPermission()) {
            // если права уже предоставлены, мы вызываем
            // метод для получения всех изображений из нашего внешнего хранилища.
            Toast.makeText(this, "Permissions granted..", Toast.LENGTH_SHORT).show();
            getImagePath();
        } else {
            // если разрешения не предоставлены, мы
            // вызов метода для запроса разрешений.
            requestPermission();
        }
    }

    private void requestPermission() {
        //в строке ниже мы запрашиваем разрешения на внешнее хранилище.
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private void prepareRecyclerView() {

        // в этом методе мы подготавливаем наше представление ресайклера.
        // в строке ниже мы инициализируем наш класс адаптера.
        imageRVAdapter = new MyAdapter(MainActivity.this, imagePaths);

        // в строке ниже мы создаем новый менеджер компоновки сетки.
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 4);

        // в нижней строке мы устанавливаем макет
        // менеджер и адаптер для нашего представления ресайклера.
        imagesRV.setLayoutManager(manager);
        imagesRV.setAdapter(imageRVAdapter);
    }

    private void getImagePath() {
        // в этом методе мы добавляем все наши пути к изображениям
        // в нашем массиве, который мы создали.
        // в строке ниже мы проверяем, есть ли у устройства SD-карта или нет.
        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (isSDPresent) {


            // если SD-карта присутствует, мы создаем новый список в
            // который мы получаем данные наших изображений с их идентификаторами.
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};

            // в нижней строке мы создаем новый
            // строка, чтобы упорядочить наши изображения по строке.
            final String orderBy = MediaStore.Images.Media._ID;

            // этот метод сохранит все изображения
            // из галереи курсора
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);


            // ниже строка для получения общего количества изображений
            int count = cursor.getCount(); // можно поставить свое число

            // в нижней строке мы запускаем цикл для добавления
            // путь к файлу изображения в нашем списке массивов.
            for (int i = 0; i < count; i++) {

                // в нижней строке мы перемещаем позицию курсора
                cursor.moveToPosition(i);

                // в нижней строке мы получаем путь к файлу изображения
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                // после этого получаем путь к файлу изображения
                // и добавляем этот путь в наш список массивов.
                imagePaths.add(cursor.getString(dataColumnIndex));
            }
            imageRVAdapter.notifyDataSetChanged();
            // после добавления данных в наш
            // список массивов мы закрываем наш курсор.
            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // этот метод вызывается после предоставления разрешений.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // мы проверяем код разрешения.
            case PERMISSION_REQUEST_CODE:
                // в данном случае мы проверяем, приняты разрешения или нет.
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        // если разрешения приняты, мы показываем всплывающее сообщение
                        // и вызов метода для получения пути к изображению.
                        Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show();
                        getImagePath();
                    } else {
                        // если разрешения запрещены, мы закрываем приложение и отображаем всплывающее сообщение
                        Toast.makeText(this, "Permissions denined, Permissions are required to use the app..", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}