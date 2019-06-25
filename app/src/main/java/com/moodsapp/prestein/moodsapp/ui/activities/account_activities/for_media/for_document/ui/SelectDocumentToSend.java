package com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.ActionMenuView;
import com.moodsapp.emojis_library.Helper.EmojiconTextView;
import com.moodsapp.prestein.moodsapp.R;
import com.moodsapp.prestein.moodsapp.data.Global_String.ExtractedStrings;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_chat.MessageType.MessageDocument.SendDocument;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.adapter.DocumentDetails;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.adapter.DocumentListAdapter;
import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_media.for_document.component.DocumentFilesController;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

public class SelectDocumentToSend extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private FloatingActionButton sendFab;
    private RecyclerView mListDocumentListView;
    private LinearLayout mInformationView;
    private ProgressBar mProgreessBar;
    private EmojiconTextView mNotFileFound;
    private String Extension;
    private SparseBooleanArray mSparseBooleanArray;
    private ArrayList<DocumentDetails> documents;
    private DocumentListAdapter adapter;
    private Toolbar toobarSwicher;
    private Menu menuSwitchFileDoc;
    private String User_id;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_document_to_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search_files_document_in_select_document_send);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        initView();
        executeTask(Extension);
    }

    private void initListeners() {
      adapter.SetOnItemClickListener(new OnItemDocumentClickedListener() {
          @Override
          public void onItemClickedListener(View view, int position) {
              onDocumentSelected(position,view);
          }
          @Override
          public void onCheckedIconClickedListener(View itemView, int position) {
              onDocumentSelected(position,itemView);
          }
      });
      sendFab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

           new Handler().post(new Runnable() {
               @Override
               public void run() {
                   mProgressDialog.setMessage("Saving file...");
                   mProgressDialog.show();
                   new SendDocument(getApplicationContext()).saveDocMessage(User_id,adapter.getCheckedItems());
                   new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          mProgressDialog.dismiss();
                          finish();
                          onBackPressed();
                          overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                      }
                  },4000);
               }
           });
          }
      });
    }
    private void onDocumentSelected(int position,View view){
        final LinearLayout selectedItem=(LinearLayout) view.findViewById(R.id.select_document_to_send_selected_icon_back);
        if(!mSparseBooleanArray.get(position)) {
            selectedItem.setVisibility(View.VISIBLE);
            mSparseBooleanArray.put(position, true);
            Log.d("log", "pos = "+position);
            if (adapter.getCheckedItems().size()==1){
                Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_grow_fade_in_from_bottom);
                selectedItem.startAnimation(an2);
                an2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        selectedItem.setVisibility(View.VISIBLE);
                        sendFab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        } else {
            selectedItem.setVisibility(View.GONE);
            mSparseBooleanArray.put(position, false);
            if (adapter.getCheckedItems().size()==0){
                Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_shrink_fade_out_from_bottom);
                selectedItem.startAnimation(an);
                an.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        selectedItem.setVisibility(View.GONE);
                        sendFab.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }
    }
    private void initView() {
        Bundle bundle = getIntent().getExtras();
        Extension= bundle != null ? bundle.getString(ExtractedStrings.INTENT_DOCUMENT_FILE_TYPE) : null;
        if (bundle != null) {
            User_id= bundle.getString(ExtractedStrings.INTENT_KEY_CHAT_ID);
        }
        mProgressDialog=new ProgressDialog(this);
        sendFab= (FloatingActionButton) findViewById(R.id.activity_select_document_to_send_fab_icon);
        mListDocumentListView=(RecyclerView) findViewById(R.id.select_document_to_send_recycler_list_view);
        mInformationView=(LinearLayout)findViewById(R.id.select_document_to_send_info_view);
        mProgreessBar=(ProgressBar)findViewById(R.id.select_document_to_send_still_loading);
        mNotFileFound=(EmojiconTextView)findViewById(R.id.select_document_to_send_Not_file_found);
        mInformationView.setVisibility(View.VISIBLE);
        mProgreessBar.setVisibility(View.VISIBLE);
        mSparseBooleanArray = new SparseBooleanArray();
        Slidr.attach(this);
    }
    private void executeTask(final String extension){
        mInformationView.setVisibility(View.VISIBLE);
        mProgreessBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                documents=new DocumentFilesController(getApplicationContext()).load(Environment.getExternalStorageDirectory(),extension);
                if (documents.isEmpty()){
                    mProgreessBar.setVisibility(View.GONE);
                    mNotFileFound.setVisibility(View.VISIBLE);
                    mNotFileFound.setText("No "+extension.substring(1,extension.length()).toUpperCase()+" File found!!");
                }else {
                    UpdateAdapter(extension);
                    mInformationView.setVisibility(View.GONE);
                    mProgreessBar.setVisibility(View.GONE);
                }
            }
        },4000);
    }

    private void UpdateAdapter(String extension) {
        adapter=new DocumentListAdapter(getApplicationContext(),documents
                ,extension.substring(1,extension.length()-1).toUpperCase()
                ,getIconDocument(extension)
                ,getColoBackDocument(extension)
                ,mSparseBooleanArray);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);

        mListDocumentListView.setLayoutManager(linearLayoutManager);
        mListDocumentListView.setAdapter(adapter);
        initListeners();
    }
    private int getIconDocument(String extension){
        if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_WORD_DOC)){
            return R.drawable.icons_microsoft_word_trans;
        } else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_EXCEL)){
            return R.drawable.icons_microsoft_mxcel_trans;
        }else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_PW_POINT)){
            return R.drawable.icons_microsoft_powerpointp_trans;
        }
        else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_PDF)){
            return R.drawable.icons_pdf_trans;
        }else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_APK)){
            return R.drawable.icons_apk_trans;
        }else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP)){
            return R.drawable.icons_zip_trans;
        }
        return R.drawable.icons_any_file_trans;
    }
    private int getColoBackDocument(String extension){
        if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_WORD_DOC)){
            return this.getResources().getColor(R.color.blue_trans);
        } else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_EXCEL)){
            return this.getResources().getColor(R.color.green_trans);
        }else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_PW_POINT)){
            return this.getResources().getColor(R.color.orange_trans);
        }
        else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_PDF)){
            return this.getResources().getColor(R.color.red_trans);
        }else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_APK)){
            return this.getResources().getColor(R.color.dark_green_trans);
        }else if (extension.toLowerCase().equals(ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP)){
            return this.getResources().getColor(R.color.lose_trans);
        }
        return this.getResources().getColor(R.color.gray_trans);
    }
    public void SwitchFileTypePrepare() {
        ActionMenuView menuItemChatToolBar = (ActionMenuView) findViewById(R.id.select_document_to_send_toolbar_switcher_file);
        menuSwitchFileDoc = menuItemChatToolBar.getMenu();
        getMenuInflater().inflate(R.menu.select_document_to_send_menu_switch, menuSwitchFileDoc);
        for (int i = 0; i < menuSwitchFileDoc.size(); i++) {
            menuSwitchFileDoc.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_menu_toobar,menu);
        MenuItem menuItem= menu.findItem(R.id.id_search_menu_icon);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
         SwitchFileTypePrepare();
        return true;
    }
    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id=item.getItemId();
            if (id==android.R.id.home){
                onBackPressed();
                if (mSparseBooleanArray.size()>0){
                    mSparseBooleanArray.clear();
                }
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }else if (id==R.id.select_document_to_send_switch_to_word){
                if (mSparseBooleanArray.size()>0){
                    mSparseBooleanArray.clear();
                }
                executeTask(ExtractedStrings.DOCUMENT_FILE_TYPE_WORD_DOC);
            }
            else if (id==R.id.select_document_to_send_switch_to_excel){
                if (mSparseBooleanArray.size()>0){
                    mSparseBooleanArray.clear();
                }
                executeTask(ExtractedStrings.DOCUMENT_FILE_TYPE_EXCEL);
            }else if (id==R.id.select_document_to_send_switch_to_power_point){
                if (mSparseBooleanArray.size()>0){
                    mSparseBooleanArray.clear();
                }
                executeTask(ExtractedStrings.DOCUMENT_FILE_TYPE_PW_POINT);
            }else if (id==R.id.select_document_to_send_switch_to_pdf){
                if (mSparseBooleanArray.size()>0){
                    mSparseBooleanArray.clear();
                }
                executeTask(ExtractedStrings.DOCUMENT_FILE_TYPE_PDF);
            }else if (id==R.id.select_document_to_send_switch_to_apk){
                if (mSparseBooleanArray.size()>0){
                    mSparseBooleanArray.clear();
                }
                executeTask(ExtractedStrings.DOCUMENT_FILE_TYPE_APK);
            }else if (id==R.id.select_document_to_send_switch_to_zip){
                if (mSparseBooleanArray.size()>0){
                    mSparseBooleanArray.clear();
                }
                executeTask(ExtractedStrings.DOCUMENT_FILE_TYPE_ZIP);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "In OnOptionItemSelect"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<DocumentDetails> documentLIST=new ArrayList<>();
        newText=newText.toLowerCase();
        for(DocumentDetails documentDetails:documents)
        {
            String cName=documentDetails.fileName.toLowerCase();
            if(cName.contains(newText))
            {
                documentLIST.add(documentDetails);
            }
        }
        adapter.setFilter(documentLIST);
        return false;
    }
}
