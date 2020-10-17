package com.kuuuurt.rickandmorty.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kuuuurt.rickandmorty.R
import com.kuuuurt.rickandmorty.databinding.ActivityMainBinding

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class MainActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.frg_main) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.tlbMain) { view, insets ->
            view.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        NavigationUI.setupWithNavController(
            viewBinding.tlbMain,
            navController
        )
    }
}