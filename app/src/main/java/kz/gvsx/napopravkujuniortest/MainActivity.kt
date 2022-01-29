package kz.gvsx.napopravkujuniortest

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kz.gvsx.napopravkujuniortest.databinding.MainActivityBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val viewBinding: MainActivityBinding by viewBinding()

    fun enableToolbarNavigationIcon() {
        viewBinding.topAppBar.setNavigationIcon(R.drawable.ic_back)
        viewBinding.topAppBar.setNavigationOnClickListener {
            viewBinding.topAppBar.navigationIcon = null
            supportFragmentManager.popBackStack()
        }
    }
}