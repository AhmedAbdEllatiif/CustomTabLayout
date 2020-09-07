package com.ahmed.library

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import java.util.*

class CustomTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.CustomTabLayoutStyle)
    : TabLayout(context, attrs, R.attr.tabStyle) {

    companion object {
        private const val TAG = "CustomTabLayout"
    }

    private lateinit var selectedColor: ColorStateList
    private lateinit var unSelectedColor: ColorStateList
    private lateinit var tabIndicatorColor: ColorStateList
    private var titles: ArrayList<String?>? = null
    private var iconsResources: ArrayList<Int?>? = null
    private var tabsCount: Int = 0
    private var showIndicator: Boolean = true
    private var smoothScroll: Boolean = true


    init {
        val theme = context.theme
        val a = theme.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout, defStyleAttr, 0)
        val states = arrayOf(intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected))
        val colors = intArrayOf(
                Color.DKGRAY,
                Color.GRAY,
        )

        val colorStateList = ColorStateList(states, colors)
        if (a.getColorStateList(R.styleable.CustomTabLayout_selectedIconColor) != null) {
            selectedColor(a.getColorStateList(R.styleable.CustomTabLayout_selectedIconColor)!!)
        } else {
            selectedColor(colorStateList)
        }
        if (a.getColorStateList(R.styleable.CustomTabLayout_unSelectedIconColor) != null) {
            unSelectedColor(a.getColorStateList(R.styleable.CustomTabLayout_unSelectedIconColor)!!)
        } else {
            unSelectedColor(colorStateList)
        }
        if (a.getColorStateList(R.styleable.CustomTabLayout_tabIndicatorColor) != null) {
            tabIndicatorColor(a.getColorStateList(R.styleable.CustomTabLayout_tabIndicatorColor)!!)
        } else {
            tabIndicatorColor(colorStateList)
        }
        showIndicator(a.getBoolean(R.styleable.CustomTabLayout_showIndicator, true))
        smoothScroll(a.getBoolean(R.styleable.CustomTabLayout_smoothScroll, true))
        a.recycle()
    }


    fun titles(titles: ArrayList<String?>?) {
        this.titles = titles
    }

    fun iconsResources(iconsResources: ArrayList<Int?>?) {
        this.iconsResources = iconsResources
    }

    fun titlesAndIconsResources(titles: ArrayList<String?>?, iconsResources: ArrayList<Int?>?) {
        this.iconsResources = iconsResources
        this.titles = titles
    }

    fun tabsCount(tabsCount: Int) {
        this.tabsCount = tabsCount
    }

    private fun smoothScroll(smoothScroll: Boolean) {
        this.smoothScroll = smoothScroll
    }

    private fun tabIndicatorColor(tabIndicatorColor: ColorStateList) {
        this.tabIndicatorColor = tabIndicatorColor
    }

    private fun selectedColor(selectedColor: ColorStateList) {
        this.selectedColor = selectedColor
    }

    private fun unSelectedColor(unSelectedColor: ColorStateList) {
        this.unSelectedColor = unSelectedColor
    }

    private fun showIndicator(showIndicator: Boolean) {
        this.showIndicator = showIndicator
    }


    /**
     * To setup tabLayout with viewPager
     * */
    fun setupWithViewPager(viewPager: ViewPager2?) {
        addTabs()
        prepareTabIconsAndTabIndicator()
        setDefaultTabColor()
        listenerOnTabSelection(viewPager)
        listenerOnPageChanges(viewPager)
    }

//*************************************************************************************************************\\
//*                                                                                                             *\\
//*                                           Listen on changes                                                   *\\
//*                                                                                                                 *\\
//**********************************************************************************************************************/
    /**
     * To listen on page change
     * */
    private fun listenerOnPageChanges(viewPager: ViewPager2?) {
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                this@CustomTabLayout.getTabAt(position)?.select()
            }
        })
    }

    /**
     * To listen on tab selection changed
     * */
    private fun listenerOnTabSelection(viewPager: ViewPager2?) {
        this@CustomTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                changePageOnTabClicked(tab, viewPager)
                if (tab.icon != null) {
                    tab.icon!!.state = intArrayOf(android.R.attr.state_selected)
                    tab.icon!!.setTintList(selectedColor)
                }
            }

            override fun onTabUnselected(tab: Tab) {
                if (tab.icon != null) {
                    tab.icon!!.state = intArrayOf(-android.R.attr.state_selected)
                    tab.icon!!.setTintList(unSelectedColor)
                }
            }

            override fun onTabReselected(tab: Tab) {}
        })

    }

    /**
     * To change the page in viewPager with selected Tab
     * */
    private fun changePageOnTabClicked(tab: Tab, viewPager: ViewPager2?) {
        viewPager?.setCurrentItem(tab.position, smoothScroll)
    }


