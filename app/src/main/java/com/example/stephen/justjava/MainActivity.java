package com.example.stephen.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //Global variables
    int numCoffeeOrdered = 2;
    int costOfCoffee = 5;
    int costOfWhipped = 1;
    int costOfChocolate = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked. Calculates price,
     * creates order summary and displays
     */
    public void submitOrder(View view) {
        //displayMessage(createOrderSummary(calculatePrice()));   //display order summary
        EditText custName = (EditText) findViewById(R.id.name_customer);

        String subjectMessage = "JustJava order for " + custName.getText().toString();
        String orderSummary = createOrderSummary(calculatePrice());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectMessage);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     *
     * @return Total price of order
     */
    private int calculatePrice() {
        CheckBox whippedBox = (CheckBox) findViewById(R.id.whipped_check_box);
        CheckBox chocBox = (CheckBox) findViewById(R.id.chocolate_check_box);

        //Calculate cost for each cup with additional toppings
        int pricePerCup = costOfCoffee;
        if(whippedBox.isChecked())
            pricePerCup += costOfWhipped;
        if(chocBox.isChecked())
            pricePerCup += costOfChocolate;

        return numCoffeeOrdered * pricePerCup;

    }

    /**
     * Create summary of order including name, quantity, price and optional items
     * @param price total price of order
     * @return Order summary with name, price and thank you message
     */
    private String createOrderSummary(int price) {
        CheckBox whippedBox = (CheckBox) findViewById(R.id.whipped_check_box);
        CheckBox chocBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        EditText custName = (EditText) findViewById(R.id.name_customer);

        String whipAdd = "";
        String chocAdd = "";

        if(whippedBox.isChecked())
            whipAdd = "\nWhipped Added";
        if(chocBox.isChecked())
            chocAdd = "\nChocolate Added";

        return "Name: " + custName.getText().toString() +
                "\nQuantity: " + numCoffeeOrdered +
                "\nTotal: " + price +
                whipAdd +
                chocAdd +
                "\nThank You!";
    }

    /**
     * Displays the text for the price TextView
     *
     * @param message Message for the total price and order summary
     */
    /*
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */

    /**
     * Increases the number of coffees ordered by one and updates
     * the price
     */
    public void increaseQuantity(View view) {
        if(numCoffeeOrdered <100) {
            numCoffeeOrdered++;
            displayQuantity(numCoffeeOrdered);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Cannot order more than One Hundred coffees.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Decreases the number of coffees ordered by one and updates
     * the price
     */
    public void decreaseQuantity(View view) {
        if (numCoffeeOrdered > 1) {
            numCoffeeOrdered--;
            displayQuantity(numCoffeeOrdered);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Cannot order fewer than One coffee.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
