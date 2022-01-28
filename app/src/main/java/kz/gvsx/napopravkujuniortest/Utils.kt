package kz.gvsx.napopravkujuniortest

import android.content.res.Resources

inline val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()