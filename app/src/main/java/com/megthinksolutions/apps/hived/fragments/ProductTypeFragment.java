package com.megthinksolutions.apps.hived.fragments;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.util.IOUtils;
import com.android.internal.http.multipart.MultipartEntity;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.activity.MainActivity;
import com.megthinksolutions.apps.hived.activity.ProductActivity;
import com.megthinksolutions.apps.hived.database.AppDatabase;
import com.megthinksolutions.apps.hived.database.DataRepository;
import com.megthinksolutions.apps.hived.database.ProductListModel;
import com.megthinksolutions.apps.hived.databinding.ProductTypeFragmentBinding;
import com.megthinksolutions.apps.hived.models.ProductReviewModel;
import com.megthinksolutions.apps.hived.models.ProductReviewWithPreSignUrlModel;
import com.megthinksolutions.apps.hived.models.UploadFile;
import com.megthinksolutions.apps.hived.networking.ApiClient;
import com.megthinksolutions.apps.hived.networking.ApiInterface;
import com.megthinksolutions.apps.hived.networking.RequestFormatter;
import com.megthinksolutions.apps.hived.responseModel.ErrorResponse;
import com.megthinksolutions.apps.hived.utils.ConstantUrl;
import com.megthinksolutions.apps.hived.utils.FileUtils;
import com.megthinksolutions.apps.hived.utils.PrefManager;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;
import com.megthinksolutions.apps.hived.utils.Utils;
import com.megthinksolutions.apps.hived.viewmodel.ProductTypeViewModel;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.provider.Contacts.SettingsColumns.KEY;

public class ProductTypeFragment extends Fragment {
    private ProductTypeFragmentBinding binding;
    private ProductTypeViewModel mViewModel;
    String[] descriptionData = {"Product", "Product Review", "Review"};

    private static final int REQUEST_CODE_GALLERY1 = 9001;
    private static final int REQUEST_CODE_GALLERY2 = 9002;
    private static final int REQUEST_CODE_GALLERY3 = 9003;

    private static final int REQUEST_CODE_CAMERA1 = 901;
    private static final int REQUEST_CODE_CAMERA2 = 902;
    private static final int REQUEST_CODE_CAMERA3 = 903;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"
            , "android.permission.WRITE_EXTERNAL_STORAGE"};
    private Uri contentURI;
    private String fileUrl;
    private Bitmap bitmap;
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private String accessKey, secretKey, strIntent;

    private List<String> imageSendList = new ArrayList<>();
    private List<String> imageFilePathList = new ArrayList<>();


    private DataRepository dataRepository;
    private List<ProductListModel> productListModelList;
    private List<String> allProductLists = new ArrayList<>();
    private List<String> allBrandListSelectWiseLists = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        strIntent = intent.getStringExtra("addNewValue");
        Log.d("strIntent", strIntent);

      //   This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (strIntent.equals("ProfileFragment")){
                  //  getActivity().onBackPressed();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }else if (strIntent.equals("AddNewFragment")){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        accessKey = PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_access_token);
        secretKey = PreferenceUtils.getInstance().getString(R.string.server_client_secret);

        credentials = new BasicAWSCredentials("AKIA2WSH67LRCGFWPQOV", "0b3Su1RWU/L7W6JIY9Zw4P5loUbS45jUS96H46HX");
        s3Client = new AmazonS3Client(credentials);

    }

    public static ProductTypeFragment newInstance() {
        return new ProductTypeFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_product_activity);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.product_type_fragment, container, false);
        imageFilePathList.clear();

        dataRepository = DataRepository.getInstance(AppDatabase.getDatabase(getActivity()));
        productListModelList = dataRepository.getProductListUri();

        Log.d("productAllList", productListModelList.get(1).getPSelect() + " " + productListModelList.get(1).getPBrand() + " " + productListModelList.get(1).getPModel());

        allProductLists.clear();
        //allProductLists.add("Select Product");
        List<String> tempProduct = new ArrayList<>();
        tempProduct = dataRepository.getAllProductList();

        for (int a = 0; a < tempProduct.size(); a++) {
            allProductLists.add(tempProduct.get(a));
        }

        binding.stateProgressBar.setStateDescriptionData(descriptionData);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strIntent.equals("ProfileFragment")){
                    getActivity().onBackPressed();

                }else if (strIntent.equals("AddNewFragment")){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        ArrayAdapter<String> selectAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, allProductLists);
        binding.autoCompleteSelect.setAdapter(selectAdapter);

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(binding.autoCompleteBrand.getContext(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.brand_list));
        binding.autoCompleteBrand.setAdapter(brandAdapter);

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(binding.autoCompleteModel.getContext(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.model_list));
        binding.autoCompleteModel.setAdapter(modelAdapter);

