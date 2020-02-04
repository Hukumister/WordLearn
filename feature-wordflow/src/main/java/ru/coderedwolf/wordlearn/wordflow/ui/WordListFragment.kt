package ru.coderedwolf.wordlearn.wordflow.ui

import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckBox
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_word_list.*
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.wordflow.R

/**
 * @author HaronCode.
 */
class WordListFragment : BaseFragment(R.layout.fragment_word_list) {

    private val wordPreviewAdapter = GroupAdapter<GroupieViewHolder>()

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
            adapter = wordPreviewAdapter
            setHasFixedSize(true)
        }
    }

}
