package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity1 = 0, quantity2 = 0, quantity3 = 0;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment1(View view) {
        quantity1 += 1;
        display1(quantity1);
    }

    public void increment2 (View view) {
        quantity2 += 1;
        display2(quantity2);
    }

    public void increment3 (View view) {
        quantity3 += 1;
        display3(quantity3);
    }

    public void decrement1(View view) {
        if(quantity1 == 0) {
            Toast.makeText(this, "You cannot order less than 0 cups", Toast.LENGTH_LONG).show();
            return;
        }
        quantity1 -= 1;
        display1(quantity1);
    }

    public void decrement2(View view) {
        if(quantity2 == 0) {
            Toast.makeText(this, "You cannot order less than 0 cups", Toast.LENGTH_LONG).show();
            return;
        }
        quantity2 -= 1;
        display2(quantity2);
    }

    public void decrement3(View view) {
        if(quantity3 == 0) {
            Toast.makeText(this, "You cannot order less than 0 cups", Toast.LENGTH_LONG).show();
            return;
        }
        quantity3 -= 1;
        display3(quantity3);
    }

    public void display1(int number1) {
        TextView textView1 = findViewById(R.id.quantity1);
        textView1.setText("" + number1);
    }

    public void display2(int number2) {
        TextView textView2 = findViewById(R.id.quantity2);
        textView2.setText("" + number2);
    }

    public void display3(int number3) {
        TextView textView3 = findViewById(R.id.quantity3);
        textView3.setText("" + number3);
    }

    private String submitOrderSummary(int quantity1, int quantity2, int quantity3, boolean addWhippedCream, boolean hasChocolate) {
        String summary = "Freshly Brewed Coffee: \t\t" + quantity1;
        summary += "\nCoffee Frappucino: \t\t" + quantity2;
        summary += "\nHazelnut Espresso: \t\t" + quantity3;

        if(addWhippedCream) {
            summary += "\nWhipped Cream: \t\t" + addWhippedCream;
        }

        if(hasChocolate) {
            summary += "\nChocolate Topping: \t\t" + hasChocolate;
        }

        return summary;
    }

    private int extraToppings(boolean addWhippedCream, boolean hasChocolate) {
        int toppingsPrice = 0;
        if(addWhippedCream) {
            toppingsPrice += 1;
        }

        if(hasChocolate) {
            toppingsPrice += 2;
        }
        return toppingsPrice;
    }

    public void submitOrder(View view) {
        int coffee1 = quantity1 * 5;
        int coffee2 = quantity2 * 10;
        int coffee3 = quantity3 * 15;

        CheckBox hasChecked = findViewById(R.id.cream);
        boolean hasWhippedCream = hasChecked.isChecked();

        CheckBox hasCheckedChocolate = findViewById(R.id.chocolate);
        boolean hasChocolate = hasCheckedChocolate.isChecked();

        int total = coffee1 + coffee2 + coffee3;
        total += extraToppings(hasWhippedCream, hasChocolate);
        TextView priceView = findViewById(R.id.price_view);
        TextView message_view = findViewById(R.id.message_view);
        priceView.setText("$" + total);
        String message = submitOrderSummary(quantity1, quantity2, quantity3, hasWhippedCream, hasChocolate);
        message_view.setText("" + message);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Coffee");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}