//        List<String> abc = new ArrayList<>();
//        for (int i = 0; i < allBrandListSelectWiseLists.size(); i++) {
//            abc.add(allBrandListSelectWiseLists.get(i));
//        }


        binding.autoCompleteSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                allBrandListSelectWiseLists.clear();
                allBrandListSelectWiseLists.add("Brand");

                Log.d("brand_list_select_wise", "Brand: " + binding.autoCompleteSelect.getText().toString());
                Toast.makeText(getContext(), "Hello " + binding.autoCompleteSelect.getText().toString(), Toast.LENGTH_SHORT).show();

                if (position != 0) {

                    allBrandListSelectWiseLists = dataRepository.getBrandListSelectWise(binding.autoCompleteSelect.getText().toString());
                    Log.d("brand_list_select_wise", "Brand: " + allBrandListSelectWiseLists.get(1));

//                    ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(binding.autoCompleteBrand.getContext(),
//                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.marital_status));
//                    binding.autoCompleteBrand.setAdapter(brandAdapter);

                } else {
//                    ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(binding.autoCompleteBrand.getContext(),
//                            android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.marital_status));
//                    binding.autoCompleteBrand.setAdapter(brandAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.imageSaveOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                final CharSequence[] items = {"Take Photo from Camera", "Choose from Gallery", "Cancel"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            if (allPermissionsGranted()) {
                                openCamera("1"); //start camera if permission has been granted by user
                            } else {
                                requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                            }

                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            // intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_CODE_GALLERY1);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }

                    }
                });
                builder.show();

            }
        });

        binding.imageSaveTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                final CharSequence[] items = {"Take Photo from Camera", "Choose from Gallery", "Cancel"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            if (allPermissionsGranted()) {
                                openCamera("2"); //start camera if permission has been granted by user
                            } else {
                                requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                            }

                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            // intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_CODE_GALLERY2);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }

                    }
                });
                builder.show();

            }
        });

        binding.imageSaveThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                final CharSequence[] items = {"Take Photo from Camera", "Choose from Gallery", "Cancel"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            if (allPermissionsGranted()) {
                                openCamera("3"); //start camera if permission has been granted by user
                            } else {
                                requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                            }

                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            // intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_CODE_GALLERY3);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }

                    }
                });
                builder.show();

            }
        });


        binding.txtContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkConnected(getContext())) {
                    Utils.showSnackBar(getActivity(), getResources().getString(R.string.internet_not_available));
                } else {
                    //binding.txtContinueBtn.setClickable(false);
                    Toast.makeText(getActivity(), "click continue!", Toast.LENGTH_SHORT).show();
                    binding.progressBarPType.setVisibility(View.VISIBLE);
                    mViewModel.sendProductTypeData(RequestFormatter.jsonObjectProductReviewPage1(
                            PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_id),
                            "",
                            PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_name),
                            binding.autoCompleteSelect.getText().toString(),
                            binding.autoCompleteBrand.getText().toString(),
                            binding.autoCompleteModel.getText().toString(),
                            imageSendList,
                            binding.editTitle.getText().toString(),
                            "", "", 0,
                            "", "", "",
                            "", "", 0));

                    getProductTypeData();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductTypeViewModel.class);
        // TODO: Use the ViewModel
    }


    private void nextFragment() {
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_productTypeFragment_to_productReviewFragment);
    }


    private void openCamera(String getCamera) {
        if (getCamera.equals("1")) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA1);
        } else if (getCamera.equals("2")) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA2);
        } else if (getCamera.equals("3")) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA3);
        } else {
            Toast.makeText(getActivity(), "Choose Camera Image", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (bitmap != null) {
            bitmap.recycle();
        }
        if (requestCode == REQUEST_CODE_GALLERY1 && resultCode == RESULT_OK) {
            if (data != null) {
                contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(this).load(contentURI)
                        .into(binding.imageSaveOne);

                addPhotoInList(contentURI);
                //uploadImageToServerFromGallery(contentURI);
            }
        }

        if (requestCode == REQUEST_CODE_GALLERY2 && resultCode == RESULT_OK) {
            if (data != null) {
                contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(this).load(contentURI)
                        .into(binding.imageSaveTwo);

                addPhotoInList(contentURI);
                //uploadImageToServerFromGallery(contentURI);
            }
        }

        if (requestCode == REQUEST_CODE_GALLERY3 && resultCode == RESULT_OK) {
            if (data != null) {
                contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(this).load(contentURI)
                        .into(binding.imageSaveThree);

                addPhotoInList(contentURI);
                //uploadImageToServerFromGallery(contentURI);
            }
        }


        if (requestCode == REQUEST_CODE_CAMERA1) {
            if (resultCode == RESULT_OK) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";

                File f = null;
                try {
                    f = new File(getContext().getCacheDir(), imageFileName + ".jpg");
                    f.createNewFile();

                } catch (Exception e) {
                    e.printStackTrace();
                    // FirebaseCrashlytics.getInstance().recordException(e);
                }

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();

                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(imageBytes);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(this).load(photo)
                        .into(binding.imageSaveOne);

                addPhotoInList(Uri.fromFile(new File(String.valueOf(f))));

                // uploadImageToServerFromCamera(f);
            }
        }

        if (requestCode == REQUEST_CODE_CAMERA2) {
            if (resultCode == RESULT_OK) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";

                File f = null;
                try {
                    f = new File(getContext().getCacheDir(), imageFileName + ".jpg");
                    f.createNewFile();

                } catch (Exception e) {
                    e.printStackTrace();
                    // FirebaseCrashlytics.getInstance().recordException(e);
                }

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();

                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(imageBytes);
                    fos.flush();
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(this).load(photo)
                        .into(binding.imageSaveTwo);

                addPhotoInList(Uri.fromFile(new File(String.valueOf(f))));

                // uploadImageToServerFromCamera(f);
            }
        }

        if (requestCode == REQUEST_CODE_CAMERA3) {
            if (resultCode == RESULT_OK) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";

                File f = null;
                try {
                    f = new File(getContext().getCacheDir(), imageFileName + ".jpg");
                    f.createNewFile();

                } catch (Exception e) {
                    e.printStackTrace();
                    // FirebaseCrashlytics.getInstance().recordException(e);
                }

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();

                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(imageBytes);
                    fos.flush();
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(this).load(photo)
                        .into(binding.imageSaveThree);

                addPhotoInList(Uri.fromFile(new File(String.valueOf(f))));

                //uploadImageToServerFromCamera(f);

            }
        }

    }

    private void addPhotoInList(Uri contentURI) {
        File file2 = FileUtils.getFile(getActivity(), contentURI); //here i am converting path to file ,,FileUtils.getFile is custom class
        String fileName = file2.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf("."));

        Log.d("fileExtName", fileName);

        imageSendList.add(fileName);
        imageFilePathList.add(file2.toString());

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                openCamera("");
            } else {
                Toast.makeText(getContext(), "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private void uploadImageToServerFromCamera(File newFile) {
        Log.d("filePathAA2", "CAMERA: " + newFile);

        if (newFile != null) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), newFile);

        }
    }


    private void uploadImageToServerFromGallery(Uri fileUri) {
        if (fileUri != null) {
            File file2 = FileUtils.getFile(getActivity(), fileUri); //here i am converting path to file ,,FileUtils.getFile is custom class
            String abc = file2.getAbsolutePath();
            Log.d("fileNameeee: ", file2.getName() + "  fileExtension: " + abc.substring(abc.lastIndexOf("."))); // Extension with dot .jpg, .png

//            TransferUtility transferUtility =
//                    TransferUtility.builder()
//                            .context(getActivity())
//                            .defaultBucket("my-profile-image")
//                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
//                            .s3Client(s3Client)
//                            .build();
//
//            // {"my-profile-image"} will be the folder that contains the file
//            long fileName2 = System.currentTimeMillis();
//            TransferObserver uploadObserver =
//                    transferUtility
//                            .upload("my-profile-image", file2.getName() + ".jpg", file2, CannedAccessControlList.PublicRead);
//
//            uploadObserver.setTransferListener(new TransferListener() {
//                @Override
//                public void onStateChanged(int id, TransferState state) {
//                    if (TransferState.COMPLETED == state) {
//                        Toast.makeText(getActivity(), "Upload Completed!", Toast.LENGTH_SHORT).show();
//                        Log.d("onState:", state.name() + id);
//                        String makeUrl = "https://my-profile-image.s3.ap-south-1.amazonaws.com/";
//                        Log.d("getURLImage:", makeUrl + file2.getName() + ".jpg");
//
//                        //file.delete();
//                    } else if (TransferState.FAILED == state) {
//                        // file.delete();
//                    }
//                }
//
//                @Override
//                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
//                    int percentDone = (int) percentDonef;
//
//                    Log.d("onProgressFilename", "ID:" + id + "|bytesCurrent: " + bytesCurrent + "|bytesTotal: " + bytesTotal + "|" + percentDone + "%");
//                }
//
//                @Override
//                public void onError(int id, Exception ex) {
//                    ex.printStackTrace();
//                    Log.d("onErrorrr:", ex.getLocalizedMessage());
//                }
//            });

            String urls = "https://my-profile-image.s3.ap-south-1.amazonaws.com/photo-with-sign.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20201225T124458Z&X-Amz-SignedHeaders=content-type%3Bhost&X-Amz-Expires=3599&X-Amz-Credential=AKIA2WSH67LRCGFWPQOV%2F20201225%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=17cdd8bd5c5953075289db68bb885b2f29b712e311ae67411a80710d0ce3677f";
            String toDiskDir = file2.toString();
            Log.d("fileToDiskDir: ", toDiskDir);

        }
    }


    private void getProductTypeData() {
        mViewModel.getProductTypeLiveData().observe(getActivity(), new Observer<ProductReviewWithPreSignUrlModel>() {
            @Override
            public void onChanged(ProductReviewWithPreSignUrlModel preSignUrlModel) {
                if (preSignUrlModel != null) {
                    Toast.makeText(getActivity(), preSignUrlModel.getProductSeller(), Toast.LENGTH_SHORT).show();
                    PreferenceUtils.getInstance().putString(
                            R.string.pref_product_id, preSignUrlModel.getProductId());

                    List<String> preSignedURL = new ArrayList<>();

                    if (preSignUrlModel.getPreSignedURL() != null) {
                        preSignedURL.clear();
                        preSignedURL = preSignUrlModel.getPreSignedURL();

                        Log.d("preSignedUrl", preSignedURL.size() + " " + preSignedURL.toString());
                        getPreSignedUrl(preSignedURL);
                    }

                    binding.progressBarPType.setVisibility(View.GONE);
                    binding.txtContinueBtn.setClickable(true);
                    nextFragment();

                }
            }
        });
    }

    private void getPreSignedUrl(List<String> preSignedURL) {
        // todo Upload photo
        if (preSignedURL != null) {

            String urls = null;
            String toDiskDir = null;

            for (int a = 0; a < preSignedURL.size(); a++) {

                // todo upload photo:
                try {
                    HttpURLConnection connection;
                    URL url = new URL(preSignedURL.get(a));
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "image/jpeg");
                    connection.setRequestMethod("PUT");

                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(imageFilePathList.get(a))));
                        OutputStream out = connection.getOutputStream();
                        byte[] readBuffArr = new byte[4096];
                        int readBytes = 0;
                        while ((readBytes = bin.read(readBuffArr)) >= 0) {
                            out.write(readBuffArr, 0, readBytes);
                        }
                        connection.getResponseCode();
                        Log.d("onURL", connection.getResponseCode() + " " + url);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            binding.progressBarPType.setVisibility(View.GONE);

        }

    }


}