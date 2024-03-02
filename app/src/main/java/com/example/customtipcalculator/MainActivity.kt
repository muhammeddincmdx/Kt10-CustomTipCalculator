package com.example.customtipcalculator

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customtipcalculator.ui.theme.CustomTipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculateApp()
                }
            }
        }
    }
}

@Composable
fun TipCalculateApp(){

    //1
    var amountInput by remember { mutableStateOf("") }
    var tipRatioInput by remember { mutableStateOf("") }
    var rounded by remember { mutableStateOf(false) }

    val tipRatio = tipRatioInput.toDoubleOrNull()?:0.0
    val amount = amountInput.toDoubleOrNull()?:0.0
    val  tip = CalculateTip(amount,tipRatio,rounded)
/*
val amout = amountInput.toDoubleOrNull()?:0.0:
Bu satır, amountInput değişkenindeki değeri bir Double değerine dönüştürür.
 Ancak, eğer dönüşüm başarısız olursa (toDoubleOrNull() null döndürürse),
  yerine 0.0 atanır
 */



    //3
    Column(modifier = Modifier
        .safeDrawingPadding()
        .statusBarsPadding()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 40.dp)
        ,horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.calculate_tip), fontWeight = FontWeight.Bold, fontSize = 28.sp,modifier = Modifier
            .padding(bottom = 16.dp, top = 40.dp)
            .align(alignment = Alignment.CenterHorizontally))

        //4
        EditNumberField(
            label = R.string.bill_amount,//Bill Amount
            iconImage = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, //numara klavyesinin açılmasını sağlar
                imeAction = ImeAction.Next // entera basınca sonraki textfielde geçer
            ),
            value =amountInput,
            onValueChanged = {amountInput=it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxSize()
        )

        //5
        EditNumberField(
            label = R.string.how_was_the_service,//Tip Percentage
            iconImage = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done // entere basınca biter
            ),
            value =tipRatioInput,
            onValueChanged = {tipRatioInput=it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxSize()
        )

        //7
        RoundedRow(rounded = rounded, onRoundedChanged = {rounded=it},modifier = Modifier.padding(bottom = 10.dp))
        Text(text = stringResource(id = R.string.tip_amount,tip),
            style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(150.dp))
    }

}




//6
@Composable
fun RoundedRow(rounded:Boolean, onRoundedChanged:(Boolean)->Unit,modifier :Modifier=Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .size(48.dp), verticalAlignment = Alignment.CenterVertically){
        Text(text= stringResource(id = R.string.round_up_tip))
        Switch(checked = rounded, onCheckedChange = onRoundedChanged,modifier= modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End))
    }

}




//3

@Composable
fun EditNumberField(
    @StringRes label : Int,
    @DrawableRes iconImage :Int,
    keyboardOptions: KeyboardOptions,
    value : String,
    onValueChanged: (String) ->Unit,
    modifier: Modifier= Modifier
){
    TextField(value = value,
        onValueChange =onValueChanged,
        singleLine = true,
        leadingIcon= { Icon(painter = painterResource(id = iconImage),null) },
        modifier = modifier,
        label = {Text(stringResource(id = label))},
        keyboardOptions=keyboardOptions)

}




//2

//string bir ifade döndürecek
private fun CalculateTip(amount:Double,tipRatio:Double=15.0,rounded:Boolean):String{
    var tip = tipRatio/100*amount
    if(rounded){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipPreview() {
    CustomTipCalculatorTheme {
        TipCalculateApp()
    }
}