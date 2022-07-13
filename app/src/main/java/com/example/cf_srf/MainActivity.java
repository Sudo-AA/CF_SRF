package com.example.cf_srf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// ENCAPSULATE EVERYTHING
public class MainActivity extends AppCompatActivity {
    private static RelativeLayout PARENT;
    private static String APP_UPDATE_SERVER_URL;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICK_IMAGE = 6666;
    private static final String version = "1.0.2";
    private static String versioncontrol ;
    // for in active handler
    private static Handler idle_handler;
    private static Runnable idle_runnable;
    private static int sec_for_idle = 300;
    private static final String Domain = "http://192.168.1.45:4545/CF_SRF_SERVICE.svc/"; // FOR TESTING
    //private static final String Domain = "http://122.53.122.154:81/srf_app/CF_SRF_SERVICE.svc/"; // ORIGINAL WEB SERVER
    private static ImageView actmenu;
    private static Button acthome;
    private static TextView acttitle;
    private static NavigationView menulayout;
    // init for objects----------------------------------------
    private static Button emp_next,emp_back,cat_return,it, me, mt,back_to_login, srf_login, srf_cancel, con_details, con_back, req_con, req_back, cat_back, srf_edit, srf_add, back_add_menu, edit_srfconfirm, edit_srfback, addimage, back_req_img, confirm_imgs, back_imageviewer_button, add_user_back, add_user_con, back_view_details, get_image_from_files, status_classback, add_action, view_action,back_to_view_details ;
    private static TextView goto_login,con_password,new_password,new_username,emp_number,srf_user_id, srf_user_pass, editreq, edit_srf, searchbar, acc_firstname, acc_surname;
    //random nothing labels
    private static TextView status_header,vercode,iden_dept, attach_textdisplay,techlist_label,new_registration, user_check, stn_check, cat, req, headcheck, gg, editheader, noimage_attach, attach_d, no_records, cat_branch_notifier, srflist_branch_notifier, image_branch_notifier, acc_details, view_srf_details;
    private static RelativeLayout for_approval,get_emp_code_layout,add_androidID, srf_login_form, srf_station_form, detailscon, selectcat, request, menu_form, srf_list_form, srf_editform, attach_img_form, image_viewer_form, view_srf_details_form, status_class_form,actions_for_srf , select_cat;
    private static LinearLayout hidebutton;
    private static RecyclerView statrec, catrec, srfrec, imagelist_imgform, review_images, view_imagelist, status_classlist, view_actions, tech_listview;
    private static ProgressBar log_in_prog, prog_details, search_prog;
    private static RecyclerView.Adapter mAdapter, searchadapter, imageadapter, reviewadapter, viewer_adap, status_adapter, actionview_adapter, tech_list_adapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static ArrayList<station> stnList;
    private static ArrayList<status_class> status_list;
    private static List<cat> catList;
    private static List<srf> srfList;
    private static List<sra> sraList;
    private static ArrayList<technician> tech_list;
    private static ArrayList<img_loc> img_locList;
    // temporary variables
    private static String regemp;
    private static String regfname;
    private static String reglname;
    // init for info holder variables--------------------------
    private static String img_stn_code;
    private static Boolean menutrigger = null;
    private static Boolean triger = null;
    private static Boolean registration = null;
    private static Boolean details_viewing_trigger = false;
    private static Boolean upload_trigger = null;
    private static Boolean status_trigger = false;
    private static Boolean idle_trigger;
    private static String android_id;
    private static String status_holder;
    private static String status_holder_name;
    private static String dept;
    private static String dept_desc;
    private static Button logout_float;
    private static AlertDialog.Builder builder1, builder2, builder3;
    private static int call_back =9999;
    private static Boolean enabler = true;
    private static View actview;
    private static Boolean actionbartrigger = false;
    private static PieChart pieChart;


    public static String getDept() {
        return dept;
    }

    public static void setDept(String dept) {
        MainActivity.dept = dept;
    }

    public static String getDept_desc() {
        return dept_desc;
    }

    public static void setDept_desc(String dept_desc) {
        MainActivity.dept_desc = dept_desc;
    }


    public static String getStatus_holder_name() {
        return status_holder_name;
    }

    public static void setStatus_holder_name(String status_holder_name) {
        MainActivity.status_holder_name = status_holder_name;
    }

    public static String getStatus_holder() {
        return status_holder;
    }

    public static void setStatus_holder(String status_holder) {
        MainActivity.status_holder = status_holder;
    }

    public static Boolean getStatus_trigger() {
        return status_trigger;
    }

    public static void setStatus_trigger(Boolean status_trigger) {
        MainActivity.status_trigger = status_trigger;
    }


    // status class holder
    private static String status_class = "";

    private static ImageView image_viewer_img;
    String currentPhotoPath;

    public static Boolean getDetails_viewing_trigger() {
        return details_viewing_trigger;
    }

    public static void setDetails_viewing_trigger(Boolean details_viewing_trigger) {
        MainActivity.details_viewing_trigger = details_viewing_trigger;
    }

    public static Boolean getRegistration() {
        return registration;
    }

    public static void setRegistration(Boolean registration) {
        MainActivity.registration = registration;
    }

    public static String getAndroid_id() {
        return android_id;
    }

    public static void setAndroid_id(String android_id) {
        MainActivity.android_id = android_id;
    }

    public static Boolean getTriger() {
        return triger;
    }

    public static void setTriger(Boolean triger) {
        MainActivity.triger = triger;
    }

