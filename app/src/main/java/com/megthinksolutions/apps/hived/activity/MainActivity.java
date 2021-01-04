package com.megthinksolutions.apps.hived.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.megthinksolutions.apps.hived.R;
import com.megthinksolutions.apps.hived.utils.GetProductListFromJson;
import com.megthinksolutions.apps.hived.utils.PreferenceUtils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    //SideNavigation: Home, Div Video, FAQs, Extend work, Service
    private AppBarConfiguration mAppBarConfiguration;
    private CircleImageView profileImageView;
    private TextView profileName;
    private BottomNavigationView bottomNavigation;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private CoordinatorLayout contentView;

    CircleImageView imageHeaderProfileImage;
    TextView tvHeaderName, tvHeaderEmail;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetProductListFromJson.getProductListData(this);
        PreferenceUtils.getInstance(this,true); // Get the preferences

        PreferenceUtils.getInstance().putBoolean(R.string.pref_is_login_key, true);
        initNavigation();


//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.megthinksolutions.apps.hived",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        //View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navigationView.getHeaderView(0);
        imageHeaderProfileImage = headerView.findViewById(R.id.img_header_profile);
        tvHeaderName = headerView.findViewById(R.id.tv_profile_header_name);
        tvHeaderEmail = headerView.findViewById(R.id.tv_header_email_id);

        imageHeaderProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.avatar1));
        tvHeaderName.setText(PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_name));
        tvHeaderEmail.setText(PreferenceUtils.getInstance().getString(R.string.pref_hived_auth_user_email));

    }

    private void initNavigation() {
        navigationView = findViewById(R.id.nav_view);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        drawer = findViewById(R.id.drawer_layout);
        contentView = findViewById(R.id.content_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_home,
                R.id.nav_div_Video,
                R.id.nav_faqs,
                R.id.nav_extend_work,
                R.id.nav_service,
                R.id.bottom_nav_home,
                R.id.bottom_nav_add_new,
                R.id.bottom_nav_profile).setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}