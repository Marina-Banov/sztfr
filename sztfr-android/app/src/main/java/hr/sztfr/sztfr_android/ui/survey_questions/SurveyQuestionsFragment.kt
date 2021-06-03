package hr.sztfr.sztfr_android.ui.survey_questions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.Question
import hr.sztfr.sztfr_android.data.model.Questions
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.databinding.*
import hr.sztfr.sztfr_android.ui.survey.SurveyPagerAdapter
import hr.sztfr.sztfr_android.ui.survey.SurveyViewModel
import hr.sztfr.sztfr_android.ui.survey_details.SurveyDetailsFragmentDirections
import java.util.*
import kotlin.collections.ArrayList

class SurveyQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentSurveyQuestionsBinding
    private var answers = hashMapOf<String, Any>()
    private var firestore = FirebaseFirestore.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val application = requireActivity().application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_questions, container, false)
        binding.lifecycleOwner = this

        val questions =SurveyQuestionsFragmentArgs.fromBundle(requireArguments()).questions
        val surveyModel = SurveyQuestionsFragmentArgs.fromBundle(requireArguments()).surveyModel

        val surveyTitle = binding.surveyTitle
        surveyTitle.text = surveyModel.title
        val questionList = binding.questionsList

        for(i in questions){
            when(i.type){
                "single-choice" -> {
                    var view = generateRadioGroupCard(i)
                    questionList.addView(view)
                }
                "multiple-choice" -> {
                    var view = generateCheckboxCard(i)
                    questionList.addView(view)
                }
                else -> {
                    var view = generateInputTextCard(i)
                    questionList.addView(view)
                }
            }

        }

        binding.sendAnswersButton.setOnClickListener {
            if (validateAnswers()) saveAnswersAndRedirect(surveyModel)
            else Toast.makeText(this.context, "Svako pitanje mora biti ispunjeno!", Toast.LENGTH_LONG).show()

        }

        binding.returnButton.setOnClickListener {
            requireActivity().onBackPressed()
        }


        return binding.root
    }

    private fun validateAnswers() : Boolean{
        for(i in answers){
            if(i.value is String && i.value == "") return false
            if(i.value is MutableList<*> && (i.value as MutableList<*>).isEmpty()) return false
        }
        return true
    }

    private fun saveAnswersAndRedirect(surveyModel: SurveyModel){
        firestore.collection("surveys").document(surveyModel.documentId)
            .collection("results").add(answers)
            .addOnSuccessListener {
                findNavController().navigate(
                    SurveyQuestionsFragmentDirections.actionSurveyQuestionsFragmentToSurveyAnswersSubmittedFragment(surveyModel)

                )
            }
            .addOnFailureListener {
                Log.d("SurveyQuestionsFragment", it.toString())
                Toast.makeText(this.context, "Neuspješan unos odgovora, probajte ponovo", Toast.LENGTH_LONG).show()
            }
    }

    private fun generateInputTextCard(q: Question): View {
        val binding = LayoutCardInputTextBinding.inflate(LayoutInflater.from(context))
        binding.question = q
        answers.put(q.order, "")
        var inputText = binding.inputText
        inputText.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                answers.put(q.order, text.toString())
            }
        }

        return binding.root
    }
    private fun generateCheckboxCard(q: Question): View {
        val binding = LayoutCardCheckboxBinding.inflate(LayoutInflater.from(context))
        binding.question = q
        answers.put(q.order, mutableListOf<String>())
        var checkboxContainer = binding.checkboxContainer
        for (item in q.choices){
            var checkBox = CheckBox(checkboxContainer.context)
            checkBox.setText(item)
            checkboxContainer.addView(checkBox)

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    var list = answers[q.order] as MutableList<String>
                    list.add(buttonView.text.toString())
                    answers.put(q.order, list)
                }else{
                    var list = answers[q.order] as MutableList<String>
                    if (list.contains(buttonView.text.toString())) list.remove(buttonView.text.toString())
                    answers.put(q.order, list)
                }
            }
        }
        return binding.root
    }

    private fun generateRadioGroupCard(q: Question): View {
        val binding = LayoutCardRadiobuttonBinding.inflate(LayoutInflater.from(context))

        binding.question = q
        val radioGroup = binding.radioGroup
        var checked = false
        for (item in q.choices){

            var radioButton = RadioButton(radioGroup.context)
            radioButton.setText(item)
            radioGroup.addView(radioButton)
            if(!checked){
                radioButton.isChecked = true
                checked = true
                answers.put(q.order, radioButton.text)
            }
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            val radio:RadioButton = group.findViewById(checkedId)
            answers.put(q.order, radio.text)

        })

        return binding.root
    }



}
