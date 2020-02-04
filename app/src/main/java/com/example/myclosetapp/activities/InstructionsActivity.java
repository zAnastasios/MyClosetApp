package com.example.myclosetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.InstructionItem;

import java.util.ArrayList;
import java.util.List;

public class InstructionsActivity extends AppCompatActivity {
    private List<InstructionItem> instructionsList;
    private ImageView instructionsImage;
    private TextView instructionsText;
    private Button nextInstruction;
    private Button previousInstruction;
    private int count ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        nextInstruction=findViewById(R.id.button_change_instruction_next);
        previousInstruction=findViewById(R.id.button_change_instruction_previous);
        instructionsImage=findViewById(R.id.instructionsImageView);
        instructionsText=findViewById(R.id.instructionsTextView);

        count=0;
        instructionsList = new ArrayList<>();
        instructionsList.add(new InstructionItem(R.drawable.help1,"Arxikh selida ki etsi xerw egw fash"));
        instructionsList.add(new InstructionItem(R.drawable.help1,"Arxikh selida ki etsi xerw egw fash1"));
        instructionsList.add(new InstructionItem(R.drawable.help1,"Arxikh selida ki etsi xerw egw fash2"));
        instructionsList.add(new InstructionItem(R.drawable.help1,"Arxikh selida ki etsi xerw egw fash3"));
        getSupportActionBar();
        setTitle("Οδηγίες "+(count+1)+"/"+instructionsList.size());
        instructionsImage.setImageResource(instructionsList.get(count).getImage());
        instructionsText.setText(instructionsList.get(count).getDescription());

        nextInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<instructionsList.size()-1){
                    count+=1;
                    instructionsImage.setImageResource(instructionsList.get(count).getImage());
                    instructionsText.setText(instructionsList.get(count).getDescription());
                    setTitle("Οδηγίες "+(count+1)+"/"+instructionsList.size());
                }
                else {
                    Toast.makeText(InstructionsActivity.this,"Δεν υπάρχουν άλλες οδηγίες",Toast.LENGTH_SHORT).show();
                }


            }
        });

        previousInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>0){
                    count-=1;
                    instructionsImage.setImageResource(instructionsList.get(count).getImage());
                    instructionsText.setText(instructionsList.get(count).getDescription());
                    setTitle("Οδηγίες "+(count+1)+"/"+instructionsList.size());
                }
                else {
                    Toast.makeText(InstructionsActivity.this,"Δεν υπάρχουν άλλες οδηγίες",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
