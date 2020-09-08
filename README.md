# CustomTabLayout supports ViewPager2

This project hepls you to set your tablayout to work with viewpager2. 

![eng](https://user-images.githubusercontent.com/40568882/92460231-02be4a80-f1c8-11ea-8fe1-71286fbb8310.jpeg)                                                                       ![arabic](https://user-images.githubusercontent.com/40568882/92460113-dc001400-f1c7-11ea-9ab5-104bf422a9e0.jpeg)  

## Gradle

**Step 1.** Add the JitPack repository to your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
      maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the library dependency to your project build.gradle:
```
dependencies {
  implementation 'com.google.android.material:material:(lastest release version)'
  implementation 'com.github.AhmedAbdEllatiif:Customtablayout:(lastest release version)'
}
```

## Usage

In your XML file add this:
```
<com.ahmed.library.CustomTabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabGravity="fill"
        app:tabMode="fixed"
        app:tabTextColor="@color/colorPrimaryDark"
        app:tabSelectedTextColor="@color/colorAccent"
        app:tabIndicatorColor="@color/colorAccent"
        app:selectedIconColor="@color/colorAccent"
        app:unSelectedIconColor="@color/colorPrimaryDark"
        app:smoothScroll="true"
        />
```

## Attributes
| Attribute                            | Description                                                                           | 
| ------------------------------------ |-------------------------------------------------------------------------------------- | 
| `app:selectedIconColor=""`            |To change icon color when the icon is selected (default=Color.DKGRAY)                                                  | 
| `app:unSelectedIconColor=""`         | To change icon color when the icon is unSelected (default=Color.GRAY)                                       | 
| `app:smoothScroll="boolean"`         | To make the viewPager scroll smooth or not when tabs clicked (default = true)                   | 
| `app:showIndicator="boolean"`             | To show or hide tab indicator (default = true)                                            | 


In your Java class:
```
CustomTabLayout tabLayout = findViewById(R.id.tabLayout);
tabLayout.titles(getTitlesList());
tabLayout.iconsResources(getIcons());
tabLayout.setupWithViewPager(viewPager);
```
Or your Koltin class:
```
val customTabLayout : CustomTabLayout = findViewById(R.id.tabLayout)
customTabLayout.iconsResources(getIcons())
customTabLayout.titles(getTitlesList())
customTabLayout.setupWithViewPager(viewPager)
```

## Methods
| Method                            | Description                                                                           | 
| ------------------------------------ |-------------------------------------------------------------------------------------- | 
| `customTabLayout.iconsResources()`   |To insert icons of tabs                                                  | 
| `customTabLayout.titles()`         | To insert titles of tabs                                    | 
| `customTabLayout.tabsCount()`         | To set the tabs count requeried when there are no titles or icons (default = 0)                   | 
| `customTabLayout.setupWithViewPager()`               | To build the tablayout with your viewpager                                            | 
