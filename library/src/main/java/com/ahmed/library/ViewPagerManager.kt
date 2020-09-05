package com.ahmed.library

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.util.*

class ViewPagerManager {

    companion object {
        private const val TAG = "ViewPagerManager"
    }





    @Suppress("DEPRECATION")
    class Builder  constructor(context: Context) {
        private  val pagerAdapter: ViewPagerAdapter = ViewPagerAdapter((context as AppCompatActivity).supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        private  val resources: Resources = context.resources
        private lateinit var viewPager: ViewPager
        private lateinit var fragments: ArrayList<Fragment?>
        private var withTabLayout: Boolean = false
       // private var tabs: ArrayList<TabLayout.Tab?>? = null
        private var titles: ArrayList<String?>? = null
        private var iconsResources: ArrayList<Int?>? = null
        private var isRTL: Boolean = false
        private var smoothScroll: Boolean = false
        private var tabsCount: Int = -1
        private var selectedColor: Int = -1
        private var unSelectedColor: Int = -1
        private var tabLayout: TabLayout? = null



//*************************************************************************************************************\\
//*                                                                                                             *\\
//*                                           Builder methods                                                    *\\
//*                                                                                                                 *\\
//**********************************************************************************************************************/
        fun viewPager(viewPager: ViewPager): Builder {
            this.viewPager = viewPager
            return this
        }


        fun isRTL(isRTL: Boolean): Builder {
            this.isRTL = isRTL
            return this
        }


        fun withTabLayout(withTabLayout: Boolean): Builder {
            this.withTabLayout = withTabLayout
            return this
        }


       /* fun tabs(tabs: ArrayList<TabLayout.Tab?>): Builder {
            this.tabs = tabs
            return this
        }*/

        fun smoothScroll(smoothScroll: Boolean): Builder {
            this.smoothScroll = smoothScroll
            return this
        }

        fun selectedColor(selectedColor: Int): Builder {
            this.selectedColor = selectedColor
            return this
        }

        fun unSelectedColor(unSelectedColor: Int): Builder {
            this.unSelectedColor = unSelectedColor
            return this
        }

        fun tabLayout(tabLayout: TabLayout): Builder {
            this.tabLayout = tabLayout
            return this
        }

        fun fragments(fragments: ArrayList<Fragment?>): Builder {
            this.fragments = fragments
            return this
        }

        fun titles(titles: ArrayList<String?>): Builder {
            this.titles = titles
            return this
        }

        fun iconsResources(iconsResources: ArrayList<Int?>): Builder {
            this.iconsResources = iconsResources
            return this
        }





        private fun setTabsCount() {
            when {
                titles != null && iconsResources != null -> {
                    this.tabsCount = titles!!.size
                }

                titles != null -> {
                    this.tabsCount = titles!!.size
                }

                iconsResources != null -> {
                    this.tabsCount = iconsResources!!.size
                }
                else -> {
                    this.tabsCount = pagerAdapter.count
                }
            }

        }


        /**
         * To add tabs with respect titles or icons
         * */
        private fun addTabs() {
            /*if (tabs != null){
                for(tab in tabs!!){
                    tabLayout?.addTab(tab!!)
                }

            } else*/
            if (titles != null && iconsResources != null) {
                for (i in 0 until tabsCount) {
                    tabLayout?.addTab(makeNewTab(titles!![i], iconsResources!![i]))
                }
            } else if (titles != null) {
                for (i in 0 until titles!!.size) {
                    tabLayout?.addTab(makeNewTab(titles!![i]))
                }
            } else if (iconsResources != null) {
                for (i in 0 until iconsResources!!.size) {
                    tabLayout?.addTab(makeNewTab(iconsResources!![i]))
                }
            } else {
                for (i in 0 until pagerAdapter.count) {
                    tabLayout?.addTab(makeNewTab())
                }
            }

        }


        /**
         * To setup viewPager
         * */
        fun build() {
            setTabsCount()

            viewPager.adapter = pagerAdapter
            pagerAdapter.addAllFragments(fragments)


            if (isRTL) {
                pagerAdapter.fragments.reverse()
                viewPager.currentItem = pagerAdapter.count - 1
            }

            if (withTabLayout) {
                if (tabLayout != null) {
                    tabLayout?.visibility ?: View.VISIBLE
                    if (isColorsSelected()){
                        tabLayout!!.setTabTextColors(resources.getColor(unSelectedColor,null),resources.getColor(selectedColor,null))
                        tabLayout!!.setSelectedTabIndicatorColor(resources.getColor(selectedColor,null))
                    }
                    addTabs()
                    listenerOnPageChanged()
                    listenerOnTabSelection()
                    setDefaultTabColor()

                }
            }

        }




//*************************************************************************************************************\\
//*                                                                                                             *\\
//*                                           make new Tab                                                       *\\
//*                                                                                                                 *\\
//**********************************************************************************************************************/
        private fun makeNewTab(): TabLayout.Tab {
            return tabLayout!!.newTab()
        }

        //To make a new tab with title
        private fun makeNewTab(title: String?): TabLayout.Tab {
            val newTab = tabLayout!!.newTab()
            newTab.text = title
            return newTab
        }

        //To make a new tab with title
        @SuppressLint("UseCompatLoadingForDrawables")
        private fun makeNewTab(iconResource: Int?): TabLayout.Tab {
            val newTab = tabLayout!!.newTab()
            newTab.icon = resources.getDrawable(iconResource!!, null)
            return newTab
        }

        //To make a new tab with title and icon

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun makeNewTab(title: String?, iconResource: Int?): TabLayout.Tab {
            // Log.e(TAG, "makeNewTab: ")
            val newTab = tabLayout!!.newTab()
            newTab.text = title
            if (iconResource != null) {
                newTab.icon = resources.getDrawable(iconResource, null)
                newTab.setIcon(iconResource)
            } else {
                Log.e(TAG, "makeNewTab: iconResource is null")
            }
            return newTab
        }


//*************************************************************************************************************\\
//*                                                                                                             *\\
//*                       listeners on Changes (pagesChanges or Tabs selection changes)                          *\\
//*                                                                                                                 *\\
//**********************************************************************************************************************/
        /**
         * To listen on page changed
         * */
        private fun listenerOnPageChanged() {
            viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }
                override fun onPageSelected(i: Int) {
                    val selectedTab = if (isRTL) { tabsCount - i - 1 } else { i }
                    tabLayout!!.getTabAt(selectedTab)?.select()
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }


        /**
         * To listen on tab selection changed
         * */
        private fun listenerOnTabSelection() {
            tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    changePageOnTabClicked(tab)
                       // tab.icon!!.setColorFilter(resources.getColor(selectedColor, null), PorterDuff.Mode.SRC_IN)
                    if (tab.icon != null && isColorsSelected()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            tab.icon!!.colorFilter = BlendModeColorFilter(resources.getColor(selectedColor,null), BlendMode.SRC_IN)
                        } else {
                            tab.icon!!.setColorFilter(resources.getColor(selectedColor, null), PorterDuff.Mode.SRC_IN)
                        }
                    }

                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    if (tab.icon != null && isColorsSelected()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            tab.icon!!.colorFilter = BlendModeColorFilter(resources.getColor(unSelectedColor, null), BlendMode.SRC_IN)
                        } else {
                            tab.icon!!.setColorFilter(resources.getColor(unSelectedColor, null), PorterDuff.Mode.SRC_IN)
                        }
                    }

                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

        }

