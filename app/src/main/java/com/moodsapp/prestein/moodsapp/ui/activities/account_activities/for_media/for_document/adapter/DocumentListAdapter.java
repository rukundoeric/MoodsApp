package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.ui.DocumentsListeners;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.ui.OnItemDocumentClickedListener;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DocumentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<DocumentDetails> listDocument;
    String extension;
    int icon;
    int backgound;
    private SparseBooleanArray mSparseBooleanArray;

    public DocumentListAdapter(Context context, ArrayList<DocumentDetails> listDocument, String extension, int icon, int backgound, SparseBooleanArray mSparseBooleanArray) {
        this.context = context;
        this.listDocument = listDocument;
        this.extension = extension;
        this.icon = icon;
        this.backgound = backgound;
        this.mSparseBooleanArray = mSparseBooleanArray;
    }

    public  void SetOnItemClickListener(OnItemDocumentClickedListener mItemClickListener) {
        DocumentsListeners.mOnItemDocumentClickedListener = mItemClickListener;
    }
    public ArrayList<String> getCheckedItems() {
        ArrayList<String> mSelectedDocuments = new ArrayList<String>();
        for (int i = 0; i < listDocument.size(); i++) {
            if (mSparseBooleanArray.get(i)) {
                String path=listDocument.get(i).filePath;
                if (new File(path).exists()){
                    mSelectedDocuments.add(path);
                }
            }
        }
        return mSelectedDocuments;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_select_document_to_sent,parent, false);
        return new DocumentItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DocumentItem v = ((DocumentItem) holder);
        String name=listDocument.get(position).fileName;
        String details= extension.toUpperCase()+" | "+listDocument.get(position).fileSize;
        v.mDocumentName.setText(name);
        v.mDocumentDetails.setText(details);
        //v.mDocumentIconBackground.setBackgroundColor(backgound);
        v.mDocumentIcon.setImageResource(icon);
        if(mSparseBooleanArray.get(position)) {
            v.mDocumentSelectedBack.setVisibility(View.VISIBLE);
            mSparseBooleanArray.put(position, true);
        } else {
            v.mDocumentSelectedBack.setVisibility(View.GONE);
            mSparseBooleanArray.put(position, false);
        }
    }
    public void setFilter(ArrayList<DocumentDetails> newList)
    {
        listDocument=new ArrayList<>();
        listDocument.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listDocument.size();
    }

    private class DocumentItem extends RecyclerView.ViewHolder{
        public ImageView mDocumentIcon;
        public RelativeLayout mDocumentIconBackground;
        public EmojiconTextView mDocumentName;
        public EmojiconTextView mDocumentDetails;
        public LinearLayout mDocumentSelectedBack;
        public ImageView nDocumentSelectedIcon;

        public DocumentItem(final View itemView) {
            super(itemView);
            mDocumentIcon=(ImageView)itemView.findViewById(R.id.select_document_to_send_icon_image);
            mDocumentIconBackground=(RelativeLayout)itemView.findViewById(R.id.select_document_to_send_icon_background);
            mDocumentName=(EmojiconTextView)itemView.findViewById(R.id.select_document_to_send_document_name);
            mDocumentDetails=(EmojiconTextView)itemView.findViewById(R.id.select_document_to_send_document_details);
            mDocumentSelectedBack=(LinearLayout)itemView.findViewById(R.id.select_document_to_send_selected_icon_back);
            nDocumentSelectedIcon=(CircleImageView)itemView.findViewById(R.id.select_document_to_send_selected_document_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentsListeners.mOnItemDocumentClickedListener.onItemClickedListener(itemView, getPosition());
                }
            });
            nDocumentSelectedIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentsListeners.mOnItemDocumentClickedListener.onCheckedIconClickedListener(itemView, getPosition());
                }
            });
        }
    }
}
