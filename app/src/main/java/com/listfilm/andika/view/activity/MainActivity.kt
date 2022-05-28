package com.listfilm.andika.view.activity

import UserManager
import android.os.Bundle
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.listfilm.andika.BuildConfig
import com.listfilm.andika.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // The gesture threshold expressed in dp
    private val GESTURE_THRESHOLD_DP = 16.0f
    private var mGestureThreshold: Int = 0

    private lateinit var navController: NavController
    private lateinit var navController2: NavController
    private lateinit var navController3: NavController
    lateinit var drawer : DrawerLayout
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the screen's density scale
        val scale: Float = resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        mGestureThreshold = (GESTURE_THRESHOLD_DP * scale + 0.5f).toInt()

        // Use mGestureThreshold as a distance in pixels...

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment

        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.setupWithNavController(navController)

        drawer = findViewById(R.id.drawerlayout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        profilem.setOnClickListener {
            drawer.openDrawer(GravityCompat.END)
        }

        userManager = UserManager(this)
        val headerView = navView.getHeaderView(0)
        val image = headerView.findViewById<ImageView>(R.id.pp2h)
        val username = headerView.findViewById<TextView>(R.id.usernamehint)
        val email = headerView.findViewById<TextView>(R.id.emailhint)

        userManager.userImage.asLiveData().observe(this){

            if (it != null && it != ""){
                Glide.with(this).load( it ).into(image)
            }else{
                Glide.with(this).load( R.drawable.pp ).into(image)
            }


        }
        userManager.userUsername.asLiveData().observe(this){

            username.text = it.toString()
        }
        userManager.userEmail.asLiveData().observe(this){

            email.text = it.toString()
        }


        navController3 = navHostFragment.navController



        navView.setupWithNavController(navController3)





        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            if (id==R.id.profileFragment){
                drawer.closeDrawer(GravityCompat.END)
            }
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            //This is for closing the drawer after acting on it

            true
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(BuildConfig.FLAVOR == "premium") {
                if (destination.id == R.id.homeFragment) {
                    bottomnav.visibility = View.VISIBLE

                } else if (destination.id == R.id.favoriteFragment) {
                    bottomnav.visibility = View.VISIBLE
                } else {
                    bottomnav.visibility = View.GONE
                }
            }else{
                if (destination.id == R.id.homeFragment) {
                    bottomnav.visibility = View.GONE

                } else if (destination.id == R.id.favoriteFragment) {
                    bottomnav.visibility = View.GONE
                } else {
                    bottomnav.visibility = View.GONE
                }
            }

        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        navController2 = navHostFragment.navController
        toolbar.setupWithNavController(navController2)



        navController2.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment ) {
                toolbar.visibility = View.VISIBLE

                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.title = null

            } else if (destination.id == R.id.favoriteFragment ){
                toolbar.visibility = View.VISIBLE
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.title = null
            }
            else {
                toolbar.visibility = View.GONE
            }

        }






    }




}
