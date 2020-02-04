package ru.haroncode.wordlearn.wordflow.ui

import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckBox
import kotlinx.android.synthetic.main.fragment_word_list.*
import ru.haroncode.wordlearn.common.ui.BaseFragment
import ru.haroncode.wordlearn.wordflow.R

/**
 * @author HaronCode.
 */
class WordListFragment : BaseFragment(R.layout.fragment_word_list) {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { }
        toolbar.inflateMenu(R.menu.menu_check_box)
        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_learn) {
                val checkBox = item.actionView
                    .findViewById<AppCompatCheckBox>(R.id.checkBox)
                    ?: return@setOnMenuItemClickListener false
                true
            } else {
                false
            }
        }
        wordPreviewList.apply {
            setHasFixedSize(true)
        }
    }

}
