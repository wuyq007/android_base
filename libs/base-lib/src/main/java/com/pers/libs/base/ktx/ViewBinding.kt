package com.pers.libs.base.ktx

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * Activity
 */
inline fun <reified VB : ViewBinding> ComponentActivity.binding(crossinline inflate: (LayoutInflater) -> VB) =
    lazy(LazyThreadSafetyMode.NONE) {
        inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
            if (binding is ViewDataBinding) binding.lifecycleOwner = this
        }
    }


/**
 * Fragment
 */
fun <VB : ViewBinding> Fragment.binding(bind: (View) -> VB) = FragmentBindingDelegate(bind)


/**
 * Dialog
 */
inline fun <reified VB : ViewBinding> Dialog.binding(crossinline inflate: (LayoutInflater) -> VB) = lazy {
    inflate(layoutInflater).also { setContentView(it.root) }
}


/**
 * ViewGroup
 */
inline fun <reified VB : ViewBinding> ViewGroup.binding(
    crossinline inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
): Lazy<VB> {
    return lazy(LazyThreadSafetyMode.NONE) {
        inflate(LayoutInflater.from(context), this, true)
    }
}



class FragmentBindingDelegate<VB : ViewBinding>(private val bind: (View) -> VB) : ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        binding = try {
            thisRef.requireView().getBinding(bind).also { binding ->
                if (binding is ViewDataBinding) binding.lifecycleOwner = thisRef.viewLifecycleOwner
            }
        } catch (e: IllegalStateException) {
            throw IllegalStateException("The property of ${property.name} has been destroyed.")
        }
        thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                //fragment销毁时置为null,避免内存泄露
                binding = null
            }
        })

        return binding!!
    }
}

@Suppress("UNCHECKED_CAST")
private fun <VB : ViewBinding> View.getBinding(bind: (View) -> VB): VB = bind(this)


///**
// * 示例代码
// */
//class TestFragment : Fragment(R.layout.activity_base_test) {
//    private val binding by binding(ActivityBaseTestBinding::bind)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.textView.text = "测试"
//    }
//}


///**
// * 示例代码
// */
//class TestActivity : AppCompatActivity() {
//
//    //绑定(不使用反射)
//    private val binding by binding(ActivityBaseTestBinding::inflate)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding.textView.text = "测试"
//    }
//}



