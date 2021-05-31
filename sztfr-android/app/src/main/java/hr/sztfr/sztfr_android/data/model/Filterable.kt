package hr.sztfr.sztfr_android.data.model

interface Filterable {
    val documentId: String
    val tags: List<String>
    val title: String
}