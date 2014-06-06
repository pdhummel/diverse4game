package pdh.android.diverse4game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.text.Html;
import android.view.*;


public class HelpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView textViewHelp = (TextView)findViewById(R.id.textViewHelp);
        textViewHelp.setText(Html.fromHtml(getResources().getString(R.string.helpText)));
        Button buttonClose = (Button)findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(buttonHandler);
    }

    
    View.OnClickListener buttonHandler = new View.OnClickListener() {
        public void onClick(View v) {
        	setResult(0);
        	finish();
        }
    };

	
}
