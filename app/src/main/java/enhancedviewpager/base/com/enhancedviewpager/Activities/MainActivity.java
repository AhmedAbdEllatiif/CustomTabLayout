package enhancedviewpager.base.com.enhancedviewpager.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import enhancedviewpager.base.com.enhancedviewpager.BaseActivity;
import enhancedviewpager.base.com.enhancedviewpager.MyApp;
import enhancedviewpager.base.com.enhancedviewpager.R;
import enhancedviewpager.base.com.enhancedviewpager.Translation.Languages;
import enhancedviewpager.base.com.enhancedviewpager.Translation.LocalManger;




public class MainActivity extends BaseActivity {

    private Switch aSwitch;
    private Button btn_showViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
         initViews();

         //onViewsClicked
        onViewsClicked();

        dealWithSwitchBtn();


    }

    //Initialize views
    private void initViews(){
        aSwitch = findViewById(R.id.aSwitch);
        btn_showViewPager = findViewById(R.id.btn_showViewPager);
    }

    //OnViewsClicked
    private  void onViewsClicked(){
        aSwitch.setOnClickListener(v -> {
            if(!aSwitch.isActivated()){
                new Handler().postDelayed(() -> setNewLocale(LocalManger.LANGUAGE_ARABIC),1500);
            }else {
                new Handler().postDelayed(() -> setNewLocale(LocalManger.LANGUAGE_ENGLISH),1500);
            }
        });

        btn_showViewPager.setOnClickListener(v -> startViewPagerActivity());

    }

    private void startViewPagerActivity(){
        Intent intent = new Intent(this,ViewPagerActivity.class);
        startActivity(intent);
    }

    //Change the language
    public void setNewLocale(String language) {
        MyApp.localeManager.setNewLocale(this, language);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


            System.exit(0);


    }


    //Change the ٍ switch btn status due to current Language
    private void dealWithSwitchBtn(){
        if (LocalManger.getLocale(getResources()).getDisplayLanguage().equals(Languages.ENGLISH)){
            aSwitch.setChecked(false);
            aSwitch.setActivated(false);
        }else {
            aSwitch.setChecked(true);
            aSwitch.setActivated(true);
        }
    }

}
