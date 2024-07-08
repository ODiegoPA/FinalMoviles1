import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.Filter

class FilterMenuViewModel : ViewModel() {
    private val _filter = MutableLiveData<Filter>()
    val filter: LiveData<Filter> get() = _filter

    fun applyFilters(city: String, date: String, time: String) {
        _filter.value = Filter(city, date, time)
    }
}
