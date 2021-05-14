package hr.sztfr.sztfr_android.ui.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyWebViewBinding


class SurveyWebViewFragment : Fragment() {

    private lateinit var binding: FragmentSurveyWebViewBinding
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_web_view, container, false)
        webView = binding.webview
        val url = SurveyWebViewFragmentArgs.fromBundle(requireArguments()).surveyUrl
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.requestFocus()
        webView.loadUrl(url)
        return binding.root
    }


}