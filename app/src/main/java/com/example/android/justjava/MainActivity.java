package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 0;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText addName = (EditText) findViewById(R.id.enter_name);
        String name = addName.getText().toString();
        Log.v("MainActivity", "Name is: " + name);

        CheckBox addWhippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = addWhippedCream.isChecked();
        Log.v("MainActivity", "Checkbox is checked: " + hasWhippedCream);

        CheckBox addChocoCream = (CheckBox) findViewById(R.id.chocolate_cream);
        boolean hasChocoCream = addChocoCream.isChecked();
        Log.v("MainActivity", "Choco cream: " + hasChocoCream);

        int price = calculatePrice(hasWhippedCream, hasChocoCream);

        String summary = createOrderSummary(price, hasWhippedCream, hasChocoCream, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }
    private int calculatePrice(boolean whipped_cream, boolean choco_cream){
        int Baseprice = 5;
        if(whipped_cream){
           Baseprice = Baseprice + 1;
        }
        if(choco_cream){
            Baseprice = Baseprice + 2;
        }
        return Baseprice * quantity;
    }
    public void increment(View view){
        if(quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }
    public void decrement(View view){
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }
    private String createOrderSummary(int priceOfOrder, boolean addWCream, boolean addCCream, String personName){
        String priceMessage = "Your order details are:";
        priceMessage += "\nName: " + personName;
        priceMessage += "\nWhipped Cream: " + addWCream;
        priceMessage += "\nChocolate Cream: " + addCCream;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: " + priceOfOrder;
        priceMessage += "\nThank You!";
        return priceMessage;

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int n) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + n);
    }

}