    public static Boolean getMenutrigger() {
        return menutrigger;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // PARENT LAYOUT
        PARENT = (RelativeLayout) findViewById(R.id.parent) ;

        // PIE CHART -----------------------------------------------------------------------------
        pieChart = findViewById(R.id.piechart);
        // idle --------------------------------------------------------------------------------
        idle_trigger = false;
        //idle calling--------------------------------------------------------------------------------
        idle_handler = new Handler();
        idle_runnable = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                dialog_idle("SESSION EXPIRED ", idle_trigger);
            }
        };
        startHandler();
        new update_checker(MainActivity.this).execute("https://raw.githubusercontent.com/V-for-velascoDMY23/CLEAN-FUEL-SRF-APPLICATION-RELEASE/main/updater_caller.json");

        // ACTIONBAR--------------------------------------------------------------------------------
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        getSupportActionBar().setElevation(0);
        actview = getSupportActionBar().getCustomView();
        getSupportActionBar().setCustomView(actview);
        getSupportActionBar().hide();
        actmenu = actview.findViewById(R.id.menu);

        acttitle = actview.findViewById(R.id.action_bar_title);
        actmenu.setVisibility(View.GONE);
        actmenu.setBackgroundResource(R.drawable.new_menu);
        menulayout = (NavigationView) findViewById(R.id.menulayout);
        logout_float = (Button) findViewById(R.id.new_log_in);

        srf_edit = (Button) findViewById(R.id.new_edit_srf);
        srf_add = (Button) findViewById(R.id.new_add_srf);
        acthome = (Button) findViewById(R.id.new_home);

        // BUTTON HERE--------------------------------------------------------------------------------
        vercode = (TextView) findViewById(R.id.vercode);
        vercode.setText("Version : "+version);
        srf_login = findViewById(R.id.srf_login_button);
        srf_cancel = findViewById(R.id.srf_cancel_button);
        con_details = findViewById(R.id.confirm_details);
        con_back = findViewById(R.id.back_confirm);
        req_con = findViewById(R.id.confirm_request);
        req_back = findViewById(R.id.back_request);
        cat_back = findViewById(R.id.cat_back);
        back_add_menu = findViewById(R.id.add_back_menu);

        // button for edit srf
        edit_srfback = findViewById(R.id.back_editsrf);
        edit_srfconfirm = findViewById(R.id.confirm_editsrf);
        // add image buttons
        addimage = findViewById(R.id.attach_img);
        get_image_from_files = findViewById(R.id.attach_viafile);
        back_req_img = findViewById(R.id.back_to_req);
        confirm_imgs = findViewById(R.id.confirm_img);
        back_imageviewer_button = findViewById(R.id.back_image_viewer);

        // TEXTBOX HERE
        srf_user_id = findViewById(R.id.srf_user);
        srf_user_pass = findViewById(R.id.srf_pass);
        editreq = findViewById(R.id.requestedit);
        edit_srf = findViewById(R.id.srf_edit);
        searchbar = findViewById(R.id.srf_searchstn);
        // RELATIVELAYOUT HERE
        srf_login_form = findViewById(R.id.login_form);
        srf_station_form = findViewById(R.id.station_form);
        detailscon = findViewById(R.id.checkform);
        selectcat = findViewById(R.id.requestfor_form);
        request = findViewById(R.id.requestform);
        menu_form = findViewById(R.id.menuform);
        srf_list_form = findViewById(R.id.srf_form);
        srf_editform = findViewById(R.id.edit_form);
        attach_img_form = findViewById(R.id.attachingform);
        image_viewer_form = findViewById(R.id.imageViewer_form);
        add_androidID = findViewById(R.id.add_androidID);

        //linear
        hidebutton = findViewById(R.id.hide_button);
        // TEXT VIEW
        user_check = findViewById(R.id.usernamecon);
        stn_check = findViewById(R.id.stn_check);
        cat = findViewById(R.id.cat);
        req = findViewById(R.id.req);
        gg = findViewById(R.id.gg);
        noimage_attach = findViewById(R.id.no_image);
        attach_d = findViewById(R.id.attach_textdisplay);
        no_records = findViewById(R.id.norecord);
        cat_branch_notifier = findViewById(R.id.cat_branch_notifier);
        srflist_branch_notifier = findViewById(R.id.srflist_branch_notifier);
        image_branch_notifier = findViewById(R.id.image_branch_notifier);

        // new acc registration
        acc_details = findViewById(R.id.new_acc_details);
        new_registration = findViewById(R.id.login_as_branch_oic);
        add_user_back = findViewById(R.id.add_user_back);
        add_user_con = findViewById(R.id.add_user_con);
        acc_firstname = findViewById(R.id.firstname);
        acc_surname = findViewById(R.id.surname);
        back_to_login = findViewById(R.id.back_to_login);
        // relative layout
        get_emp_code_layout = findViewById(R.id.get_emp_code_layout);
        //buttons
        emp_back = findViewById(R.id.emp_back);
        emp_next = findViewById(R.id.emp_next);
        // textboxes
        emp_number = findViewById(R.id.emp_number);
        new_username = findViewById(R.id.new_username);
        new_password = findViewById(R.id.new_password);
        con_password = findViewById(R.id.con_password);

        // for aproval
        goto_login = findViewById(R.id.goto_login);
        for_approval = findViewById(R.id.for_approval);

        //header
        headcheck = findViewById(R.id.headerstatus);
        editheader = findViewById(R.id.edit_headerstatus);
        srfrec = findViewById(R.id.srf_list);
        // PROGRESSBAR
        log_in_prog = findViewById(R.id.login_prog);
        imagelist_imgform = findViewById(R.id.imagelist);
        // image viewer
        image_viewer_img = findViewById(R.id.imageViewer_form_img);
        //PERMISSION STRING
        review_images = findViewById(R.id.reviewimages_list);
        // viewing details

        back_view_details = findViewById(R.id.back_to_srflist_view);
        view_srf_details = findViewById(R.id.view_text_details);
        view_srf_details_form = findViewById(R.id.view_full_details);
        view_imagelist = findViewById(R.id.view_imagelist);
        // status_classholder
        status_class_form =  (RelativeLayout) findViewById(R.id.status_class_form);
        status_classback = (Button) findViewById(R.id.back_to_menu_from_status);
        status_classlist=(RecyclerView) findViewById(R.id.status_class_list);
        catrec = (RecyclerView) findViewById(R.id.requestor_list);
        //action
        add_action =  (Button) findViewById(R.id.add_action);
        view_action = (Button) findViewById(R.id.view_action);
        view_actions = (RecyclerView) findViewById(R.id.view_actions);
        back_to_view_details = (Button) findViewById(R.id.back_to_view_details);
        actions_for_srf =(RelativeLayout) findViewById(R.id.actions_for_srf);
        // for techlist
        techlist_label =  (TextView) findViewById(R.id.text_for_tech);
        tech_listview = (RecyclerView) findViewById(R.id.tech_list);
        attach_textdisplay =(TextView) findViewById(R.id.attach_textdisplay);
        prog_details = (ProgressBar) findViewById(R.id.prog_for_details);
        statrec = findViewById(R.id.station_list);
        status_header = findViewById(R.id.status_header);

        // for cat select
        select_cat = (RelativeLayout) findViewById(R.id.select_cat) ;
        it = (Button) findViewById(R.id.it) ;
        me = (Button) findViewById(R.id.me) ;
        mt = (Button) findViewById(R.id.mt) ;
        search_prog = (ProgressBar) findViewById(R.id.search_prog);
        iden_dept = (TextView) findViewById(R.id.identifier_dept);
        cat_return= (Button) findViewById(R.id.cat_return) ;
        builder1 = new AlertDialog.Builder(MainActivity.this);
        builder2 = new AlertDialog.Builder(MainActivity.this);
        builder3 = new AlertDialog.Builder(MainActivity.this);
        String[] permissionRequest = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        setAndroid_id(android_id);

        srf_login_form.setVisibility(View.VISIBLE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);

        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();

        srf_user_id.setOnFocusChangeListener(ofcListener);
        srf_user_pass.setOnFocusChangeListener(ofcListener);
        editreq.setOnFocusChangeListener(ofcListener);
        edit_srf.setOnFocusChangeListener(ofcListener);
        searchbar.setOnFocusChangeListener(ofcListener);
        acc_firstname.setOnFocusChangeListener(ofcListener);
        acc_surname.setOnFocusChangeListener(ofcListener);


        OnBackPressedCallback callback = new OnBackPressedCallback(enabler /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                switch (call_back) {

                    case 8888:
                        to_login();
                        break;
                    case 9999:
                        finish();
                        break;
                    case 0:
                        dialog_to_exit("YOUR WORK WILL BE DISCARDED, LOG OUT NOW?", 3);
                        break;
                    case 1:
                        to_station();
                        break;
                    case 2:
                        to_menuform();
                        break;
                    case 3:
                        to_dept();
                        break;
                    case 4:
                        to_srflist();
                        break;
                    case 5:
                        to_viewing();
                        break;
                    case 6:
                        to_view_actions();
                        break;
                    case 7:
                        to_editform();
                        break;
                    case 8:
                        to_request();
                        break;
                    case 9:
                        to_upload_img();
                        break;
                    case 10:
                        to_status_class();
                        break;
                    case 11:
                        to_catselect();
                        break;
                    case 12:
                        to_newacc();
                        break;
                    case 13:
                        to_details();
                        break;
                    case 14:
                        to_newacc();
                        break;
                    case 15:
                        dialog_to_exit("DISCARD YOUR WORK ?", 1); // to back viewer;
                        break;
                    case 16:
                        dialog_to_exit("DISCARD YOUR WORK ?", 2);; // for request back to menu from cat;
                        break;
                    case 17:
                        to_get_empcode();
                        break;
                }
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        srf_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = srf_user_id.getText().toString();
                String pass = srf_user_pass.getText().toString();

                if (isEmpty(user) || isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "PLEASE FILL EMPTY TEXT BOXES", Toast.LENGTH_SHORT).show();
                    log_in_prog.setVisibility(View.GONE);
                } else {
                    srf_login.setEnabled(false);
                    log_in_prog.setVisibility(View.VISIBLE);
                    new getlogin_method(MainActivity.this).execute(Domain.concat("check_login/"+user+"/" + md5(pass.trim()).trim()));
                    setRegistration(false);
                }
            }

        });

        srf_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_to_exit("DISCARD YOUR WORK ?", 2);;
            }
        });

        req_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_catselect();
            }
        });

        req_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isEmpty(editreq.getText().toString())) {
                    to_upload_img();
                    if (img_locList != null) {
                        addsrfimages_adapter();
                    }
                } else if (isEmpty(editreq.getText().toString())) {
                    dialog("THE REQUEST IS EMPTY");
                }


            }
        });

        con_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getMenutrigger() == true) {
                    to_upload_img();
                    if (img_locList != null) {
                        addsrfimages_adapter();
                    }
                } else if (getMenutrigger() == false) {
                    if (status_holder.trim().equals("8888")){
                        to_editform();
                    }else{
                        to_status_class();
                    }
                }

            }
        });

        con_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_to_exit("PROCEED AND CONTINUE?", 4);

            }
        });


        back_add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_menuform();
            }
        });

        edit_srfback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_to_exit("DISCARD YOUR WORK ?", 1);

            }
        });

        edit_srfconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(edit_srf.getText().toString())) {
                    setStatus_trigger(true);
                    if (status_holder.trim().equals("8888")){
                        to_details();
                    }else{
                        to_status_class();
                    }

                } else if (isEmpty(edit_srf.getText().toString())) {
                    dialog("THE ACTION IS EMPTY");
                }

            }
        });

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adaptergetter();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //add images
        addimage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                upload_trigger = true;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            File photofile = null;
                            try {
                                photofile = createImageFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (photofile != null) {
                                Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.cf_srf.fileprovider", photofile);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                startActivityForResult(intent, CAMERA_REQUEST);
                            }
                        }
                    }

                });
            }
        });
        get_image_from_files.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                upload_trigger = false;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("image/*");
                        startActivityForResult(pickIntent, PICK_IMAGE);

                    }

                });
            }
        });
        back_req_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getMenutrigger() == true) {
                    to_request();
                } else if (getMenutrigger() == false) {
                    to_editform();
                }

            }
        });

        confirm_imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_details();
            }
        });

        back_imageviewer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getDetails_viewing_trigger().equals(false)) {
                    srf_login_form.setVisibility(View.GONE);
                    srf_station_form.setVisibility(View.GONE);
                    detailscon.setVisibility(View.GONE);
                    selectcat.setVisibility(View.GONE);
                    request.setVisibility(View.GONE);
                    menu_form.setVisibility(View.GONE);
                    srf_list_form.setVisibility(View.GONE);
                    srf_editform.setVisibility(View.GONE);
                    attach_img_form.setVisibility(View.VISIBLE);
                    image_viewer_form.setVisibility(View.GONE);
                    view_srf_details_form.setVisibility(View.GONE);


                } else if (getDetails_viewing_trigger().equals(true)) {
                    srf_login_form.setVisibility(View.GONE);
                    srf_station_form.setVisibility(View.GONE);
                    detailscon.setVisibility(View.GONE);
                    selectcat.setVisibility(View.GONE);
                    request.setVisibility(View.GONE);
                    menu_form.setVisibility(View.GONE);
                    srf_list_form.setVisibility(View.GONE);
                    srf_editform.setVisibility(View.GONE);
                    attach_img_form.setVisibility(View.GONE);
                    image_viewer_form.setVisibility(View.GONE);
                    view_srf_details_form.setVisibility(View.VISIBLE);
                }

            }
        });

        new_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to_new acc as first login
                to_get_empcode();
            }
        });
        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRegistration(false);
                to_login();
                hidebutton.setVisibility(View.GONE);
            }
        });
        back_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_srflist();
                if (img_locList != null) {
                    img_locList.removeAll(img_locList);
                } else {
                    img_locList = null;
                }

                setDetails_viewing_trigger(false);
            }
        });
        status_classback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getStatus_trigger() ==  false){
                    to_dept();
                }else if(getStatus_trigger() == true){
                    to_editform();
                }

            }
        });
        view_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_actions.setVisibility(View.GONE);
                to_view_actions();
            }
        });
        add_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_tech(MainActivity.this).execute(Domain.concat("get_tech/"+ removeAllDigit(srf_adapter.getUni_catcode().trim()).trim()));
                to_editform();

            }
        });
       back_to_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            to_viewing();
            }
        });
