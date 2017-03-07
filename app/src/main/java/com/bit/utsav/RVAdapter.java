package com.bit.utsav;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CustomViewHolder> {
    private List<ParseObject> list;
    private Context mContext;

    public RVAdapter(Context context,List<ParseObject> list) {
        this.list = list;
        mContext = context;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RVAdapter.CustomViewHolder holder,final int position) {
        holder.textView.setText(list.get(position).getString("text"));
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setimage(holder,position);
            }
        });


    }

    private CustomViewHolder setimage(final CustomViewHolder holder, final int i) {
        String root = Environment.getExternalStorageDirectory().toString();
        File imgFile = new  File(root + "/bit_utsav_images/" + "Image-"+ (list.size()-i) +".jpg");
        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imageView.setImageBitmap(myBitmap);
            return holder;
        }
        else {
            ParseFile imageData = (ParseFile) list.get(i).get("image");
            if (imageData != null) {
                imageData.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {

                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            if(data.length>100)
                            saveImage(bitmap, list.size()-i);
                            holder.imageView.setImageBitmap(bitmap);
                        } else {
                        }
                    }
                });
            } else
                holder.imageView.setVisibility(View.GONE);
            return holder;
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }
    private void saveImage(Bitmap finalBitmap,int i) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/bit_utsav_images");
        myDir.mkdirs();
        String fname = "Image-"+ i +".jpg";
        File file = new File (myDir, fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
