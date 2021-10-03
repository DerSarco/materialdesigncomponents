package com.sarco.mdcmaterialdesigncomponents

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.card.MaterialCardView
import com.sarco.mdcmaterialdesigncomponents.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity() {


    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //que es esto?
        //setea los elementos de la vista en una variable para poder acceder mas facil a ellos
        //solo accede a los elementos que tengan un identificador en la raiz
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener{
            if (binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER){
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

            }
        }

        binding.bottomAppBar.setNavigationOnClickListener {
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_SHORT)
//                    setea la posicio sobre algun elemento de la vista
                .setAnchorView(binding.fab)
                .show()
        }

        binding.content.btnSkip?.setOnClickListener{
            binding.content.cvAd?.visibility = View.GONE
        }

        binding.content.btnBuy?.setOnClickListener {
            Snackbar.make(it, R.string.card_buy_message, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .setAction(R.string.card_to_go) {
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_LONG).show()
                }
                .show()
        }

        //libreria glide que ayuda en la implementacion de imagenes de internet

        loadImageFromUrl()

        binding.content.cbEnabledPass?.setOnClickListener {
            binding.content.tilPassword?.isEnabled = !binding.content.tilPassword!!.isEnabled
            }

        binding.content.etUrl!!.setOnFocusChangeListener{ it, focused ->
            var errorStr: String? = null

            val url = binding.content.etUrl!!.text.toString();
            if(!focused){
                if(url.isEmpty()){
                    errorStr = getString(R.string.card_required)
//                    URLUtil tiene una funcion que se encarga de validar los enlaces y que
//                    estos sean validos.
                } else if(URLUtil.isValidUrl(url)){
                    loadImageFromUrl(url)
                } else{
                    errorStr = getString(R.string.card_invalid_url)
                }
            }

            binding.content.tilUrl!!.error = errorStr
            }

        binding.content.toogleButton!!.addOnButtonCheckedListener { group, checkedId, isChecked ->
            binding.content.root.setBackgroundColor(
                when (checkedId) {
                    R.id.btnRed -> Color.RED
                    R.id.btnBlue -> Color.BLUE
                    else -> Color.GREEN
                }
            )
        }
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadImageFromUrl(url: String = "https://scontent.fscl19-1.fna.fbcdn.net/v/t1.6435-9/136691250_1292513834460284_8763543874399854286_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=e3f864&_nc_eui2=AeFJU_h-RR0WX5rVSsxdh8dgFP7KgJiVowwU_sqAmJWjDBTtLVpRAyLn4rVw6mnj7tM&_nc_ohc=r8_MaX6jDMMAX_Abu3o&tn=ZOSL0AYGUk-Y4lUR&_nc_ht=scontent.fscl19-1.fna&oh=0367975411c689a549d9193a8a5c6755&oe=617D465D" ) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.content.imgCover!!)
    }
}