// SELECT DEPARTMENT
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDept("IT");
                setDept_desc("IT DEPARTMENT");
                to_status_class();
            }
        });
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDept("ME");
                setDept_desc("MECHANICAL");
                to_status_class();
            }
        });
        mt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDept("MT");
                setDept_desc("MAINTENANCE");
                to_status_class();
            }
        });
        cat_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_station();
            }
        });

        logout_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_to_exit("YOUR WORK WILL BE DISCARDED, LOG OUT NOW?", 3);
            }
        });

// menu action bar show ---------------------------------------------------------------------------------------------------
        // close menu anywhere ----------------------------------------------------------------------------------------------

        PARENT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav_closer();
            }
        });
        actmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionbartrigger.equals(false)){
                    nav_opener();
                }else{
                    nav_closer();
                }

            }
        });
        acthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_menuform();
                nav_closer();
                acthome.setEnabled(false);
                srf_add.setEnabled(true);
                srf_edit.setEnabled(true);

                acthome.setBackgroundResource(R.color.white);
                srf_add.setBackgroundResource(R.color.cf);
                srf_edit.setBackgroundResource(R.color.cf);
                acthome.setTextColor(Color.BLACK);
                srf_add.setTextColor(Color.WHITE);
                srf_edit.setTextColor(Color.WHITE);
                acttitle.setText("SERVICE REQUEST FORM");
            }
        });
        srf_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav_closer();
                menutrigger = true;
                to_station();
                acthome.setEnabled(true);
                srf_add.setEnabled(false);
                srf_edit.setEnabled(true);
                acthome.setBackgroundResource(R.color.cf);
                srf_add.setBackgroundResource(R.color.white);
                srf_edit.setBackgroundResource(R.color.cf);
                acthome.setTextColor(Color.WHITE);
                srf_add.setTextColor(Color.BLACK);
                srf_edit.setTextColor(Color.WHITE);
                acttitle.setText("ADD SRF");
            }
        });
        srf_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav_closer();
                menutrigger = false;
                to_station();
                acthome.setEnabled(true);
                srf_add.setEnabled(true);
                srf_edit.setEnabled(false);
                acthome.setBackgroundResource(R.color.cf);
                srf_add.setBackgroundResource(R.color.cf);
                srf_edit.setBackgroundResource(R.color.white);
                acthome.setTextColor(Color.WHITE);
                srf_add.setTextColor(Color.WHITE);
                srf_edit.setTextColor(Color.BLACK);
                acttitle.setText("VIEW SRF");
            }
        });
