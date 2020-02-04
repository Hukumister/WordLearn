package ru.haroncode.wordlearn.mainflow.ui.word.set

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_word_set.*
import ru.haroncode.wordlearn.common.ui.BaseFragment
import ru.haroncode.wordlearn.mainflow.R

/**
 * @author HaronCode.
 */
class WordSetFragment : BaseFragment(R.layout.fragment_word_set) {

    companion object {

        fun newInstance() = WordSetFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordSetPagerAdapter = WordSetPagerAdapter(requireContext(), childFragmentManager)
        viewPager.adapter = wordSetPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
