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

        // Set navigation icon on the configuration change and when navigated somewhere.
        if (savedInstanceState != null && supportFragmentManager.backStackEntryCount != 0)
            viewBinding.topAppBar.setNavigationIcon(R.drawable.ic_back)

        viewBinding.topAppBar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        // Change navigation icon based on the back stack entry count.
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount != 0)
                viewBinding.topAppBar.setNavigationIcon(R.drawable.ic_back)
            else
                viewBinding.topAppBar.navigationIcon = null
        }
    }
}