// menu action bar show ---------------------------------------------------------------------------------------------------
// for registration

        emp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_login();
            }
        });
        emp_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emp = emp_number.getText().toString();
                if (isEmpty(emp)){
                    dialog("PLEASE ENTER YOUR EMPLOYEE NUMBER ");
                }else{
                    new get_techinfo(MainActivity.this).execute(Domain.concat("get_emp/"+emp.trim()));
                }

            }
        });
        add_user_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_get_empcode();
                hidebutton.setVisibility(View.VISIBLE);
            }
        });
        add_user_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern =  new_username.getText().toString();
                String newpass = new_password.getText().toString();
                String conpass = con_password.getText().toString();

                if (isEmpty(usern) || isEmpty(newpass) || isEmpty(conpass)) {
                    if (conpass.equals(newpass)){
                        new add_acc(MainActivity.this).execute(Domain.concat("add_new_user/"+regemp.trim()+"/"+regfname.trim()+"/"+reglname.trim()+"/"+usern+"/"+ newpass ));
                    }else{
                        dialog("TWO PASSWORDS DOES NOT MATCH, PLEASE CHECK AND RETYPE");
                    }
                }else{
                    dialog("PLEASE FILL EMPTY TEXT BOXES");
                }


            }
        });

    }


    public void checkPermissions(String[] permissionRequests) {
        final ArrayList<String> permissionRequestList = new ArrayList<String>();
        for (final String request : permissionRequests) {
            if (ContextCompat.checkSelfPermission(this, request) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, request)) {
                    permissionRequestList.add(request);
                } else {
                    permissionRequestList.add(request);
                }
            }
        }
        if (!permissionRequestList.isEmpty()) {
            final String[] results = new String[permissionRequestList.size()];
            permissionRequestList.toArray(results);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("notification....");
            String msg = "PLEASE GRANT PERMISSION TO ACCESS EXTERNAL STORAGE";
            for (String str : results)
                msg += ("\n- " + str);
            builder.setMessage(msg);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this, results, MY_CAMERA_PERMISSION_CODE);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_CAMERA_PERMISSION_CODE: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, permissions[i] + "permision granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, permissions[i] + "permision denied", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (getMenutrigger() == true) {
                new upimagename(MainActivity.this).execute(Domain.concat("addfile/" + station_adapter.getUni_stncode().trim()));
            }
        } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String path = ImageFilePath.getPath(MainActivity.this, selectedImage);
            currentPhotoPath = path;
            if (getMenutrigger() == true) {
                    new upimagename(MainActivity.this).execute(Domain.concat("addfile/" + station_adapter.getUni_stncode().trim()));

            }
        }
    }

    void filter(String text) {
        ArrayList<station> temp = new ArrayList<>();
        for (station items : stnList) {
            if (items.getStn_name().contains(text.toUpperCase(Locale.ROOT))) {
                temp.add(items);
            }
        }
        station_adapter stn = new station_adapter();
        stn.filterList(temp);
    }

    public void upload() {

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("image/jpg");
            RequestBody body = RequestBody.create(mediaType, new File(currentPhotoPath));
            Request request = new Request.Builder()
                    .url(Domain+"FileUpload")
                    .method("POST", body)
                    .addHeader("Content-Type", "image/jpg")
                    .build();
            Response response = client.newCall(request).execute();
            Toast.makeText(MainActivity.this, response.body().toString().trim(), Toast.LENGTH_LONG).show();
            if (upload_trigger.equals(true)) {
                File file = new File(currentPhotoPath);
                file.delete();
            }

            runOnUiThread(new Runnable() {
                public void run() {
                    if (getMenutrigger() == true) {

                            new get_imagelist(MainActivity.this).execute(Domain.concat("imageret/" + station_adapter.getUni_stncode() + "/" + img_stn_code.trim()));// basic user


                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Boolean isEmpty(String strValue) {
        return strValue == null || strValue.trim().equals((""));
    }

    // radio button

    public void to_newacc() {
        call_back = 17;
        idle_trigger = false;
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        add_androidID.setVisibility(View.VISIBLE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

    } //12

    public void to_station() {
        statrec.setVisibility(View.GONE);
        actmenu.setVisibility(View.VISIBLE);
        search_prog.setVisibility(View.VISIBLE);
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.VISIBLE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);


           if( getMenutrigger().equals(false)){
               new getstation_method(MainActivity.this).execute(Domain.concat("getstation_method/" + user.getOps_area().trim()));
               iden_dept.setText(Html.fromHtml("WITH PENDING : " +
                       "<font color='#1100FF'> (IT) </font>" +
                       "<font color='#FF9F12'> (ME) </font>" +
                       "<font color='#FF0000'> (MT) </font>"), TextView.BufferType.SPANNABLE);
               call_back = 2;
               iden_dept.setVisibility(View.VISIBLE);

               idle_trigger = true;
           }else{
               new getstation_method(MainActivity.this).execute(Domain.concat("getstation_method/8888"));
               iden_dept.setVisibility(View.GONE);
               call_back = 2;
               idle_trigger = true;
           }


    }//1
    public void to_dept(){
        actmenu.setVisibility(View.VISIBLE);
        call_back = 1;
        idle_trigger = true;
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        select_cat.setVisibility(View.VISIBLE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

    } //3
    public void to_status_class() {
        actmenu.setVisibility(View.VISIBLE);
        idle_trigger = true;

            if(getStatus_trigger().equals(false)){
                call_back = 3;
                new getstatus_class(MainActivity.this).execute(Domain.concat("status/"+station_adapter.getUni_stncode().trim()+"/"+getDept().trim()));

                if (status_list != null) {
                    if (status_list.size() != 0) {
                        status_header.setText("SELECT STATUS TO VIEW");
                    } else {
                        status_header.setText("NO DATA");
                    }
                }else{
                    status_header.setText("NO DATA");
                }
            }else{
                call_back = 7;
                status_header.setText("SELECT STATUS TO BE APPLIED");
                new getstatus_class(MainActivity.this).execute(Domain.concat("status/0000/0000"));
            }

        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.VISIBLE);
        actions_for_srf.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);


    }// 10
    public void to_login() {
        menulayout.setVisibility(View.GONE);
        getSupportActionBar().hide();
        actmenu.setVisibility(View.GONE);
        call_back = 9999;
        idle_trigger = false;
        srf_login_form.setVisibility(View.VISIBLE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        user.setFirstname(null);
        user.setLastname(null);
        user.setUsername(null);
        user.setOps_area(null);
        user.setEmpcode(null);
        user.setEmpdept(null);
        user.setAdmin(null);
        user.setApprove(null);
        registration = false;
        select_cat.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);



    } // 8888

    public void to_details() {
        actmenu.setVisibility(View.VISIBLE);
        image_viewer_form.setVisibility(View.GONE);
        idle_trigger = true;
        req.setTextSize(18);
        if (getMenutrigger() == true) {
            call_back = 9;
            srf_login_form.setVisibility(View.GONE);
            attach_img_form.setVisibility(View.GONE);
            srf_station_form.setVisibility(View.GONE);
            detailscon.setVisibility(View.VISIBLE);
            selectcat.setVisibility(View.GONE);
            request.setVisibility(View.GONE);
            menu_form.setVisibility(View.GONE);
            srf_list_form.setVisibility(View.GONE);
            srf_editform.setVisibility(View.GONE);
            add_androidID.setVisibility(View.GONE);
            view_srf_details_form.setVisibility(View.GONE);
            actions_for_srf.setVisibility(View.GONE);
            status_class_form.setVisibility(View.GONE);
            attach_textdisplay.setVisibility(View.VISIBLE);
            image_viewer_form.setVisibility(View.GONE);
            select_cat.setVisibility(View.GONE);
            for_approval.setVisibility(View.GONE);
            add_androidID.setVisibility(View.GONE);
            get_emp_code_layout.setVisibility(View.GONE);

            String srf_status;

            srf_status = "PENDING";

            // user trigger

                user_check.setText(Html.fromHtml("USERNAME: <font color='blue'>"+user.getFirstname().trim()+"</font>"), TextView.BufferType.SPANNABLE);
                stn_check.setText(Html.fromHtml("STATION NAME: <font color='blue'>"+station_adapter.getUni_stnname().trim() + " (" + station_adapter.getUni_stncode().trim() + ") "+"</font>"), TextView.BufferType.SPANNABLE);
                cat.setText(Html.fromHtml("CATEGORY: <font color='blue'>"+cat_adapter.getUni_catname() + " (" + cat_adapter.getUni_catcode() + ") "+"</font>" + "<br><br>STATUS:  <font color='blue'>"+srf_status+ ") "+"</font>"), TextView.BufferType.SPANNABLE);
                req.setText(Html.fromHtml("REQUEST: <br><br> <font color='blue'>"+editreq.getText()+"</font><br>" ), TextView.BufferType.SPANNABLE);

            noimage_attach.setVisibility(View.VISIBLE);
            review_images.setVisibility(View.GONE);
            if (img_locList != null) {

                if (img_locList.isEmpty()) {
                    noimage_attach.setVisibility(View.VISIBLE);
                    review_images.setVisibility(View.GONE);
                } else if (!img_locList.isEmpty()) {
                    review_images.setVisibility(View.VISIBLE);
                    noimage_attach.setVisibility(View.GONE);
                    review_images.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    review_images.setLayoutManager(layoutManager);
                    reviewadapter = new reusable_adapter(MainActivity.this, img_locList);
                    reviewadapter.notifyDataSetChanged();
                    review_images.setAdapter(reviewadapter);
                }

            }

        } else if (getMenutrigger() == false) {
            if (status_holder.trim().equals("8888")){
                call_back = 7;
            }else{
                call_back = 10;
            }
            srf_login_form.setVisibility(View.GONE);
            attach_img_form.setVisibility(View.GONE);
            srf_station_form.setVisibility(View.GONE);
            detailscon.setVisibility(View.VISIBLE);
            selectcat.setVisibility(View.GONE);
            request.setVisibility(View.GONE);
            menu_form.setVisibility(View.GONE);
            srf_list_form.setVisibility(View.GONE);
            srf_editform.setVisibility(View.GONE);
            add_androidID.setVisibility(View.GONE);
            actions_for_srf.setVisibility(View.GONE);
            status_class_form.setVisibility(View.GONE);
            attach_textdisplay.setVisibility(View.GONE);
            select_cat.setVisibility(View.VISIBLE);
            select_cat.setVisibility(View.GONE);
            logout_float.setVisibility(View.VISIBLE);
            user_check.setText(Html.fromHtml("SRF NO : <font color='blue'>" + srf_adapter.getUni_srfcode() +"</font> <br>"+
                    "ENCODED BY: <font color='blue'>" + srf_adapter.getUni_date().trim() + "<br></font>" +
                    "ENCODED BY: <font color='blue'>" + srf_adapter.getUni_user().trim() + "<br></font>" +
                    "STATION NAME: <font color='blue'>" + srf_adapter.getUni_stn().trim() + " (" + srf_adapter.getUni_stncode().trim() + ") " + "<br></font>" +
                    "CATERGORY: <font color='blue'>" + srf_adapter.getUni_catdesc().trim() + " (" + srf_adapter.getUni_catcode().trim() + ") " + "<br></font>" +
                    "PREVIOUS STATUS: <font color='blue'>" + srf_adapter.getUni_status().trim() + "<br></font>"+
                    "REQUEST:<br><font color='red'>"+srf_adapter.getUni_problem()+"</font>"

            ), TextView.BufferType.SPANNABLE);
            String tech_replace = null;
            if (technician_adapter.getEmpname()!= null ){
                tech_replace = "<br>"+technician_adapter.getEmpname().trim();
                if(technician_adapter.getEmpname().trim().equals("") ){
                    tech_replace = "NO DATA";
                }
            }else{
                tech_replace = "NO DATA";
            }
            stn_check.setText(Html.fromHtml(
                    "<br>TO UPDATE BY: <font color='blue'>"+user.getFirstname().trim()+"</font><br>"+
                    "<br>UPDATE STATUS TO: <font color='blue'>" + status_class_adapter.getStatus_desc()+ "</font><br>"+
                    "<br>TECHNICIAN: <font color='blue'>"+tech_replace+"</font>"), TextView.BufferType.SPANNABLE);
            cat.setText("ACTION TAKEN:" );
            req.setTextColor(Color.parseColor("#FF0000"));

            req.setText(edit_srf.getText()+"\n");
            MainActivity.setTriger(false);
            noimage_attach.setVisibility(View.GONE);
            review_images.setVisibility(View.GONE);
        }
    } // 13

    public void to_catselect()
    {
        cat_branch_notifier.setText("SELECT SRF CATEGORY\nBRANCH: " + station_adapter.getUni_stnname().trim());
        actmenu.setVisibility(View.VISIBLE);
        call_back = 16;
        new getcat_method(MainActivity.this).execute(Domain + "cat_method");
        idle_trigger = true;
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.VISIBLE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);


    } // 11

    public void to_request() {
        actmenu.setVisibility(View.VISIBLE);
        call_back = 11;
        idle_trigger = true;
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.VISIBLE);
        menu_form.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);


            headcheck.setText("USERNAME: " + user.getFirstname().trim() + "\n" + "STATION NAME: " + station_adapter.getUni_stnname().trim() + " (" + station_adapter.getUni_stncode().trim() + ") " + "\n" + "CATERGORY: " + cat_adapter.getUni_catname() + " (" + cat_adapter.getUni_catcode() + ") ");

    } // 8

    public void to_srflist() {
        actmenu.setVisibility(View.VISIBLE);
        call_back = 10;
        idle_trigger = true;
        srfrec.setVisibility(View.GONE);
        no_records.setVisibility(View.GONE);
        srflist_branch_notifier.setText(station_adapter.getUni_stnname().trim() + "'S SERVICE REQUEST FORMS\n" + getStatus_holder_name().trim()+"("+getDept_desc()+")");
        srfList = null;
        new getretrieve_method(MainActivity.this).execute(Domain.concat("retrivestnsrf/" + station_adapter.getUni_stncode().trim()+"/"+getStatus_holder().trim()).trim()+"/"+getDept().trim());

        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.VISIBLE);
        srf_editform.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

    }// 4

    public void to_menuform() {
        new pie_data_getter(MainActivity.this).execute(Domain.concat("totals"));
        getSupportActionBar().show();
        menulayout.setVisibility(View.GONE);
        actmenu.setBackgroundResource(R.drawable.new_menu);
        acthome.setEnabled(false);
        srf_add.setEnabled(true);
        srf_edit.setEnabled(true);

        acthome.setBackgroundResource(R.color.white);
        srf_add.setBackgroundResource(R.color.cf);
        srf_edit.setBackgroundResource(R.color.cf);
        acthome.setTextColor(Color.BLACK);
        srf_add.setTextColor(Color.WHITE);
        srf_edit.setTextColor(Color.WHITE);
        acttitle.setText("SERVICE REQUEST FORM");
        actmenu.setVisibility(View.VISIBLE);
        call_back = 0;
        idle_trigger = true;
        status_trigger = false;
        srfList = null;
        if (img_locList != null) {
            img_locList.removeAll(img_locList);
        } else {
            img_locList = null;
        }


        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        menu_form.setVisibility(View.VISIBLE);
        srf_list_form.setVisibility(View.GONE);
        user_check.setVisibility(View.VISIBLE);
        stn_check.setVisibility(View.VISIBLE);
        cat.setVisibility(View.VISIBLE);
        req.setVisibility(View.VISIBLE);
        con_back.setVisibility(View.VISIBLE);
        con_details.setVisibility(View.VISIBLE);
        back_add_menu.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_d.setVisibility(View.VISIBLE);
        hidebutton.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);


    } // 2

    public void to_editform() {
        actmenu.setVisibility(View.VISIBLE);
        call_back = 15;
        idle_trigger = false;
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.VISIBLE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

        editheader.setTextColor(Color.parseColor("#0000FF"));
        editheader.setText("SRF NO : " + srf_adapter.getUni_srfcode() + "\nENCODED BY: " + srf_adapter.getUni_date().trim() + "\n" + "ENCODED BY: " + srf_adapter.getUni_user().trim() + "\n" + "STATION NAME: " + srf_adapter.getUni_stn().trim() + " (" + srf_adapter.getUni_stncode().trim() + ") " + "\n" + "CATERGORY: " + srf_adapter.getUni_catdesc().trim() + " (" + srf_adapter.getUni_catcode().trim() + ") " + "\n" + "CURRENT STATUS: " + srf_adapter.getUni_status().trim() + "\n"+ "REQUEST:\n\n"+srf_adapter.getUni_problem());


    }// 7

    public void to_upload_img() {
        image_branch_notifier.setText("ATTACH IMAGE/S" + "\nBRANCH: " + station_adapter.getUni_stnname().trim() + "\n note: no image attachments? click confirm to continue");
        actmenu.setVisibility(View.VISIBLE);
        call_back = 8;
        idle_trigger = false;
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.VISIBLE);
        imagelist_imgform.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

    }// 9

    public void to_viewing() {
        actmenu.setVisibility(View.VISIBLE);
        call_back = 4;
        idle_trigger = false;
        setDetails_viewing_trigger(true);
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.VISIBLE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        view_action.setVisibility(View.VISIBLE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

        edit_srf.setText("");
        String att = "";
        String content;
        if (srf_adapter.getUni_file().trim().equals("FALSE")) {
            att = "NO ATTACHMENTS";
            view_imagelist.setVisibility(View.GONE);

        } else if (srf_adapter.getUni_file().trim().equals("TRUE")) {
            att = "WITH ATTACHMENTS";
            view_imagelist.setVisibility(View.VISIBLE);

            new view_images(MainActivity.this).execute(Domain + "imageret/" + srf_adapter.getUni_stncode().trim() + "/" + srf_adapter.getUni_srfcode().trim());
        }
        content = "DATE:  <font color='#005eb8'>" + srf_adapter.getUni_date().trim() +"</font>"+
                "<br>SRF NO:  <font color='#005eb8'>" + srf_adapter.getUni_srfcode().trim() +"</font>"+
                "<br>ENCODED BY:  <font color='#005eb8'>" + srf_adapter.getUni_user().trim() +"</font>"+
                "<br>BRANCH:  <font color='#005eb8'>" + srf_adapter.getUni_stn().trim() + " (" + srf_adapter.getUni_stncode().trim() + ") " +"</font>"+
                "<br>CATEGORY:  <font color='#005eb8'>" + srf_adapter.getUni_catdesc().trim() + " (" + srf_adapter.getUni_catcode().trim() + ") " +"</font>"+
                "<br><br>REPORTED REQUEST:  <br><font color='red'>" + srf_adapter.getUni_problem().trim() +"</font>"+
                "<br><br>SRF STATUS:  <font color='#005eb8'>" + srf_adapter.getUni_status().trim() +"</font>"+
                "<br>LAST UPDATE BY:  <font color='#005eb8'>"+srf_adapter.getUni_srfupdateby().trim()+"</font>"+
                "<br>LAST UPDATE DATE:  <font color='#005eb8'>"+srf_adapter.getUni_srfupdate_date().trim()+"</font>"+
                "<br>NUMBER OF ACTION(S) TAKEN:  <font color='#005eb8'>" +srf_adapter.getUni_srfaction().trim()+"</font>"+
                "<br>CLOSED DATE:  <font color='red'>" + srf_adapter.getUni_srfclosed().trim()+"</font>"+
                "<br>IMAGE ATTACHMENT/S:  <font color='#005eb8'>" + att+"</font>";
        view_srf_details.setText(Html.fromHtml(content), TextView.BufferType.SPANNABLE);
        if (srf_adapter.getUni_srfaction().trim().equals("NO ACTIONS YET") ||srf_adapter.getUni_srfaction().trim() == "NO ACTIONS YET"){
            view_action.setVisibility(View.GONE);
        }else{
            view_action.setVisibility(View.VISIBLE);
        }



        if(getStatus_holder().equals("0001")) {
            add_action.setVisibility(View.GONE);
        }else if (getStatus_holder().trim().equals("8888")){
            if (!srf_adapter.getUni_srfclosed().trim().equals("NOT CLOSED YET")){
                add_action.setVisibility(View.GONE);
            }else{
                add_action.setVisibility(View.VISIBLE);
            }
        }else{
            add_action.setVisibility(View.VISIBLE);
        }




    }// 5

    public void to_view_actions() {
        actmenu.setVisibility(View.VISIBLE);
        call_back = 5;
        idle_trigger = false;
        new getaction_method(MainActivity.this).execute(Domain + "get_action_per_srf/"+srf_adapter.getUni_stncode().trim()+"/"+srf_adapter.getUni_srfcode().trim());
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.VISIBLE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.VISIBLE);
        select_cat.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

    }// 6

    public void to_be_approved(){
        actmenu.setVisibility(View.GONE);
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        for_approval.setVisibility(View.VISIBLE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.GONE);

    }
    public void to_get_empcode(){
        call_back = 8888;
        actmenu.setVisibility(View.GONE);
        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        view_srf_details_form.setVisibility(View.GONE);
        status_class_form.setVisibility(View.GONE);
        actions_for_srf.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.GONE);
        select_cat.setVisibility(View.GONE);
        for_approval.setVisibility(View.GONE);
        add_androidID.setVisibility(View.GONE);
        get_emp_code_layout.setVisibility(View.VISIBLE);

    }

    // end for layout init

    public void adaptergetter() {


        statrec.setVisibility(View.VISIBLE);
        statrec.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        statrec.setLayoutManager(layoutManager);
        searchadapter = new station_adapter(MainActivity.this, stnList);
        statrec.setAdapter(searchadapter);
    }

    public void addsrfimages_adapter() {
        imagelist_imgform.setVisibility(View.GONE);
        if (imagelist_imgform != null) {
            if (img_locList.size() != 0) {
                imagelist_imgform.setVisibility(View.VISIBLE);
                imagelist_imgform.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                imagelist_imgform.setLayoutManager(layoutManager);
                imageadapter = new img_loc_adapter(MainActivity.this, img_locList);
                imageadapter.notifyDataSetChanged();
                imagelist_imgform.setAdapter(imageadapter);
            } else if (img_locList.size() == 0) {
                imagelist_imgform.setVisibility(View.GONE);
            }
        }

    }

    public void image_refresh() {

        if (getMenutrigger() == true) {
            new get_imagelist(MainActivity.this).execute(Domain + "imageret/" + station_adapter.getUni_stncode().trim() + "/" + img_stn_code.trim());

        } else if (getMenutrigger() == false) {
            new get_imagelist(MainActivity.this).execute(Domain + "imageret/" + srf_adapter.getUni_stncode().trim() + "/" + srf_adapter.getUni_srfcode().trim());
        }
        addsrfimages_adapter();
    }

    public void emptysearchbar() {
        searchbar.setText("");
    }

    public void del_image() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (getMenutrigger() == true) {
                        new deleteimages(MainActivity.this).execute(Domain + "FileDelete/" + station_adapter.getUni_stncode().trim() + "/" + img_loc_adapter.getFile_name().trim());
                        new get_imagelist(MainActivity.this).execute(Domain + "imageret/" + station_adapter.getUni_stncode().trim() + "/" + img_stn_code.trim());

                } else if (getMenutrigger() == false) {
                    new deleteimages(MainActivity.this).execute(Domain + "FileDelete/" + srf_adapter.getUni_stncode().trim() + "/" + img_loc_adapter.getFile_name().trim());
                    new get_imagelist(MainActivity.this).execute(Domain + "imageret/" + srf_adapter.getUni_stncode().trim() + "/" + srf_adapter.getUni_srfcode().trim());
                }


            }
        });
    }

    public void dialog_imageviewer() {

        if (getMenutrigger() == true) {
            Bitmap pathName = getBitmapFromURL(Domain + "GetImage/" + img_loc_adapter.getUni_stn().trim() + "/" + img_loc_adapter.getFile_name().trim());
            image_viewer_img.setImageBitmap(pathName);
        } else if (getMenutrigger() == false) {
            Bitmap pathName = getBitmapFromURL(Domain + "GetImage/" + img_loc_adapter.getUni_stn().trim() + "/" + img_loc_adapter.getFile_name().trim());
            image_viewer_img.setImageBitmap(pathName);
        }

        srf_login_form.setVisibility(View.GONE);
        srf_station_form.setVisibility(View.GONE);
        detailscon.setVisibility(View.GONE);
        selectcat.setVisibility(View.GONE);
        request.setVisibility(View.GONE);
        menu_form.setVisibility(View.GONE);
        srf_list_form.setVisibility(View.GONE);
        srf_editform.setVisibility(View.GONE);
        attach_img_form.setVisibility(View.GONE);
        image_viewer_form.setVisibility(View.VISIBLE);
        view_srf_details_form.setVisibility(View.GONE);
    }

    public Bitmap getBitmapFromURL(String src) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            input.close();
            connection.disconnect();
            return myBitmap;
        } catch (IOException e) {

            return null;
        }
    }

    public void dialog(String msg) {

        builder1.setTitle("SRF notification");
        builder1.setMessage(msg);
        builder1.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder1.create();
        dialog.show();
    }

    public void dialog_to_exit(String msg, int holder) {

        builder2.setTitle("SRF notification");
        builder2.setMessage(msg);
        builder2.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switch (holder){
                    case 1:
                        to_viewing();
                        setStatus_trigger(false);
                        edit_srf.setText("");
                        break;
                    case 2:
                        to_station();
                        editreq.setText("");
                        break;

                    case 3:
                        to_login();
                        srf_user_id.setText("");
                        srf_user_pass.setText("");
                        Toast.makeText(MainActivity.this, "LOG OUT SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                        stnList = null;

                        catList = null;

                        srfList = null;

                        img_locList = null;
                        break;
                    case 4:
                        prog_details.setVisibility(View.VISIBLE);
                        con_details.setEnabled(false);
                        if (getMenutrigger() == true) {
                            //addd

                                new getreq_method(MainActivity.this).execute(Domain.concat("addservicerequest/" + station_adapter.getUni_stncode() + "/" + cat_adapter.getUni_catcode() + "/" + cat_adapter.getUni_catname() + "/" + editreq.getText() + "/" +user.getFirstname()+"/9999".trim()));

                        } else {
                            /// action
                            new add_action_method(MainActivity.this).execute(Domain.concat("add_action/"+srf_adapter.getUni_stncode().trim()+"/"+srf_adapter.getUni_srfcode().trim()+"/"+edit_srf.getText().toString().trim()+"/"+user.getFirstname().trim()+"/"+technician_adapter.getEmpcode()+" "+"/"+status_class_adapter.getStatus_code().trim()));
                        }
                        break;
                }
            }
        });
        builder2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder2.create();
        dialog.show();
    }
    public void dialog_idle(String msg, boolean status) {

        builder3.setTitle("SRF notification");
        builder3.setMessage(msg);
        if (status){
            builder3.setPositiveButton("RE LOGIN", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    to_login();
                    srf_user_id.setText("");
                    srf_user_pass.setText("");
                    Toast.makeText(MainActivity.this, "LOG OUT SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                    stnList = null;

                    catList = null;

                    srfList = null;

                    img_locList = null;
                }
            });
        }else {
            builder3.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                finish();
                }
            });
        }


        AlertDialog dialog = builder3.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // get file path alway close to avoid confusion
    public static class ImageFilePath {
        @SuppressLint("NewApi")
        public static String getPath(final Context context, final Uri uri) {
            //check for KITKAT or above
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }

                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }

                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }

            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }

        public static String getDataColumn(Context context, Uri uri, String selection,
                                           String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        public static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        public static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        public static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        public static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        }
    }

    class getstation_method extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public getstation_method(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();
                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {

                    stnList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        stnList.add(new station(object.getString("SRF_stnCode"), object.getString("SRF_station_name"), object.getString("SRF_penIT"), object.getString("SRF_penME"), object.getString("SRF_penMT"), object.getString("SRF_penTotal")));
                    }
                    search_prog.setVisibility(View.GONE);
                    adaptergetter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }

    class getcat_method extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public getcat_method(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {

                    catrec.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    catrec.setLayoutManager(layoutManager);
                    catList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String cat_code = object.getString("Catcode");
                        String cat_name = object.getString("Catdesc");
                        catList.add(new cat(cat_code.trim(), cat_name.trim()));
                    }
                    mAdapter = new cat_adapter(MainActivity.this, catList);
                    catrec.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, "CONNECTION LOST", Toast.LENGTH_LONG).show();
            }
        }
    }
    class getstatus_class extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public getstatus_class(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    status_classlist.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    status_classlist.setLayoutManager(layoutManager);
                    status_list = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String status_code = object.getString("Status_code");
                        String status_name = object.getString("Status_desc");
                        String number = object.getString("Number");
                        status_list.add(new status_class(status_code.trim(), status_name.trim(),number));
                    }
                    status_adapter = new status_class_adapter(MainActivity.this, status_list);
                    status_classlist.setAdapter(status_adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                dialog("CAN'T CONNECT TO SERVER");
            }
        }
    }

    class getlogin_method extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public getlogin_method(Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {

            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }else{
                    status = "SS";
                }
                conn.disconnect();
            } catch (Exception ex) {
                status = "SS";
                ex.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && !result.equals("SS")) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        user.setFirstname(object.getString("Firstname").trim());
                        user.setLastname(object.getString("Lastname").trim());
                        user.setUsername(object.getString("Username").trim());
                        user.setOps_area(object.getString("Ops_area").trim());
                        user.setEmpcode(object.getString("Empcode").trim());
                        user.setEmpdept(object.getString("Empdept").trim());
                        user.setAdmin(object.getString("Admin").trim());
                        user.setApprove(object.getString("Approve").trim());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                srf_login.setEnabled(true);
                log_in_prog.setVisibility(View.GONE);
                to_menuform();

            }else if(result != null && result.equals("SS")){
                dialog("CONNECTION LOST PLEASE RESTART THE APPLICATION AND CHECK YOUR INTERNET CONNECTION");
                srf_login.setEnabled(true);
                log_in_prog.setVisibility(View.GONE);
            } else {
                srf_user_id.setText("");
                srf_user_pass.setText("");
                srf_login.setEnabled(true);
                log_in_prog.setVisibility(View.GONE);
                dialog("WRONG PASSWORD OR USERNAME");
            }
        }
    }

    class getreq_method extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public getreq_method(Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                result = result.replaceAll("^\"|\"$", "");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                user_check.setVisibility(View.GONE);
                stn_check.setVisibility(View.GONE);
                cat.setVisibility(View.GONE);
                req.setVisibility(View.GONE);
                con_back.setVisibility(View.GONE);
                con_details.setVisibility(View.GONE);
                back_add_menu.setVisibility(View.VISIBLE);
                editreq.setText("");
                review_images.setVisibility(View.GONE);
                noimage_attach.setVisibility(View.GONE);
                img_locList = null;
                prog_details.setVisibility(View.GONE);
                attach_d.setVisibility(View.GONE);
                gg.setText(result + "\n" + "THANKYOU");
                con_details.setEnabled(true);
                call_back = 2;
            } else {
                con_details.setEnabled(true);
                dialog("ERROR OCCUR, PLEASE CHECK YOUR INTERNET CONNECTION AND CLICK CONFIRM AGAIN");
                prog_details.setVisibility(View.GONE);
            }
        }
    }

    class add_action_method extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public add_action_method (Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }
                conn.disconnect();
            } catch (Exception ex) {

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                result = result.replaceAll("^\"|\"$", "");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                user_check.setVisibility(View.GONE);
                stn_check.setVisibility(View.GONE);
                cat.setVisibility(View.GONE);
                req.setVisibility(View.GONE);
                con_back.setVisibility(View.GONE);
                con_details.setVisibility(View.GONE);
                back_add_menu.setVisibility(View.VISIBLE);
                edit_srf.setText("");
                review_images.setVisibility(View.GONE);
                noimage_attach.setVisibility(View.GONE);
                prog_details.setVisibility(View.GONE);
                attach_d.setVisibility(View.GONE);
                gg.setText(result + "\n" + "THANKYOU");
                con_details.setEnabled(true);
                call_back = 2;
            } else {
                con_details.setEnabled(true);
                dialog("ERROR OCCUR, PLEASE CHECK YOUR INTERNET CONNECTION AND CLICK CONFIRM AGAIN");
                prog_details.setVisibility(View.GONE);
            }
        }
    }


    // pause muna natin to medyo tricky eh
    class getretrieve_method extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public getretrieve_method(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {

                    srfrec.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    srfrec.setLayoutManager(layoutManager);
                    srfList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        srfList.add(new srf(object.getString("SRF_Stn"), object.getString("SRF_StnCode"), object.getString("SRF_No"), object.getString("SRF_Date"), object.getString("SRF_CatCode"), object.getString("SRF_CatDesc"), object.getString("SRF_Problem"), object.getString("SRF_User"), object.getString("SRF_Status"), object.getString("Images_status"), object.getString("Updated_by"),object.getString("Updated_date"),object.getString("Action"), object.getString("Closed_date")));
                    }
                    mAdapter = new srf_adapter(MainActivity.this, srfList);
                    srfrec.setAdapter(mAdapter);
                    srfrec.setVisibility(View.VISIBLE);
                    no_records.setVisibility(View.GONE);
                    if (srfList.size() == 0) {
                        srfrec.setVisibility(View.GONE);
                        no_records.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                dialog("CONNECTION LOST PLEASE RESTART THE APPLICATION AND CHECK YOUR INTERNET CONNECTION");
            }
        }
    }

    class upimagename extends AsyncTask<String, Void, String> {

        String status = null;
        Activity context;


        public upimagename(Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                result = result.replaceAll("^\"|\"$", "").trim();
                img_stn_code = result;
                Toast.makeText(MainActivity.this, "UPLOAD FILE FIRST PHASE DONE " + img_stn_code, Toast.LENGTH_LONG).show();
                upload();
            } else {
                Toast.makeText(MainActivity.this, "UPLOAD FILE FIRST PHASE FAILED", Toast.LENGTH_LONG).show();

            }
        }
    }

    class get_imagelist extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public get_imagelist(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    img_locList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String stn = object.getString("Stn");
                        String srfno = object.getString("Srfno");
                        String page = object.getString("Page");
                        String file = object.getString("Filename");
                        img_locList.add(new img_loc(stn.trim(), srfno.trim(), page.trim(), file.trim()));

                    }
                    addsrfimages_adapter();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            } else {
                dialog("UNABLE TO RETRIEVE IMAGES");

            }
        }
    }

    class deleteimages extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public deleteimages(Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                img_locList.removeAll(img_locList);
            } else {

            }
        }


    }

