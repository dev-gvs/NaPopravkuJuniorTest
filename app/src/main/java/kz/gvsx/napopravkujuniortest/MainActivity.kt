package kz.gvsx.napopravkujuniortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kz.gvsx.napopravkujuniortest.databinding.MainActivityBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val viewBinding: MainActivityBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.topAppBar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount != 0)
                viewBinding.topAppBar.setNavigationIcon(R.drawable.ic_back)
            else
                viewBinding.topAppBar.navigationIcon = null
        }
    }
}