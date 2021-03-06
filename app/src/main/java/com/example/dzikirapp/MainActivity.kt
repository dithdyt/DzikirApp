package com.example.dzikirapp

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // property kosong sementara dan kegunaan lateinit untuk agar dia bisa mengizinkan sebuah property atau sebuah variabel
    private lateinit var llsunnahQauliyahShalat: LinearLayout
    private lateinit var lldzikirSetiapSaat: LinearLayout
    private lateinit var lldzikirDoaHarian: LinearLayout
    private lateinit var lldzikirPagiPetang: LinearLayout // Property berisi view kosong dan dipanggil di dalam function
    private lateinit var vpArtikel: ViewPager2
    private lateinit var sliderDots: LinearLayout

    private lateinit var dotsIndicator: Array<ImageView?>
    private val ListArtikel: ArrayList<Artikel> = arrayListOf()

    private val slidingCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            for (i in 0 until ListArtikel.size) {
                dotsIndicator[i]?.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.dot_inactive)
                )
            }

            dotsIndicator[position]?.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.dot_active)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()

        setupViewPage()
    }

    private fun setupViewPage() {
        dotsIndicator = arrayOfNulls(ListArtikel.size)
        for (i in 0 until ListArtikel.size) {
            dotsIndicator[i] = ImageView(this)
            dotsIndicator[i]?.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.dot_inactive)
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 0)
            params.gravity = Gravity.CENTER_VERTICAL
            sliderDots.addView(dotsIndicator[i], params)
        }
    }

    private fun initView() {
        llsunnahQauliyahShalat = findViewById(R.id.ll_sunnah_qauliyah)
        lldzikirSetiapSaat = findViewById(R.id.ll_dzikir_setiap_saat)
        lldzikirDoaHarian = findViewById(R.id.ll_dzikir_doa_harian)
        lldzikirPagiPetang = findViewById(R.id.ll_dzikir_pagi_petang)

        llsunnahQauliyahShalat.setOnClickListener(this) // this membutuhkan sebuah interface
        lldzikirSetiapSaat.setOnClickListener(this)
        lldzikirDoaHarian.setOnClickListener(this)
        lldzikirPagiPetang.setOnClickListener(this)

        vpArtikel = findViewById(R.id.vp_article)
        vpArtikel.adapter = ArtikelAdapter(ListArtikel)
        vpArtikel.registerOnPageChangeCallback(slidingCallback)

        sliderDots = findViewById(R.id.ll_slider_dots)
    }

    private fun initData() {
        val titleArtikel = resources.getStringArray(R.array.title_artikel)
        val descArtikel = resources.getStringArray(R.array.desc_artikel)
        val imgArtikel = resources.obtainTypedArray(R.array.img_artikel)

        ListArtikel.clear()
        for (data in titleArtikel.indices) {
            val artikel = Artikel(
                imgArtikel.getResourceId(data, 0),
                titleArtikel[data],
                descArtikel[data]
            )
            ListArtikel.add(artikel)
        }
        imgArtikel.recycle()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ll_sunnah_qauliyah -> {
                startActivity(
                    Intent(
                        this,
                        QauliyahShalatActivity::class.java // Setiap ada activity dia harus ada ::class.java
                    )
                )
            }
            R.id.ll_dzikir_setiap_saat -> {
                startActivity(
                    Intent(
                        this, SetiapSaatDzikirActivity::class.java
                    )
                )
            }
            R.id.ll_dzikir_doa_harian -> {
                startActivity(
                    Intent(
                        this, HarianDzikirDoaActivity::class.java
                    )
                )
            }
            R.id.ll_dzikir_pagi_petang -> {
                startActivity(
                    Intent(
                        this, PagiPetangDzikirActivity::class.java
                    )
                )
            }
        }


    }
}