// ADD ACC
    class add_acc extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public add_acc(Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                result = result.replaceAll("^\"|\"$", "");
                if (result.trim().equals("TRUE")) {
                    Toast.makeText(MainActivity.this, "ACCOUNT REGISTERED, PLEASE TRY TO LOGIN YOUR NEW ACCOUNT", Toast.LENGTH_LONG).show();

                } else if (result.trim().equals("FALSE")) {
                    dialog("THIS USERNAME ALREADY EXIST");

                }

            } else {
                dialog("ERROR OCCUR PLEASE RESTART THE APP");


            }
        }
    }

   // GET TECHCODE

    class get_techinfo extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public get_techinfo(Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {

            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }else{
                    status = "SS";
                }
                conn.disconnect();
            } catch (Exception ex) {
                status = "SS";
                ex.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && !result.equals("SS")) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        regemp = object.getString("Empcode").trim();
                        regfname = object.getString("Empfname").trim();
                        reglname =  object.getString("Emplname").trim();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                emp_number.setText("");
                emp_next.setEnabled(true);
                acc_details.setText("EMPLOYEE NO: " + regemp);
                acc_firstname.setText(regfname);
                acc_surname.setText(reglname);
                to_newacc();

            }else if(result != null && result.equals("SS")){
                dialog("CONNECTION LOST PLEASE RESTART THE APPLICATION AND CHECK YOUR INTERNET CONNECTION");
                emp_next.setEnabled(true);
            } else {
                emp_number.setText("");
                dialog("UNKNOWN EMPLOYEE NO. \nOR EMPLOYEE NO. IS ALREADY BOUND TO AN ACCOUNT");
            }
        }
    }
    class view_images extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public view_images(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    img_locList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String stn = object.getString("Stn");
                        String srfno = object.getString("Srfno");
                        String page = object.getString("Page");
                        String file = object.getString("Filename");
                        img_locList.add(new img_loc(stn.trim(), srfno.trim(), page.trim(), file.trim()));
                    }

                    view_imagelist.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    view_imagelist.setLayoutManager(layoutManager);
                    viewer_adap = new img_loc_adapter(MainActivity.this, img_locList);
                    viewer_adap.notifyDataSetChanged();
                    view_imagelist.setAdapter(viewer_adap);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            } else {
                dialog("UNABLE TO RETRIEVE IMAGES");

            }
        }
    }

    class getaction_method extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public getaction_method(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    view_actions.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(MainActivity.this);
                    view_actions.setLayoutManager(layoutManager);
                    sraList= new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        sraList.add(new sra(object.getString("Sra_date"), object.getString("Sra_seq"), object.getString("Sra_action"),object.getString("Sra_userID"),object.getString("Sra_techID"),object.getString("Sra_Status")));
                    }
                    actionview_adapter = new sra_adapter(MainActivity.this, sraList);
                    view_actions.setAdapter(actionview_adapter);
                    view_actions.setVisibility(View.VISIBLE);
                    if (sraList.size() == 0) {
                        view_actions.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                dialog("CONNECTION LOST PLEASE RESTART THE APPLICATION AND CHECK YOUR INTERNET CONNECTION");
            }
        }
    }
    class get_tech extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public get_tech(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();
                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            }

            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    tech_list = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        tech_list.add(new technician(object.getString("Empcode"), object.getString("Techname")));
                    }
                    techadapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }

    }
    public void techadapter(){

        if (tech_list !=null){
            if(tech_list.size() ==0){
                tech_listview.setVisibility(View.GONE);
                techlist_label.setVisibility(View.GONE);
            }else{
                tech_listview.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                tech_listview.setLayoutManager(layoutManager);
                tech_list_adapter = new technician_adapter(MainActivity.this, tech_list);
                tech_list_adapter.notifyDataSetChanged();
                tech_listview.setAdapter(tech_list_adapter);
                tech_listview.setVisibility(View.VISIBLE);
                techlist_label.setVisibility(View.VISIBLE);
            }
        }else{
            tech_listview.setVisibility(View.GONE);
            techlist_label.setVisibility(View.GONE);
        }

    }
    public static String removeAllDigit(String str)
    {
        // Converting the given string
        // into a character array
        char[] charArray = str.toCharArray();
        String result = "";

        // Traverse the character array
        for (int i = 0; i < charArray.length; i++) {

            // Check if the specified character is not digit
            // then add this character into result variable
            if (!Character.isDigit(charArray[i])) {
                result = result + charArray[i];
            }
        }

        // Return result
        return result;
    }
    @Override
    public void onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }
    public void stopHandler() {
        idle_handler.removeCallbacks(idle_runnable);
    }
    public void startHandler() {
        idle_handler.postDelayed(idle_runnable, sec_for_idle*1000);
    }


    class update_checker extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public update_checker(Activity context) {
            this.context = context;

        }


        @Override
        protected String doInBackground(String... connUrl) {
            BufferedReader reader;
            try {

                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    line = reader.readLine();
                    status = line;

                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        versioncontrol= object.getString("versionCode").trim();
                        APP_UPDATE_SERVER_URL= object.getString("url").trim();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(versioncontrol != null){
                    if(versioncontrol.trim().equals(version)){

                    }else{
                        dialog("THERE IS NEWER VERSION \n version : "+ versioncontrol);
                        String url = APP_UPDATE_SERVER_URL.trim();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);;
                    }
                }

            } else {
                dialog("NO INTERNET CONNECTION");
            }
        }
    }

    // for total pie chart

    class pie_data_getter extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;

        public pie_data_getter(Activity context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... connUrl) {

            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type:", "application/json; charset=utf-8");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();

                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                } else {
                    status = "SS";

                }
                conn.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
                status = "SS";
            }
            return status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && !result.equals("SS")) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //it
                        pie_variables.setItPending(object.getString("IT_pen").trim());
                        pie_variables.setItOngoing(object.getString("IT_on").trim());
                        //me
                        pie_variables.setMePending(object.getString("ME_pen").trim());
                        pie_variables.setMeOngoing(object.getString("ME_on").trim());
                        //mt
                        pie_variables.setMtPending(object.getString("MT_pen").trim());
                        pie_variables.setMtOngoing(object.getString("MT_on").trim());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GRAPHData();
            } else {
                dialog("CONNECTION LOST PLEASE RESTART THE APPLICATION AND CHECK YOUR INTERNET CONNECTION");
                finish();
            }
        }
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(!hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }

    private void GRAPHData()
    {

        Legend legend = pieChart.getLegend();
        legend.setFormSize(10);
        legend.setDrawInside(false);
        legend.setStackSpace(100);
        legend.setWordWrapEnabled(true);


        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();


        pieEntries.add(new PieEntry(Float.parseFloat(pie_variables.getItPending()), "IT DEPT PENDING"));
        colors.add(Color.parseColor("#3590ae"));
        pieEntries.add(new PieEntry(Float.parseFloat(pie_variables.getItOngoing()), "IT DEPT ONGOING"));
        colors.add(Color.parseColor("#09e8d9"));

        pieEntries.add(new PieEntry(Float.parseFloat(pie_variables.getMePending()), "MECHANICAL PENDING"));
        colors.add(Color.parseColor("#ffae00"));
        pieEntries.add(new PieEntry(Float.parseFloat(pie_variables.getMeOngoing()), "MECHANICAL ONGOING"));
        colors.add(Color.parseColor("#fffb00"));

        pieEntries.add(new PieEntry(Float.parseFloat(pie_variables.getMtPending()), "MAINTENANCE PENDING" ));
        colors.add(Color.parseColor("#f44336"));
        pieEntries.add(new PieEntry(Float.parseFloat(pie_variables.getMtOngoing()), "MAINTENANCE ONGOING"));
        colors.add(Color.parseColor("#e809b0"));

        //collecting the entries with label name

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");


        pieDataSet.setValueFormatter(new ValueFormatter() {
        @Override
        public String getFormattedValue(float value) {
            return String.valueOf((int) Math.floor(value));
        }
    });

        // trialials


        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setHighlightEnabled(true);
        pieDataSet.setSliceSpace(0.5f);

        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        pieDataSet.setValueLinePart1OffsetPercentage(1f);
        pieChart.getDescription().setText("PENDING AND ONGOING");
        pieChart.setExtraBottomOffset(5f);
        pieChart.setEntryLabelTextSize(8f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setCenterText("SERVICE REQUEST FORM");
        //grouping the data set from entry to chartW
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    // MD5 HASHING
    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void nav_closer(){
        menulayout.setVisibility(View.GONE);
        actionbartrigger =  false;
        actmenu.setBackgroundResource(R.drawable.new_menu);
        PARENT.setVisibility(View.GONE);

    }
    public static void nav_opener(){
        menulayout.setVisibility(View.VISIBLE);
        actionbartrigger =  true;
        actmenu.setBackgroundResource(R.drawable.new_back);
        PARENT.setVisibility(View.VISIBLE);
    }


}
