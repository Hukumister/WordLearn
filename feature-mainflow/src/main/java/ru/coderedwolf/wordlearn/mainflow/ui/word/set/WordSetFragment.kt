package ru.coderedwolf.wordlearn.mainflow.ui.word.set

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_word_set.*
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.mainflow.R

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class WordSetFragment : BaseFragment() {

    companion object {

        fun newInstance() = WordSetFragment()
    }

    override val layoutRes = R.layout.fragment_word_set

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordSetPagerAdapter = WordSetPagerAdapter(requireContext(), childFragmentManager)
        viewPager.adapter = wordSetPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

}