package com.example.filemanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    Context context;
    File[] filesAndFolders;

    public Adapter(Context context, File[] filesAndFolders) {
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
File selectedFile = filesAndFolders[position];
holder.textView.setText(selectedFile.getName());


if(selectedFile.isDirectory()) {
    holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
} else {
    holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
}


holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(selectedFile.isDirectory()) {
            Intent intent = new Intent(context,FileListActivity.class);
            String path = selectedFile.getAbsolutePath();
            intent.putExtra("path",path);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {

            try {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                String type = "image/*";
                intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()),type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } catch(Exception e) {
                Toast.makeText(context.getApplicationContext(),"Nie moge otworzyc pliku",Toast.LENGTH_SHORT).show();
            }
        }
    }
});


holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {

        PopupMenu popupMenu = new PopupMenu(context,view);
        popupMenu.getMenu().add("DELETE");


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(menuItem.getTitle().equals("DELETE")) {
                    boolean deleted = selectedFile.delete();
                    if(deleted) {
                        Toast.makeText(context.getApplicationContext(),"DELETED",Toast.LENGTH_SHORT).show();
                        view.setVisibility(View.GONE);
                    }
                }

                return true;
            }
        });

        popupMenu.show();
        return true;
    }
});



    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;




        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.filenametextview);
            imageView = itemView.findViewById(R.id.iconview);
        }
    }





}

