package com.example.moodtracker.moodtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoodTestQuestionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoodTestQuestionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var questionCount = 1;

    private lateinit var progressBar: ProgressBar
    private lateinit var positiveButton: Button
    private lateinit var negativeButton: Button
    private lateinit var questionsTextView: TextView
    private lateinit var imageView: ImageView

    private val answersMap : MutableMap<Int, Boolean> by lazy {
        mutableMapOf()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_test_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseViews(view)
        setClickListenersAndStoreResults()
    }

    private fun initialiseViews(view: View) {
        progressBar = view.findViewById(R.id.determinateBar)
        positiveButton = view.findViewById(R.id.btn_positive)
        negativeButton = view.findViewById(R.id.btn_negative)
        questionsTextView = view.findViewById(R.id.question_text_view)
        imageView = view.findViewById(R.id.image_view)
    }

    private fun setClickListenersAndStoreResults() {
        positiveButton.setOnClickListener {
            answersMap[questionCount] = true
            questionCount++
            setQuestionsData()
        }

        negativeButton.setOnClickListener {
            answersMap[questionCount] = false
            questionCount++
            setQuestionsData()
        }
    }

    private fun setQuestionsData() {
        when(questionCount){
            1 -> setData(R.string.mood_test_question_one, R.drawable.pleasure, 1)
            2 -> setData(R.string.mood_test_question_two, R.drawable.weight, 2)
            3 -> setData(R.string.mood_test_question_three, R.drawable.fears, 3)
            4 -> setData(R.string.mood_test_question_four, R.drawable.eating, 4)
            5 -> setData(R.string.mood_test_question_five, R.drawable.worry, 5)
            6 -> navigateToResultFragment()
        }
    }

    private fun navigateToResultFragment() {
        var happinessCount = 0
        // 0, 5
        // 0 -> Very sad
        // 1 -> sad
        // 2 -> slightly sad
        // 3 -> neutral, not very sad not very happy
        // 4 -> slightly happy
        // 5 -> happy, joyful and content with his/her life

        answersMap.forEach{ (_, value) ->
            if(!value) {
                happinessCount++
            }
        }
//        val bundle = bundleOf("happinessCount" to happinessCount)
        val directions : NavDirections = MoodTestQuestionsFragmentDirections.actionMoodTestQuestionsFragmentToResultFragment(happinessCount = happinessCount)

        findNavController().navigate(directions)

    }

    private fun setData(
            @StringRes questionsTextRes: Int,
            @DrawableRes imageResource: Int,
            progress: Int
    ) {
        questionsTextView.text = activity?.getString(questionsTextRes)
        imageView.setImageResource(imageResource)
        progressBar.progress = (progress - 1) * 20
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoodTestQuestionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MoodTestQuestionsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}