        /**
         * To change the page in viewPager with selected Tab
         * */
        private fun changePageOnTabClicked(tab: TabLayout.Tab) {
            val currentItemPositionViewPager = if (isRTL){
                (tabsCount - tab.position) - 1
            }else{
                tab.position
            }
            viewPager.setCurrentItem(currentItemPositionViewPager, smoothScroll)
        }


//*************************************************************************************************************\\
//*                                                                                                             *\\
//*                                                  Others                                                       *\\
//*                                                                                                                 *\\
//**********************************************************************************************************************/
        /**
         * To set filter color by default to tabs
         * */
        private fun setDefaultTabColor() {
            val tabsCount = tabLayout!!.tabCount
            for (i in 0 until tabsCount) {
                if (tabLayout!!.getTabAt(i)?.isSelected!!) {
                    val tab = tabLayout!!.getTabAt(i)
                    if (tab?.icon != null && isColorsSelected()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            tab.icon!!.colorFilter = BlendModeColorFilter(resources.getColor(selectedColor,null), BlendMode.SRC_IN)
                        } else {
                            tab.icon!!.setColorFilter(resources.getColor(selectedColor,null), PorterDuff.Mode.SRC_IN)
                        }
                    }


                } else {
                    val tab = tabLayout!!.getTabAt(i)
                    if (tab?.icon != null && isColorsSelected()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            tab.icon!!.colorFilter = BlendModeColorFilter(resources.getColor(unSelectedColor,null), BlendMode.SRC_IN)
                        } else {
                            tab.icon!!.setColorFilter(resources.getColor(unSelectedColor,null), PorterDuff.Mode.SRC_IN)
                        }
                    }

                }
            }
        }

        /**
         * @return true if user select colors
         * */
        private fun isColorsSelected() : Boolean{
            return selectedColor != -1 && unSelectedColor != -1
        }



    }

}