//*************************************************************************************************************\\
//*                                                                                                             *\\
//*                                           make new Tab                                                        *\\
//*                                                                                                                 *\\
//**********************************************************************************************************************/

    private fun makeNewTab(): Tab {
        return this@CustomTabLayout.newTab()
    }

    //To make a new tab with title
    private fun makeNewTab(title: String?): Tab {
        val newTab = this@CustomTabLayout.newTab()
        newTab.text = title
        return newTab
    }

    //To make a new tab with title
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun makeNewTab(iconResource: Int?): Tab {
        val newTab = this@CustomTabLayout.newTab()
        newTab.icon = resources.getDrawable(iconResource!!, null)
        return newTab
    }

    //To make a new tab with title and icon

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun makeNewTab(title: String?, iconResource: Int?): Tab {

        val newTab = this@CustomTabLayout.newTab()
        newTab.text = title
        if (iconResource != null) {
            newTab.icon = resources.getDrawable(iconResource, null)
            newTab.setIcon(iconResource)
        }
        return newTab
    }


    /**
     * To add tabs with respect titles or icons
     * */
    private fun addTabs() {

        when {
            titles != null && iconsResources != null -> {
                Log.d(TAG, "addTabs: with titles & iconsResources")
                this.tabsCount = titles!!.size
                for (i in 0 until tabsCount) {
                    this.addTab(makeNewTab(titles!![i], iconsResources!![i]))
                }
            }

            titles != null -> {
                Log.d(TAG, "addTabs: with titles")
                this.tabsCount = titles!!.size
                for (i in 0 until tabsCount) {
                    this.addTab(makeNewTab(titles!![i]))
                }
            }

            iconsResources != null -> {
                Log.d(TAG, "addTabs: with iconsResources")
                this.tabsCount = iconsResources!!.size
                for (i in 0 until tabsCount) {
                    this.addTab(makeNewTab(iconsResources!![i]))
                }
            }

            else -> {
                Log.d(TAG, "addTabs: with tabsCount")
                for (i in 0 until tabsCount) {
                    this.addTab(makeNewTab())
                }
            }

        }
    }

//*************************************************************************************************************\\
//*                                                                                                             *\\
//*                                           Setup tabLayout                                                     *\\
//*                                                                                                                 *\\
//**********************************************************************************************************************/
    /**
     * To prepare tab layout (tabs and indicator color)
     * */
    private fun prepareTabIconsAndTabIndicator() {
        if (!showIndicator) {
            this.setSelectedTabIndicator(null)
        }

        this.setTabTextColors(unSelectedColor.getColorForState(intArrayOf(-android.R.attr.state_selected), unSelectedColor.defaultColor),
                selectedColor.getColorForState(intArrayOf(android.R.attr.state_selected), selectedColor.defaultColor))
        this.setSelectedTabIndicatorColor(tabIndicatorColor.defaultColor)
    }


    /**
     * To initialize tabs with colors
     * */
    private fun setDefaultTabColor() {
        val tabsCount = this.tabCount
        for (i in 0 until tabsCount) {
            if (this.getTabAt(i)?.isSelected!!) {
                val tab = this.getTabAt(i)
                if (tab?.icon != null) {
                    tab.icon!!.state = intArrayOf(android.R.attr.state_selected)
                    tab.icon!!.setTintList(selectedColor)
                }
            } else {
                val tab = this.getTabAt(i)
                if (tab?.icon != null) {
                    tab.icon!!.state = intArrayOf(-android.R.attr.state_selected)
                    tab.icon!!.setTintList(unSelectedColor)
                }
            }
        }
    }

/*  @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.CustomTabLayoutStyle)
            : super(context, attrs, R.attr.tabStyle) {
        val theme = context.theme
        val a = theme.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout, defStyleAttr, 0)

        val states = arrayOf(intArrayOf(android.R.attr.state_selected),
                intArrayOf(-android.R.attr.state_selected))

        val colors = intArrayOf(
                Color.DKGRAY,
                Color.GRAY,
        )

        val colorStateList = ColorStateList(states, colors)

        if (a.getColorStateList(R.styleable.CustomTabLayout_selectedIconColor) != null) {
            selectedColor(a.getColorStateList(R.styleable.CustomTabLayout_selectedIconColor)!!)
        }else{
            selectedColor(colorStateList)
        }

        if (a.getColorStateList(R.styleable.CustomTabLayout_unSelectedIconColor) != null) {
        unSelectedColor(a.getColorStateList(R.styleable.CustomTabLayout_unSelectedIconColor)!!)
        }else{
            unSelectedColor(colorStateList)
        }

        if (a.getColorStateList(R.styleable.CustomTabLayout_tabIndicatorColor) != null) {
        tabIndicatorColor(a.getColorStateList(R.styleable.CustomTabLayout_tabIndicatorColor)!!)
        }else{
            tabIndicatorColor(colorStateList)
        }

        showIndicator(a.getBoolean(R.styleable.CustomTabLayout_showIndicator, true))
        smoothScroll(a.getBoolean(R.styleable.CustomTabLayout_smoothScroll, true))

        a.recycle()
    }*/
}