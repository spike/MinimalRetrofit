package com.github.spike.minimalretrofit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.github.spike.minimalretrofit.ui.theme.MinimalRetrofitTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinimalRetrofitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ListComposable()
                }
            }
        }
    }
    @Composable
    fun OneRow(data:UserDataItem){
        Card(modifier= Modifier
            .fillMaxWidth()
            .padding(4.dp),
            shape = RoundedCornerShape(CornerSize(12.dp)),
            elevation = 2.dp
        ) {
            Row(modifier=Modifier.fillMaxWidth()) {
                Image(painter = rememberImagePainter(data.avatar_url), contentDescription ="image",
                    modifier= Modifier
                        .padding(4.dp)
                        .size(152.dp)
                        .clip(RoundedCornerShape(CornerSize(12.dp)))

                )
                Text(text=data.login, fontWeight = FontWeight.Bold, fontSize = 30.sp,textAlign= TextAlign.Center,
                    modifier= Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                        .clickable {
                            Toast
                                .makeText(
                                    applicationContext,
                                    "Clicked on " + data.login,
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                )

            }
        }

    }
    @Composable
    fun ListComposable() {
        val users =UserDataInstance.getUserData.getUserInfo()
        val data =remember { mutableStateOf<UserData>(UserData())}
        users.enqueue(object: Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                val userData=response.body()
                if (userData!=null )
                {
                    data.value=userData
                }
            }
            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Toast.makeText(applicationContext,t.toString(),Toast.LENGTH_SHORT ).show()
            }
        })
        LazyColumn{
            items(data.value){
                    data-> OneRow(data = data)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MinimalRetrofitTheme {
            ListComposable()
        }
    }
}
