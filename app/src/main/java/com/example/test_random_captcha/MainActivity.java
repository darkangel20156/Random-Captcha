package com.example.test_random_captcha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private int[] tapOrder = {1, 2, 3};
    private int currentTapIndex = 0;
    private TextView position1, position2, position3;

    private int[] imageIds = {R.drawable.circle, R.drawable.rectangle, R.drawable.triangle, R.drawable.diamond};
    private Random random = new Random();

    TextView  guidanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        guidanceTextView = findViewById(R.id.guidanceTextView);

        position1 = findViewById(R.id.position1);
        position2 = findViewById(R.id.position2);
        position3 = findViewById(R.id.position3);

        shuffleImages();
//        shufflePosition();

        Integer [] arr = {1, 2, 3};
        List<Integer> listPosition = Arrays.asList(arr);
        Collections.shuffle(listPosition);

        position1.setText(listPosition.get(0).toString());
        position2.setText(listPosition.get(1).toString());
        position3.setText(listPosition.get(2).toString());

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageClick(listPosition.get(0));
                v.setVisibility(View.INVISIBLE);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageClick(listPosition.get(1));
                v.setVisibility(View.INVISIBLE);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageClick(listPosition.get(2));
                v.setVisibility(View.INVISIBLE);
            }
        });

        Button submitButton = findViewById(R.id.btn_click);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTapIndex == tapOrder.length) {
                    // Nếu người dùng đã bấm đúng thứ tự, chuyển sang trang mới
                    Intent intent = new Intent(MainActivity.this, NewPageActivity.class);
                    startActivity(intent);
                }
            }
        });

        updateGuidance(guidanceTextView);

        Button tryAgainButton = findViewById(R.id.btn_try_again);

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset lại trang hoặc làm bất kỳ điều gì cần thiết để làm mới trạng thái ban đầu.
                resetPage();
            }
        });
    }

    private void shuffleImages() {
        List<Integer> imageOrder = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(imageOrder);

        image1.setImageResource(imageIds[imageOrder.get(0)]);
        image2.setImageResource(imageIds[imageOrder.get(1)]);
        image3.setImageResource(imageIds[imageOrder.get(2)]);
    }

    private void handleImageClick(int order) {
        if (order == tapOrder[currentTapIndex]) {
            currentTapIndex++;
            updateGuidance(findViewById(R.id.guidanceTextView));
        } else {
            Toast.makeText(this,"Bạn đã chọn sai vui lòng thử lại", Toast.LENGTH_LONG).show();
            Button tryAgainButton = findViewById(R.id.btn_try_again);
            tryAgainButton.setVisibility(View.VISIBLE); // Hiển thị nút "Thử lại"
            guidanceTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void updateGuidance(TextView guidanceTextView) {
        if (currentTapIndex < tapOrder.length) {
            guidanceTextView.setText("Bấm vào hình " + tapOrder[currentTapIndex] + " tiếp theo.");
        } else if (currentTapIndex == tapOrder.length) {
            guidanceTextView.setText("Bấm đúng thứ tự! Nhấn nút 'CLICK' để tiếp tục.");
        } else {
            guidanceTextView.setText("Bạn đã chọn sai thứ tự, vui lòng chọn lại!");
        }
    }

    private void resetPage() {
        // Đặt lại trạng thái ban đầu của trang
        currentTapIndex = 0;
        // Hiển thị lại hướng dẫn và hình ảnh nếu có
        updateGuidance(findViewById(R.id.guidanceTextViewRefresh));
        // Ẩn nút "Thử lại"
        Button tryAgainButton = findViewById(R.id.btn_try_again);
        tryAgainButton.setVisibility(View.GONE);

        guidanceTextView.setVisibility(View.VISIBLE);
        // Hiển thị lại hình ảnh nếu bạn đã ẩn nó khi bấm sai
        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.VISIBLE);
        image3.setVisibility(View.VISIBLE);
    }

//    private void shufflePosition() {
//        Integer [] arr = {1, 2, 3};
//        List<Integer> listPosition = Arrays.asList(arr);
//        Collections.shuffle(listPosition);
//
//        position1.setText(listPosition.get(0).toString());
//        position2.setText(listPosition.get(1).toString());
//        position3.setText(listPosition.get(2).toString());
//    }
}