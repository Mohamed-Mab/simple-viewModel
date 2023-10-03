package com.example.tryingthings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tryingthings.ui.theme.TryingThingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TryingThingsTheme { // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    HelloScreen()
                }
            }
        }
    }
}

class HelloViewModel : ViewModel() {
    private val _name = MutableLiveData("")
    private val _count = MutableLiveData(0)
    val count : LiveData<Int> = _count
    val name : LiveData<String> = _name
    fun onNameChange(newName : String) {
        _name.value = newName
    }
    fun onButtonClicked(){
        _count.value = _count.value?.plus(1)
    }
}
@Composable
fun HelloScreen(helloViewModel : HelloViewModel = viewModel()) {
    val name by helloViewModel.name.observeAsState("")
    val count by helloViewModel.count.observeAsState(0)
    HelloContent(count,name = name, onNameChange = { helloViewModel.onNameChange(it) },
        onButtonClicked
    = {helloViewModel.onButtonClicked()})


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloContent(count:Int,name : String, onNameChange : (String) -> Unit,onButtonClicked:()->Unit) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello! $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
        Button(onClick = onButtonClicked) {
            Text(text = "count $count")
        }
    }
}
