package com.example.bookofrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookofrecipes.R;
import com.example.bookofrecipes.model.Instruction;


import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>{

    Context context;
    List<Instruction> instructions;

    public InstructionAdapter(Context context,List<Instruction> instructions) {
        this.instructions = instructions;
        this.context = context;
    }

    @Override
    public InstructionAdapter.InstructionViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View instructionItems = LayoutInflater.from(context).inflate(R.layout.instruction_item,parent,false);
        return new InstructionViewHolder(instructionItems);
    }

    @Override
    public void onBindViewHolder( InstructionViewHolder holder, int position) {
        holder.number.setText( String.valueOf(instructions.get(position).getNumber()));
        holder.instruction.setText( instructions.get(position).getInstruction());
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public static final class InstructionViewHolder extends RecyclerView.ViewHolder
    {
        TextView instruction;
        TextView number;

        public InstructionViewHolder(@NonNull View itemView) {
            super(itemView);
            instruction = itemView.findViewById(R.id.instruction);
            number = itemView.findViewById(R.id.number);
        }
    }
}
