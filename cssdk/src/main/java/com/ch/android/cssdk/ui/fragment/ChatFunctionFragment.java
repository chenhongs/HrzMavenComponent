package com.ch.android.cssdk.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ch.android.cssdk.R;
import com.ch.android.cssdk.base.BaseFragment;
import com.ch.android.cssdk.ui.activity.ImageGridActivity;
import com.ch.android.cssdk.ui.activity.KeFuChatActivity;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.util.PathUtil;


import java.io.File;


import static com.ch.android.cssdk.ui.activity.KeFuChatActivity.REQUEST_CODE_CAMERA;
import static com.ch.android.cssdk.ui.activity.KeFuChatActivity.REQUEST_CODE_LOCAL;
import static com.ch.android.cssdk.ui.activity.KeFuChatActivity.REQUEST_CODE_SELECT_VIDEO;


/**
 * @author pan
 */
public class ChatFunctionFragment extends BaseFragment implements View.OnClickListener{

    TextView chatFunctionPhoto;
    TextView chatFunctionPhotograph;
    TextView chatFunctionVideo;

    private View rootView;
    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE3 = 8;
    private File output;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_chat_function, container, false);
            chatFunctionPhoto=rootView.findViewById(R.id.chat_function_photo);
            chatFunctionPhotograph=rootView.findViewById(R.id.chat_function_photograph);
            chatFunctionVideo=rootView.findViewById(R.id.chat_function_video);
            chatFunctionPhoto.setOnClickListener(this);
            chatFunctionPhotograph.setOnClickListener(this);
            chatFunctionVideo.setOnClickListener(this);
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        //拍照
        if (i == R.id.chat_function_photograph) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE2);
            } else {
                selectPicFromCamera();
            }
        //照片
        } else if (i == R.id.chat_function_photo) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE2);
            } else {
                selectPicFromLocal();
            }
        //视频
        }else if(i == R.id.chat_function_video){
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE3);
            } else {
                selectVideoFromLocal();
            }
        }
    }

    /**
     * 照相获取图片
     */
    protected void selectPicFromCamera() {

        try{
            File cameraFile = new File(PathUtil.getInstance().getImagePath(), ChatClient.getInstance().currentUserName()
                    + System.currentTimeMillis() + ".jpg");
            KeFuChatActivity.cameraFilePath = cameraFile.getAbsolutePath();
            if (!cameraFile.getParentFile().exists()){
                cameraFile.getParentFile().mkdirs();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
            } else {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext().getApplicationContext(), getContext().getPackageName() +  ".fileprovider", cameraFile));
            }
            getActivity().startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 从图库获取图片
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        getActivity().startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * 从本地获取视频
     */
    private void selectVideoFromLocal() {
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
       // startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
        getActivity().startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPicFromCamera();
            } else {
                toastShow("请同意系统权限后继续");
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPicFromLocal();
            } else {
                toastShow("请同意系统权限后继续");
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE3){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectVideoFromLocal();
            } else {
                toastShow("请同意系统权限后继续");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
